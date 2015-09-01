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

public class ChangeBgmActivity extends AbstractTitleActivity implements HeadClick{

	@ViewInject(R.id.bt_change_bgm) Button bt_1;
	@ViewInject(R.id.bt_change_bgm1) Button bt_2;
	
	
	@SuppressLint("NewApi")
	@OnClick(R.id.bt_change_bgm)
	public void btChangeBgmonClick(View v){
		bt_1.setText("使用");
		bt_1.setBackgroundResource(R.drawable.change_bgm_selector);
		
		bt_2.setText("使用中");
		bt_2.setBackground(null);
	}
	
	@SuppressLint("NewApi")
	@OnClick(R.id.bt_change_bgm1)
	public void btChangeBgm1onClick(View v){
		bt_2.setText("使用");
		bt_2.setBackgroundResource(R.drawable.change_bgm_selector);
		
		bt_1.setText("使用中");
		bt_1.setBackground(null);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_changebgm);
		setTitle("换皮肤");
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
