package com.icar.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.icar.base.AbstractTitleActivity;
import com.icar.bean.City;
import com.icar.db.DBHelper;
import com.icar.utils.AppUtil;
import com.icar.view.MyLetterListView;
import com.icar.view.MyLetterListView.OnTouchingLetterChangedListener;
import com.lidroid.xutils.HttpUtils;

public class CitySelectActivity extends AbstractTitleActivity {
	private ListAdapter adapter;
	private ListView personList;
	private TextView overlay; // 对话框首字母textview
	private MyLetterListView letterListView; // A-Z listview
	private HashMap<String, Integer> alphaIndexer;// 存放存在的汉语拼音首字母和与之对应的列表位置
	// private String[] sections;// 存放存在的汉语拼音首字母
	private Handler handler;
	private OverlayThread overlayThread; // 显示首字母对话框
	// private ArrayList<City> allCity_lists; // 所有城市列表
	private ArrayList<City> data = new ArrayList<City>();
	// private ArrayList<City> ShowCity_lists; // 需要显示的城市列表-随搜索而改变
	// private ArrayList<City> city_lists;// 城市列表
	// private String lngCityName ="";//存放返回的城市名
	private LocationClient locationClient = null;
	// private EditText sh;
	private TextView lng_city;
	private LinearLayout lng_city_lay;
	private ProgressDialog progress;
	private DBHelper dbHelper;
	private static final int SHOWDIALOG = 2;
	private static final int DISMISSDIALOG = 3;
	private static final int HOT_CITY_COUNT = 4;// 热门城市的个数

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_city);

		setTitle("城市选择");
		isShowRightView(R.string.null_tips, false);

		personList = (ListView) findViewById(R.id.list_view);
		lng_city_lay = (LinearLayout) findViewById(R.id.lng_city_lay);
		lng_city = (TextView) findViewById(R.id.lng_city);
		lng_city.setText("正在定位您当前的城市...");
		handler = new Handler();
		alphaIndexer = new HashMap<String, Integer>();
		letterListView = (MyLetterListView) findViewById(R.id.myLetterListView);
		letterListView
				.setOnTouchingLetterChangedListener(new LetterListViewListener());
		// allCity_lists = new ArrayList<City>();
		dbHelper = new DBHelper(CitySelectActivity.this);
		handler2.sendEmptyMessage(SHOWDIALOG);
		
		new Thread(new Runnable() {
			public void run() {
				
				cityInit();
				handler2.sendEmptyMessage(DISMISSDIALOG);
			}
		}).start();

		overlayThread = new OverlayThread();
		personList.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Intent intent = new Intent();
				intent.putExtra("city", data.get(arg2));
				setResult(RESULT_OK, intent);

				finish();
			}
		});
		lng_city_lay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				String lngName = lng_city.getText().toString();
				City city = dbHelper.getCityByName(lngName);
				if (city != null) {
					intent.putExtra("city", city);
					setResult(RESULT_OK, intent);
				} else {
					Toast.makeText(CitySelectActivity.this, "尚未定位成功", 0).show();
					setResult(RESULT_CANCELED);
				}
				finish();
			}
		});

		initGps();
		initOverlay();
	}

	/**
	 * 热门城市
	 */
	public void cityInit() {
		data.addAll(dbHelper.getAll());
		Collections.sort(data);
		data.add(0, dbHelper.getCityByName("上海市"));
		data.add(0, dbHelper.getCityByName("北京市"));
		data.add(0, dbHelper.getCityByName("广州市"));
		data.add(0, dbHelper.getCityByName("深圳市"));
	}

	/**
	 * a-z排序
	 */

	// private void setAdapter(List<City> list) {
	// adapter = new ListAdapter(this, list);
	// personList.setAdapter(adapter);
	// }

	public class ListAdapter extends BaseAdapter {
		private LayoutInflater inflater;
		final int VIEW_TYPE = 3;

		public ListAdapter(Context context) {
			this.inflater = LayoutInflater.from(context);
			alphaIndexer = new HashMap<String, Integer>();
			for (int i = HOT_CITY_COUNT; i < data.size(); i++) {
				City city = data.get(i);
				if (alphaIndexer.containsKey(city.getLetter())) {
					continue;
				}
				alphaIndexer.put(city.getLetter(), i);
			}
			Log.e("TGA", "");
			// sections = new String[ShowCity_lists.size()];
			// for (int i = 0; i < ShowCity_lists.size(); i++) {
			// // 当前汉语拼音首字母
			// String currentStr = getAlpha(ShowCity_lists.get(i).getPinyi());
			// // 上一个汉语拼音首字母，如果不存在为“ ”
			// String previewStr = (i - 1) >= 0 ? getAlpha(ShowCity_lists.get(i
			// - 1)
			// .getPinyi()) : " ";
			// if (!previewStr.equals(currentStr)) {
			// String name = getAlpha(ShowCity_lists.get(i).getPinyi());
			// alphaIndexer.put(name, i);
			// sections[i] = name;
			//
			// }
			// }
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = inflater.inflate(R.layout.city_list_item, null);
				holder = new ViewHolder();
				holder.alpha = (TextView) convertView.findViewById(R.id.alpha);
				holder.linear1 = convertView
						.findViewById(R.id.city_list_item_linear_1);
				holder.linear2 = convertView
						.findViewById(R.id.city_list_item_linear_2);
				holder.linear3 = convertView
						.findViewById(R.id.city_list_item_linear_3);
				holder.name = (TextView) convertView.findViewById(R.id.name);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			// if (sh.getText().length()==0) {//搜所状态
			// holder.name.setText(list.get(position).getName());
			// holder.alpha.setVisibility(View.GONE);
			// }else if(position>0){
			// 显示拼音和热门城市，一次检查本次拼音和上一个字的拼音，如果一样则不显示，如果不一样则显示

			holder.name.setText(data.get(position).getName());

			if (position == 0) {
				holder.alpha.setVisibility(View.VISIBLE);
				holder.alpha.setText("热门城市");
				return convertView;
			} else if (position < HOT_CITY_COUNT) {
				return convertView;
			}
			String currentStr = data.get(position).getLetter();// 本次拼音
			String previewStr = (position - 1) >= 0 ? data.get(position - 1)
					.getLetter() : " ";// 上一个拼音
			if (!previewStr.equals(currentStr) || position == HOT_CITY_COUNT) {// 不一样,或者是热门后的第一个则显示
				holder.alpha.setVisibility(View.VISIBLE);
				holder.alpha.setText(currentStr);
				// holder.linear3.setVisibility(View.GONE);
			} else {
				holder.alpha.setVisibility(View.GONE);
				holder.alpha.setText("");
				holder.linear1.setVisibility(View.GONE);
				holder.linear2.setVisibility(View.GONE);
			}
			// }
			return convertView;
		}

		private class ViewHolder {
			public View linear3;
			public View linear2;
			public View linear1;
			TextView alpha; // 首字母标题
			TextView name; // 城市名字
		}
	}

	// 初始化汉语拼音首字母弹出提示框
	private void initOverlay() {
		overlay = (TextView) View.inflate(CitySelectActivity.this,
				R.layout.overlay, null);
		overlay.setVisibility(View.INVISIBLE);
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT,
				WindowManager.LayoutParams.TYPE_APPLICATION,
				WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
						| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
				PixelFormat.TRANSLUCENT);
		WindowManager windowManager = (WindowManager) this
				.getSystemService(Context.WINDOW_SERVICE);
		windowManager.addView(overlay, lp);
	}

	private class LetterListViewListener implements
			OnTouchingLetterChangedListener {

		@Override
		public void onTouchingLetterChanged(final String s) {
			if (alphaIndexer.get(s) != null) {
				int position = alphaIndexer.get(s);
				personList.setSelection(position);
				overlay.setText(data.get(position).getLetter());
				overlay.setVisibility(View.VISIBLE);
				handler.removeCallbacks(overlayThread);
				// 延迟一秒后执行，让overlay为不可见
				handler.postDelayed(overlayThread, 1500);
			}
		}

	}

	// 设置overlay不可见
	private class OverlayThread implements Runnable {
		@Override
		public void run() {
			overlay.setVisibility(View.GONE);
		}

	}

	private void initGps() {
		try {
			MyLocationListenner myListener = new MyLocationListenner();
			locationClient = new LocationClient(CitySelectActivity.this);
			locationClient.registerLocationListener(myListener);
			LocationClientOption option = new LocationClientOption();
			option.setOpenGps(true);
			option.setAddrType("all");
			option.setCoorType("bd09ll");
			option.setScanSpan(5000);
			option.disableCache(true);
			option.setPoiNumber(5);
			option.setPoiDistance(1000);
			option.setPoiExtraInfo(true);
			option.setPriority(LocationClientOption.GpsFirst);
			locationClient.setLocOption(option);
			locationClient.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		locationClient.stop();
		handler.removeCallbacksAndMessages(null);
		handler2.removeCallbacksAndMessages(null);
	}

	private class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {

			if (location == null)
				return;
			StringBuffer sb = new StringBuffer(256);
			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				// sb.append(location.getAddrStr());
				sb.append(location.getCity());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {
				sb.append(location.getCity());
			} else {
				return;
			}
			if (!TextUtils.isEmpty(sb)) {
				lng_city.setText(sb.toString());
			}

		}

		public void onReceivePoi(BDLocation poiLocation) {

		}
	}

	Handler handler2 = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SHOWDIALOG:
				progress = AppUtil.showProgress(CitySelectActivity.this,
						"正在加载数据，请稍候...");
				break;
			case DISMISSDIALOG:
				if (progress != null) {
					progress.dismiss();
				}
				adapter = new ListAdapter(CitySelectActivity.this);
				personList.setAdapter(adapter);
			default:
				break;
			}
		};
	};
}