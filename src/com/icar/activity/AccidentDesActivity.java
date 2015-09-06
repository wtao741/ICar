package com.icar.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.easemob.chat.MessageBody;
import com.icar.adapter.MsgAdapter;
import com.icar.base.AbstractTitleActivity;
import com.icar.base.BaseApplication;
import com.icar.bean.Msg;
import com.icar.utils.DateUtil;
import com.icar.utils.SystemUtil;
import com.icar.view.PullListView;
import com.icar.view.RecordVoiceDialog;
import com.icar.view.RecordVoiceDialog.OnRecordVoiceListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class AccidentDesActivity extends AbstractTitleActivity {

	@ViewInject(R.id.btn_set_mode_voice)
	Button bt_voice; // 声音按钮
	@ViewInject(R.id.btn_set_mode_keyboard)
	Button buttonSetModeKeyboard; // 键盘按钮
	@ViewInject(R.id.btn_send)
	Button bt_send; // 发送按钮
	@ViewInject(R.id.btn_speak)
	Button bt_speak; // 录音
	@ViewInject(R.id.et_sendmessage)
	EditText et_message; // 消息输入框
	@ViewInject(R.id.chat_lv)
	PullListView list; // 消息

	private InputMethodManager manager;
	private RecordVoiceDialog mRcdVoiceDialog;
	public String playMsgId;
    private List<Msg> datas = new ArrayList<Msg>();
	private MsgAdapter adapter;
      
	@OnClick(R.id.btn_send)
	public void btnSendonClick(View v){
		String message = et_message.getText().toString();
		if(message.length() == 0){
			showShortToast("请输入内容");
			return ;
		}else{
			Msg msg = new Msg();
			msg.setContent(message);
			msg.setMsgType(1);
			msg.setUser(BaseApplication.getUserName());
			datas.add(msg);
			
			adapter = new MsgAdapter(this, datas);
			adapter.notifyDataSetChanged();
		}
	}
	
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
		// 录音
		mRcdVoiceDialog = new RecordVoiceDialog(this, R.style.voiceDialogStyle,
				true, SystemUtil.getAudioDir());
		mRcdVoiceDialog.setRecordVoiceListener(bt_speak,
				new OnRecordVoiceListener() {

					@Override
					public void onSuccess(int rcdTime, String fileName) {

						// MsgManager.getInstance().sendMsg(
						// MsgType.MSG_TYPE_VOICE, mUser.getUser_id(),
						// mFriend.getUser_id(), null, rcdTime, fileName);
						Log.e("tag", "onSuccess");
						Log.e("tag", fileName);
						if (rcdTime >= 30 * 1000)
							SystemUtil
									.ToastMessageLong(R.string.chat_rcd_maxtime);

						if (!SystemUtil.isNetWorkOk(AccidentDesActivity.this)) {

							SystemUtil.ToastMessageLong(R.string.network_bad);
//							MsgManager.getInstance().saveNotSendMsg(
//									MsgType.MSG_TYPE_VOICE, mUser.getUser_id(),
//									mFriend.getUser_id(), null, rcdTime,
//									fileName);

							// MsgManager.getInstance().createMsg(msgType, from,
							// to,
							// content, mediaLength, key)
							return;
						} else {
//							MsgManager.getInstance().sendMsg(ChatActivity.this,
//									MsgType.MSG_TYPE_VOICE, mUser.getUser_id(),
//									mFriend.getUser_id(), null, rcdTime,
//									fileName);
							
							Log.e("tag", "send");
						}
					}

					@Override
					public void onRecordProgress(int rcdTime) {
						Log.e("tag", "onRecordProgress");
						mRcdVoiceDialog.getRecdView().setText(
								DateUtil.formatAudioLength(rcdTime));
					}

					@Override
					public void onFailed() {
						Log.e("tag", "onFailed");
						mRcdVoiceDialog.getRecdView().setText("00:00");
					}

					@Override
					public boolean mayStart() {
						Log.e("tag", "mayStart");
						return true;
					}
				});
	}

	/**
	 * 显示语音图标按钮
	 * 
	 * @param view
	 */
	public void setModeVoice(View view) {
		hideKeyboard();
		bt_send.setVisibility(View.GONE);
		view.setVisibility(View.GONE);
		buttonSetModeKeyboard.setVisibility(View.VISIBLE);
		et_message.setVisibility(View.GONE);
		//view_speak.setVisibility(View.VISIBLE);
		bt_speak.setVisibility(View.VISIBLE);
	}

	/**
	 * 显示键盘图标
	 * 
	 * @param view
	 */
	public void setModeKeyboard(View view) {
		et_message.setVisibility(View.VISIBLE);
		view.setVisibility(View.GONE);
		bt_voice.setVisibility(View.VISIBLE);
		et_message.requestFocus();
		//view_speak.setVisibility(View.GONE);
		bt_send.setVisibility(View.VISIBLE);
		bt_speak.setVisibility(View.GONE);
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
