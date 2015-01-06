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

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.MultipleInputs;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.cassandra.hadoop.ColumnFamilyInputFormat;
import org.apache.cassandra.hadoop.ColumnFamilyOutputFormat;
import org.apache.cassandra.hadoop.ConfigHelper;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;
import org.apache.cassandra.utils.ByteBufferUtil;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.SortedMap;
import org.apache.cassandra.db.IColumn;

import com.intersys.writable.*;


public class TipBucketJob {
	
	public static void main(String[] args) {

		String tipbucket_input_extracttip = "file:///home/hduser/input/";
		String tipbucket_output_buckettip = "file:///home/hduser/ScoredTransactions/";

		String countBase = "file:///home/hduser/workspaces/";

		Properties prop = new Properties();
		try {
			if (args.length > 0) {
				InputStream stream = new FileInputStream(args[0]);
				prop.loadFromXML(stream);
				tipbucket_input_extracttip = prop.getProperty("TipBucket.input.ExtractTip",tipbucket_input_extracttip);
				tipbucket_output_buckettip = prop.getProperty("TipBucket.output.BucketTip",tipbucket_output_buckettip);

				countBase = prop.getProperty("count.base.dir",countBase);

				prop.list(System.out);
			}
		} catch (Exception e) {
			System.out.println("Properties file not found. Will use default properties instead.");
		}
		
		try {
			Configuration conf = new Configuration();

			conf.set("TipBucket.input.ExtractTip",tipbucket_input_extracttip);
			conf.set("TipBucket.output.BucketTip",tipbucket_output_buckettip);

			conf.set("count.base.dir",countBase);

				// Begin custon config logic

				// End custon config logic

			Job job = new Job(conf, "com.intersys.score.TipBucket.TipBucketJob : ");

			job.setJarByClass(TipBucketJob.class);

			job.setMapOutputKeyClass(Card.class);
			job.setMapOutputValueClass(Tip.class);

				// Configure mapper for input ExtractTip
			MultipleInputs.addInputPath(job, new Path(tipbucket_input_extracttip), TextInputFormat.class, ExtractTipMapper.class);

	        job.setReducerClass(TipBucketReducer.class);
	        job.setOutputKeyClass(Text.class);
	        job.setOutputValueClass(Text.class);
	        job.setOutputFormatClass(TextOutputFormat.class);
	        
	        Path outPath;
	        FileSystem dfs;


	        outPath = new Path(tipbucket_output_buckettip);
			SequenceFileOutputFormat.setOutputPath(job, outPath);
	        dfs = FileSystem.get(outPath.toUri(), conf);
	        if (dfs.exists(outPath)) {
	          dfs.delete(outPath, true);
	        }
            new BucketTipConcurrentOutputFormat().configure(job.getConfiguration());
            MultipleOutputs.addNamedOutput(job, "BucketTip", BucketTipConcurrentOutputFormat.class, Card.class, Score.class);

	        
	        outPath = new Path(countBase+"/score/TipBucket");
			TextOutputFormat.setOutputPath(job, outPath);
	        dfs = FileSystem.get(outPath.toUri(), conf);
	        if (dfs.exists(outPath)) {
	          dfs.delete(outPath, true);
	        }

	        job.setNumReduceTasks(1);

			job.waitForCompletion(true);

		} catch (Exception e) {
			System.out.println(e.toString());
				// Handle Exception...
		} finally {

		}

	} 
}
