package com.tataren.main.domain.user;

import com.tataren.main.domain.Entity;
import java.io.Serializable;
import java.sql.Timestamp;

public class WebUser extends Entity
  implements Serializable
{
  private String loginName;
  private String userName;
  private String pwd;
  private Integer RIGHTT;
  private String departMent;
  private String address;
  private String email;
  private String mobile;
  private String tel;
  private String fax;
  private Timestamp createDate;
  private String state;
  private String sex;
  private String operator;
  private Integer account;

  public Integer getAccount()
  {
    return this.account;
  }

  public void setAccount(Integer account) {
    this.account = account;
  }

  public String getUserName() {
    return this.userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Timestamp getCreateDate() {
    return this.createDate;
  }

  public void setCreateDate(Timestamp createDate) {
    this.createDate = createDate;
  }

  public String getState() {
    return this.state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getOperator() {
    return this.operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getSex()
  {
    return this.sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getEmail()
  {
    return this.email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getFax()
  {
    return this.fax;
  }

  public void setFax(String fax)
  {
    this.fax = fax;
  }

  public String getLoginName()
  {
    return this.loginName;
  }

  public void setLoginName(String loginName)
  {
    this.loginName = loginName;
  }

  public String getMobile()
  {
    return this.mobile;
  }

  public void setMobile(String mobile)
  {
    this.mobile = mobile;
  }

  public String getPwd()
  {
    return this.pwd;
  }

  public void setPwd(String pwd)
  {
    this.pwd = pwd;
  }

  public String getTel()
  {
    return this.tel;
  }

  public void setTel(String tel)
  {
    this.tel = tel;
  }

  public Integer getRIGHTT() {
    return this.RIGHTT;
  }

  public void setRIGHTT(Integer RIGHTT) {
    this.RIGHTT = RIGHTT;
  }

  public String getDepartMent() {
    return this.departMent;
  }

  public void setDepartMent(String departMent) {
    this.departMent = departMent;
  }
}