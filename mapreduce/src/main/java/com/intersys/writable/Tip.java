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
public class Tip implements Writable , WritableComparable {

  public static int	PROPERTY_RESTAURANTID = 0;
  public static int	PROPERTY_CHECKAMOUNT = 1;
  public static int	PROPERTY_TIPAMOUNT = 2;
   
  private boolean[] changeFlag = new boolean[3];
   
  private long	_restaurantID;
  private double	_checkAmount;
  private double	_tipAmount;

  public Tip() {
    resetChangeFlags();
  }

  public Tip(long _restaurantID, double _checkAmount, double _tipAmount) {
    resetChangeFlags();
	setRestaurantID(_restaurantID);  
	setCheckAmount(_checkAmount);  
	setTipAmount(_tipAmount);  
  }

  public Tip(Tip original) {
    resetChangeFlags();
	setRestaurantID(original.getRestaurantID());  
	setCheckAmount(original.getCheckAmount());  
	setTipAmount(original.getTipAmount());  
  }
  
  public Tip(byte[] bytes) {
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
   
  public double getCheckAmount() {
    return _checkAmount;
  }
  
  public void setCheckAmount(double value) {
    _checkAmount = value;
    changeFlag[PROPERTY_CHECKAMOUNT] = true;
  }  
   
  public double getTipAmount() {
    return _tipAmount;
  }
  
  public void setTipAmount(double value) {
    _tipAmount = value;
    changeFlag[PROPERTY_TIPAMOUNT] = true;
  }  
  
  public void readFields(DataInput in) throws IOException {
		
			// Read long _restaurantID
		
		_restaurantID = in.readLong();
	    changeFlag[PROPERTY_RESTAURANTID] = true;
				
			// Read double _checkAmount
		
		_checkAmount = in.readDouble();
	    changeFlag[PROPERTY_CHECKAMOUNT] = true;
				
			// Read double _tipAmount
		
		_tipAmount = in.readDouble();
	    changeFlag[PROPERTY_TIPAMOUNT] = true;
		  }
  
  public void write(DataOutput out) throws IOException {
		
			// Write long _restaurantID
		
	  out.writeLong(_restaurantID);
		
			// Write double _checkAmount
		
	  out.writeDouble(_checkAmount);
		
			// Write double _tipAmount
		
	  out.writeDouble(_tipAmount);
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
    changeFlag[PROPERTY_CHECKAMOUNT] = false;
    changeFlag[PROPERTY_TIPAMOUNT] = false;
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
  
   public byte[] getCheckAmountAsBytes() throws IOException {
	  ByteArrayOutputStream os = new ByteArrayOutputStream();
	  DataOutputStream out = new DataOutputStream(os);
		
			// Write double _checkAmount
		
	  out.writeDouble(_checkAmount);

	  out.flush();
	  out.close();
	  return os.toByteArray();
   }
  
   public byte[] getTipAmountAsBytes() throws IOException {
	  ByteArrayOutputStream os = new ByteArrayOutputStream();
	  DataOutputStream out = new DataOutputStream(os);
		
			// Write double _tipAmount
		
	  out.writeDouble(_tipAmount);

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
  
   public void setCheckAmountFromBytes(byte[] b) throws IOException {
	  ByteArrayInputStream is = new ByteArrayInputStream(b);
	  DataInput in = new DataInputStream(is);
	  int len;
		
			// Read double _checkAmount
		
		_checkAmount = in.readDouble();
	    changeFlag[PROPERTY_CHECKAMOUNT] = true;
		
   }
  
   public void setTipAmountFromBytes(byte[] b) throws IOException {
	  ByteArrayInputStream is = new ByteArrayInputStream(b);
	  DataInput in = new DataInputStream(is);
	  int len;
		
			// Read double _tipAmount
		
		_tipAmount = in.readDouble();
	    changeFlag[PROPERTY_TIPAMOUNT] = true;
		
   }
	

	/*
	 * Returns:
	 *   a negative integer if this object is less than the other object,
	 *   zero if this object "is equal to" the other object, or
	 *   a positive integer if this object "is greater than" the other object. 
	 */ 
	public int compareTo(Object obj) {

			// Begin comparable logic
		
		if (obj instanceof Tip) { 
	
			Tip other = (Tip) obj;
			int test = 0;

	
			return 0;
		
		}
	
		return 0;
	
			// End comparable logic
	}


	public static Tip fromJson(String source) {
	
		Tip obj = null;

		try {
			JSONObject jsonObj = new JSONObject(source);
			obj = fromJson(jsonObj);
		} catch (JSONException e) {
			System.out.println(e.toString());
		}

		return obj;	
	}

	public static Tip fromJson(JSONObject jsonObj) {
	
		Tip obj = new Tip();

		try {

			if (jsonObj.has("restaurantID")) {
				obj.setRestaurantID(jsonObj.getLong("restaurantID"));
			}

			if (jsonObj.has("checkAmount")) {
				obj.setCheckAmount(jsonObj.getDouble("checkAmount"));
			}

			if (jsonObj.has("tipAmount")) {
				obj.setTipAmount(jsonObj.getDouble("tipAmount"));
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

			jsonObj.put("checkAmount", getCheckAmount());

			jsonObj.put("tipAmount", getTipAmount());

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
        sb.append(String.valueOf(getCheckAmount()));

        sb.append("\t");
        sb.append(String.valueOf(getTipAmount()));

		return sb.toString();	
	}


}
