package com.icar.utils;

import java.util.Timer;

import android.util.Log;


/**
 *用户信息，含cookie
 */
public class DataModel {
	private String userName = null;
	private String userloginTime = null;
	private String serverName = null;
	private String sessionID = null;

	private Integer UTMPKEY = null;
	private Integer UTMPNUM = null;
	private String UTMPUSERID = null;
	private Integer LOGINTIME = null;

	// 用于处理cookie是否过期
	public Timer LASTACCESS = null;

	public DataModel() {
		// TODO Auto-generated constructor stub
		setUserName("guest");
		setServerName("");
		setSessionID("");
		setUTMPKEY(Integer.parseInt("0"));
		setUTMPNUM(Integer.parseInt("0"));
		setUTMPUSERID("");
		setLOGINTIME(Integer.parseInt("0"));

		LASTACCESS = null;
	}

	public void clear() {

		setUserName("guest");
		setServerName("");
		setSessionID("");
		setUTMPKEY(Integer.parseInt("0"));
		setUTMPNUM(Integer.parseInt("0"));
		setUTMPUSERID("");
		setLOGINTIME(Integer.parseInt("0"));

		LASTACCESS = null;
	}

	public void setUserName(String userName) {
		Log.e("","usernameaa" +userName);
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public String getUserloginTime() {
		return userloginTime;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setUTMPKEY(Integer uTMPKEY) {
		UTMPKEY = uTMPKEY;
	}

	public Integer getUTMPKEY() {
		return UTMPKEY;
	}

	public void setUTMPNUM(Integer uTMPNUM) {
		UTMPNUM = uTMPNUM;
	}

	public Integer getUTMPNUM() {
		return UTMPNUM;
	}

	public void setUTMPUSERID(String uTMPUSERID) {
		UTMPUSERID = uTMPUSERID;
	}

	public String getUTMPUSERID() {
		return UTMPUSERID;
	}

	public void setLOGINTIME(Integer lOGINTIME) {
		LOGINTIME = lOGINTIME;
	}

	public Integer getLOGINTIME() {
		return LOGINTIME;
	}
}
