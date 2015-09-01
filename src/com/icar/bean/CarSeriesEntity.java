package com.icar.bean;

import java.io.Serializable;

public class CarSeriesEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	private String showname;  //name
	private String imgUrl;    //pictrue url
	private String infoid;
	
	public CarSeriesEntity() {
		super();
	}
	
	public CarSeriesEntity(String showname, String imgUrl, String infoid) {
		super();
		this.showname = showname;
		this.imgUrl = imgUrl;
		this.infoid = infoid;
	}

	public CarSeriesEntity(int id, String showname, String imgUrl, String infoid) {
		super();
		this.id = id;
		this.showname = showname;
		this.imgUrl = imgUrl;
		this.infoid = infoid;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getShowname() {
		return showname;
	}
	public void setShowname(String showname) {
		this.showname = showname;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public String getInfoid() {
		return infoid;
	}
	public void setInfoid(String infoid) {
		this.infoid = infoid;
	}
}
