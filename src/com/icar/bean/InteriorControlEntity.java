package com.icar.bean;

public class InteriorControlEntity {

	private int classid;
	private String icons;
	private String name;
	private String url;

	public int getClassid() {
		return classid;
	}

	public void setClassid(int classid) {
		this.classid = classid;
	}

	public String getIcons() {
		return icons;
	}

	public void setIcons(String icons) {
		this.icons = icons;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public InteriorControlEntity() {
		super();
	}

	public InteriorControlEntity(int classid, String icons, String name,
			String url) {
		super();
		this.classid = classid;
		this.icons = icons;
		this.name = name;
		this.url = url;
	}

}
