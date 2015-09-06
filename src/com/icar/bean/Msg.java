package com.icar.bean;

import java.io.Serializable;



/**
 * 聊天消息实体类
 * 
 * @author mitbbs
 * 
 */
public class Msg implements Serializable {
	private static final String TAG = "Msg";
	/* 消息报文字段 */
	private int msgType;
	private long postTime;
	private String content;
	private String user;
	private String path;         //路径
	private int mediaLength = 0; // 多媒体长度，单位秒
	public int getMsgType() {
		return msgType;
	}
	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}
	public long getPostTime() {
		return postTime;
	}
	public void setPostTime(long postTime) {
		this.postTime = postTime;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getMediaLength() {
		return mediaLength;
	}
	public void setMediaLength(int mediaLength) {
		this.mediaLength = mediaLength;
	}

}
