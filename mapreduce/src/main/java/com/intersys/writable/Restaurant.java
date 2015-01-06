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
public class Restaurant implements Writable , WritableComparable {

  public static int	PROPERTY_ID = 0;
   
  private boolean[] changeFlag = new boolean[1];
   
  private long	_id;

  public Restaurant() {
    resetChangeFlags();
  }

  public Restaurant(long _id) {
    resetChangeFlags();
	setId(_id);  
  }

  public Restaurant(Restaurant original) {
    resetChangeFlags();
	setId(original.getId());  
  }
  
  public Restaurant(byte[] bytes) {
	ByteArrayInputStream is = new ByteArrayInputStream(bytes);
	DataInput in = new DataInputStream(is);
	try { readFields(in); } catch (IOException e) { }
	resetChangeFlags();
  }
  
   
  public long getId() {
    return _id;
  }
  
  public void setId(long value) {
    _id = value;
    changeFlag[PROPERTY_ID] = true;
  }  
  
  public void readFields(DataInput in) throws IOException {
		
			// Read long _id
		
		_id = in.readLong();
	    changeFlag[PROPERTY_ID] = true;
		  }
  
  public void write(DataOutput out) throws IOException {
		
			// Write long _id
		
	  out.writeLong(_id);
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
    changeFlag[PROPERTY_ID] = false;
  }
  
  public boolean getChangeFlag(int i) {
  	return changeFlag[i];
  }

  
   public byte[] getIdAsBytes() throws IOException {
	  ByteArrayOutputStream os = new ByteArrayOutputStream();
	  DataOutputStream out = new DataOutputStream(os);
		
			// Write long _id
		
	  out.writeLong(_id);

	  out.flush();
	  out.close();
	  return os.toByteArray();
   }

  
   public void setIdFromBytes(byte[] b) throws IOException {
	  ByteArrayInputStream is = new ByteArrayInputStream(b);
	  DataInput in = new DataInputStream(is);
	  int len;
		
			// Read long _id
		
		_id = in.readLong();
	    changeFlag[PROPERTY_ID] = true;
		
   }
	

	/*
	 * Returns:
	 *   a negative integer if this object is less than the other object,
	 *   zero if this object "is equal to" the other object, or
	 *   a positive integer if this object "is greater than" the other object. 
	 */ 
	public int compareTo(Object obj) {

			// Begin comparable logic
		
		if (obj instanceof Restaurant) { 
	
			Restaurant other = (Restaurant) obj;
			int test = 0;

	
			return ((int) this._id - (int) other.getId());
		
		}
	
		return 0;
	
			// End comparable logic
	}


	public static Restaurant fromJson(String source) {
	
		Restaurant obj = null;

		try {
			JSONObject jsonObj = new JSONObject(source);
			obj = fromJson(jsonObj);
		} catch (JSONException e) {
			System.out.println(e.toString());
		}

		return obj;	
	}

	public static Restaurant fromJson(JSONObject jsonObj) {
	
		Restaurant obj = new Restaurant();

		try {

			if (jsonObj.has("id")) {
				obj.setId(jsonObj.getLong("id"));
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

			jsonObj.put("id", getId());

			return jsonObj;	
		} catch (JSONException e) { }

		return null;	
	}

	public String toJsonString() {
	
		return toJson().toString();	

	}

	public String toTSV() {
	
		StringBuffer sb = new StringBuffer();

        sb.append(String.valueOf(getId()));

		return sb.toString();	
	}


}
