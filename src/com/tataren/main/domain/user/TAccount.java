package com.tataren.main.domain.user;

import java.io.Serializable;
import java.sql.Timestamp;

import com.tataren.main.domain.Entity;

public class TAccount extends Entity implements Serializable {

	
	private String user_id;
	private String user_name;
	private String money;
	private Integer beaccount;
	private Integer enaccount;
	private Timestamp chargetime;
	
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public Integer getBeaccount() {
		return beaccount;
	}
	public void setBeaccount(Integer beaccount) {
		this.beaccount = beaccount;
	}
	public Integer getEnaccount() {
		return enaccount;
	}
	public void setEnaccount(Integer enaccount) {
		this.enaccount = enaccount;
	}
	public Timestamp getChargetime() {
		return chargetime;
	}
	public void setChargetime(Timestamp chargetime) {
		this.chargetime = chargetime;
	}
	
	
}
