package com.intersys.example.restaurantscore.simple.datageneration;


import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import com.intersys.example.restaurantscore.simple.datageneration.Restaurant;

import static org.junit.Assert.assertEquals;

/** Unit tests the Restaurant class.
 */
public class Restaurant_UnitTest {
    
    /** A helper for the unit tests. */
    Restaurant testRestaurant;
    
    /** Sets up for the unit tests by initializing the helper members. Also
     * seeds the random number generator in the helper instance.
     */
    @Before
    public void setUp() {
        testRestaurant = new Restaurant(2);
        testRestaurant.reSeedGenerator(12345);
    }//Close setUp.
    
    /** Tears down after the unit tests by setting the helper members to null.
     */
    @After
    public void tearDown() {
        testRestaurant = null;
    }//Close tearDown.
    
    /** Tests that the getScore method returns the correct value.
     */
    @Test
    public void getScore_test1() {
        //Set up the truth.
        double truth = 2.0;
        
        //Assert equality.
        assertEquals(truth, testRestaurant.getScore(), 1e-14);
    }//Close getScore_test1.
    
    /** Tests that the getCheck method returns the correct value.
     */
    @Test
    public void getCheck_test1() {
        //Set up the truth.
        double truth = 90.49;
        
        //Obtain the answer.
        double answer = testRestaurant.getCheck();
        
        //Assert equality.
        assertEquals(truth, answer, 1e-14);
    }//Close getCheck_test1.
}//Close Restaurant_UnitTest.