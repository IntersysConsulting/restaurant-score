package com.intersys.example.restaurantscore.simple.datageneration;


import org.apache.commons.math3.random.RandomDataGenerator;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import com.intersys.example.restaurantscore.simple.datageneration.Person;
import com.intersys.example.restaurantscore.simple.datageneration.Restaurant;

import static org.junit.Assert.assertEquals;

/** Unit tests the Person class.
 */
public class Person_UnitTest {
    
    /** A helper for the unit tests.*/
    Person testPerson;
    
    /** A helper for the unit tests. */
    Restaurant testRestaurant;
    
    /** Sets up for the unit tests by initializing the helper members and 
     * setting the random number generator seed.
     */
    @Before
    public void setUp() {
        testPerson = new Person(0.15, 5);
        testPerson.reSeedGenerator(12345);
        testRestaurant = new Restaurant(1.25);
    }//Close setUp.
    
    /** Tears down after the unit tests by setting the helper members to null.
     */
    @After
    public void tearDown() {
        testPerson = null;
        testRestaurant = null;
    }//Close tearDown.
    
    /** Tests that the getBaseTipPercentage method returns the correct value.
     */
    @Test
    public void getBaseTipPercentage_test1() {
        //Set up the truth.
        double truth = 0.15;
        
        //Assert equality.
        assertEquals(truth, testPerson.getBaseTipPercentage(), 1e-14);
    }//Close getBaseTipPercentage_test1.
    
    /** Tests that the getNumRestaurants returns the correct value.
     */
    @Test
    public void getNumRestaurants_test1() {
        //Set up the truth.
        int truth = 5;
        
        //Assert equality.
        assertEquals(truth, testPerson.getNumRestaurants());
    }//Close getNumRestaurants_test1.
    
    /** Tests that the getGenerator method returns the correct value.
     */
    @Test
    public void getGenerator_test1() {
        //Set up the inputs / truth.
        RandomDataGenerator truth = new RandomDataGenerator();
        
        //Create a new instance with this random generator.
        Person newPerson = new Person(0.15, 5, truth);
        
        //Assert equality.
        assertEquals(truth, newPerson.getGenerator());
    }//Close getGenerator_test1.
    
    /** Tests that the getTipPercentage method returns the correct value.
     */
    @Test
    public void getTipPercentage_test1() {
        //Set up the truth.
        double truth = 0.19;
        
        //Obtain the answer.
        double answer = testPerson.getTipPercentage(testRestaurant);
        
        //Assert equality.
        assertEquals(truth, answer, 1e-14);
    }//Close getTipPercentage_test1.
}//Close Person_UnitTest.
