package com.icar.utils;

import com.icar.activity.R;

import android.content.Context;

/**
 * 
 * @author wangc 通用的异常处理类
 *
 *         messge,异常描述
 * 
 *         code, 异常代码 RESULT_NET_BAD RESULT_UN_BAD
 * 
 */
public class WSError extends Throwable {

	public static final int RESULT_NET_BAD = 0; /* 网络异常 */
	public static final int RESULT_UN_BAD = 1; /* 程序内部错误 */
	public static final int RESULT_NO_DATA = 2; /* 没有数据 */
	public static final int RESULT_NO_PERMISS = 3; /* 没有权限 */
	public static final int DATA_BAD = 5; // 数据错误。
	public static final int AES_BAD = 4; // 加密解密异常。

	private static final long serialVersionUID = 1L;
	private String message;
	private int code;

	public WSError() {
	}

	public WSError(String message, int code) {
		this.message = message;
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public int getCode() {
		return code;
	}

	/*
	 * 根据当前异常返回各种提示信息
	 */
	public String getTip(Context context) {
		if (code == RESULT_NET_BAD) {
			return context.getString(R.string.net_bad);
		} else if (code == RESULT_UN_BAD) {
			return context.getString(R.string.error);
		} else if (code == RESULT_NO_DATA) {
			return context.getString(R.string.no_data);
		} else if (code == RESULT_NO_PERMISS) {
			return context.getString(R.string.no_permiss);
		} else if (code == AES_BAD) {
			return "加密异常";
		} else if (code == DATA_BAD) {
			return "数据错误，请重试";
		} else {
			return "未知异常";
		}
	}
}
