package com.tataren.main.domain;

import org.apache.commons.lang.builder.ToStringBuilder;


public abstract class Entity implements Cloneable {

	// the id
	private Integer sid;

	public String getTableName() {
		//		return getClass().getSimpleName();
		String className = getClass().getName();

		className = className.substring(className.lastIndexOf(".") + 1);
		return className;

	}

	public boolean isNew() {
		return this.sid == null || sid.intValue() == 0;
	}

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	public void setSid(int sid) {
		this.sid = new Integer(sid);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public Object clone() {
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			// this shouldn't happen, since we are Cloneable
			throw new InternalError();
		}
	}
}
