package com.tataren.main.bean;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;

import com.tataren.main.util.StringUtils;



public class ClassUtils {
	
	public static final Class[] AllPrimativeClasses = new Class[] { int.class,
			short.class, long.class, char.class, boolean.class, double.class,
			float.class, byte.class, Integer.class, Short.class, Long.class,
			Character.class, Boolean.class, Double.class, Float.class,
			Byte.class };

	public static final Class[] DatabaseTypeClasses = new Class[] { int.class,
			short.class, long.class, char.class, boolean.class, double.class,
			float.class, byte.class, Integer.class, Short.class, Long.class,
			Character.class, Boolean.class, Double.class, Float.class,
			Byte.class, String.class,java.util.Date.class,java.sql.Date.class,Timestamp.class, };

	public static final int[] DatabaseTypes = new int[] { Types.INTEGER,
			Types.SMALLINT, Types.INTEGER, Types.CHAR, Types.SMALLINT,
			Types.DOUBLE, Types.FLOAT, Types.SMALLINT, Types.INTEGER,
			Types.SMALLINT, Types.INTEGER, Types.CHAR, Types.SMALLINT,
			Types.DOUBLE, Types.FLOAT, Types.SMALLINT,Types.CHAR, Types.DATE,Types.TIME,Types.TIMESTAMP  };

	public static final Class[] PrimativeClasses = new Class[] { int.class,
			short.class, long.class, char.class, boolean.class, double.class,
			float.class, byte.class };

	public static final Class[] WrapperedPrimativeClasses = new Class[] {
			Integer.class, Short.class, Long.class, Character.class,
			Boolean.class, Double.class, Float.class, Byte.class };

	public static final Object[] EmptyObjectArrays = new Object[] {};

	public static final Class[] EmptyClassArrays = new Class[] {};

	private static class MethodIdentifier {

		private Class claz;

		private Class[] parameterTypes;

		private String name;

		private MethodIdentifier(Class claz, Class[] parameterTypes, String name) {
			this.claz = claz;
			this.parameterTypes = parameterTypes;
			this.name = name;
		}

		public boolean equals(Object obj) {
			MethodIdentifier other = (MethodIdentifier) obj;
			return this.claz == other.claz && this.name.equals(other.name)
					&& Arrays.equals(this.parameterTypes, other.parameterTypes);
		}
	}

	public static Field getField(Class claz, String name) {
		try {
			return claz.getField(name);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Field[] getDeclaredFields(Class claz) {
		return claz.getDeclaredFields();
	}

	public static Field[] getAllDeclaredFields(Class claz) {
		ArrayList list = new ArrayList();
		for (; claz != null; claz = claz.getSuperclass()) {
			Field[] fields = claz.getDeclaredFields();
			for (int i = 0; i < fields.length; i++)
				list.add(fields[i]);
		}
		return (Field[]) list.toArray(new Field[] {});
	}

	// FIXME:to finish
	public static boolean isNumericType(Class claz) {
		return false;
	}

	public static Method getMethod(Class claz, String methodName) {
		return getMethod(claz, EmptyClassArrays, methodName);
	}

	public static Method getGetterMethod(Class claz, String fieldName) {
		return getGetterMethod(claz, EmptyClassArrays, fieldName);
	}

	private static Method getGetterMethod(Class claz, Class[] parameterTypes,
			String fieldName) {

		Method m = getMethod(claz, parameterTypes, "get"
				+ StringUtils.captiallize(fieldName));
		if (m == null)
			m = getMethod(claz, parameterTypes, "is"
					+ StringUtils.captiallize(fieldName));
		return m;
	}

	public static Method getSetterMethod(Class claz, String fieldName) {

		Field field = null;
		try {
			field = claz.getDeclaredField(fieldName);
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (field == null)
			return null;
		return getMethod(claz, new Class[] { field.getType() }, "set"
				+ StringUtils.captiallize(fieldName));
	}

	public static Method getMethod(Class claz, Class[] parameterTypes,
			String methodName) {

		Method[] methods = claz.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method.getName().equals(methodName)) {
				Class[] declaredParameters = method.getParameterTypes();
				if (Arrays.equals(declaredParameters, parameterTypes)) {
					return method;
				}
			}
		}
		return null;
	}
}
