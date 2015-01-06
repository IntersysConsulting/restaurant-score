package com.intersys.score.LoadTransactions;

import org.apache.hadoop.io.Text;

import com.intersys.hadoop.platform.*;
import com.intersys.writable.*;

public class ScoresRestaurantConcurrentOutputFormat extends ConcurrentTextOutputFormat<Text, Text> {

	@Override
	public String getMultiOutputName() {
		return "ScoresRestaurant";
	}

}
