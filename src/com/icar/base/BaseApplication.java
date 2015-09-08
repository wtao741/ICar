package com.icar.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.impl.client.BasicCookieStore;

import com.icar.bean.CarBrandEntity;
import com.icar.bean.UserEntity;
import com.icar.utils.DataModel;
import com.icar.view.AudioPlayTask;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.DisplayMetrics;
import android.util.Log;

public class BaseApplication extends Application {

	private static BaseApplication mInstance;

	private BasicCookieStore mCookieStore = new BasicCookieStore();

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

	public static CarBrandEntity bean = new CarBrandEntity(); // 选择我关注的车用的

	public static List<Activity> acts = new ArrayList<Activity>();

	public static CarBrandEntity myLikeCar = new CarBrandEntity(); // 我关注的车

	public static UserEntity user = new UserEntity();

	private static SharedPreferences preference;
	private static Editor edit;

	public static String hotline = ""; // 客服电话

	public static String myLikeIcon = "";
	
	/* 当前音频播放器 */
	private AudioPlayTask mAudioPlayTask;

	public static BaseApplication getInstance() {
		return mInstance;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mInstance = this;
		getWidthAndHeight();
		preference = getSharedPreferences("login", MODE_PRIVATE);
		edit = preference.edit();
		initUniversalImageLoader();
		
		Log.e("tag", "这是测试");
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

	public void setAudioPlayTask(AudioPlayTask audioPlayTask) {
		mAudioPlayTask = audioPlayTask;
	}

	public AudioPlayTask getAudioPlayTask() {
		return mAudioPlayTask;
	}

	public BasicCookieStore getCookieStore() {
		return mCookieStore;
	}

	// 将字符串转为时间戳

	public static String getTime(String user_time) {
		String re_time = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		Date d;
		try {
			d = sdf.parse(user_time);
			long l = d.getTime();
			String str = String.valueOf(l);
			re_time = str.substring(0, 10);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re_time;
	}

	// 将时间戳转为字符串
	public static String getStrTime(String cc_time) {
		String re_StrTime = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
		// 例如：cc_time=1291778220
		long lcc_time = Long.valueOf(cc_time);
		re_StrTime = sdf.format(new Date(lcc_time * 1000L));

		return re_StrTime;

	}
}
