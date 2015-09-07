package com.icar.utils;

import java.io.File;

import com.icar.base.BaseApplication;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class SystemUtil {

	private Context context;
	private static SystemUtil util = new SystemUtil();
	private static final String APP_DIR = "/icar/";
	private static final String CHAT = "chat/";
	private static final String EXCEPT_CHAT = "except_chat/";
	private static final String SDCARD_DIR = Environment
			.getExternalStorageDirectory().getAbsolutePath();
	
	public static SystemUtil getInstance() {
		return util;
	}

	public boolean isOnLine(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE); 
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo == null || !networkInfo.isAvailable()) {
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * 网络连接是否正常
	 */
	public static boolean isNetWorkOk(Context context) {
		boolean netWorkState = false;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null) {
			if (info.isConnected()) {
				netWorkState = true;
			} else {
				netWorkState = false;
			}
		} else {
			netWorkState = false;
		}
		return netWorkState;
	}
	
	/**
	 * 获取聊天音频文件路径
	 * 
	 * @return
	 */
	public static String getAudioDir() {
		String path = SDCARD_DIR + APP_DIR + CHAT + ".audio/";
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path;
	}
	
	/**
	 * 检测Sdcard是否存在
	 * 
	 * @return
	 */
	public static boolean isExitsSdcard() {
		if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
			return true;
		else
			return false;
	}
	
	/**
	 * Toast长消息
	 * 
	 * @param context
	 * @param resId
	 */
	public static void ToastMessageLong(int resId) {
		Toast.makeText(BaseApplication.getInstance(), resId,
				Toast.LENGTH_LONG).show();
	}

	public static void ToastMessageShort(int resId) {
		// Toast.makeText(JiaoYou8Application.getInstance(), resId,
		// Toast.LENGTH_SHORT).show();
		// Toast.makeText(JiaoYou8Application.getInstance(), resId, 0).show();
		ToastUtil.showToast(BaseApplication.getInstance(), resId);
	}
	
	/**
	 * 生成唯一文件名
	 * 
	 * @return String
	 */
	public static synchronized String createFileNameUnique() {
		return String.valueOf(System.currentTimeMillis());
	}
	
	public static String[] ShortText(String string) {
		String key = "weclub"; // 自定义生成MD5加密字符串前的混合KEY
		String[] chars = new String[] { // 要使用生成URL的字符
		"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
				"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
				"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B",
				"C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
				"O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

		String hex = Encript.md5(key + string);
		int hexLen = hex.length();
		int subHexLen = hexLen / 8;
		String[] ShortStr = new String[4];

		for (int i = 0; i < subHexLen; i++) {
			String outChars = "";
			int j = i + 1;
			String subHex = hex.substring(i * 8, j * 8);
			long idx = Long.valueOf("3FFFFFFF", 16) & Long.valueOf(subHex, 16);

			for (int k = 0; k < 6; k++) {
				int index = (int) (Long.valueOf("0000003D", 16) & idx);
				outChars += chars[index];
				idx = idx >> 5;
			}
			ShortStr[i] = outChars;
		}

		return ShortStr;
	}
	
	public static String getImgThumbnailDirExceptChat() {
		String path = SDCARD_DIR + APP_DIR + EXCEPT_CHAT + "img/thumbnail/";
		Log.e("getImgThumbnailDirExceptChat", "Bitmap-path--->"+path);
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		}
		return path;
	}
}
