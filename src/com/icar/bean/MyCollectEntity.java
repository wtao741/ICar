package com.icar.bean;

public class MyCollectEntity {

	private int id;
	private String imgurl;
	private String title;
	private String des;
	private String url;
	private String type;
	private boolean checked;  //是否选中
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getImgurl() {
		return imgurl;
	}
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
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
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public MyCollectEntity() {
		super();
	}
	public MyCollectEntity(int id, String imgurl, String title, String des,
			String url, String type, boolean checked) {
		super();
		this.id = id;
		this.imgurl = imgurl;
		this.title = title;
		this.des = des;
		this.url = url;
		this.type = type;
		this.checked = checked;
	}
}
