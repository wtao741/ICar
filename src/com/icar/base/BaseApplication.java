package com.icar.base;

import java.util.ArrayList;
import java.util.List;

import com.icar.bean.CarBrandEntity;
import com.icar.bean.UserEntity;
import com.icar.utils.DataModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.DisplayMetrics;

public class BaseApplication extends Application {

	public static String URL = "";

	public static String PHONENUMBER = "";

	public static String PASSWROD = "";

	public static String USER_NAME = "";

	public static String SEX = "";

	public static String HEAD_IMG = "";

	public static String ADDRESS = "";

	public static int WIDTH = 0;

	public static int HEIGHT = 0;

	public static int SCALE = 1;

	public static DataModel appData = new DataModel();

	public static List<CarBrandEntity> myLikes = new ArrayList<CarBrandEntity>(); // 我关注的车

	public static CarBrandEntity bean = new CarBrandEntity();    //选择我关注的车用的

	public static List<Activity> acts = new ArrayList<Activity>();

	public static CarBrandEntity  myLikeCar = new CarBrandEntity();    //我关注的车
	
	public static UserEntity user = new UserEntity();
	
	private static SharedPreferences preference;
	private static Editor edit;

	public static String hotline = "";    //客服电话
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		getWidthAndHeight();
		preference = getSharedPreferences("login", MODE_PRIVATE);
		edit = preference.edit();
		initUniversalImageLoader();
	}

	public void getWidthAndHeight() {
		DisplayMetrics metrics = getResources().getDisplayMetrics();
		WIDTH = metrics.widthPixels;
		HEIGHT = metrics.heightPixels;
		SCALE = (int) metrics.scaledDensity;
	}

	/*
	 * 将textview中的字符全角化。即将所有的数字、字母及标点全部转为全角字符，
	 * 使它们与汉字同占两个字节，这样就可以避免由于占位导致的排版混乱问题了。
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	public static String getUserName() {
		return preference.getString("username", "");
	}

	public static String getPassword() {
		return preference.getString("password", "");
	}

	public static void saveUsername(String name, String password) {
		edit.putString("username", name);
		edit.putString("password", password);
		edit.commit();
	}

	public void initUniversalImageLoader() {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getApplicationContext()).memoryCacheExtraOptions(480, 800)
				.threadPoolSize(5).build();
		ImageLoader.getInstance().init(config);
	}
}
