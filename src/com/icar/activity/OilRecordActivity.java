package com.icar.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.icar.base.BaseApplication;
import com.icar.bean.OilRecordEntity;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("JavascriptInterface")
public class OilRecordActivity extends Activity implements HttpCallBack {

	@ViewInject(R.id.head_left)
	LinearLayout head_left;
	@ViewInject(R.id.head_title)
	TextView head_title;
	@ViewInject(R.id.oil_recore_recently)
	Button bt_recently;
	@ViewInject(R.id.oil_recore_month)
	Button bt_month;
	@ViewInject(R.id.oil_recore_year)
	Button bt_year;
	@ViewInject(R.id.oil_record_viewPager)
	ViewPager viewPager;
	@ViewInject(R.id.oil_distance_tv)
	TextView tv_distance;
	@ViewInject(R.id.oil_sum_tv)
	TextView tv_sum;
	@ViewInject(R.id.oil_money_tv)
	TextView tv_money;
	@ViewInject(R.id.oil_recently_tv)
	TextView tv_recently;
	@ViewInject(R.id.oil_avg_tv)
	TextView tv_avg;
	@ViewInject(R.id.oil_avg_money_tv)
	TextView avg_money;

	@OnClick(R.id.head_left)
	public void headLeftonClick(View v) {
		finish();
	}

	@OnClick(R.id.add_oil_record)
	public void addOilRecordonClick(View v) {
		Intent intent = new Intent(this, AddOilHistoryActivity.class);
		intent.putExtra("type", 1);
		startActivity(intent);
	}

	@OnClick(R.id.oil_record_real)
	public void addOilRecordRealonClick(View v) {
		Intent intent = new Intent(this, OilHistoryRecordActivity.class);
		startActivity(intent);
	}

	private HttpUtil http;
	private String oilavg;
	private String mileage;
	private String chargeoil;
	private String money;
	private String currentoil;
	private String avgoil;
	private Gson gson;
	private ArrayList<ArrayList<OilRecordEntity>> datas;
	private List<WebView> views;

