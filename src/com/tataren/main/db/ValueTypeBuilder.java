
package com.tataren.main.db;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class ValueTypeBuilder {

	private List values;

	private List types;

	public ValueTypeBuilder() {
		values = new ArrayList();
		types = new ArrayList();
	}

	public Object[] getValues() {
		return values.toArray();
	}

	public int[] getTypes() {
		int[] t = new int[types.size()];
		for (int i = 0; i < t.length; i++) {
			t[i] = ((Integer) types.get(i)).intValue();
		}
		return t;
	}

	public ValueTypeBuilder b(String value) {
		values.add(value);
		types.add(new Integer(Types.VARCHAR));
		// ??? types.add(new Integer(Types.CHAR));
		return this;
	}

	public ValueTypeBuilder b(Integer value) {
		values.add(value);
		types.add(new Integer(Types.INTEGER));
		return this;
	}

	public ValueTypeBuilder b(Boolean value) {
		values.add(value);
		// types.add(new Integer(Types.SMALLINT));
		types.add(new Integer(Types.BOOLEAN));
		return this;
	}

	public ValueTypeBuilder b(Short value) {
		values.add(value);
		types.add(new Integer(Types.SMALLINT));
		return this;
	}

	public ValueTypeBuilder b(Float value) {
		values.add(value);
		types.add(new Integer(Types.FLOAT));
		return this;
	}

	public ValueTypeBuilder b(Double value) {
		values.add(value);
		types.add(new Integer(Types.DOUBLE));
		return this;
	}

	public ValueTypeBuilder b(int value) {
		values.add(new Integer(value));
		types.add(new Integer(Types.INTEGER));
		return this;
	}

	public ValueTypeBuilder b(boolean value) {
		values.add(new Boolean(value));
		// types.add(new Integer(Types.SMALLINT));
		types.add(new Integer(Types.BOOLEAN));
		return this;
	}

	public ValueTypeBuilder b(short value) {
		values.add(new Short(value));
		types.add(new Integer(Types.SMALLINT));
		return this;
	}

	public ValueTypeBuilder b(float value) {
		values.add(new Float(value));
		types.add(new Integer(Types.FLOAT));
		return this;
	}

	public ValueTypeBuilder b(double value) {
		values.add(new Float(value));
		types.add(new Integer(Types.DOUBLE));
		return this;
	}

	public ValueTypeBuilder b(Date value) {
		values.add(value);
		types.add(new Integer(Types.DATE));
		return this;
	}

	public ValueTypeBuilder b(Timestamp value) {
		values.add(value);
		types.add(new Integer(Types.TIMESTAMP));
		return this;
	}

	public ValueTypeBuilder b(int[] values) {
		for (int i = 0; i < values.length; i++)
			b(values[i]);
		return this;
	}

	public ValueTypeBuilder b(boolean[] values) {
		for (int i = 0; i < values.length; i++)
			b(values[i]);
		return this;
	}

	public ValueTypeBuilder b(short[] values) {
		for (int i = 0; i < values.length; i++)
			b(values[i]);
		return this;
	}

	public ValueTypeBuilder b(float[] values) {
		for (int i = 0; i < values.length; i++)
			b(values[i]);
		return this;
	}

	public ValueTypeBuilder b(double[] values) {
		for (int i = 0; i < values.length; i++)
			b(values[i]);
		return this;
	}

	public ValueTypeBuilder b(Date[] values) {
		for (int i = 0; i < values.length; i++)
			b(values[i]);
		return this;
	}

	public ValueTypeBuilder b(Timestamp[] values) {
		for (int i = 0; i < values.length; i++)
			b(values[i]);
		return this;
	}

	public ValueTypeBuilder b(Integer[] values) {
		for (int i = 0; i < values.length; i++)
			b(values[i]);
		return this;
	}

	public ValueTypeBuilder b(Boolean[] values) {
		for (int i = 0; i < values.length; i++)
			b(values[i]);
		return this;
	}

	public ValueTypeBuilder b(Short[] values) {
		for (int i = 0; i < values.length; i++)
			b(values[i]);
		return this;
	}

	public ValueTypeBuilder b(Float[] values) {
		for (int i = 0; i < values.length; i++)
			b(values[i]);
		return this;
	}

	public ValueTypeBuilder b(Double[] values) {
		for (int i = 0; i < values.length; i++)
			b(values[i]);
		return this;
	}

}
