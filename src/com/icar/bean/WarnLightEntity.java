package com.icar.bean;

public class WarnLightEntity {

	private int icon;
	
    private String title;
	
	private String des;
	
	private String answer;
	
	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
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

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public WarnLightEntity() {
		super();
	}

	public WarnLightEntity(int icon, String title, String des, String answer) {
		super();
		this.icon = icon;
		this.title = title;
		this.des = des;
		this.answer = answer;
	}

}
