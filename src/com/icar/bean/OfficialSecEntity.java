package com.icar.bean;

public class OfficialSecEntity {

	private int id;
	private String classname;
	private int parentid;
	private int startpage; // 起始页
	private int endpage;

	public OfficialSecEntity() {
		super();
	}

	public OfficialSecEntity(String classname, int parentid, int startpage,
			int endpage) {
		super();
		this.classname = classname;
		this.parentid = parentid;
		this.startpage = startpage;
		this.endpage = endpage;
	}

	public OfficialSecEntity(int id, String classname, int parentid,
			int startpage, int endpage) {
		super();
		this.id = id;
		this.classname = classname;
		this.parentid = parentid;
		this.startpage = startpage;
		this.endpage = endpage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public int getParentid() {
		return parentid;
	}

	public void setParentid(int parentid) {
		this.parentid = parentid;
	}

	public int getStartpage() {
		return startpage;
	}

	public void setStartpage(int startpage) {
		this.startpage = startpage;
	}

	public int getEndpage() {
		return endpage;
	}

	public void setEndpage(int endpage) {
		this.endpage = endpage;
	}
}
