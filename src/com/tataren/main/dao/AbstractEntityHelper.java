package com.tataren.main.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.tataren.main.bean.ClassUtils;
import com.tataren.main.util.StringUtils;
import com.tataren.main.domain.Entity;


public abstract class AbstractEntityHelper {

	protected Logger logger = Logger.getLogger(AbstractEntityHelper.class);

	private Set excludedFeilds;

	private Set excludedMethods;

	private boolean isCompiled;

	private String sql;

	private Object[] values;

	private int[] types;

	public AbstractEntityHelper() {
		isCompiled = false;
		excludedFeilds = new HashSet();
		excludedMethods = new HashSet();
	}

	protected void setResult(String sql, Object[] values, int[] types) {
		this.sql = sql;
		this.values = values;
		this.types = types;

	}

	protected abstract void doCompile();

	private void compile() {
		if (!isCompiled) {
			doCompile();
		}
		isCompiled = true;
	}

	public final String getSql() {
		compile();
		return sql;
	}

	public final Object[] getValues() {
		compile();
		return values;
	}

	public final int[] getTypes() {
		compile();
		return types;
	}

	public final AbstractEntityHelper addExcludedFeild(String field) {
		excludedFeilds.add(field);
		return this;
	}

	public final AbstractEntityHelper addExcludedMethod(String method) {
		excludedMethods.add(method);
		return this;
	}

	protected final Method getFieldMethod(Class claz, Field field)
			throws SecurityException, NoSuchMethodException {
		if (field.getType().equals(Boolean.class)
				|| field.getType().equals(boolean.class)) {
			try {
				Method method = claz.getMethod("get"
						+ StringUtils.captiallize(field.getName()),
						ClassUtils.EmptyClassArrays);
				return method;
			} catch (Exception e) {

			}
			Method method = claz.getMethod(field.getName(),
					ClassUtils.EmptyClassArrays);
			return method;
		} else {
			Method method = claz.getMethod("get"
					+ StringUtils.captiallize(field.getName()),
					ClassUtils.EmptyClassArrays);
			return method;
		}
	}

	protected final Object invokeValue(Entity bean, Method method)
			throws IllegalArgumentException, IllegalAccessException,
			InvocationTargetException {
		Object value = method.invoke(bean, ClassUtils.EmptyObjectArrays);
		if (value instanceof Boolean)
			return value.equals(Boolean.TRUE) ? new Integer(1) : new Integer(0);

		return value;
	}

	protected final Field[] getFields(Class claz) {
		Field[] fields = ClassUtils.getAllDeclaredFields(claz);
		List list = new ArrayList(fields.length);
		for (int i = 0; i < fields.length; i++) {
			Field f = fields[i];
			int modifiers = f.getModifiers();
//			if (Modifier.isPublic(modifiers)) {
//				continue;
//			}
			if (Modifier.isNative(modifiers)) {
				continue;
			}
			if (Modifier.isStatic(modifiers)) {
				continue;
			}
			if (isExcludedField(f.getName())) {
				continue;
			}
			Class type = f.getType();
			if (!inDatabaseClass(type)) {
				continue;
			}
			list.add(f);
		}
		return (Field[]) list.toArray(new Field[] {});

	}

	protected final boolean isExcludedField(String field) {
		return excludedFeilds.contains(field);
	}

	protected final boolean isExcludedMethod(String method) {
		return excludedMethods.contains(method);
	}

	protected int getType(Class claz) {
		for (int i = 0; i < ClassUtils.DatabaseTypeClasses.length; i++) {
			if (claz == ClassUtils.DatabaseTypeClasses[i])
				return ClassUtils.DatabaseTypes[i];
		}
		throw new IllegalArgumentException(" no such class: " + claz);
	}

	protected final boolean inDatabaseClass(Class type) {
		for (int i = 0; i < ClassUtils.DatabaseTypeClasses.length; i++) {
			if (type.equals(ClassUtils.DatabaseTypeClasses[i]))
				return true;
		}
		return false;
	}
}
