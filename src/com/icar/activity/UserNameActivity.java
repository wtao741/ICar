package com.icar.activity;

import com.icar.base.BaseApplication;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UserNameActivity extends Activity{

	@ViewInject(R.id.cancel) TextView tv_cancel;
	@ViewInject(R.id.save)   TextView tv_save;
	@ViewInject(R.id.name)   EditText et_name;
	
	@OnClick(R.id.cancel)
	public void cancelonClick(View v){
		finish();
	}
	
	private String name;
	private HttpUtil http;
	
	@OnClick(R.id.save)
	public void saveonClick(View v){
		String name = et_name.getText().toString().trim();
		Intent intent = new Intent();
		intent.putExtra("name", name);
		setResult(RESULT_OK, intent);
		http.updateUserInfo("nickname", name);
		finish();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_username);
		ViewUtils.inject(this);
		
		Intent intent1 = getIntent();
		name = intent1.getStringExtra("name");
		et_name.setText(name);
		et_name.setSelection(name.length());
		http = new HttpUtil(this);
	}
}
