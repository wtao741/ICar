package com.icar.bean;

import java.io.Serializable;

public class CarYearEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String year;
	private boolean isChekced;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public boolean isChekced() {
		return isChekced;
	}

	public void setChekced(boolean isChekced) {
		this.isChekced = isChekced;
	}

	public CarYearEntity() {
		super();
	}

	public CarYearEntity(String year, boolean isChekced) {
		super();
		this.year = year;
		this.isChekced = isChekced;
	}

}
