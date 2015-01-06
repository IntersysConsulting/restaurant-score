package com.intersys.example.restaurantscore.simple.datageneration;


import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.random.MersenneTwister;

import com.intersys.example.restaurantscore.simple.datageneration.Restaurant;

/** Generates Restaurants.
 */
public class RestaurantGenerator {
    
    /** Creates an instance with the provided generator.
     * 
     * @param generator The generator use for creating the restaurants.
     */
    public RestaurantGenerator(RandomDataGenerator generator) {
        this.generator = generator;
    }//Close constructor.
    
    /** Creates an instance with it's own generator (Mersenne Twister).
     */
    public RestaurantGenerator() { 
        this(new RandomDataGenerator(new MersenneTwister()));
    }//Close constructor.
    
    /** Generates an array of Restaurants.
     * 
     * @param numRestaurants The number of restaurants to generate.
     * @throws IllegalArgumentException If the number of restaurants is not
     * greater than zero.
     * @return An array of restaurants.
     */
    public Restaurant[] generate(int numRestaurants) { 
        //Validate numRestaurants.
        if(numRestaurants <= 0) {
            throw new IllegalArgumentException("Number of restaurants " + 
                "to generate must be greater than zero.");
        }//Close if statement on numRestaurants.
        
        Restaurant[] restaurants = new Restaurant[numRestaurants];
        
        for(int ii=0; ii<numRestaurants; ++ii) {
            double score = generator.nextGaussian(meanScore, stdScore);
            
            //Truncate at zero.
            score = (score < 0.0) ? 0.0 : score;
            
            restaurants[ii] = new Restaurant(score, generator);
        }//Close for loop over numRestaurants (ii).
        
        return restaurants;
    }//Close generate.
    
    /** Obtains the mean score. */
    public double getMeanScore() { return meanScore; }
    /** Obtains the standard deviation for the scores. */
    public double getStdScore() { return stdScore; }
    /** Obtains the random number generator creating the scores. */
    public RandomDataGenerator getGenerator() { return generator; }
    
    /** Reseeds the random number generator. */
    public void reSeedGenerator(long newSeed) { generator.reSeed(12345); }
    
    /** The mean score for generating scores. */
    private final double meanScore = 1.0;
    /** The standard deviation of the scores for generating the scores. */
    private final double stdScore = 0.2;
    /** The random number generator generating the scores. */
    private final RandomDataGenerator generator;
}//Close RestaurantGenerator.