package com.tataren.main.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.tataren.main.domain.Entity;



public class CreatationEntityHelper extends AbstractEntityHelper {

	private Entity bean;

	public CreatationEntityHelper(Entity bean) {
		this.bean = bean;
	}

	protected void doCompile() {
		// TODO: use excluded fields from configuration file

		Class claz = bean.getClass();
		Field[] fields = getFields(claz);
		Map map = new HashMap();
		for (int i = 0; i < fields.length; i++) {
			Field f = (Field) fields[i];
			Class type = f.getType();
			if (!inDatabaseClass(type)) {
				continue;
			}

			map.put(f.getName(), f);
		}
		for (int i = 0; i < fields.length; i++) {
			String fn = fields[i].getName();
			if (map.containsKey(fn + "To") && map.containsKey(fn + "From")) {
				map.remove(fn + "To");
				map.remove(fn + "From");
			}
		}
		Object[] values = new Object[map.size()];
		int[] types = new int[map.size()];
		StringBuffer sb = new StringBuffer();
		sb.append(" insert into ");
		sb.append(bean.getTableName());
		sb.append("(");
		fields = (Field[]) map.values().toArray(new Field[] {});
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			sb.append(f.getName());
			sb.append(',');
			Object value = null;
			try {
				Method method = this.getFieldMethod(claz, f);
				if (isExcludedMethod(method.getName())) {
					continue;
				}
				value = this.invokeValue(bean, method);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
			if (value instanceof String && value != null)
				value = String.valueOf(value).trim();
			values[i] = value;
			types[i] = getType(f.getType());
		}
		sb = new StringBuffer(sb.toString().substring(0,
				sb.toString().length() - 1));
		sb.append(") values (");
		for (int i = 0; i < fields.length - 1; i++) {
			sb.append("?,");
		}
		sb.append("?)");
		String sql = sb.toString();

		setResult(sql, values, types);
	}

}
