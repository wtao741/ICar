package com.icar.bean;

public class ResucePhoneEntity {

	private String name;
	private String des;
	private String phoneNumber;
	
	public ResucePhoneEntity() {
		super();
	}
	
	public ResucePhoneEntity(String name, String des, String phoneNumber) {
		super();
		this.name = name;
		this.des = des;
		this.phoneNumber = phoneNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
