package com.icar.bean;

import java.util.List;

public class CarTypeEntity {

	private int id;
	private String name;
	private List<CarSeriesEntity> datas;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CarSeriesEntity> getDatas() {
		return datas;
	}

	public void setDatas(List<CarSeriesEntity> datas) {
		this.datas = datas;
	}

	// private int[] icons;
	// private List<String> names;
	// private ArrayList<String> years; //哪年生产的
	//
	// public ArrayList<String> getYears() {
	// return years;
	// }
	//
	// public void setYears(ArrayList<String> years) {
	// this.years = years;
	// }

	public CarTypeEntity() {
		super();
	}

	public CarTypeEntity(String name, List<CarSeriesEntity> datas) {
		super();
		this.name = name;
		this.datas = datas;
	}

	public CarTypeEntity(int id, String name, List<CarSeriesEntity> datas) {
		super();
		this.id = id;
		this.name = name;
		this.datas = datas;
	}

}
