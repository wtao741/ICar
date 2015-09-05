package com.icar.bean;

import java.io.Serializable;

import android.graphics.Bitmap;

public class ImageBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String picPath = "";
	Bitmap imgBitmap = null;
	String type;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public Bitmap getImgBitmap() {
		return imgBitmap;
	}

	public void setImgBitmap(Bitmap imgBitmap) {
		this.imgBitmap = imgBitmap;
	}

	public ImageBean() {
		super();
	}

	public ImageBean(String picPath, Bitmap imgBitmap, String type) {
		super();
		this.picPath = picPath;
		this.imgBitmap = imgBitmap;
		this.type = type;
	}

}
