package com.intersys.example.restaurantscore.simple.datageneration;

import com.intersys.example.restaurantscore.simple.datageneration.TipGenerator;
import com.intersys.example.restaurantscore.simple.datageneration.PersonGenerator;
import com.intersys.example.restaurantscore.simple.datageneration.RestaurantGenerator;
import com.intersys.example.restaurantscore.simple.datageneration.Person;
import com.intersys.example.restaurantscore.simple.datageneration.Restaurant;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.random.MersenneTwister;

import java.io.IOException;
import java.io.FileWriter;

/** Generates a dataset for the "restaurant scoring problem."
 * 
 * Dataset format is as follows:
 * 
 * card id,restaurant id,check value,tip value
 */
public class GenerateTipData {
    /** Runs the tip dataset generating simulation.
     * 
     * @param args The first element is the name of the file to output the
     * dataset to.
     */
    public static void main(String[] args) throws IOException {
        if(args.length < 1) {
            System.err.println("Requires an argument for the output file " + 
                "name, and an optional argument for the true restaurant" +
                "score values.");
            System.exit(1);
        }//Close if statement on args.
        
        String recordOutFileName = args[0];
        
        //Create the generator to use.
        RandomDataGenerator generator = new RandomDataGenerator(
            new MersenneTwister());
        generator.reSeed(54321);
        
        //Create the generator for the people.
        double meanBaseTipPercentage = 0.15;
        double meanVisits = 8.75; //Approx 2 per week, allowing for long tail.
        PersonGenerator personGenerator = new PersonGenerator(
            meanBaseTipPercentage, meanVisits, generator);
        
        //Create the generator for the restaurants.
        RestaurantGenerator restaurantGenerator = 
            new RestaurantGenerator(generator);
        
        //Create the people and the restaurants.
        int numPeople = 1000000;
        int numRestaurants = 2350;
        
        Person[] people = personGenerator.generate(numPeople);
        Restaurant[] restaurants = restaurantGenerator.generate(numRestaurants);
        
        //Create the tip generator.
        TipGenerator tipGenerator = new TipGenerator(people, restaurants,
            generator);
        
        //Generate the records and write to file.
        FileWriter recordOut = new FileWriter(recordOutFileName);
        
        for(String record: tipGenerator) {
            recordOut.write(record + "\n");
        }//Close loop over the generated tips (record).
        
        recordOut.close();
        
        if(args.length > 1) {
            String truthOutFileName = args[1];
            FileWriter truthOut = new FileWriter(truthOutFileName);
            
            for(int ii=0; ii<restaurants.length; ++ii) {
                truthOut.write(
                    new StringBuilder().append(ii).append(",")
                        .append(restaurants[ii].getScore())
                        .append("\n").toString());
            }//Close for loop over restaurants (ii).
            
            truthOut.close();
        }//CLose if statement on args.length.
    }//Close main.
}//Close GenerateTipData.