	private String[] str1;
	private String[] str2;
	private String[] str3;
	private String[] str4;
	private String[] str5;
	private String[] str6;
	private WebView webView;
	private Handler mHandler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_oil_record);
		ViewUtils.inject(this);

		head_title.setText("油耗记录");
		http = new HttpUtil(this);
		http.setHttpCallBack(this);
		http.oilRecord(BaseApplication.getUserName());
		gson = new Gson();
	}

	@SuppressLint("JavascriptInterface")
	private void initWebView() {

		str1 = new String[datas.get(0).size()];
		str2 = new String[datas.get(0).size()];

		for (int i = 0; i < datas.get(0).size(); i++) {
			str1[i] = datas.get(0).get(i).getAddtime();
			str2[i] = datas.get(0).get(i).getChargeoil();
		}

		str3 = new String[datas.get(1).size()];
		str4 = new String[datas.get(1).size()];
		for (int i = 0; i < datas.get(1).size(); i++) {
			str3[i] = datas.get(1).get(i).getAddtime();
			str4[i] = datas.get(1).get(i).getChargeoil();
		}

		str5 = new String[datas.get(2).size()];
		str6 = new String[datas.get(2).size()];
		for (int i = 0; i < datas.get(2).size(); i++) {
			str5[i] = datas.get(2).get(i).getAddtime();
			str6[i] = datas.get(2).get(i).getChargeoil();
		}

		for (int i = 0; i < datas.size(); i++) {
			ArrayList<OilRecordEntity> list = datas.get(i);
			for (int j = 0; j < list.size(); j++) {

				Log.e("tag", list.get(j).toString());
			}
		}

		views = new ArrayList<WebView>();
		
		
		for (int i = 0; i < 3; i++) {
			webView = new WebView(this);
			WebSettings webSettings = webView.getSettings();
	        webSettings.setJavaScriptEnabled(true);
			if (i == 0) {
//				webView.addJavascriptInterface(new DemoJavaScriptInterface(
//						str1, str2), "demo");
				webView.loadUrl("javascript:getParam("
						+ BaseApplication.WIDTH + "," + str1 + "," + str2
						+ ")");// 调用js函数
			} else if (i == 1) {
//				webView.addJavascriptInterface(new DemoJavaScriptInterface(
//						str3, str4), "demo");
				webView.loadUrl("javascript:getParam("
						+ BaseApplication.WIDTH + "," + str3 + "," + str4
						+ ")");// 调用js函数
			} else if (i == 2) {
//				webView.addJavascriptInterface(new DemoJavaScriptInterface(
//						str5, str6), "demo");
				webView.loadUrl("javascript:getParam("
						+ BaseApplication.WIDTH + "," + str5 + "," + str6
						+ ")");// 调用js函数
			}
			webView.loadUrl("file:///android_asset/itjs2.html");
			views.add(webView);
		}
	}

	@Override
	public void onFailure(int requestCode, HttpException arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(int requestCode, ResponseInfo<String> arg0) {
		// TODO Auto-generated method stub
		datas = new ArrayList<ArrayList<OilRecordEntity>>();
		try {
			JSONObject object = new JSONObject(arg0.result);
			String code = object.getString("code");
			if (code.equals("200")) {
				JSONObject dataObject = object.getJSONObject("data");
				oilavg = dataObject.getString("oilavg");
				mileage = dataObject.getString("mileage");
				chargeoil = dataObject.getString("chargeoil");
				money = dataObject.getString("money");
				currentoil = dataObject.getString("currentoil");
				avgoil = dataObject.getString("avgoil");
				JSONArray listArray = dataObject.getJSONArray("list");

				int listLen = listArray.length();
				for (int i = 0; i < listLen; i++) {
					ArrayList<OilRecordEntity> itemDatas = new ArrayList<OilRecordEntity>();
					JSONArray itemArray = listArray.getJSONArray(i);
					int itemLen = itemArray.length();
					for (int j = 0; j < itemLen; j++) {
						JSONObject itemObject = itemArray.getJSONObject(j);
						String time = itemObject.getString("chargedate");
						String oil = itemObject.getString("oilavg");
						OilRecordEntity bean = new OilRecordEntity(oil, time);
						itemDatas.add(bean);
					}
					datas.add(itemDatas);
				}

				initView();
				initWebView();
				setViewPager();
			} else {
				Toast.makeText(OilRecordActivity.this, code, 2000).show();
			}
		} catch (JSONException e) {
			Log.e("tag", e.getMessage());
			e.printStackTrace();
		}

	}

	private void setViewPager() {
		viewPager.setAdapter(new MyViewPager());
	}

	private void initView() {
		tv_distance.setText(mileage + "公里");
		tv_sum.setText(chargeoil + "升");
		tv_money.setText(money + "元");
		tv_recently.setText(currentoil + "升");
		tv_avg.setText(avgoil + "升");
		avg_money.setText(oilavg + "元/公里");
	}

	class MyViewPager extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 3;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}
		
		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager) container).removeView(views.get(position));
		}

		@Override
		public Object instantiateItem(View container, int position) {
			// TODO Auto-generated method stub
			((ViewPager) container).addView(views.get(position));
			return views.get(position);
		}
	}
	
	final class DemoJavaScriptInterface {

		String[] strs;
		String[] strs1;

		DemoJavaScriptInterface(String[] strs, String[] strs1) {
			this.strs = strs;
			this.strs1 = strs1;
		}

		public void getParam(final String[] strs, final String[] strs1) {
			mHandler.post(new Runnable() {
				@Override
				public void run() {
					webView.loadUrl("javascript:getParam("
							+ BaseApplication.WIDTH + "," + strs + "," + strs1
							+ ")");
				}
			});
		}
	}
}
