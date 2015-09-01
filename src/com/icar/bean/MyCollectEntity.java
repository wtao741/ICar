package com.icar.bean;

public class MyCollectEntity {

	private int icons;
	private String title;
	private String des;
	private boolean checked;  //是否选中
	
	public MyCollectEntity() {
		super();
	}
	
	public MyCollectEntity(int icons, String title, String des,
			boolean checked) {
		super();
		this.icons = icons;
		this.title = title;
		this.des = des;
		this.checked = checked;
	}

	public int getIcons() {
		return icons;
	}
	public void setIcons(int icons) {
		this.icons = icons;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDes() {
		return des;
	}
	public void setDes(String des) {
		this.des = des;
	}
	
	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}
