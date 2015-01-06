package com.intersys.example.restaurantscore.simple.datageneration;


import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.random.MersenneTwister;

import com.intersys.example.restaurantscore.simple.datageneration.Person;

/** Generates people with configurable parameters.
 */
public class PersonGenerator {
    
    /** Creates an instance that has the provided random generator.
     * 
     * @param meanVisits The mean number of restaurant visits for the people.
     * @param meanBaseTipPercentage The mean base tip percentage for the people.
     * @param generator The random generator to use.
     */
    public PersonGenerator(double meanBaseTipPercentage, double meanVisits,
        RandomDataGenerator generator) {
        
        //Validate meanVisits.
        if(meanVisits <= 0.0) {
            throw new IllegalArgumentException("Mean number of visits must " +
                "be greater than zero.");
        }//Close if statement validating meanVisits.
        
        //Validate meanBaseTipPercentage.
        if(meanBaseTipPercentage < 0.0) {
            throw new IllegalArgumentException("Mean base tip percentage " +
                "be positive.");
        }//Close if statement validating meanBaseTipPercentage.
        
        this.meanVisits = meanVisits;
        this.meanBaseTipPercentage = meanBaseTipPercentage;
        this.generator = generator;
        
    }//Close constructor.
    
    /** Creates an instance that has it's own Mersenne Twister based random
     * generator.
     * 
     * @param meanVisits The mean number of restaurant visits for the people.
     * @param meanBaseTipPercentage The mean base tip percentage of people.
     */
    public PersonGenerator(double meanBaseTipPercentage, double meanVisits) {
        
        this(meanBaseTipPercentage, meanVisits,
            new RandomDataGenerator(new MersenneTwister()));
    }//Close constructor.
    
    /** Generates an array of Persons with properties generated using the
     * members of this class.
     * 
     * @param numPeople The number of people to generate.
     * @throws IllegalArgumentException If numPeople is less than or equal to
     * zero.
     * @return An array of Person objects.
     */
    public Person[] generate(int numPeople) {
        
        //Validate numPeople.
        if(numPeople <= 0) {
            throw new IllegalArgumentException("Number of people must be " +
                "greater than zero.");
        }//Close if statement validating numPeople.
        
        Person[] people = new Person[numPeople];
        
        //For each person, draw their base tip percentage from a Gaussian,
        //and their number of visits from a Poisson.
        for(int ii=0; ii<numPeople; ++ii) {
            double baseTipPercentage = generator.nextGaussian(
                meanBaseTipPercentage, stdBaseTipPercentage);
            //Truncate at zero.
            baseTipPercentage = 
                baseTipPercentage < 0.0 ? 0.0 : baseTipPercentage;
            
            int numVisits = (int) generator.nextPoisson(meanVisits);
            //TODO: The Gamma might be a better distribution to use here. It's
            //worth investigating.
            people[ii] = new Person(baseTipPercentage, numVisits, generator);
        }//Close for loop over numPeople.
        
        return people;
    }//Close generate method.
    
    /** Obtains the mean visits. */
    public double getMeanVisits() { return meanVisits; }
    /** Obtains the mean base tip percentage. */
    public double getMeanBaseTipPercentage() { return meanBaseTipPercentage; }
    /** Obtains the standard deviation of the base tip percentage. */
    public double getStdBaseTipPercentage() { return stdBaseTipPercentage; }
    /** Obtains the random generator. */
    public RandomDataGenerator getGenerator() { return generator; }
    
    /** Reseeds the random generator.
     * 
     * @param newSeed The new seed for the random generator.
     */
    public void reSeedGenerator(long newSeed) { generator.reSeed(newSeed); }
    
    /** The mean number of restaurant visits for the people. */
    private final double meanVisits;
    /** The mean base tip percentage for the people. */
    private final double meanBaseTipPercentage;
    /** The standard deviation for the mean base tip percentage. */
    private final double stdBaseTipPercentage = 0.04;
    /** The random number generator. */
    private final RandomDataGenerator generator;
}//Close PersonGenerator.