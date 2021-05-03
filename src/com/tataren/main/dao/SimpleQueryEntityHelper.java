package com.tataren.main.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.tataren.main.domain.Entity;



public class SimpleQueryEntityHelper extends AbstractEntityHelper {

	private Entity bean;

	class WhereInfo {
		String columnName;

		String operator;

		Object value;

		Field field;

		int filedType;

		public String toString() {
			// if (value.getClass().equals(Boolean.class)
			// || value.getClass().equals(boolean.class))
			// return columnName + operator + " ? ";
			// else

			if (doSimilaryQuery()) {
				if (value.getClass().equals(String.class)) {
					if (operator.equals(" >= ") || operator.equals(" <= ")) {
						return columnName + operator + " ? ";
					}
					// similar query for all stirng type
					value = "%" + value.toString() + "%";
					// columnName = " lower(" + columnName + ") ";
					return "lower(" + columnName + ")" + operator
							+ " lower( ? ) ";
				}
			}
			return columnName + operator + " ? ";
		}

	}

	protected boolean doSimilaryQuery() {
		// TODO: how to process case sensitive?
		return true;
	}

	public SimpleQueryEntityHelper(Entity bean) {
		this.bean = bean;

	}

	protected void doCompile() {
		// TODO: use excluded fields from configuration file,especially for
		// similary-query fields
		Class claz = bean.getClass();
		Field[] fields = getFields(claz);

		Map map = new LinkedHashMap();

		for (int i = 0; i < fields.length; i++) {
			Field f = (Field) fields[i];
			WhereInfo wi = new WhereInfo();
			wi.columnName = f.getName();
			wi.filedType = getType(f.getType());
			wi.field = f;
			if (f.getType().equals(String.class)) {
				wi.operator = " like ";
			} else {
				wi.operator = " = ";
			}
			map.put(f.getName(), wi);
		}
		for (int i = 0; i < fields.length; i++) {
			String fn = fields[i].getName();
			if (map.containsKey(fn + "To") && map.containsKey(fn + "From")) {

				map.remove(fn);
				WhereInfo wi = new WhereInfo();
				wi.columnName = fn;
				wi.operator = " <= ";

				wi.filedType = getType(fields[i].getType());
				for (int j = 0; j < fields.length; j++) {
					if (fields[j].getName().equals(fn + "To"))
						wi.field = fields[j];
				}
				map.put(fn + "To", wi);

				wi = new WhereInfo();
				wi.columnName = fn;
				wi.operator = " >= ";

				wi.filedType = getType(fields[i].getType());
				for (int j = 0; j < fields.length; j++) {
					if (fields[j].getName().equals(fn + "From"))
						wi.field = fields[j];
				}
				//wi.field = fields[i];
				map.put(fn + "From", wi);
			}
		}

		ArrayList wiList = new ArrayList();
		for (Iterator iter = map.keySet().iterator(); iter.hasNext();) {
			String fieldName = iter.next().toString();
			Object value = null;
			try {
				Method method = this.getFieldMethod(claz, ((WhereInfo) map
						.get(fieldName)).field);
				if (isExcludedMethod(method.getName())) {
					continue;
				}

				value = this.invokeValue(bean, method);
				// value = method.invoke(bean, ClassUtils.EmptyObjectArrays);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (value == null)
				continue;

			if (value.getClass().equals(String.class)
					&& value.toString().trim().length() == 0) {
				continue;
			}

			WhereInfo wi = (WhereInfo) map.get(fieldName);
			wi.value = value;
			wiList.add(wi);
		}

		StringBuffer sb = new StringBuffer();
		sb.append(" select * from  ");
		sb.append(bean.getTableName());
		sb.append(" where (1=1  ");
		for (int i = 0; i < wiList.size(); i++) {
			WhereInfo wi = (WhereInfo) wiList.get(i);
			sb.append(" and ");
			sb.append(wi);
		}
		sb.append(')');
		String sql = sb.toString();

		Object[] values = new Object[wiList.size()];
		int[] types = new int[wiList.size()];
		for (int i = 0; i < wiList.size(); i++) {
			WhereInfo wi = (WhereInfo) wiList.get(i);
			if (wi.value instanceof String && wi.value != null)
				wi.value = String.valueOf(wi.value).trim();
			values[i] = wi.value;
			types[i] = wi.filedType;
		}

		setResult(sql, values, types);

	}
}
