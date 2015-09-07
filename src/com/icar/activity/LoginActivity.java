package com.icar.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.icar.base.AbstractTitleActivity;
import com.icar.base.BaseApplication;
import com.icar.base.HeadClick;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * @author wt
 * 登录
 */
@SuppressLint("ResourceAsColor")
public class LoginActivity extends AbstractTitleActivity implements HeadClick,HttpCallBack{

	@ViewInject(R.id.user_name) EditText et_name;
	@ViewInject(R.id.user_password) EditText et_pass;
	
	@OnClick(R.id.login_return_password)
	public void loginreturnpassword(View v){
		Intent intent = new Intent(this,ReturnPassActivity.class);
		startActivity(intent);
	}
	
	@OnClick(R.id.login_login)
	public void loginLoginonClick(View v){
		user = et_name.getText().toString().trim();
		password = et_pass.getText().toString().trim();
		int userLen = user.length();
		int passLen = password.length();
		if (userLen == 0) {
			showShortToast("请输入电话号码");
		} else if (userLen != 13) {
			showShortToast("请填写正确的电话号码");
		} else {
				if (passLen == 0) {
					showShortToast("请输入密码");
				} else if (passLen < 6 || passLen > 20) {
					showShortToast("请输入正确的密码");
				} else {
					user = user.replace(" ", "");
					Log.e("tag", user);
					http.login(user, password);
				}
		}
	}
	
	 private int beforeLen = 0;  
     private int afterLen = 0; 
     private String user;
     private String password;
     private HttpUtil http;
     
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		setHeadClick(this);
		ViewUtils.inject(this);
		setTitle("登录");
		isShowRightView(R.string.login_register, true);
		setRightTextColor(R.color.white);
		
		initView();
		http = new HttpUtil(this);
		http.setHttpCallBack(this);
	}

	private void initView() {
		 et_name.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				beforeLen = s.length(); 
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				 String txt = et_name.getText().toString();  
		            afterLen = txt.length();  
		  
		            if (afterLen > beforeLen) {  
		                if (txt.length() == 4 || txt.length() == 9) {  
		                	et_name.setText(new StringBuffer(txt).insert(  
		                            txt.length() - 1, " ").toString());  
		                	et_name.setSelection(et_name.getText()  
		                            .length());  
		                }  
		            } else {  
		                if (txt.startsWith(" ")) {  
		                	et_name.setText(new StringBuffer(txt).delete(  
		                            afterLen - 1, afterLen).toString());  
		                	et_name.setSelection(et_name.getText()  
		                            .length());  
		                }  
		            }  
			}
		});
	}

	@Override
	public void left() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void right() {
		Intent intent = new Intent(this,RegisterActivity.class);
	    startActivity(intent);	
	}

	@Override
	public void onFailure(int requestCode, HttpException arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(int requestCode, ResponseInfo<String> arg0) {
		// TODO Auto-generated method stub
		String result = arg0.result;
		Log.e("login", result);
		try {
			JSONObject object = new JSONObject(result);
			String code = object.getString("code");
			if(code.equals("200")){
				showShortToast("登录成功");
				BaseApplication.saveUsername(user, password);
				JSONObject userObject = object.getJSONObject("data");
				BaseApplication.USER_NAME = userObject.getString("nickname");
				BaseApplication.ADDRESS = userObject.getString("cityname");
				BaseApplication.SEX = userObject.getString("sex");
				String url = userObject.getString("avatar");
				url = url.replace("./", "/");
				BaseApplication.user.setHead_url("http://api.iucars.com"+url);
				BaseApplication.user.setName(user);
				BaseApplication.user.setUserName(userObject.getString("nickname"));
				BaseApplication.user.setCity(userObject.getString("cityname"));
				String sex = userObject.getString("sex");
				if(sex.equals("1")){
					sex = "男";
				}else{
					sex = "女";
				}
				BaseApplication.user.setUserSex(sex);
				BaseApplication.user.setPassword(password);
				finish();
			}else if(result.equals("0")){
				showShortToast("登录失败");
			}else{
				showShortToast("登录失败,请填入正确的用户名或密码");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("tag", e.getMessage());
			e.printStackTrace();
		}
	}
}
