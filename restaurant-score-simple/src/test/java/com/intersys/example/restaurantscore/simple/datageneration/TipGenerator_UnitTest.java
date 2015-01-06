package com.intersys.example.restaurantscore.simple.datageneration;

import com.intersys.example.restaurantscore.simple.datageneration.TipGenerator;
import com.intersys.example.restaurantscore.simple.datageneration.Person;
import com.intersys.example.restaurantscore.simple.datageneration.Restaurant;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.random.MersenneTwister;

import java.util.HashMap;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

/** Unit tests the TipGenerator class.
 */
public class TipGenerator_UnitTest { 
    
    /** A helper for the unit tests. */
    TipGenerator testTipGenerator;
    
    /** Sets up for the unit tests by initializing the helper members and seeds
     * the random number generator.
     */
    @Before
    public void setUp() {
        //Use the same random generator for all of the objects in the 
        //simulation.
        long seed = 12345;
        RandomDataGenerator generator = new RandomDataGenerator(
            new MersenneTwister(seed));
        
        //Create the people.
        Person[] people = 
        {
            new Person(0.15, 2, generator),
            new Person(0.12, 3, generator),
            new Person(0.17, 1, generator),
            new Person(0.10, 1, generator)
        };
        
        //Create the restaurants.
        Restaurant[] restaurants = 
        {
            new Restaurant(1.2, generator),
            new Restaurant(0.8, generator)
        };
        
        //Initialize the TipGenerator.
        testTipGenerator = new TipGenerator(people, restaurants, generator);
    }//Close setUp.
    
    /** Tears down after the unit tests by setting the helper members to null.
     */
    @After
    public void tearDown() {
        testTipGenerator = null;
    }//Close tearDown.
    
    /** Tests that full iteration produces the correct sequence of values.
     */
    @Test
    public void iteration_test1() {
        //Set up the truth.
        String[] truth = 
        {
            "3,0,70.09,7.7",
            "1,0,58.44,8.18",
            "2,1,98.42,14.76",
            "1,1,98.26,7.86",
            "0,1,91.71,10.08",
            "1,0,71.41,9.28",
            "0,0,49.53,7.92"
        };
        
        //Obtain the answer.
        ArrayList<String> answer = new ArrayList<String>();
        for(String record: testTipGenerator) {
            answer.add(record);
        }//Close for loop over testTipGenerator.
        
        //Assert equality.
        assertArrayEquals(truth, answer.toArray(new String[answer.size()]));
    }//Close iteration_test1.
    
    /** Tests that the makeRecord method functions correctly.
     */
    @Test
    public void makeRecord_test1() {
        //Set up the truth, including side effects.
        String truth_record = "3,0,70.09,7.7";
        
        HashMap<Person,Integer> truth_personIds = 
            new HashMap<Person, Integer>();
        truth_personIds.put(testTipGenerator.people.get(0),0);
        truth_personIds.put(testTipGenerator.people.get(1),1);
        truth_personIds.put(testTipGenerator.people.get(2),2);
        
        HashMap<Person,Integer> truth_visitsRemaining = 
            new HashMap<Person, Integer>();
        truth_visitsRemaining.put(testTipGenerator.people.get(0),2);
        truth_visitsRemaining.put(testTipGenerator.people.get(1),3);
        truth_visitsRemaining.put(testTipGenerator.people.get(2),1);
        
        ArrayList<Person> truth_people = new ArrayList<Person>();
        truth_people.add(testTipGenerator.people.get(0));
        truth_people.add(testTipGenerator.people.get(1));
        truth_people.add(testTipGenerator.people.get(2));
        
        int truth_totalVisitsRemaining = 6;
        
        //Obtain the answer.
        String answer = testTipGenerator.makeRecord();
        
        //Assert equality.
        assertEquals(truth_record, answer);
        assertEquals(truth_personIds, testTipGenerator.personIds);
        assertEquals(truth_visitsRemaining, testTipGenerator.visitsRemaining);
        assertEquals(truth_people, testTipGenerator.people);
        assertEquals(truth_totalVisitsRemaining, 
            testTipGenerator.getTotalVisitsRemaining());
    }//Close makeRecord_test1()
    
    /** Tests that the computeTotalVisitsRemaining method returns the correct
     * value.
     */
    @Test
    public void computeTotalVisitsRemaining_test1() {
        //Set up the inputs.
        Person[] people = 
        {
            new Person(0.15, 1),
            new Person(0.17, 2)
        };
        
        //Set up the truth.
        int truth = 3;
        
        //Assert equality.
        assertEquals(truth, 
            testTipGenerator.computeTotalVisitsRemaining(people));
    }//Close computeTotalVisitsRemaining_test1.
    
    /** Tests that the computePersonIds method returns the correct alue.
     */
    @Test
    public void computePersonIds() {
        //Set up the inputs.
        Person[] people = 
        {
            new Person(0.15, 1),
            new Person(0.14, 1)
        };
        
        //Set up the truth.
        HashMap<Person, Integer> truth = new HashMap<Person, Integer>();
        truth.put(people[0], 0);
        truth.put(people[1], 1);
        
        //Obtain the answer.
        HashMap<Person, Integer> answer = 
            testTipGenerator.computePersonIds(people);
        
        //Assert equality.
        assertEquals(truth, answer);
    }//Close computePersonIds.
    
    /** Tests that the computeVisitsRemaining method returns the correct value.
     */
    @Test
    public void computeVisitsRemaining_test1() {
        //Set up the inputs.
        Person[] people = 
        {
            new Person(0.15, 1),
            new Person(0.17, 2)
        };
        
        //Set up the truth.
        HashMap<Person, Integer> truth = new HashMap<Person, Integer>();
        truth.put(people[0],1);
        truth.put(people[1],2);
        
        //Obtain the answer.
        HashMap<Person, Integer> answer = 
            testTipGenerator.computeVisitsRemaining(people);
        
        //Assert equality.
        assertEquals(truth, answer);
    }//Close computeVisitsRemaining_test1.
    
    /** Tests that the getTotalVisitsRemaining method returns the correct value.
     */
    @Test
    public void getTotalVisitsRemaining_test1() {
        //Set up the truth.
        int truth = 7;
        
        //Assert equality.
        assertEquals(truth, testTipGenerator.getTotalVisitsRemaining());
    }//Close getTotalVisitsRemaining_test1.
    
    /** Tests that the getGenerator method returns the correct value.
     */
    @Test
    public void getGenerator_test1() {
        //Set up the inputs.
        RandomDataGenerator truth = new RandomDataGenerator();
        Person[] people = 
        {
            new Person(0.15, 1)
        };
        Restaurant[] restaurants = 
        {
            new Restaurant(1.3)
        };
        
        //Create an instance with this generator.
        TipGenerator newTipGenerator = 
            new TipGenerator(people, restaurants, truth);
        
        //Call the method.
        RandomDataGenerator answer = newTipGenerator.getGenerator();
        
        //Assert equality.
        assertEquals(truth, answer);
    }//Close getGenerator_test1.
}//Close TipGenerator_UnitTest.