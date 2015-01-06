package com.intersys.example.restaurantscore.simple.datageneration;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.random.MersenneTwister;

/** Models a restaurant. Currently possesses only a score and a way to generate
 * checks.
 * 
 * There might be additional abstraction layers we could apply to make the
 * restaurants more complicated for modeling in the future.
 */
public class Restaurant { 
    
    /** Creates an instance with a specified score and a fresh random data
     * generator with the Mersenne Twister algorithm used.
     * 
     * @param score The score for the restaurant.
     */
    public Restaurant(double score) {
        this(score, new RandomDataGenerator(new MersenneTwister()));
    }//Close constructor.
    
    /** Creates an instance with the specified score and random generator.
     * 
     * @param score The score for the restaurant.
     * @param generator The random generator for creating the checks.
     */
    public Restaurant(double score, RandomDataGenerator generator) {
        
        this.score = score;
        this.generator = generator;
    }//Close constructor.
    
    /** Obtains the score. */
    public double getScore() { return score; }
    /** Obtains the generator. */
    //TODO: Unit test.
    public RandomDataGenerator getGenerator() { return generator; }
    
    /** Reseeds the random generator with the specified seed.
     * @param newSeed The new seed for the random generator.
     */
    public void reSeedGenerator(long newSeed) { generator.reSeed(newSeed); }
    
    /** Obtains a check drawn from a uniform(15,100) distribution, lower bound
     * inclusive.
     * 
     * @return The check value.
     */
    public double getCheck()
    {
        return Math.floor(generator.nextUniform(15.00, 100.00, true)*100)/100;
    }//Close getCheck.
    
    /** The score. */
    private final double score;
    
    /** The random generator for the checks. */
    private final RandomDataGenerator generator;
}//Close Restaurant.