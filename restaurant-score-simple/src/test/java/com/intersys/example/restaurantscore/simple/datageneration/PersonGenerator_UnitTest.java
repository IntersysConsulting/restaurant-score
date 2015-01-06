package com.intersys.example.restaurantscore.simple.datageneration;


import org.apache.commons.math3.random.RandomDataGenerator;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import com.intersys.example.restaurantscore.simple.datageneration.Person;
import com.intersys.example.restaurantscore.simple.datageneration.PersonGenerator;

import static org.junit.Assert.assertEquals;

/** Unit tests the PersonGenerator class.
 */
public class PersonGenerator_UnitTest {
    
    /** A helper member for the unit tests. */
    PersonGenerator testPersonGenerator;
    
    /** Sets up for the unit tests by initializing the helper members and
     * sets the random seed.
     */
    @Before
    public void setUp() {
        testPersonGenerator = new PersonGenerator(0.15, 8.75);
        testPersonGenerator.reSeedGenerator(12345);
    }//Close setUp.
    
    /** Tears down after the unit tests by setting the helper members to null.
     */
    @After
    public void tearDown() {
        testPersonGenerator = null;
    }//Close tearDown.
    
    /** Tests that the constructor throws an IllegalArgumentException when the
     * mean number of visits is less than or equal to zero.
     */
    @Test(expected=IllegalArgumentException.class)
    public void constructor_test1() {
        //Call the method that will throw the exception.
        new PersonGenerator(0.15, 0.0);
    }//Close constructor_test1.
    
    /** Tests that the constructor throws an IllegalArgumentException when the
     * mean base tip percentage is less than zero.
     */
    @Test(expected=IllegalArgumentException.class)
    public void constructor_test2() {
        //Call the method that will throw the exception.
        new PersonGenerator(-0.15, 8.75);
    }//Close constructor_test2.
    
    /** Tests that the generate method throws an IllegalArgumentException
     * when the numPeople argument is not greater than zero.
     */
    @Test(expected=IllegalArgumentException.class)
    public void generate_test1() {
        //Call the method that will throw the exception.
        testPersonGenerator.generate(-1);
    }//Close generate_test1.
    
    /** Tests that the generate method returns the correct value.
     */
    @Test
    public void generate_test2() {
        //Set up the truth.
        double[] truth_baseTips = 
        {
            0.17842794505572630,
            0.12593294494858093,
            0.16961176873542297
        };
        
        int[] truth_numVisits = {16, 9, 4};
        
        RandomDataGenerator truth_generator = 
            testPersonGenerator.getGenerator();    
        
        //Obtain the answer.
        Person[] answer = testPersonGenerator.generate(3);
        
        //Assert equality.
        assertEquals(3, answer.length);
        for(int ii=0; ii<answer.length; ++ii) {
            assertEquals(truth_baseTips[ii], 
                answer[ii].getBaseTipPercentage(), 1e-14);
            assertEquals(truth_numVisits[ii], answer[ii].getNumRestaurants());
            assertEquals(truth_generator, answer[ii].getGenerator());
        }//Close for loop over answer (ii).
    }//Close generate_test2.
    
    /** Tests that the getMeanVisits method returns the correct value.
     */
    @Test
    public void getMeanVisits_test1() {
        //Set up the truth.
        double truth = 8.75;
        
        //Assert equality.
        assertEquals(truth, testPersonGenerator.getMeanVisits(), 1e-14);
    }//Close getMeanVisits_test1.
    
    /** Tests that the getMeanBaseTipPercentage method returns the correct
     * value.
     */
    @Test
    public void getMeanBaseTipPercentage_test1() {
        //Set up the truth.
        double truth = 0.15;
        
        //Assert equality.
        assertEquals(truth, testPersonGenerator.getMeanBaseTipPercentage(), 
            1e-14);
    }//Close getMeanBaseTipPercentage_test1.
    
    /** Tests that the getStdBaseTipPercentage method returns the correct value.
     */
    @Test
    public void getStdBaseTipPercentage_test1() {
        //Set up the truth.
        double truth = 0.04;
        
        //Assert equality.
        assertEquals(truth, testPersonGenerator.getStdBaseTipPercentage(),
            1e-14);
    }//Close getStdBaseTipPercentage_test1.
    
    /** Tests that the getGenerator method returns the correct value.
     */
    @Test
    public void getGenerator_test1() {
        //Set up a new generator.
        RandomDataGenerator newGenerator = new RandomDataGenerator();
        
        //Create the instance.
        PersonGenerator newPersonGenerator = 
            new PersonGenerator(0.15, 8.5, newGenerator);
        
        //Assert equality.
        assertEquals(newGenerator, newPersonGenerator.getGenerator());
    }//Close getGenerator_test1.
        
}//Close PersonGenerator_UnitTest.