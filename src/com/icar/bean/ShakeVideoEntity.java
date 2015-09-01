package com.icar.bean;

public class ShakeVideoEntity {

	private String uu;
	private String vu;
	private String pu;

	public String getUu() {
		return uu;
	}

	public void setUu(String uu) {
		this.uu = uu;
	}

	public String getVu() {
		return vu;
	}

	public void setVu(String vu) {
		this.vu = vu;
	}

	public String getPu() {
		return pu;
	}

	public void setPu(String pu) {
		this.pu = pu;
	}

	public ShakeVideoEntity() {
		super();
	}

	public ShakeVideoEntity(String uu, String vu, String pu) {
		super();
		this.uu = uu;
		this.vu = vu;
		this.pu = pu;
	}

}
