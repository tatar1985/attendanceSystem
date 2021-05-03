
package com.tataren.main.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import com.tataren.main.domain.Entity;



public class UpdateEntityHelper extends AbstractEntityHelper {

	private Entity bean;

	public UpdateEntityHelper(Entity bean) {
		this.bean = bean;
	}

	protected void doCompile() {
		// TODO: use excluded fields from configuration file
		Class claz = bean.getClass();
		Field[] fields = this.getFields(claz);
		Map map = new HashMap();
		for (int i = 0; i < fields.length; i++) {
			Field f = (Field) fields[i];
			map.put(f.getName(), f);
		}
		for (int i = 0; i < fields.length; i++) {
			String fn = fields[i].getName();
			if (map.containsKey(fn + "To") && map.containsKey(fn + "From")) {
				map.remove(fn + "To");
				map.remove(fn + "From");
			}
		}
		Object[] values = new Object[map.size() + 1];
		int[] types = new int[map.size() + 1];
		StringBuffer sb = new StringBuffer();
		sb.append(" update ");
		sb.append(bean.getTableName());
		sb.append(" set ");

		fields = (Field[]) map.values().toArray(new Field[] {});
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			sb.append(f.getName());
			sb.append("=?,");
			Object value = null;
			try {
				Method method = this.getFieldMethod(claz, f);
				if (isExcludedMethod(method.getName())) {
					continue;
				}
				value = this.invokeValue(bean, method);
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
			if (value instanceof String && value != null)
				value = String.valueOf(value).trim();
			values[i] = value;
			types[i] = getType(f.getType());
		}
		sb = new StringBuffer(sb.toString().substring(0,
				sb.toString().length() - 1));
		sb.append(" where sid = ?");
		String sql = sb.toString();
		values[values.length - 1] = bean.getSid();
		types[types.length - 1] = Types.INTEGER;

		setResult(sql, values, types);
	}

}
