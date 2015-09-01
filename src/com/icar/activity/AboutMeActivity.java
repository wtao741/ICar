package com.icar.activity;

import android.os.Bundle;

import com.icar.base.AbstractTitleActivity;

public class AboutMeActivity extends AbstractTitleActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_about_me);
		setTitle("关于我们");
		isShowLeftView(true);
		isShowRightView(0, false);
	}
}
