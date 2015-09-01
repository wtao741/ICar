package com.icar.bean;

import java.util.List;

public class CarBrandEntity {

	private int id;

	private String icon;
	private String name;
	private boolean isChecked; // 是否选中，用来改变背景的
	private List<CarTypeEntity> types;

	private String type;    //用于选择车型用
	private String year;
	private CarSeriesEntity seriesBean;
	
	public CarSeriesEntity getSeriesBean() {
		return seriesBean;
	}

	public void setSeriesBean(CarSeriesEntity seriesBean) {
		this.seriesBean = seriesBean;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}
	
	public CarBrandEntity() {
		super();
	}
	
	public CarBrandEntity(String icon, String name) {
		super();
		this.icon = icon;
		this.name = name;
	}

	public CarBrandEntity(String icon, String name, boolean isChecked,
			List<CarTypeEntity> types) {
		super();
		this.icon = icon;
		this.name = name;
		this.isChecked = isChecked;
		this.types = types;
	}
	
	public CarBrandEntity(int id, String icon, String name, boolean isChecked,
			List<CarTypeEntity> types) {
		super();
		this.id = id;
		this.icon = icon;
		this.name = name;
		this.isChecked = isChecked;
		this.types = types;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isChecked() {
		return isChecked;
	}

	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	public List<CarTypeEntity> getTypes() {
		return types;
	}

	public void setTypes(List<CarTypeEntity> types) {
		this.types = types;
	}

}
