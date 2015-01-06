package com.intersys.example.restaurantscore.simple.datageneration;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.random.MersenneTwister;

/** Models a person. Currently possesses the number of restaurants to visit
 * and the base tip value.
 */
public class Person {
    
    /** Creates an instance with the specified random generator.
     * 
     * @param baseTipPercentage The base tip percentage for the person.
     * @param numRestaurants The number of restaurants the person will visit.
     * @param generator The random generator to use for drawing the tip
     * percentage.
     */
    public Person(double baseTipPercentage, int numRestaurants, 
        RandomDataGenerator generator) {
            this.baseTipPercentage = baseTipPercentage;
            this.numRestaurants = numRestaurants;
            this.generator = generator;
    }//Close constructor.
    
    /** Creates an instance with it's own random generator (the Mersenne Twister
     * algorithm is used in this case).
     * 
     * @param baseTipPercentage The base tip percentage for the person.
     * @param numRestaurants The number of restaurants the person will visit.
     */
    public Person(double baseTipPercentage, int numRestaurants) {
        this(baseTipPercentage, numRestaurants, 
            new RandomDataGenerator(new MersenneTwister()));
    }//Close constructor.
    
    /** Obtains the tip percentage given the restaurant. Draws from 
     * 
     * @param restaurant The restaurant to obtain the tip from.
     * @return The tip percentage based on the restaurant's score and the base
     * tip percentage for the individual.
     */
    public double getTipPercentage(Restaurant restaurant) {
        return Math.floor(generator.nextGaussian(
            baseTipPercentage*restaurant.getScore(), noiseStdDev) * 100)/100;
    }//Close getTipPercentage.
    
    /** Sets the generator seed. Note that if this generator instance is shared
     * all generators will be affected by the reseed operation.
     * 
     * @param newSeed The new seed for the generator.
     */
    public void reSeedGenerator(long newSeed) { generator.reSeed(newSeed); }
    
    /** Obtains the base tip percentage. */
    public double getBaseTipPercentage() { return baseTipPercentage; }
    /** Obtains the number of restaurants the person will visit. */
    public int getNumRestaurants() { return numRestaurants; }
    /** Obtains the generator for creating tip percentages. */
    public RandomDataGenerator getGenerator() { return generator; }
    
    /** The base tip percentage for the person, serving as the mean of tips
     * given.
     */
    private final double baseTipPercentage;
    /** The number of restaurants the person will visit. */
    private final int numRestaurants;
    /** The random number generator for drawing the tip values. */
    private final RandomDataGenerator generator;
    /** The noise standard deviation. */
    private final double noiseStdDev = 0.01;
}//Close Person.