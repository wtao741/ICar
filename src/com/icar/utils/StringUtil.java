package com.icar.utils;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	/**
	 * 判断字符串是否为空 包括空格，回车，换行，tab
	 * 
	 * @param params
	 * @return
	 */
	public static boolean isEmpty(String params) {
		if (params == null || "".equals(params)) {
			return true;
		}
		for (int i = 0; i < params.length(); i++) {
			char c = params.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 是否为合法邮箱
	 * 
	 * @param strEmail
	 * @return
	 */
	public static boolean isEmail(String strEmail) {
		return Pattern
				.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*")
				.matcher(strEmail).matches();
	}

	/**
	 * 是否为合法手机号
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isCellphone(String str) {
		return Pattern.compile("1[0-9]{10}").matcher(str).matches();
	}

	/**
	 * 验证用户名是否合法
	 * 
	 * @param username
	 * @return
	 */
	public static boolean isUsernameOk(String username) {
		return Pattern
				.compile(
						"^[a-zA-Z\u4e00-\u9fa5][a-zA-Z0-9\u4e00-\u9fa5]{1,12}$")
				.matcher(username).matches();
	}

	/**
	 * 返回字数的个数
	 * 
	 * @param username
	 * @return
	 */

	public static double getCount(CharSequence s) {
		double num = 0;
		Pattern pattern = Pattern
				.compile("[,，。()！@*.~#!$%^&-_/=\\[\\]\\\\+0123456789]");
		String str = s.toString();
		for (int i = 0; i < str.length(); i++) {
			Matcher matcher = pattern.matcher(str.substring(i, i + 1));
			int s2 = str.charAt(i);
			if ((s2 >= 65 && s2 <= 90) || (s2 >= 97 && s2 <= 122)
					|| matcher.matches()) {
				num += 0.5;
			} else {
				num++;
			}
		}
		return num;
	}

	/**/
	public static int countStr(String str1, String str2, int count) {

		if (str1.indexOf(str2) == -1) {
			return count;
		} else if (str1.indexOf(str2) != -1) {
			count++;
			count = countStr(
					str1.substring(str1.indexOf(str2) + 1, str1.length()),
					str2, count);
			return count;
		}
		return 0;
	}

	/*
	 * 比较版本号：2.1.0 > 2.0.0
	 * 
	 * @param versionName1 versionName2
	 * 
	 * @return 大于 = 1 等于 = 0 小于 = -1
	 */
	public static int compareVersionName(final String versionName1,
			final String versionName2) {
		String[] IntVer1 = versionName1.split("\\.");
		String[] IntVer2 = versionName2.split("\\.");
		int length = IntVer1.length >= IntVer2.length ? IntVer2.length
				: IntVer1.length;
		for (int i = 0; i < length; i++) {
			try {
				if (Integer.parseInt(IntVer1[i]) > Integer.parseInt(IntVer2[i])) {
					return 1;
				} else if (Integer.parseInt(IntVer1[i]) < Integer
						.parseInt(IntVer2[i])) {
					return -1;
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return 0;
			}
		}

		return IntVer1.length != IntVer2.length ? IntVer1.length > IntVer2.length ? 1
				: -1
				: 0;
	}


}
