package com.icar.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.icar.base.AbstractTitleActivity;
import com.icar.base.HeadClick;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class ShareSettingActivity extends AbstractTitleActivity implements HeadClick{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_share_setting);
		setTitle("分享设置");
		isShowRightView(0, false);
		ViewUtils.inject(this);
	}
	
	@Override
	public void left() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void right() {
		// TODO Auto-generated method stub
		
	}

}
