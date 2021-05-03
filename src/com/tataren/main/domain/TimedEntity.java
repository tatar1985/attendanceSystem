package com.tataren.main.domain;

import java.sql.Timestamp;


public abstract class TimedEntity extends Entity {

	// the id
	private Timestamp systemTime;

	private Timestamp systemTimeFrom;

	private Timestamp systemTimeTo;
	
//	private Timestamp createTime;
//
//	private Timestamp updateTime;


	public TimedEntity() {
		
		//FIXME: no auto create!
		//systemTime = new Timestamp(System.currentTimeMillis());
	}

	public Timestamp getSystemTime() {
		return systemTime;
	}

	public void setSystemTime(Timestamp systemTime) {
		this.systemTime = systemTime;
	}

	public Timestamp getSystemTimeFrom() {
		return systemTimeFrom;
	}

	public Timestamp getSystemTimeTo() {
		return systemTimeTo;
	}

	public void setSystemTimeFrom(Timestamp systemTimeFrom) {
		this.systemTimeFrom = systemTimeFrom;
	}

	public void setSystemTimeTo(Timestamp systemTimeTo) {
		this.systemTimeTo = systemTimeTo;
	}

}
