package com.tataren.main.domain.user;

import java.io.Serializable;
import java.sql.Timestamp;

import com.tataren.main.domain.Entity;

public class TMessage extends Entity implements Serializable {

	

	private String creator;

	private String title;
	
	private String message;

	private String address;
	
	private String mobile;
	
	private String company;
	
	private String email;
	
	private String loginName;
	
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	private String tel;
	
	private String fax;
	
	private Timestamp createdate;
	
	private String state;
	
	private String sex;
	
	private Timestamp operatordate;
	
	private String operator;
	
	private String mongolia;

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public Timestamp getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Timestamp getOperatordate() {
		return operatordate;
	}

	public void setOperatordate(Timestamp operatordate) {
		this.operatordate = operatordate;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getMongolia() {
		return mongolia;
	}

	public void setMongolia(String mongolia) {
		this.mongolia = mongolia;
	}
		
	
}
