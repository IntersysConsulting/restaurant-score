package com.intersys.example.restaurantscore.simple;

import java.util.Arrays;

/** Class that computes the quantile of a value, given a list of values.
 * 
 * This class uses the "R-2, SAS-5" method on the wikipedia page for quantiles.
 * @see en.wikipedia.org/wiki/Quantile
 */
public class Quantile {
    
    /** This constructor builds the quantile values from samples, assuming 
     * they're already sorted in ascending order. 
     * Note that if the samples are not sorted, the results will be incorrect.
     * 
     * @param samples The (sorted) samples.
     * @param numQuantiles The number of quantiles desired.
     */
    public Quantile(double[] samples, int numQuantiles) {
        
        this.quantileValues = 
            getQuantileValues(samples, 
                getIndices(samples.length, numQuantiles));
    }//Close constructor.
    
    /** This constructor builds the quantile values from samples, with a boolean
     * indicator as to whether or not they are sorted (ascending). 
     * If they are not sorted
     * the samples are COPIED, then sorted using java.util.Arrays. Note that 
     * if the sorted parameter is true and the samples aren't sorted, the 
     * results will be incorrect.
     * 
     * @param samples The sorted or unsorted samples.
     * @param numQuantiles The number of quantiles desired.
     * @param sorted Whether or not the samples are sorted.
     */
    public Quantile(double[] samples, int numQuantiles, boolean sorted) {
        //Call the other constructor.
        this(sorted ? samples : copyAndSort(samples), numQuantiles);
    }//Close constructor.
    
    /** This constructor uses the quantile values directly. It assumes they 
     * are sorted in ascending order.
     * 
     * @param quantileValues The values of the quantiles in ascending order.
     */
    public Quantile(double[] quantileValues) {
        //Manually set quantile boundaries.
        this.quantileValues = quantileValues;
    }//Close constructor.
    
    
    /** Obtains the quantile with the smallest value greater than the sample
     * value, zero if the sample is less than the zeroth quantile, and the
     * maximum number of quantiles if the sample is larger than the largest
     * quantile value.
     * 
     * @param sample The sample being tested.
     * @return The quantile nearest to the sample.
     */
    public int getNearestQuantile(double sample) {
        
        for(int ii=0; ii<quantileValues.length; ++ii) {
            
            //If the quantile value is larger than the sample, return that 
            //index.
            if(quantileValues[ii] > sample) 
                return ii;
        
        }//Close for loop over quantileValues.length (ii).
        
        //If the sample's greater than all of them, return the highest quantile.
        return quantileValues.length-1;
    }//Close getNearestQuantile.
    
    /** Obtains the quantile values that have already been computed.
     */
    public double[] getQuantileValues() {
        return quantileValues;
    }//Close getQuantileValues.
    
    
    /** Obtains the "indices" of the quantile values. The actual formula is
     * numSamples * (ii / numQuantiles) + 0.5, where ii is the ii'th quantile.
     * The first and last indices are zero and the end, respectively.
     * 
     * @param numSamples The number of samples present.
     * @param numQuantiles The number of quantile indices to compute.
     * @return The "indices" (offset by 0.5) of the quantile values.
     */
    static double[] getIndices(int numSamples, int numQuantiles) {
        
        //+1 accounts for zeroth quantile value (first in the sample set).
        double[] indices = new double[numQuantiles+1];
        
        //First element is the first in the sample set.
        indices[0] = 0.0;
        
        //Obtain the "internal" indices by dividing ii by numQuantiles.
        for(int ii=1; ii<numQuantiles; ++ii) {
            //Subtract 1 because the formula assumes one-indexing.
            indices[ii] = 
                numSamples * ((double) ii) / ((double) numQuantiles) + 0.5 - 1;
        }//Close for loop over indices (ii).
        
        //Last element is the last element of the sample set.
        indices[numQuantiles] = (((double) numSamples) - 1.0);
        
        return indices;
    }//Close getIndices.
    
    /** Obtains the quantile values from the indices, according to the formula:
     * 
     * (x[lower] + x[upper]) * 0.5, where lower is the ceiling of (index - 0.5),
     *  and upper is the floor of (index + 0.5).
     * 
     * @param samples The samples.
     * @param indices The "indices" of the quantile values.
     */
    static double[] getQuantileValues(double[] samples, double[] indices) {
        
        double[] quantileValues = new double[indices.length];
        
        //First element is the first element in the dataset.
        quantileValues[0] = samples[(int) indices[0]];
        
        //Now get the "middle" indices. This is from the R-2 / SAS-5 method
        //on the quantile wikipedia page mentioned above.
        for(int ii=1; ii<indices.length-1; ++ii) {
            
            int lowerIndex = (int) Math.ceil(indices[ii] - 0.5);
            int upperIndex = (int) Math.floor(indices[ii] + 0.5);
            
            quantileValues[ii] = 0.5 * 
                (samples[lowerIndex] + samples[upperIndex]);
        }//Close for loop over middle indices(ii).
        
        //Get the last quantile value.
        int finalIndex = (int) indices[indices.length-1];
        quantileValues[indices.length-1] = samples[finalIndex];
        
        return quantileValues;
    }//Close getQuantileValues.
    
    /** Copies the input array and sorts in ascending order.
     * 
     * NOTE: This class is totally not the best place for this method.
     * @param array An unsorted array.
     * @return A copy of the array, sorted in ascending order.
     */
    static double[] copyAndSort(double[] array) {
        
        //This should be pretty obvious.
        double[] newArray = Arrays.copyOf(array, array.length);
        Arrays.sort(newArray);
        
        return newArray;
    }//Close copyAndSort.
    
    /** The values at each of the quantiles. */
    private final double[] quantileValues;
}//Close Quantile.