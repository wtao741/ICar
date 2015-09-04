package com.icar.bean;

public class CarCommentEntity {

	private String title;
	private int score;
	
	public CarCommentEntity(String title, int score) {
		super();
		this.title = title;
		this.score = score;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	
	
}
