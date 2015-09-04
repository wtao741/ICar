package com.icar.utils;

import java.security.MessageDigest;

public class Encript {

	// ʮ����������ֵ��ַ��ӳ������
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	/** ��inputString���� */
	public static String md5(String inputStr) {
		return encodeByMD5(inputStr);
	}

	/**
	 * ��֤����������Ƿ���ȷ
	 * 
	 * @param password
	 *            ��������루���ܺ�������룩
	 * @param inputString
	 *            ������ַ�
	 * @return ��֤���boolean����
	 */
	public static boolean authenticatePassword(String password,
			String inputString) {
		if (password.equals(encodeByMD5(inputString))) {
			return true;
		} else {
			return false;
		}
	}

	/** ���ַ����MD5���� */
	private static String encodeByMD5(String originString) {
		if (originString != null) {
			try {
				// ��������ָ���㷨��Ƶ���ϢժҪ
				MessageDigest md5 = MessageDigest.getInstance("MD5");
				// ʹ��ָ�����ֽ������ժҪ���������£�Ȼ�����ժҪ����
				byte[] results = md5.digest(originString.getBytes());
				// ���õ����ֽ��������ַ���
				String result = byteArrayToHexString(results);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * �ֻ��ֽ�����Ϊʮ������ַ�
	 * 
	 * @param b
	 *            �ֽ�����
	 * @return ʮ������ַ�
	 */
	private static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	// ��һ���ֽ�ת����ʮ�������ʽ���ַ�
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}
}
