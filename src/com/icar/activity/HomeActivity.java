package com.icar.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class HomeActivity extends Activity {

	@ViewInject(R.id.head_left)
	LinearLayout head_left;
	@ViewInject(R.id.home_car_type)
	ImageView iv_type_icon;
	@ViewInject(R.id.home_my_car)
	TextView tv_type_name;
	@ViewInject(R.id.home_gridView)
	GridView gridView;
	@ViewInject(R.id.home_search)
	TextView tv_search;
	@ViewInject(R.id.home_viewPager)
	ViewPager viewPager;
	@ViewInject(R.id.v_dot0)
	View dot0;
	@ViewInject(R.id.v_dot1)
	View dot1;
	@ViewInject(R.id.v_dot2)
	View dot2;

	@OnClick(R.id.home_oil)
	public void homeOilonClick(View v) {
		Intent intent = new Intent(this, OilRecordActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.home_car_type)
	public void homeCarTypeonClick(View v) {
		Intent intent = new Intent(this, CarTypeActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.home_my_car)
	public void homeMyCaronClick(View v) {
		Intent intent = new Intent(this, CarTypeActivity.class);
		startActivity(intent);
	}

	private int home_icon_normal = R.drawable.home_add;

	private List<String> uris = new ArrayList<String>();

	private List<ImageView> views = new ArrayList<ImageView>();

	private List<View> dots = new ArrayList<View>();

	private int[] cars = new int[] { R.drawable.car1, R.drawable.car2,
			R.drawable.car3 };

	private int[] strs = { R.string.home_bt1, R.string.home_bt2,
			R.string.home_bt3, R.string.home_bt4, R.string.home_bt5,
			R.string.home_bt6, R.string.home_bt7, R.string.home_bt8,
			R.string.home_bt9, R.string.home_bt10 };

	private int[] icons = { R.drawable.home_bt_icon1, R.drawable.home_bt_icon2,
			R.drawable.home_bt_icon3, R.drawable.home_bt_icon4,
			R.drawable.home_bt_icon5, R.drawable.home_bt_icon6,
			R.drawable.home_bt_icon7, R.drawable.home_bt_icon8,
			R.drawable.home_bt_icon9, R.drawable.home_bt_icon10 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home);

		ViewUtils.inject(this);

		setGridView();
		setEditListener();

		setViewPager();
		head_left.setVisibility(View.GONE);
	}

	private void setViewPager() {
		// TODO Auto-generated method stub
		dots.add(dot0);
		dots.add(dot1);
		dots.add(dot2);
		for (int i = 0; i < cars.length; i++) {
			ImageView view = new ImageView(this);
			view.setScaleType(ScaleType.FIT_XY);
			view.setImageResource(cars[i]);
			views.add(view);
		}

		viewPager.setAdapter(new ViewPagerAdapter());
		viewPager.setOnPageChangeListener(new ViewPagerListener());
	}

	public void setGridView() {
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < strs.length; i++) {
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("image", icons[i]);
			temp.put("str", getResources().getString(strs[i]));
			map.add(temp);
		}

		SimpleAdapter adapter = new SimpleAdapter(this, map,
				R.layout.home_bt_item, new String[] { "image", "str" },
				new int[] { R.id.home_bt_iv, R.id.home_bt_tv });
		gridView.setAdapter(adapter);

		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Class classType = null;
				switch (position) {
				case 4:
					classType = WarnLightActivity.class;
					break;
				case 8:
					classType = ShakeActivity.class;
					break;
				default:
					classType = InteriorControlActivity.class;
					break;
				}
				if (classType != null) {
					Intent intent = new Intent(HomeActivity.this, classType);
					startActivity(intent);
				}
			}
		});
	}

	public void setEditListener() {
		tv_search.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					Intent intent = new Intent(HomeActivity.this,
							SearchActivity.class);
					{
						startActivity(intent);
					}
				}
				return false;
			}
		});
	}

	class ViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return cars.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == (View) arg1;
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

	class ViewPagerListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageSelected(int arg0) {
			for (int i = 0; i < dots.size(); i++) {
				if (i == arg0) {
					dots.get(i).setBackgroundResource(R.drawable.dot_focused);
				} else {
					dots.get(i).setBackgroundResource(R.drawable.dot_normal);
				}
			}
		}
	}
}
