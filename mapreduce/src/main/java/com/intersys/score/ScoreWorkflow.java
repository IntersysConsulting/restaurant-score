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
package com.intersys.score;

import java.util.ArrayList;

public class ScoreWorkflow {

	/**
	 * @param args
	 */
	public static void main(String[] args) {


		com.intersys.score.TipBucket.TipBucketJob.main(args);
				

		com.intersys.score.LoadTransactions.LoadTransactionsJob.main(args);
				
	}

}
