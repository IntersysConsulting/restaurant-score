package com.intersys.example.restaurantscore.simple;


import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import com.intersys.example.restaurantscore.simple.Quantile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

/** Unit tests the Quantile class.
 */
public class Quantile_UnitTest {
    
    /** A helper for the unit tests. */
    Quantile testQuantile;
    
    /** Sets up for the unit tests by initializing the helper members.
     */
    @Before
    public void setUp() {
        testQuantile = new Quantile(
            new double[] {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0},
        5);
    }//Close setUp.
    
    /** Tears down after the unit tests by setting helper members to null.
     */
    @After
    public void tearDown() {
        testQuantile = null;
    }//Close tearDown.
    
    /** Tests that the getIndices method returns the correct value.
     */
    @Test
    public void getIndices_test1() {
        //Set up the inputs.
        int numSamples = 10;
        int numQuantiles = 5;
        
        //Set up the truth.
        double[] truth = {0.0, 1.5, 3.5, 5.5, 7.5, 9.0};
        
        //Obtain the answer.
        double[] answer = Quantile.getIndices(numSamples, numQuantiles);
        
        //Assert equality.
        assertArrayEquals(truth, answer, 1e-14);
    }//Close getIndices_test1.
    
    /** Tests that the getNearestQuantile method returns the correct value when
     * the value is less than the minimum value of the dataset.
     */
    @Test
    public void getNearestQuantile_test1() {
        //Set up the inputs.
        double sample = 0.0;
        
        //Set up the truth.
        int truth = 0;
        
        //Assert equality.
        assertEquals(truth, testQuantile.getNearestQuantile(sample));
    }//Close getNearestQuantile_test1.
    
    /** Tests that the getNearestQuantile method returns the correct value 
     * when the value is within the min and max of the dataset.
     */
    @Test
    public void getNearestQuantile_test2() {
        //Set up the inputs.
        double sample = 4.7;
        
        //Set up the truth.
        int truth = 3;
        
        //Assert equality.
        assertEquals(truth, testQuantile.getNearestQuantile(sample));
    }//Close getNearestQuantile_test2.
    
    /** Tests that the getNearestQuantile method returns the correct value
     * when the value is greater than the max of the dataset.
     */
    @Test
    public void getNearestQuantile_test3() {
        //Set up the inputs.
        double sample = 13.2;
        
        //Set up the truth.
        int truth = 5;
        
        //Assert equality.
        assertEquals(truth, testQuantile.getNearestQuantile(sample));
    }//Close getNearestQuantile_test3.
    
    /** Tests that the getQuantileValues method returns the correct value.
     */
    @Test
    public void getQuantileValues_test1() {
        //Set up the inputs.
        double[] samples = 
            {1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0, 10.0};
        double[] indices = {0.0, 1.5, 3.5, 5.5, 7.5, 9.0};
        
        //Set up the truth.
        double[] truth = {1.0, 2.5, 4.5, 6.5, 8.5, 10.0};
        
        //Obtain the answer.
        double[] answer = Quantile.getQuantileValues(samples, indices);
        
        //Assert equality.
        assertArrayEquals(truth, answer, 1e-14);
    }//Close getQuantileValues.
    
    /** Tests that the getQuantileValues method returns the correct value.
     */
    @Test
    public void getQuantileValues_test2() {
        //Set up the truth.
        double[] truth = {1.0, 2.5, 4.5, 6.5, 8.5, 10.0};
        
        //Assert equality.
        assertArrayEquals(truth, testQuantile.getQuantileValues(), 1e-14);
    }//Close getQuantileValues_test2.
    
    /** Tests that the copyAndSort method returns the correct value.
     */
    @Test
    public void copyAndSort_test1() {
        
        //Set up the inputs.
        double[] array = {4.0, 2.5, 7.8, 0.3};
        
        //Set up the truth.
        double[] truth = {0.3, 2.5, 4.0, 7.8};
        double[] truth_original = {4.0, 2.5, 7.8, 0.3};
        
        //Obtain the answer.
        double[] answer = Quantile.copyAndSort(array);
        
        //Assert equality.
        assertArrayEquals(truth, answer, 1e-14);
        //Asserts that the original remains unchanged.
        assertArrayEquals(truth_original, array, 1e-14);
    }//Close copyAndSort_test1.
}//Close Quantile_UnitTest.