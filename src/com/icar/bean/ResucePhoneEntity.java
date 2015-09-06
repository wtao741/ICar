package com.icar.bean;

public class ResucePhoneEntity {

	private int id;
	private String mobile; // 用户手机号
	private String name;
	private String phoneNumber;

	public ResucePhoneEntity() {
		super();
	}
	
	public ResucePhoneEntity(String mobile, String name,
			String phoneNumber) {
		super();
		this.mobile = mobile;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public ResucePhoneEntity(int id,String mobile,String name, String phoneNumber) {
		super();
		this.id = id;
		this.mobile = mobile;
		this.name = name;
		this.phoneNumber = phoneNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "ResucePhoneEntity [id=" + id + ", mobile=" + mobile + ", name="
				+ name + ", phoneNumber=" + phoneNumber + "]";
	}
	

}
