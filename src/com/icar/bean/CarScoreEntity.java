package com.icar.bean;

import java.util.List;

public class CarScoreEntity {

	private String des;

	private List<FunctionItemEntity> params;

	private int score;

	public CarScoreEntity() {
		super();
	}

	public CarScoreEntity(String des, List<FunctionItemEntity> params, int score) {
		super();
		this.des = des;
		this.params = params;
		this.score = score;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public List<FunctionItemEntity> getMaps() {
		return params;
	}

	public void setMaps(List<FunctionItemEntity> params) {
		this.params = params;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
