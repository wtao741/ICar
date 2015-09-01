package com.icar.bean;

public class OilHistoryEntity {

	private String date;
	private String money;
	private String avg;
	
	public OilHistoryEntity(String date, String money, String avg) {
		super();
		this.date = date;
		this.money = money;
		this.avg = avg;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getAvg() {
		return avg;
	}

	public void setAvg(String avg) {
		this.avg = avg;
	}
	
}
