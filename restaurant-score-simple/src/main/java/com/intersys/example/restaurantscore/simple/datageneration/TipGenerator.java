package com.intersys.example.restaurantscore.simple.datageneration;

import com.intersys.example.restaurantscore.simple.datageneration.Restaurant;
import com.intersys.example.restaurantscore.simple.datageneration.Person;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.random.MersenneTwister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Arrays;

/** This class generates the records from a list of people and restaurants as 
 * an iterable generator.<p>
 * 
 * This class has a little more bookkeeping than I'd like - it might be better
 * to refactor the package a little bit so that the instances of Person track
 * their own numVisits, rather than the external hash map here. It would
 * look something like {@code Tuple<Double, Double> visit(Restaurant r)},
 * where the tuple is a holder class for the check,tip values.
 * 
 * Also, UUIDs for restaurants and people are a little more robust than using
 * the indices of the original arrays, but that might be overkill.
 */
public class TipGenerator implements Iterable<String> {
    
    /** Creates an instance using the available people, restaurants, and 
     * provided generator.
     * 
     * @param people The people for the simulation.
     * @param restaurants The restaurants for the simulation.
     * @param generator The generator for the simulation.
     */
    public TipGenerator(Person[] people, Restaurant[] restaurants,
        RandomDataGenerator generator) {
        
        this.generator = generator;
        this.people = new ArrayList<Person>(Arrays.asList(people));
        this.restaurants = restaurants;
        //This procedure is a little inefficient because it loops over the
        //persons array three times, but it's only called at construction time
        //so might not be much of a bottleneck. It's written this way to make
        //it easier to test. It might be better to use the scheme in the
        //class javadoc above in the future, if we need to.
        this.visitsRemaining = computeVisitsRemaining(people);
        this.personIds = computePersonIds(people);
        this.totalVisitsRemaining = computeTotalVisitsRemaining(people);
    }//Close constructor.
    
    /** Creates an instance with a new random generator based on the Mersenne 
     * Twister.
     * 
     * @param people The people for the simulation.
     * @param restaurants The restaurants for the simulation.
     */
    public TipGenerator(Person[] people, Restaurant[] restaurants) {
        this(people, restaurants, 
            new RandomDataGenerator(new MersenneTwister()));
    }//Close constructor.
    
    /** Obtains the total visits remaining. */
    public int getTotalVisitsRemaining() { return totalVisitsRemaining; }
    /** Obtains the generator. */
    public RandomDataGenerator getGenerator() { return generator; }
    
    /** Reseeds the random generator. */
    public void reSeedGenerator(long newSeed) { generator.reSeed(newSeed); }
    
    /** Creates an iterator over the records which acts as a generator for
     * the records. Each iteration produces a new record until there are no
     * more available visits.
     * 
     * @return An iterator over the records.
     */
    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            @Override
            public boolean hasNext() { return totalVisitsRemaining != 0; }
            
            @Override
            public String next() { 
                return makeRecord();
            }//Close next.
            
            @Override
            public void remove() {
                ;
            }//Close remove.
        };
    }//Close iterator.
    
    /** Creates a record by drawing a person (who has visits remaining), and
     * restaurant and computing the check and tip.
     * 
     * NOTE: This method has a lot of side effects on the helper members that 
     * I'm not a huge fan of, so this is worth refactoring in the future.
     * See the class javadoc above for a better refactoring of the formulation.
     * 
     * NOTE: If a person has 0 visits (a possibility), then they still get a 
     * visit before they leave the simulation.
     * 
     * @return A record of the form "personID,restaurantID,check,tip" where
     * the tip is in dollars.
     */
    String makeRecord() {
        
        //Select the person and the restaurant.
        //Subtract 1 because endpoints are included in nextInt.
        //If people.size() or restaurants.length is equal to 1, use zero.
        int personIndex = 
            people.size() > 1 ? generator.nextInt(0, people.size()-1) : 0;
        int restaurantIndex = 
            restaurants.length > 1 ? 
                generator.nextInt(0, restaurants.length-1) : 0;
        Person person = people.get(personIndex);
        Restaurant restaurant = restaurants[restaurantIndex];
        
        //Obtain the check.
        double check = restaurant.getCheck();
        //Obtain the tip (in dollars).
        double tip = Math.floor(
            check * person.getTipPercentage(restaurant) * 100) / 100;
        
        //Create the record with a StringBuilder.
        String record = new StringBuilder()
            .append(personIds.get(person)).append(",")
            .append(restaurantIndex).append(",")
            .append(check).append(",")
            .append(tip).toString();
            
        //Update the helpers.
        visitsRemaining.put(person, visitsRemaining.get(person)-1);
        --totalVisitsRemaining;
        
        //If there are no more visits remaining for this person, remove them. 
        if(visitsRemaining.get(person) <= 0) {
            visitsRemaining.remove(person);
            personIds.remove(person);
            people.remove(personIndex); //Faster for ArrayList, I think.
        }//Close if statement on no more visits remaining for user.
        
        //Write the record.
        return record;
    }//Close makeRecord.
    
    /** A helper method that creates a hash map that hashes people to their
     * numResetaurants value.
     * 
     * @param people The people for the simulation.
     * @return A HashMap hashing people to their numRestaurants member.
     */
    HashMap<Person, Integer> computeVisitsRemaining(Person[] people) {
        HashMap<Person, Integer> visitsRemaining = 
            new HashMap<Person, Integer>();
        
        for(Person person: people) {
            visitsRemaining.put(person, person.getNumRestaurants());
        }//Close for loop over people (person).
        
        return visitsRemaining;
    }//Close computeVisitsRemaining.
    
    /** Hashes the person to their "ID" - which is their original position in 
     * the constructor-passed array.
     * 
     * @param people The original array of people.
     * @return A HashMap hashing the person to their original position in the
     * constructor passed array (as an "Id").
     */
    HashMap<Person, Integer> computePersonIds(Person[] people) {
        HashMap<Person, Integer> personIds = new HashMap<Person, Integer>();
        
        for(int ii=0; ii<people.length; ++ii) {
            personIds.put(people[ii], ii);
        }//Close for loop over people (ii).
        
        return personIds;
    }//Close computePersonIds.
    
    /** Sums the visits remaining for the people.
     * 
     * @param people The people for the simulation.
     * @return The total number of visits for the people.
     */
    int computeTotalVisitsRemaining(Person[] people) {
        
        int totalVisitsRemaining = 0;
        
        for(Person person: people) {
            totalVisitsRemaining += person.getNumRestaurants();
        }//Close for loop over people (person).
        
        return totalVisitsRemaining;
    }//Close computeTotalVisitsRemaining.
    
    /** The random generator for this class. */
    final RandomDataGenerator generator;
    /** Hashes the person to the number of visits remaining. */
    final HashMap<Person, Integer> visitsRemaining;
    /** Hashes the person to their ID. This makes the initial indices
     * persistent as the people ArrayList mutates when people have
     * "spent" their visits.
     */
    final HashMap<Person, Integer> personIds;
    /** A list of people who have visits remaining. People are removed when they
     * don't have any more visits remaining.
     */
    final ArrayList<Person> people;
    /** The list of available restaurants. Thankfully, this one doesn't mutate.
     */
    private final Restaurant[] restaurants;
    /** The number of available visits remaining. */
    private int totalVisitsRemaining;
}//Close TipGenerator.