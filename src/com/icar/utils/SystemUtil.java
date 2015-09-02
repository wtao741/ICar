package com.icar.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class SystemUtil {

	private Context context;
	private static SystemUtil util = new SystemUtil();

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
}
