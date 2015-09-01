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
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * @author gisdom
 * 注册
 */
@SuppressLint("ResourceAsColor")
public class RegisterActivity extends AbstractTitleActivity implements
		HeadClick, HttpCallBack {

	private int beforeLen = 0;
	private int afterLen = 0;
	private String mobile = ""; // 格式化电话号码
	private String str = ""; // 正确的电话号码
	private String auth = ""; // 验证码
	private String password = ""; // 密码
	private HttpUtil http;
	private DownTimer timer;

	@ViewInject(R.id.user_name)
	EditText et_name;
	@ViewInject(R.id.user_password)
	EditText et_pass;
	@ViewInject(R.id.user_auth)
	EditText et_auth;
	@ViewInject(R.id.register_auth)
	Button bt_auth;
	@ViewInject(R.id.login_login)
	Button bt_register;

	@OnClick(R.id.register_auth)
	public void registerAuthonClick(View v) {
		mobile = et_name.getText().toString();
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

	@OnClick(R.id.login_login)
	public void loginLoginonClick(View v) {
		mobile = et_name.getText().toString();
		auth = et_auth.getText().toString();
		password = et_pass.getText().toString();
		int len = mobile.length();
		int authLen = auth.length();
		int passLen = password.length();
		if (len == 0) {
			showShortToast("请输入电话号码");
		} else if (len != 13) {
			showShortToast("请填写正确的电话号码");
		} else {
			if (authLen == 0) {
				showShortToast("请输入验证码");
			} else if (authLen != 4) {
				showShortToast("请输入正确的验证码");
			} else {
				if (passLen == 0) {
					showShortToast("请输入密码");
				} else if (passLen < 6 || passLen > 20) {
					showShortToast("请输入正确的密码");
				} else {
					str = mobile.replace(" ", "");
					http.register(str, auth, password);
				}
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		setTitle("注册");
		isShowRightView(0, false);
		ViewUtils.inject(this);
		initView();

		http = new HttpUtil(this);
		http.setHttpCallBack(this);
		timer = new DownTimer(60000, 1000);
	}

	private void initView() {
		et_name.addTextChangedListener(new TextWatcher() {

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
				String txt = et_name.getText().toString();
				afterLen = txt.length();

				if (afterLen > beforeLen) {
					if (txt.length() == 4 || txt.length() == 9) {
						et_name.setText(new StringBuffer(txt).insert(
								txt.length() - 1, " ").toString());
						et_name.setSelection(et_name.getText().length());
					}
				} else {
					if (txt.startsWith(" ")) {
						et_name.setText(new StringBuffer(txt).delete(
								afterLen - 1, afterLen).toString());
						et_name.setSelection(et_name.getText().length());
					}
				}
				
				if(afterLen == 0){
					timer.cancel();
					bt_auth.setBackgroundResource(R.drawable.register_auth_selector);
					bt_auth.setText("发送验证码");
					bt_auth.setClickable(true);
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

	class DownTimer extends CountDownTimer {

		public DownTimer(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onTick(long millisUntilFinished) {
			// TODO Auto-generated method stub
			bt_auth.setText(millisUntilFinished / 1000 + "  s");
			bt_auth.setBackgroundResource(R.drawable.register_auth_click);
			bt_auth.setClickable(false);
		}

		@Override
		public void onFinish() {
			bt_auth.setText("发送验证码");
			bt_auth.setBackgroundResource(R.drawable.register_auth_selector);
			bt_auth.setClickable(true);
		}

	}

	@Override
	public void onFailure(int requestCode,HttpException arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(int requestCode,ResponseInfo<String> arg0) {
		String result = arg0.result;
		Log.e("auth", result);
		if (result.equals("200")) {
			showShortToast("请求验证码成功");
			timer.start();
		} else if (result.equals("201")) {
			showShortToast("该号码已注册，请重试");
		} else {
			showShortToast("错误");
		}
	}
}
