package com.intersys.example.restaurantscore.simple.datageneration;


import org.apache.commons.math3.random.RandomDataGenerator;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import com.intersys.example.restaurantscore.simple.datageneration.Restaurant;
import com.intersys.example.restaurantscore.simple
    .datageneration.RestaurantGenerator;

import static org.junit.Assert.assertEquals;

/** Unit tests the RestaurantGenerator class.
 */
public class RestaurantGenerator_UnitTest {
    
    /** A helper for the unit tests. */
    RestaurantGenerator testRestaurantGenerator;
    
    /** Sets up for the unit tests by initializing the helper members and 
     * setting the random number generator seeds.
     */
    @Before
    public void setUp() {
        testRestaurantGenerator = new RestaurantGenerator();
        testRestaurantGenerator.reSeedGenerator(12345);
    }//Close setUp.
    
    /** Tears down after unit tests by setting the helper members to null.
     */
    @After
    public void tearDown() {
        testRestaurantGenerator = null;
    }//Close tearDown.
    
    /** Tests that the generate method throws an IllegalArgumentException when
     * the number of restaurants requested is not greater than zero.
     */
    @Test(expected=IllegalArgumentException.class)
    public void generate_test1() {
        //Call the method that will throw the exception.
        testRestaurantGenerator.generate(0);
    }//Close generate_test1.
    
    /** Tests that the generate method returns the correct value.
     */
    @Test
    public void generate_test2() {
        //Set up the truth.
        double[] truth_score = 
        {
            1.1421397252786314,
            0.8796647247429047,
            0.8420122488976501,
            1.0784517621221874,
            1.1153538793375286
        };
        
        RandomDataGenerator truth_generator = 
            testRestaurantGenerator.getGenerator();
        
        //Obtain the answer.
        Restaurant[] answer = testRestaurantGenerator.generate(5);
        
        //Assert equality.
        assertEquals(truth_score.length, answer.length);
        for(int ii=0; ii<answer.length; ++ii) {
            assertEquals(truth_score[ii], answer[ii].getScore(), 1e-14);
            assertEquals(truth_generator, answer[ii].getGenerator());
        }//Close for loop over answer (ii).
    }//Close generate_test2.
    
    /** Tests that the getMeanScore method returns the correct value.
     */
    @Test
    public void getMeanScore_test1() {
        //Set up the truth.
        double truth = 1.0;
        
        //Assert equality.
        assertEquals(truth, testRestaurantGenerator.getMeanScore(), 1e-14);
    }//Close getMeanScore_test1.
    
    /** Tests that the getStdScore method returns the correct value.
     */
    @Test
    public void getStdScore_test1() {
        //Set up the truth.
        double truth = 0.2;
        
        //Assert equality.
        assertEquals(truth, testRestaurantGenerator.getStdScore(), 1e-14);
    }//Close getStdScore_test1.
    
    /** Tests that the getGenerator method returns the correct value.
     */
    @Test
    public void getGenerator_test1() {
        //Set up the truth.
        RandomDataGenerator truth = new RandomDataGenerator();
        
        //Create the instance.
        RestaurantGenerator newRestaurantGenerator = 
            new RestaurantGenerator(truth);
        
        //Assert equals.
        assertEquals(truth, newRestaurantGenerator.getGenerator());
    }//Close getGenerator_test1.
}//Close RestaurantGenerator_UnitTest.