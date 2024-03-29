package com.intersys.hadoop.platform;

import org.apache.cassandra.hadoop.ColumnFamilyOutputFormat;
import org.apache.hadoop.conf.Configurable;
import org.apache.hadoop.conf.Configuration;

public abstract class ConcurrentColumnFamilyOutputFormat 
							extends ColumnFamilyOutputFormat 
							implements Configurable {
	
	private static String[] propertyName = {
		"cassandra.output.keyspace" ,
		"cassandra.output.keyspace.username" ,
		"cassandra.output.keyspace.passwd" ,
		"cassandra.output.columnfamily" ,
		"cassandra.output.thrift.port" ,
		"cassandra.output.thrift.address" ,
		"cassandra.consistencylevel.write" ,
        "cassandra.output.compression.class",
		"cassandra.output.compression.length",
		"cassandra.output.partitioner.class",
		"cassandra.output.transport.factory.class"
		};
	
	private Configuration configuration;

	public ConcurrentColumnFamilyOutputFormat() {
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
