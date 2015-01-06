package com.intersys.score.TipBucket;

import org.apache.hadoop.io.Text;

import com.intersys.hadoop.platform.*;
import com.intersys.writable.*;

public class BucketTipConcurrentOutputFormat extends ConcurrentTextOutputFormat<Card, Card> {

	@Override
	public String getMultiOutputName() {
		return "BucketTip";
	}

}
