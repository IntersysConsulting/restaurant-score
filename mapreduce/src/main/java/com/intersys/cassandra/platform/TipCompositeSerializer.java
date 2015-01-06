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
package com.intersys.cassandra.platform;

import static me.prettyprint.hector.api.ddl.ComparatorType.BYTESTYPE;

import java.nio.ByteBuffer;

import org.apache.thrift.TBaseHelper;

import com.intersys.writable.Tip;

import me.prettyprint.cassandra.serializers.*;
import me.prettyprint.hector.api.beans.AbstractComposite.ComponentEquality;
import me.prettyprint.hector.api.beans.Composite;
import me.prettyprint.hector.api.ddl.ComparatorType;

/**
 * Converts bytes to Tip and back again
 * 
 */
public final class TipCompositeSerializer extends AbstractSerializer<Tip> {

  private static final TipCompositeSerializer instance = new TipCompositeSerializer();

  public static TipCompositeSerializer get() {
    return instance;
  }

  @Override
  public ByteBuffer toByteBuffer(Tip obj) {
    if (obj == null) {
      return null;
    }
	Composite composite = new Composite();
	composite.addComponent(0, obj.getRestaurantID() , LongSerializer.get(), "LongType", ComponentEquality.EQUAL);
	composite.addComponent(1, obj.getCheckAmount() , DoubleSerializer.get(), "DoubleType", ComponentEquality.EQUAL);
	composite.addComponent(2, obj.getTipAmount() , DoubleSerializer.get(), "DoubleType", ComponentEquality.EQUAL);
	ByteBuffer byteBuffer = new CompositeSerializer().toByteBuffer(composite);
	return byteBuffer;
  }

  @Override
  public Tip fromByteBuffer(ByteBuffer byteBuffer) {
    if (byteBuffer == null) {
      return null;
    }
	Tip obj = new Tip();
	Composite decomposed = new CompositeSerializer().fromByteBuffer(TBaseHelper.rightSize(byteBuffer));
	obj.setRestaurantID(decomposed.get(0, LongSerializer.get()));
	obj.setCheckAmount(decomposed.get(1, DoubleSerializer.get()));
	obj.setTipAmount(decomposed.get(2, DoubleSerializer.get()));
	return obj;
  }

  @Override
  public ComparatorType getComparatorType() {
    return BYTESTYPE;
  }

}
