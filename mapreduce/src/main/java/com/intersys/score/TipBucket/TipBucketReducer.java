/*
 * 
 * This source code and information are provided "AS-IS" without 
 * warranty of any kind, either expressed or implied, including
 * but not limited to the implied warranties of merchantability
 * and/or fitness for a particular purpose.
 * 
 * This source code was generated using an evaluation copy 
 * of the Cassandra/Hadoop Accelerator and may not be used for
 * production purposes.
 *
 * For more information, contact Chris Gerken at
 * chris.gerken@gerkenip.com
 * 
 */
package com.intersys.score.TipBucket;

// Begin imports

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.logging.Logger;

import org.apache.cassandra.db.IColumn;
import org.apache.cassandra.utils.ByteBufferUtil;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.Mutation;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.RawKeyValueIterator;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.StatusReporter;
import org.apache.hadoop.mapreduce.TaskAttemptID;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import com.intersys.writable.*;
import com.intersys.example.restaurantscore.simple.Quantile;
// End imports

public class TipBucketReducer extends Reducer<Card,Tip, Card, Score> {

	// Begin declarations 

	private MultipleOutputs mos;
	
	private String tipbucket_output_buckettip;
	
	private long	counterBucketTip = 0;

	// End declarations 

	@Override
	protected void setup(Reducer<Card,Tip, Card, Score>.Context context)
			throws java.io.IOException, InterruptedException {

		// Begin setup logic
		
		mos = new MultipleOutputs(context);

		Configuration conf = context.getConfiguration();

		tipbucket_output_buckettip = conf.get("TipBucket.output.BucketTip");
		if (!tipbucket_output_buckettip.endsWith("/")) {
			tipbucket_output_buckettip = tipbucket_output_buckettip + "/";
		}
		tipbucket_output_buckettip = tipbucket_output_buckettip + "part";

		// End setup logic

	}

	protected void reduce(Card key, Iterable<Tip> values,
			Reducer<Card,Tip, Card, Score>.Context context) throws java.io.IOException, InterruptedException {

		// Begin reduce logic 

		try {

			Card outKey = key;
			Score outValue;		
			
			//TODO: This is really nasty, it would be easier if the Quantile
			//class took a Double array or a Collection.
			ArrayList<Long> restaurantIDs = new ArrayList<Long>();
			ArrayList<Double> tipValueList = new ArrayList<Double>();
			for (Tip value : values) {
			    restaurantIDs.add(value.getRestaurantID());
			    tipValueList.add(value.getTipAmount());
			}
			
			double[] tipPercentages = new double[tipValueList.size()];
			
			for(int ii=0; ii < tipValueList.size(); ++ii) {
			    tipPercentages[ii] = tipValueList.get(ii).doubleValue();
			}//Close for loop over tipvalueList.
			
			Quantile scorer = new Quantile(tipPercentages, 3);
			
			for(int ii=0; ii<restaurantIDs.size(); ++ii) {
			    outValue = new Score(restaurantIDs.get(ii), 
			        scorer.getNearestQuantile(tipPercentages[ii]));
			     String line = new StringBuilder()
			        .append(outKey.getCardNumber())
			        .append(",")
			        .append(outValue.getRestaurantID())
			        .append(",")
			        .append(outValue.getTipBucket())
			        .toString();
			    writeBucketTip(line);
			}
			
		} catch (Exception e) {
			context.getCounter("Exception",e.getMessage()).increment(1);
		}
		// End reduce logic 
		
	}

	@Override
	protected void cleanup(org.apache.hadoop.mapreduce.Reducer.Context context)
			throws IOException, InterruptedException {

		// Begin cleanup logic

		mos.close();

		//context.write(new Text("score\tO\tTipBucket\tBucketTip\t"+counterBucketTip), new Text(""));

		// End cleanup logic
		
	}
	

	private void writeBucketTip(String line) throws IOException, InterruptedException {
	
		mos.write("BucketTip", new Text(line), null, tipbucket_output_buckettip);
		counterBucketTip++;
		
	}

	// Begin custom methods 

	// End custom methods

/*	public Reducer<Card,Tip, Card, Score>.Context testContext(Configuration conf,TaskAttemptID taid,
			RawKeyValueIterator rkvi, Counter counter1, Counter counter2, RecordWriter<Card, Score> rw, 
			OutputCommitter oc, StatusReporter sr, RawComparator<Card> rc, Class<Card> arg9, Class<Tip> arg10) 
				throws IOException, InterruptedException {
	
		return null;	
//		return new Context(conf,taid, rkvi, counter1, counter2, rw, oc, sr, rc, arg9, arg10);

	}
*/

}
