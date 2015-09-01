package com.icar.bean;

/*
 * 事故判责实体
 */
public class FaultLiabilityEntity {

	private int icon;
	private String des;
	private boolean isClose;     //是否打开

	public boolean isClose() {
		return isClose;
	}

	public void setClose(boolean isClose) {
		this.isClose = isClose;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int icon) {
		this.icon = icon;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public FaultLiabilityEntity() {
		super();
	}

	public FaultLiabilityEntity(int icon, String des, boolean isClose) {
		super();
		this.icon = icon;
		this.des = des;
		this.isClose = isClose;
	}

}
