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
package com.intersys.writable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;
import com.eaio.uuid.UUID;
import org.apache.hadoop.io.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressWarnings("rawtypes")
public class Score implements Writable , WritableComparable {

  public static int	PROPERTY_RESTAURANTID = 0;
  public static int	PROPERTY_TIPBUCKET = 1;
   
  private boolean[] changeFlag = new boolean[2];
   
  private long	_restaurantID;
  private int	_tipBucket;

  public Score() {
    resetChangeFlags();
  }

  public Score(long _restaurantID, int _tipBucket) {
    resetChangeFlags();
	setRestaurantID(_restaurantID);  
	setTipBucket(_tipBucket);  
  }

  public Score(Score original) {
    resetChangeFlags();
	setRestaurantID(original.getRestaurantID());  
	setTipBucket(original.getTipBucket());  
  }
  
  public Score(byte[] bytes) {
	ByteArrayInputStream is = new ByteArrayInputStream(bytes);
	DataInput in = new DataInputStream(is);
	try { readFields(in); } catch (IOException e) { }
	resetChangeFlags();
  }
  
   
  public long getRestaurantID() {
    return _restaurantID;
  }
  
  public void setRestaurantID(long value) {
    _restaurantID = value;
    changeFlag[PROPERTY_RESTAURANTID] = true;
  }  
   
  public int getTipBucket() {
    return _tipBucket;
  }
  
  public void setTipBucket(int value) {
    _tipBucket = value;
    changeFlag[PROPERTY_TIPBUCKET] = true;
  }  
  
  public void readFields(DataInput in) throws IOException {
		
			// Read long _restaurantID
		
		_restaurantID = in.readLong();
	    changeFlag[PROPERTY_RESTAURANTID] = true;
				
			// Read int _tipBucket
		
		_tipBucket = in.readInt();
	    changeFlag[PROPERTY_TIPBUCKET] = true;
		  }
  
  public void write(DataOutput out) throws IOException {
		
			// Write long _restaurantID
		
	  out.writeLong(_restaurantID);
		
			// Write int _tipBucket
		
	  out.writeInt(_tipBucket);
  }
  
  public byte[] getBytes() throws IOException {
	  ByteArrayOutputStream os = new ByteArrayOutputStream();
	  DataOutputStream out = new DataOutputStream(os);
	  write(out);
	  out.flush();
	  out.close();
	  return os.toByteArray();
  }

  public void resetChangeFlags() {
    changeFlag[PROPERTY_RESTAURANTID] = false;
    changeFlag[PROPERTY_TIPBUCKET] = false;
  }
  
  public boolean getChangeFlag(int i) {
  	return changeFlag[i];
  }

  
   public byte[] getRestaurantIDAsBytes() throws IOException {
	  ByteArrayOutputStream os = new ByteArrayOutputStream();
	  DataOutputStream out = new DataOutputStream(os);
		
			// Write long _restaurantID
		
	  out.writeLong(_restaurantID);

	  out.flush();
	  out.close();
	  return os.toByteArray();
   }
  
   public byte[] getTipBucketAsBytes() throws IOException {
	  ByteArrayOutputStream os = new ByteArrayOutputStream();
	  DataOutputStream out = new DataOutputStream(os);
		
			// Write int _tipBucket
		
	  out.writeInt(_tipBucket);

	  out.flush();
	  out.close();
	  return os.toByteArray();
   }

  
   public void setRestaurantIDFromBytes(byte[] b) throws IOException {
	  ByteArrayInputStream is = new ByteArrayInputStream(b);
	  DataInput in = new DataInputStream(is);
	  int len;
		
			// Read long _restaurantID
		
		_restaurantID = in.readLong();
	    changeFlag[PROPERTY_RESTAURANTID] = true;
		
   }
  
   public void setTipBucketFromBytes(byte[] b) throws IOException {
	  ByteArrayInputStream is = new ByteArrayInputStream(b);
	  DataInput in = new DataInputStream(is);
	  int len;
		
			// Read int _tipBucket
		
		_tipBucket = in.readInt();
	    changeFlag[PROPERTY_TIPBUCKET] = true;
		
   }
	

	/*
	 * Returns:
	 *   a negative integer if this object is less than the other object,
	 *   zero if this object "is equal to" the other object, or
	 *   a positive integer if this object "is greater than" the other object. 
	 */ 
	public int compareTo(Object obj) {

			// Begin comparable logic
		
		if (obj instanceof Score) { 
	
			Score other = (Score) obj;
			int test = 0;

	
			return 0;
		
		}
	
		return 0;
	
			// End comparable logic
	}


	public static Score fromJson(String source) {
	
		Score obj = null;

		try {
			JSONObject jsonObj = new JSONObject(source);
			obj = fromJson(jsonObj);
		} catch (JSONException e) {
			System.out.println(e.toString());
		}

		return obj;	
	}

	public static Score fromJson(JSONObject jsonObj) {
	
		Score obj = new Score();

		try {

			if (jsonObj.has("restaurantID")) {
				obj.setRestaurantID(jsonObj.getLong("restaurantID"));
			}

			if (jsonObj.has("tipBucket")) {
				obj.setTipBucket(jsonObj.getInt("tipBucket"));
			}

		} catch (JSONException e) {
			System.out.println(e.toString());
			obj = null;
		}

		return obj;	
	}

	public JSONObject toJson() {
	
		try {
			JSONObject jsonObj = new JSONObject();
			JSONArray jsonArray;

			jsonObj.put("restaurantID", getRestaurantID());

			jsonObj.put("tipBucket", getTipBucket());

			return jsonObj;	
		} catch (JSONException e) { }

		return null;	
	}

	public String toJsonString() {
	
		return toJson().toString();	

	}

	public String toTSV() {
	
		StringBuffer sb = new StringBuffer();

        sb.append(String.valueOf(getRestaurantID()));

        sb.append("\t");
        sb.append(String.valueOf(getTipBucket()));

		return sb.toString();	
	}


}
