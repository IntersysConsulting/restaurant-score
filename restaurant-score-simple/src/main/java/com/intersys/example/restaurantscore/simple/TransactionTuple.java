package com.intersys.example.restaurantscore.simple;

/** Holds the data for a restaurant transaction.
 */
public class TransactionTuple {
    
    /** Constructs an instance from numeric primitives.
     * 
     * @param cardNumber The card number for the transaction.
     * @param restaurantID The restaurant ID for the transaction.
     * @param check The check value for the transaction.
     * @param tip The tip value for the transaction.
     */
    TransactionTuple(long cardNumber, long restaurantID, double check, 
        double tip) {
        
        this.cardNumber = cardNumber;
        this.restaurantID = restaurantID;
        this.check = check;
        this.tip = tip;
    }//Close constructor.
    
    
    /** A static factory method that creates a TransactionTuple from a comma
     * delimited string of the form:
     * card number, restaurant id, check, tip
     * 
     * @param record A comma delimited string formatted as above.
     * @throws IllegalArgumentException If the record does not have four fields.
     * @throws IllegalArgumentException If the value of the check is zero (or
     *  very close to zero).
     */
    public static TransactionTuple extract(String record) {
        
        //Split the string.
        String[] splitRecord = record.split(",");
        
        //Validate the record.
        if(splitRecord.length != 4) {
            throw new IllegalArgumentException("Record must have four fields.");
        }//Close if statement validating record.
        
        //Parse out the values.
        long cardNumber = Long.valueOf(splitRecord[0].trim());
        long restaurantID = Long.valueOf(splitRecord[1].trim());
        double check = Double.valueOf(splitRecord[2].trim());
        double tip = Double.valueOf(splitRecord[3].trim());
        
        //Make sure the check isn't 0.
        if(check <= 1e-14) {
            throw new IllegalArgumentException("Check must be greater than " +
                "zero.");
        }//Close if statement validating check.
        
        return new TransactionTuple(cardNumber, restaurantID, check, tip);
    }//Close constructor.
    
    /** Obtains the card number. */
    public long getCardNumber() { return cardNumber; }
    /** Obtains the restaurant ID. */
    public long getRestaurantID() { return restaurantID; }
    /** Obtains the check value. */
    public double getCheck() { return check; }
    /** Obtains the tip value. */
    public double getTip() { return tip; }
    /** Calculates the tip percentage. */
    public double getTipPercentage() { return tip / check; }
    
    /** The unique card number. */
    private final long cardNumber;
    /** The unique restaurant ID. */
    private final long restaurantID;
    /** The value of the check in dollars. */
    private final double check;
    /** The value of the tip in dollars. */
    private final double tip;
}//Close TransactionTuple.