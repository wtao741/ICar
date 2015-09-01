package com.icar.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.icar.base.AbstractTitleActivity;
import com.icar.base.HeadClick;
import com.icar.view.ClearMemoryDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class SettingActivity extends AbstractTitleActivity implements HeadClick{

	@OnClick(R.id.real_change_bgm)
	public void realChangeBgmonClick(View v){
		Intent intent = new Intent(this,ChangeBgmActivity.class);
		startActivity(intent);
	}
	
	@OnClick(R.id.real_share_setting)
	public void realShareSettingonClick(View v){
		Intent intent = new Intent(this,ShareSettingActivity.class);
		startActivity(intent);
	}
	
	@OnClick(R.id.real_clear_memory)
	public void realClearMemoryonClick(View v){
		ClearMemoryDialog dialog = new ClearMemoryDialog(this);
		dialog.showDialog();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setting);
		setHeadClick(this);
		setTitle("设置");
		ViewUtils.inject(this);
		isShowRightView(0, false);
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
