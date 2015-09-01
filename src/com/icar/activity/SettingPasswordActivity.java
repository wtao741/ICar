package com.icar.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.icar.base.AbstractTitleActivity;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class SettingPasswordActivity extends AbstractTitleActivity {

	@ViewInject(R.id.setting_auth) EditText et_auth;
	@ViewInject(R.id.setting_pass) EditText et_pass;
	@ViewInject(R.id.setting_submit) Button bt_submit;
	
	@OnClick(R.id.setting_submit)
	public void settingSubmitonClick(View v){
		auth = et_auth.getText().toString().trim();
		pass = et_pass.getText().toString().trim();
		int authLen = auth.length();
		int passLen = pass.length();
		if(authLen == 0){
			showShortToast("请输入验证码");
		}else if(authLen != 4){
			showShortToast("请输入正确的验证码");
		}else if(passLen == 0){
			showShortToast("请输入密码");
		}else if(passLen <6 || passLen >20){
			showShortToast("请输入6-20位密码");
		}else{
			
		}
		
	}
	
	private String auth;
	private String pass;
	private HttpUtil http;
	private String mobile;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_setting_password);
		setTitle("重置密码");
		isShowRightView(0, false);
		ViewUtils.inject(this);
		
		http = new HttpUtil(this);
		mobile = getIntent().getExtras().getString("mobile");
	}
}
