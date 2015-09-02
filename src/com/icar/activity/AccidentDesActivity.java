package com.icar.activity;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.icar.base.AbstractTitleActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class AccidentDesActivity extends AbstractTitleActivity {

	@ViewInject(R.id.btn_set_mode_voice)
	Button bt_voice; // 声音按钮
	@ViewInject(R.id.btn_send)
	Button bt_send; // 发送按钮
	@ViewInject(R.id.btn_press_to_speak)
	View view_speak; // 录音
	@ViewInject(R.id.et_sendmessage)
	EditText et_message; // 消息输入框
	@ViewInject(R.id.recording_container)
	RelativeLayout recording_container; // 语音录入框
	@ViewInject(R.id.list)
	ListView list; // 消息
	@ViewInject(R.id.chat_swipe_layout)
	SwipeRefreshLayout swipeRefreshLayout;
	@ViewInject(R.id.pb_load_more)
	ProgressBar loadmorePB; // 消息

	private InputMethodManager manager;
	private Drawable[] micImages;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_accident_dex);
		setTitle("事故描述");
		isShowRightView(R.string.null_tips, false);
		ViewUtils.inject(this);

		manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		initView();
	}

	public void initView() {
		// 动画资源文件,用于录制语音时
		micImages = new Drawable[] {
				getResources().getDrawable(R.drawable.record_animate_01),
				getResources().getDrawable(R.drawable.record_animate_02),
				getResources().getDrawable(R.drawable.record_animate_03),
				getResources().getDrawable(R.drawable.record_animate_04),
				getResources().getDrawable(R.drawable.record_animate_05),
				getResources().getDrawable(R.drawable.record_animate_06),
				getResources().getDrawable(R.drawable.record_animate_07),
				getResources().getDrawable(R.drawable.record_animate_08),
				getResources().getDrawable(R.drawable.record_animate_09),
				getResources().getDrawable(R.drawable.record_animate_10),
				getResources().getDrawable(R.drawable.record_animate_11),
				getResources().getDrawable(R.drawable.record_animate_12),
				getResources().getDrawable(R.drawable.record_animate_13),
				getResources().getDrawable(R.drawable.record_animate_14) };
		
		swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
                android.R.color.holo_orange_light, android.R.color.holo_red_light);


	}

	/**
	 * 显示语音图标按钮
	 * 
	 * @param view
	 */
	public void setModeVoice(View view) {
		hideKeyboard();
		bt_send.setVisibility(View.GONE);
		et_message.setVisibility(View.GONE);
		view_speak.setVisibility(View.VISIBLE);
		recording_container.setVisibility(View.VISIBLE);
	}

	/**
	 * 隐藏软键盘
	 */
	private void hideKeyboard() {
		if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
			if (getCurrentFocus() != null)
				manager.hideSoftInputFromWindow(getCurrentFocus()
						.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}
}
