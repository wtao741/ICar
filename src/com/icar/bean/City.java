package com.icar.bean;

import java.io.Serializable;

public class City implements Comparable<City> ,Serializable{
	/**
	CREATE TABLE "jiche_city" (
		 "Id" integer NOT NULL,
		 "name" text,
		 "pId" integer,
		 "lat" text,
		 "lon" text,
		 "letter" text,
		PRIMARY KEY("Id")
	);
	INSERT INTO "jiche_city" VALUES (1, '北京市', 1, 39.908497, 116.397434, 'B');
	 */
	private String id;
	private String name;
	private int pId;
	private String lat;
	private String lon;
	private String letter;
	
	public City() {
		super();
	}
	public City(String id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	public City(String id, String name, int pId, String lat, String lon,
			String letter) {
		super();
		this.id = id;
		this.name = name;
		this.pId = pId;
		this.lat = lat;
		this.lon = lon;
		this.letter = letter;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getpId() {
		return pId;
	}
	public void setpId(int pId) {
		this.pId = pId;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	public String getLetter() {
		return letter.toCharArray()[0]+"";
	}
	public void setLetter(String letter) {
		this.letter = letter;
	}
	@Override
	public String toString() {
		return "City [id=" + id + ", name=" + name + ", pId=" + pId + ", lat="
				+ lat + ", lon=" + lon + ", letter=" + letter + "]";
	}
	@Override
	public int compareTo(City another) {
		return letter.toCharArray()[0]-another.letter.toCharArray()[0];
	}
	
}
