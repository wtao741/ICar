package com.icar.bean;

/*
 * 车震，功能中每一个item
 */
public class FunctionItemEntity {

	private String name;
	private String score;
	private boolean change; // 是否变成蓝色

	public FunctionItemEntity(String name, String score, boolean change) {
		super();
		this.name = name;
		this.score = score;
		this.change = change;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}

}
