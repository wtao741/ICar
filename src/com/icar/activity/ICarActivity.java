package com.icar.activity;

import java.util.ArrayList;
import java.util.List;

import com.icar.adapter.MyLikeCarAdapter;
import com.icar.base.AbstractTitleActivity;
import com.icar.base.BaseApplication;
import com.icar.bean.CarBrandEntity;
import com.icar.view.HorizontalListView;
import com.icar.view.RoundImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class ICarActivity extends AbstractTitleActivity {

	@ViewInject(R.id.user_icon)
	RoundImageView iv_user_icon;
	@ViewInject(R.id.user_name)
	TextView tv_user_name;
	@ViewInject(R.id.user_type_tips)
	TextView tv_user_type_tips;
	@ViewInject(R.id.user_type)
	TextView tv_user_type;
	@ViewInject(R.id.listView)
	HorizontalListView listView;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	@OnClick(R.id.user_name)
	public void usernameClick(View v) {
		if (tv_user_name.getText().equals("请登录")) {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
	}

	@OnClick(R.id.icar_real_mycollect)
	public void icarRealMycollectonClick(View v) {
		openActivity(MyCollectActivity.class);
	}

	@OnClick(R.id.icar_real_mysetting)
	public void icalRealMysettingonClick(View v) {
		Intent intent = new Intent(this, SettingActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.icar_userinfo)
	public void icarUserInfo(View v) {
		if (BaseApplication.getUserName().length() > 0) {
			Intent intent = new Intent(this, UserInfoActivity.class);
			startActivity(intent);
		} else {
			Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		}
	}

	@OnClick(R.id.icar_real_myadvice)
	public void icarRealMyadviceonClick(View v) {
		Intent intent = new Intent(this, AdviceActivity.class);
		startActivity(intent);
	}

	@OnClick(R.id.icar_real_myabout)
	public void icarRealMyaboutonClick(View v) {
		Intent intent = new Intent(this, AboutMeActivity.class);
		startActivity(intent);
	}

	private List<CarBrandEntity> datas;
	private MyLikeCarAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_icar);
		ViewUtils.inject(this);
		setTitle("ICar");
		isShowLeftView(false);
		isShowRightView(0, false);
		
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.userinfo)
				.showImageOnFail(R.drawable.userinfo).showImageForEmptyUri(R.drawable.userinfo).build();
		
	}

	private void setAdapter() {
		adapter = new MyLikeCarAdapter(this, datas);
		listView.setAdapter(adapter);

		if (datas.size() > 0) {
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					if (position < datas.size()) {
						tv_user_type.setText(datas.get(position).getType());
						// 没有BaseApplication.myLikeCar = null;会有bug，求原因
						CarBrandEntity bean = datas.get(position);
						BaseApplication.myLikeCar = null;
						BaseApplication.myLikeCar = new CarBrandEntity();
						BaseApplication.myLikeCar.setIcon(bean.getIcon());
						BaseApplication.myLikeCar.setName(bean.getName());
						BaseApplication.myLikeCar.setType(bean.getType());
						BaseApplication.myLikeCar.setSeriesBean(bean
								.getSeriesBean());

						for (int i = 0; i < datas.size(); i++) {
							if (i == position) {
								datas.get(i).setChecked(true);
							} else {
								datas.get(i).setChecked(false);
							}
						}
						adapter.refresh(datas);
					}
				}
			});
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		datas = new ArrayList<CarBrandEntity>();
		datas = BaseApplication.myLikes;
		if (datas.size() > 0) {
			if (BaseApplication.myLikeCar.getType() != null) { // 没有设置我关注的车
				String str = BaseApplication.myLikeCar.getType();
				for (int i = 0; i < datas.size(); i++) {
					if (datas.get(i).getIcon().equals(str)) {
						datas.get(i).setChecked(true);
					}
				}
			} else {
				BaseApplication.myLikeCar = datas.get(0); // 设置我关注的车
				datas.get(0).setChecked(true);
				tv_user_type.setText(datas.get(0).getType());
			}
		}
		for (int i = 0; i < datas.size(); i++) {
			Log.e("tag", datas.get(i).getType());
		}
		setAdapter();

		String userName = BaseApplication.getUserName();
		if (userName.length() > 0) {
			tv_user_type_tips.setVisibility(View.VISIBLE);
			tv_user_type.setVisibility(View.VISIBLE);
			if (BaseApplication.getUserName() == null) {
				tv_user_name.setText("来自星星的网友");
			} else {
				tv_user_name.setText(BaseApplication.user.getUserName());
			}
		} else {
			tv_user_type_tips.setVisibility(View.GONE);
			tv_user_type.setVisibility(View.GONE);
			tv_user_name.setText("请登录");
		}
		
		imageLoader.displayImage(BaseApplication.user.getHead_url(),iv_user_icon, options);
	}
}
