package com.icar.bean;

import java.util.List;

public class OfficialEntity {
	private int infoid;
	private int id;
	private String classname;
	private int pagesum;
	private int zipsize;
	private List<OfficialSecEntity> secDatas;

	public OfficialEntity() {
		super();
	}

	public OfficialEntity(String classname, int pagesum, int zipsize,
			List<OfficialSecEntity> secDatas) {
		super();
		this.classname = classname;
		this.pagesum = pagesum;
		this.zipsize = zipsize;
		this.secDatas = secDatas;
	}

	public OfficialEntity(int infoid,int id, String classname, int pagesum, int zipsize,
			List<OfficialSecEntity> secDatas) {
		super();
		this.infoid = infoid;
		this.id = id;
		this.classname = classname;
		this.pagesum = pagesum;
		this.zipsize = zipsize;
		this.secDatas = secDatas;
	}

	public int getInfoid() {
		return infoid;
	}

	public void setInfoid(int infoid) {
		this.infoid = infoid;
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

	public int getPagesum() {
		return pagesum;
	}

	public void setPagesum(int pagesum) {
		this.pagesum = pagesum;
	}

	public int getZipsize() {
		return zipsize;
	}

	public void setZipsize(int zipsize) {
		this.zipsize = zipsize;
	}

	public List<OfficialSecEntity> getSecDatas() {
		return secDatas;
	}

	public void setSecDatas(List<OfficialSecEntity> secDatas) {
		this.secDatas = secDatas;
	}

	@Override
	public String toString() {
		return "OfficialEntity [id=" + id + ", classname=" + classname
				+ ", pagesum=" + pagesum + ", zipsize=" + zipsize
				+ ", secDatas=" + secDatas + "]";
	}

}
