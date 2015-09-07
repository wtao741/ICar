package com.icar.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.icar.base.AbstractTitleActivity;
import com.icar.base.HeadClick;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class UserSexActivity extends AbstractTitleActivity implements HeadClick{

	private String temp;
	private HttpUtil http;
	
	@ViewInject(R.id.sex_man) ImageView iv_man;
	@ViewInject(R.id.sex_women) ImageView iv_women;
	
	@OnClick(R.id.real_sex_man)
	public void realSexManonClick(View v){
		iv_man.setVisibility(View.VISIBLE);
		iv_women.setVisibility(View.GONE);
		temp = "男";
	}
	
	@OnClick(R.id.real_sex_women)
	public void realSexWomenonClick(View v){
		iv_man.setVisibility(View.GONE);
		iv_women.setVisibility(View.VISIBLE);
		temp = "女";
	}
	
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_user_sex);
		setTitle(R.string.userinfo_sex);
		setHeadClick(this);
		ViewUtils.inject(this);
		isShowRightView(R.string.save, true);
		setRightTextColor(R.color.white);
		
		temp = getIntent().getStringExtra("sex");
		if(temp.equals("男")){
			iv_man.setVisibility(View.VISIBLE);
			iv_women.setVisibility(View.GONE);
		}else if(temp.equals("女")){
			iv_man.setVisibility(View.GONE);
			iv_women.setVisibility(View.VISIBLE);
		}else{
			temp = "男";
		}
		
		http = new HttpUtil(this);
	}

	@Override
	public void left() {
		
	}

	@Override
	public void right() {
		Intent intent = new Intent();
		intent.putExtra("sex", temp);
		setResult(RESULT_OK, intent);
		Log.e("tag", temp);
		if(temp.equals("男")){
			temp = "1";
		}else{
			temp = "0";
		}
		http.updateUserInfo("sex",temp);
		finish();
	}
}
