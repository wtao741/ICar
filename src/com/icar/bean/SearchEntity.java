package com.icar.bean;

import java.io.Serializable;

/**
 * @author gisdom
 *搜索结果
 */
public class SearchEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int classid;
	private String classname;
	private String description;
	private String url;

	public SearchEntity() {
		super();
	}

	public SearchEntity(String classname, String description, String url) {
		super();
		this.classname = classname;
		this.description = description;
		this.url = url;
	}

	public SearchEntity(int classid, String classname, String description,
			String url) {
		super();
		this.classid = classid;
		this.classname = classname;
		this.description = description;
		this.url = url;
	}

	public int getClassid() {
		return classid;
	}

	public void setClassid(int classid) {
		this.classid = classid;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
