package com.intersys.example.restaurantscore.simple;


import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import com.intersys.example.restaurantscore.simple.TransactionTuple;

import static org.junit.Assert.assertEquals;

/** Unit tests the TransactionTuple class.
 */
public class TransactionTuple_UnitTest {
    
    /** A helper for the unit tests that is difficult to say ten time fast. */
    TransactionTuple testTransactionTuple;    
    
    /** Sets up for the unit tests by initializing the helper members.
     */
    @Before
    public void setUp() {
        testTransactionTuple = new TransactionTuple(123, 456, 100.00, 15.00);
    }//Close setUp.
    
    /** Tears down after the unit tests by setting the helpers to null.
     */
    @After
    public void tearDown() {
        testTransactionTuple = null;
    }//Close tearDown.
    
    /** Tests that the extract method throws an IllegalArgumentException when
     * the input record has an incorrect number of fields.
     */
    @Test(expected=IllegalArgumentException.class)
    public void extract_test1() {
        //Call the method that will throw the exception.
        TransactionTuple.extract("123,456,40.00"); //No tip.
    }//Close extract_test1.
    
    /** Tests that the extract method throws an IllegalArgumentException when
     * the input record has zero for the check value.
     */
    @Test(expected=IllegalArgumentException.class)
    public void extract_test2() {
        //Call the method that will throw the exception.
        TransactionTuple.extract("123,456,0.00,5.00");
    }//Close extract_test2.
    
    /** Tests that the extract method returns the correct value.
     */
    @Test
    public void extract_test3() {
        //Set up the inputs.
        String record = "123, 456, 100.00, 25.00";
        
        //Set up the truth.
        long truth_cardNumber = 123;
        long truth_restaurantID = 456;
        double truth_check = 100.00;
        double truth_tip = 25.00;
        
        //Obtain the answer.
        TransactionTuple answer = TransactionTuple.extract(record);
        
        //Assert equality.
        assertEquals(truth_cardNumber, answer.getCardNumber());
        assertEquals(truth_restaurantID, answer.getRestaurantID());
        assertEquals(truth_check, answer.getCheck(), 1e-14);
        assertEquals(truth_tip, answer.getTip(), 1e-14);
    }//Close extract_test3.
    
    /** Tests that the getCardNumber method returns the correct value.
     */
    @Test
    public void getCardNumber_test1() {
        //Set up the truth.
        long truth = 123;
        
        //Assert equality.
        assertEquals(truth, testTransactionTuple.getCardNumber());
    }//Close getCardNumber_test1.
    
    /** Tests that the getRestaurantID method returns the correct value.
     */
    @Test
    public void getRestaurantID_test1() {
        //Set up the truth.
        long truth = 456;
        
        //Assert equality.
        assertEquals(truth, testTransactionTuple.getRestaurantID());
    }//Close getRestaurantID_test1.
    
    /** Tests that the getCheck method returns the correct value.
     */
    @Test
    public void getCheck_test1() {
        //Set up the truth.
        double truth = 100.00;
        
        //Assert equality.
        assertEquals(truth, testTransactionTuple.getCheck(), 1e-14);
    }//Close getCheck_test1.
    
    /** Tests that the getTip method returns the correct value.
     */
    @Test
    public void getTip_test1() {
        //Set up the truth.
        double truth = 15.00;
        
        //Assert equality.
        assertEquals(truth, testTransactionTuple.getTip(), 1e-14);
    }//Close getTip_test1.
    
    /** Tests that the getTipPercentage method returns the correct value.
     */
    @Test
    public void getTipPercentage_test1() {
        //Set up the truth.
        double truth = 0.15;
        
        //Assert equality.
        assertEquals(truth, testTransactionTuple.getTipPercentage(), 1e-14);
    }//Close getTipPercentage_test1.
}//Close TransactionTuple_UnitTest.