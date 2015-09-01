package com.icar.bean;

public class ShakeEditEntity {

	private String author;
	private String avatar;
	private String content;

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ShakeEditEntity() {
		super();
	}

	public ShakeEditEntity(String author, String avatar, String content) {
		super();
		this.author = author;
		this.avatar = avatar;
		this.content = content;
	}

}
