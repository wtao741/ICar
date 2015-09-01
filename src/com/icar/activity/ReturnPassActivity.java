package com.icar.activity;

import com.icar.base.AbstractTitleActivity;
import com.icar.base.HeadClick;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

@SuppressLint("ResourceAsColor")
public class ReturnPassActivity extends AbstractTitleActivity implements HeadClick,HttpCallBack{

	@ViewInject(R.id.return_phone) EditText et_mobile;
	@ViewInject(R.id.return_submit) Button bt_submit;
	
	@OnClick(R.id.return_submit)
	public void returnSubmitonClick(View v){
		String mobile = et_mobile.getText().toString();
		int len = mobile.length();
		if (len == 0) {
			showShortToast("请输入电话号码");
		} else if (len != 13) {
			showShortToast("请填写正确的电话号码");
		} else if (len == 13) {
			str = mobile.replace(" ", "");
			http.getAuthCode(str);
		}
	}
	
	private int beforeLen = 0;
	private int afterLen = 0;
	private String str;
	private HttpUtil http;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_returnpass);
		
		setTitle("找回密码");
		isShowRightView(0, false);
		setHeadClick(this);
		ViewUtils.inject(this);
		
		initView();
		http = new HttpUtil(this);
		http.setHttpCallBack(this);
	}

	private void initView() {
		et_mobile.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
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
				String txt = et_mobile.getText().toString();
				afterLen = txt.length();

				if (afterLen > beforeLen) {
					if (txt.length() == 4 || txt.length() == 9) {
						et_mobile.setText(new StringBuffer(txt).insert(
								txt.length() - 1, " ").toString());
						et_mobile.setSelection(et_mobile.getText().length());
					}
				} else {
					if (txt.startsWith(" ")) {
						et_mobile.setText(new StringBuffer(txt).delete(
								afterLen - 1, afterLen).toString());
						et_mobile.setSelection(et_mobile.getText().length());
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onFailure(int requestCode,HttpException arg0, String arg1) {
		// TODO Auto-generated method stub
		showShortToast(arg0.getExceptionCode());
	}

	@Override
	public void onSuccess(int requestCode,ResponseInfo<String> arg0) {
        String result = arg0.result;
        if(result.equals("200")){
        	Bundle bundle = new Bundle();
        	bundle.putString("mobile", str);
        	showShortToast("请求成功");
        	openActivity(SettingPasswordActivity.class, bundle);
        	finish();
        }else{
        	showShortToast("请求失败，请重试");
        }
	}
}
