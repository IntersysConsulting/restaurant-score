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
package com.intersys.score.LoadTransactions;

// Begin imports

import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.SortedMap;
import java.util.StringTokenizer;

import java.io.IOException;

import com.intersys.writable.*;

import org.apache.cassandra.db.IColumn;
import org.apache.cassandra.utils.ByteBufferUtil;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.OutputCommitter;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.StatusReporter;
import org.apache.hadoop.mapreduce.TaskAttemptID;
import org.apache.hadoop.mapreduce.Mapper.Context;

// End imports

public class ReadScoresMapper extends
		org.apache.hadoop.mapreduce.Mapper<LongWritable,Text,Restaurant,Score> {

	// Begin declarations 


	// End declarations 

	@Override
	protected void setup(
			org.apache.hadoop.mapreduce.Mapper<LongWritable,Text,Restaurant,Score>.Context context)
			throws IOException, InterruptedException {

		// Begin setup logic
		

		// End setup logic

	}

	@Override
	public void map(LongWritable key,Text value, Context context)
			throws IOException, InterruptedException {

		// Begin map logic 

		try {

            //TODO: We can use a smaller transport class instead of Score - 
            //The value of this mapper doesn't need the restaurant ID.
            String[] splitRecord = value.toString().split(",");
            long restaurantID = Long.valueOf(splitRecord[1].trim());
            int score = Integer.valueOf(splitRecord[2].trim());
            
			Restaurant outKey = new Restaurant(restaurantID);
			Score outValue = 
			    new Score(restaurantID, score);
			
			context.write(outKey, outValue);
			

		} catch (Exception e) {
			context.getCounter("Exception",e.getMessage()).increment(1);
		}

		// End map logic 

	}

	@Override
	protected void cleanup(Context context) throws IOException,	InterruptedException {

		// Begin cleanup logic


		// End cleanup logic
		
	}

	// Begin custom methods 

	// End custom methods

/*	public Mapper<Card,Score,Restaurant,Score>.Context testContext(Configuration conf,TaskAttemptID taid,
			RecordReader<Card, Score> rr,RecordWriter<Restaurant, Score> rw,
			OutputCommitter oc, StatusReporter sr, InputSplit is) throws IOException, InterruptedException {
		
		return new Context(conf,taid,rr,rw,oc,sr,is);

	}
*/

}
