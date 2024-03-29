package com.intersys.hadoop.platform;

import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public abstract class ConcurrentSequenceFileOutputFormat <K, V> 
					extends SequenceFileOutputFormat<K, V> 
					implements Configurable {
	
	private static String[] propertyName = {
		"mapred.output.compression.type" ,
		"mapred.output.compression.codec" ,
		"mapred.output.compress" ,
		"mapred.output.dir"
		};
	
	private Configuration configuration;


	public ConcurrentSequenceFileOutputFormat() {
		super();
	}

	public Configuration getConf() {
		return configuration;
	}

	public void setConf(Configuration conf) {

		configuration = conf;
		
		String prefix = "multiple.outputs." + getMultiOutputName() + ".";
		
		for (int i = 0; i < propertyName.length; i++) {
			String property = prefix + propertyName[i];
			String value = conf.get(property);
			if (value != null) {
				conf.set(propertyName[i], value);
			}
		}
	
	}

	public void configure(Configuration conf) {

		String prefix = "multiple.outputs." + getMultiOutputName() + ".";
		
		for (int i = 0; i < propertyName.length; i++) {
			String property = prefix + propertyName[i];
			String value = conf.get(propertyName[i]);
			if (value != null) {
				conf.set(property, value);
			}
		}
	
	}

	public abstract String getMultiOutputName();

}
