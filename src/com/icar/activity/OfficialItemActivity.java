package com.icar.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.icar.base.BaseApplication;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class OfficialItemActivity extends Activity implements OnClickListener{

	public String tag = "OfficialItemActivity";

	@ViewInject(R.id.viewPager)
	ViewPager viewPager;
	@ViewInject(R.id.official_item_left_arrow)
	ImageView iv_left; // viewPager向左
	@ViewInject(R.id.official_item_right_arrow)
	ImageView iv_right; // viewPager向右
	@ViewInject(R.id.official_item_collect)
	ImageView iv_collect; // 收藏图片
	@ViewInject(R.id.official_item_menu)
	ImageView iv_menu; // 菜单图片
	@ViewInject(R.id.return_menu)
	TextView tv_menu;
	@ViewInject(R.id.official_current)
	EditText et_current; // viewPager当前页
	@ViewInject(R.id.official_count)
	TextView tv_count; // viewPager总页数
	@ViewInject(R.id.head_left)
	LinearLayout head_left;//头部返回键
	@ViewInject(R.id.head_title)
	TextView head_title;//头部标题

	private int[] icons = { R.drawable.car1, R.drawable.car2, R.drawable.car3,
			R.drawable.zhiyan };

	private List<ImageView> views;

	int current;

	@OnClick(R.id.official_item_left_arrow)
	public void officialItemLeftArrowonClick(View v) {
		current = current - 1;
		if (current >= 0) {
			viewPager.setCurrentItem(current);
			et_current.setText(current + "");
			et_current.setSelection(et_current.getText().toString().trim()
					.length());
			setTitle(current + "");
		} else {
			current = 0;
		}
	}

	@OnClick(R.id.official_item_right_arrow)
	public void officialItemRightArrowonClick(View v) {
		current = current + 1;
		if (current <= icons.length - 1) {
			viewPager.setCurrentItem(current);
			et_current.setText(current + "");
			et_current.setSelection(et_current.getText().toString().trim()
					.length());
			setTitle(current + "");
		} else {
			current = icons.length;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_official_item);
		ViewUtils.inject(this);
		setTitle("仪表和控制仪");
		setViewPager();
		setListener();

	}

	private void setViewPager() {
		// TODO Auto-generated method stub

		et_current.setText(viewPager.getCurrentItem() + "");
		et_current
				.setSelection(et_current.getText().toString().trim().length());

		views = new ArrayList<ImageView>();
		for (int i = 0; i < icons.length; i++) {
			ImageView view = new ImageView(this);
			view.setImageResource(icons[i]);
			views.add(view);
		}

		tv_count.setText("/   " + icons.length);

		LayoutParams params = (LayoutParams) viewPager.getLayoutParams();
		params.width = BaseApplication.WIDTH;
		params.height = (int) (BaseApplication.WIDTH / 1.5);
		viewPager.setLayoutParams(params);

		viewPager.setAdapter(new MyViewPagerAdapter());
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				current = arg0;
				setTitle(current + "");
				et_current.setText(current + "");

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

		et_current.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				String str = s.toString().trim();
				if (!str.equals("")) {
					int pager = Integer.parseInt(str);
					viewPager.setCurrentItem(pager);
				}
				et_current.setSelection(et_current.getText().toString().trim()
						.length());
			}
		});
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
			setContentView(R.layout.activity_official_item);
			ViewUtils.inject(this);
			setTitle("仪表和控制仪");
			setViewPager();
			setListener();
		} else if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
			setContentView(R.layout.activity_official_item_land);
			ViewUtils.inject(this);
			setTitle("仪表和控制仪");
			setViewPager();
			setListener();
		}
	}

	class MyViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return icons.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(View container, int position) {
			// TODO Auto-generated method stub
			((ViewPager) container).addView(views.get(position));
			return views.get(position);
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			((ViewPager) container).removeView(views.get(position));
		}
	}
	
	public void setTitle(String str){
		head_title.setText(str);
	}
	
	public void setListener(){
		head_left.setOnClickListener(this);
		tv_menu.setOnClickListener(this);
		iv_menu.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.head_left:
			finish();
			break;
		case R.id.return_menu:
			finish();
		case R.id.official_item_menu:
			finish();
		default:
			break;
		}
	}
}
