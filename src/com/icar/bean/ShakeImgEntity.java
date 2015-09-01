package com.icar.bean;

import android.os.Parcel;
import android.os.Parcelable;


public class ShakeImgEntity implements Parcelable {

	private String url;
	private String des;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public ShakeImgEntity() {
		super();
	}

	public ShakeImgEntity(String url, String des) {
		super();
		this.url = url;
		this.des = des;
	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		// TODO Auto-generated method stub
		out.writeString(url);
		out.writeString(des);
	}

	public static final Parcelable.Creator<ShakeImgEntity> CREATOR = new Creator<ShakeImgEntity>() {
		@Override
		public ShakeImgEntity[] newArray(int size) {
			return new ShakeImgEntity[size];
		}

		@Override
		public ShakeImgEntity createFromParcel(Parcel in) {
			return new ShakeImgEntity(in);
		}
	};

	public ShakeImgEntity(Parcel in) {
		url = in.readString();
		des = in.readString();
	}
}
