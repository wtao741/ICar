package com.icar.bean;

public class AddicentHistoryEntity {

	private int icon;
	private String date;
	private String address;
	private boolean checked;  //是否选中 
	
	public AddicentHistoryEntity() {
		super();
	}

	public AddicentHistoryEntity(int icon, String date, String address
			,boolean checked) {
		super();
		this.icon = icon;
		this.date = date;
		this.address = address;
		this.checked = checked;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
