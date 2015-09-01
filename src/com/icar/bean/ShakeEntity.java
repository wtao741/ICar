package com.icar.bean;

public class ShakeEntity {

	private int icons;
	private String des;

	public int getIcons() {
		return icons;
	}

	public void setIcons(int icons) {
		this.icons = icons;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public ShakeEntity() {
		super();
	}

	public ShakeEntity(int icons, String des) {
		super();
		this.icons = icons;
		this.des = des;
	}

}
