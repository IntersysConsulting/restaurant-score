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


public class LoadTransactionsJob {
	
	public static void main(String[] args) {

		String loadtransactions_input_readscores = "file:///home/hduser/ScoredTransactions/";
		String loadtransactions_output_scoresrestaurant = "file:///home/hduser/output/";

		String countBase = "file:///home/hduser/workspaces/";

		Properties prop = new Properties();
		try {
			if (args.length > 0) {
				InputStream stream = new FileInputStream(args[0]);
				prop.loadFromXML(stream);
				loadtransactions_input_readscores = prop.getProperty("LoadTransactions.input.ReadScores",loadtransactions_input_readscores);
				loadtransactions_output_scoresrestaurant = prop.getProperty("LoadTransactions.output.ScoresRestaurant",loadtransactions_output_scoresrestaurant);

				countBase = prop.getProperty("count.base.dir",countBase);

				prop.list(System.out);
			}
		} catch (Exception e) {
			System.out.println("Properties file not found. Will use default properties instead.");
		}
		
		try {
			Configuration conf = new Configuration();

			conf.set("LoadTransactions.input.ReadScores",loadtransactions_input_readscores);
			conf.set("LoadTransactions.output.ScoresRestaurant",loadtransactions_output_scoresrestaurant);

			conf.set("count.base.dir",countBase);

				// Begin custon config logic

				// End custon config logic

			Job job = new Job(conf, "com.intersys.score.LoadTransactions.LoadTransactionsJob : ");

			job.setJarByClass(LoadTransactionsJob.class);

			job.setMapOutputKeyClass(Restaurant.class);
			job.setMapOutputValueClass(Score.class);

				// Configure mapper for input ReadScores
			MultipleInputs.addInputPath(job, new Path(loadtransactions_input_readscores), TextInputFormat.class, ReadScoresMapper.class);

	        job.setReducerClass(LoadTransactionsReducer.class);
	        job.setOutputKeyClass(Text.class);
	        job.setOutputValueClass(Text.class);
	        job.setOutputFormatClass(TextOutputFormat.class);
	        
	        Path outPath;
	        FileSystem dfs;


	        outPath = new Path(loadtransactions_output_scoresrestaurant);
			TextOutputFormat.setOutputPath(job, outPath);
	        dfs = FileSystem.get(outPath.toUri(), conf);
	        if (dfs.exists(outPath)) {
	          dfs.delete(outPath, true);
	        }
            new ScoresRestaurantConcurrentOutputFormat().configure(job.getConfiguration());
            MultipleOutputs.addNamedOutput(job, "ScoresRestaurant", ScoresRestaurantConcurrentOutputFormat.class, Text.class, Text.class);

	        
	        outPath = new Path(countBase+"/score/LoadTransactions");
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
