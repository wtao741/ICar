package com.icar.bean;

import java.io.Serializable;

public class OilRecordEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean checked;  //是否选中
	private int id;
	private String mobile;
	private String chargedate;
	private String mileage;
	private String money;
	private String price;
	private String chargeoil;    //加油量（L）
	private int light;
	private int full;
	private int forget;
	private int disabled;
	private String addtime;
	private String edtime;
	
	public OilRecordEntity() {
		super();
	}
	
	public OilRecordEntity(String chargeoil, String addtime) {
		super();
		this.chargeoil = chargeoil;
		this.addtime = addtime;
	}

	public OilRecordEntity(boolean checked, int id, String mobile,
			String chargedate, String mileage, String money, String price,
			String chargeoil, int light, int full, int forget, int disabled,
			String addtime, String edtime) {
		super();
		this.checked = checked;
		this.id = id;
		this.mobile = mobile;
		this.chargedate = chargedate;
		this.mileage = mileage;
		this.money = money;
		this.price = price;
		this.chargeoil = chargeoil;
		this.light = light;
		this.full = full;
		this.forget = forget;
		this.disabled = disabled;
		this.addtime = addtime;
		this.edtime = edtime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getChargedate() {
		return chargedate;
	}

	public void setChargedate(String chargedate) {
		this.chargedate = chargedate;
	}

	public String getMileage() {
		return mileage;
	}

	public void setMileage(String mileage) {
		this.mileage = mileage;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getChargeoil() {
		return chargeoil;
	}

	public void setChargeoil(String chargeoil) {
		this.chargeoil = chargeoil;
	}

	public int getLight() {
		return light;
	}

	public void setLight(int light) {
		this.light = light;
	}

	public int getFull() {
		return full;
	}

	public void setFull(int full) {
		this.full = full;
	}

	public int getForget() {
		return forget;
	}

	public void setForget(int forget) {
		this.forget = forget;
	}

	public int getDisabled() {
		return disabled;
	}

	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}

	public String getAddtime() {
		return addtime;
	}

	public void setAddtime(String addtime) {
		this.addtime = addtime;
	}

	public String getEdtime() {
		return edtime;
	}

	public void setEdtime(String edtime) {
		this.edtime = edtime;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	@Override
	public String toString() {
		return "OilRecordEntity [checked=" + checked + ", id=" + id
				+ ", mobile=" + mobile + ", chargedate=" + chargedate
				+ ", mileage=" + mileage + ", money=" + money + ", price="
				+ price + ", chargeoil=" + chargeoil + ", light=" + light
				+ ", full=" + full + ", forget=" + forget + ", disabled="
				+ disabled + ", addtime=" + addtime + ", edtime=" + edtime
				+ "]";
	}


}
