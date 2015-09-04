package com.icar.view;

import com.icar.activity.R;
import com.icar.base.BaseApplication;
import com.icar.utils.SystemUtil;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 录音对话框
 * 
 * @author mitbbs
 * 
 */
public class RecordVoiceDialog extends Dialog {
	private LinearLayout mRcdArea; // 录音状态区域
	private ImageView mRcdNotify; // 录音时间太短提示
	private LinearLayout mVolumeArea; // 麦克风加音量
	private ImageView mVoiceRcdVolume; // 音量
	private ImageView mRcdCancel; // 取消录制
	private TextView mRcdTips; // 文字提示
	private TextView recdTime; // 文字提示

	private String mPath = SystemUtil.getAudioDir(); // 录音文件路径

	private OnRecordVoiceListener mOnRecordVoiceListener;

	private AudioRecordTask2 mRecorder;

	private boolean mIsDeleStatus = false; // 是否为松手取消录音状态

	/* 录音按钮的坐标 */
	private float[] mBtnXY = new float[2];

	public RecordVoiceDialog(Context context, int theme) {
		super(context, theme);
		setContentView(R.layout.chat_rcd_dialog);
		mRcdArea = (LinearLayout) findViewById(R.id.rcd_area);
		mRcdNotify = (ImageView) findViewById(R.id.rcd_notify);
		mVolumeArea = (LinearLayout) findViewById(R.id.volume_area);
		mVoiceRcdVolume = (ImageView) findViewById(R.id.voice_rcd_volume);
		mRcdCancel = (ImageView) findViewById(R.id.rcd_cancel);
		mRcdTips = (TextView) findViewById(R.id.rcd_tips);
		recdTime = (TextView) findViewById(R.id.rec_time);
		recdTime.setVisibility(View.INVISIBLE);
	}

	public RecordVoiceDialog(Context context, int theme, boolean isShowTime,
			String filePath) {
		super(context, theme);
		setContentView(R.layout.chat_rcd_dialog);
		mRcdArea = (LinearLayout) findViewById(R.id.rcd_area);
		mRcdNotify = (ImageView) findViewById(R.id.rcd_notify);
		mVolumeArea = (LinearLayout) findViewById(R.id.volume_area);
		mVoiceRcdVolume = (ImageView) findViewById(R.id.voice_rcd_volume);
		mRcdCancel = (ImageView) findViewById(R.id.rcd_cancel);
		mRcdTips = (TextView) findViewById(R.id.rcd_tips);
		recdTime = (TextView) findViewById(R.id.rec_time);
		recdTime.setVisibility(isShowTime ? View.VISIBLE : View.INVISIBLE);
		mPath = filePath;
		mRcdTips.setText(R.string.move_up_to_cancel);
	}

	public TextView getRecdView() {
		return recdTime;
	}

	/**
	 * 设置关联按钮和结果回调
	 * 
	 * @param button
	 * @param listener
	 */
	public void setRecordVoiceListener(final Button button,
			OnRecordVoiceListener listener) {
		mOnRecordVoiceListener = listener;

		button.setOnTouchListener(new OnTouchListener() {
			private int actionDownCount = 1;
			private boolean canStart = false;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// if(true){}
				if (!SystemUtil.isExitsSdcard()){
					if (event.getAction() == MotionEvent.ACTION_DOWN
							&& actionDownCount == 1) {
						SystemUtil.ToastMessageShort(R.string.Send_voice_need_sdcard_support);
						actionDownCount++;
					} else if (event.getAction() == MotionEvent.ACTION_UP) {
						actionDownCount = 1;
					}
					return false;
				}
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					mBtnXY[0] = event.getRawX();
					mBtnXY[1] = event.getRawY();
					// v.setBackgroundResource(R.drawable.chat_voice_rcd_btn_press);

					if (!(canStart=mOnRecordVoiceListener.mayStart())) {

						return false;
					}
					if (BaseApplication.getInstance().getAudioPlayTask() != null) {
						BaseApplication.getInstance().getAudioPlayTask()
								.stop();
					}
					mRecorder = getAudioRecord();
					mRecorder.start();
				} else if (event.getAction() == MotionEvent.ACTION_UP) {
					// v.setBackgroundResource(R.drawable.chat_voice_rcd_btn_normal);
					if (!canStart || !SystemUtil.isExitsSdcard())
						return false;
					mRecorder.stopRcd(false, mIsDeleStatus);
					Log.v("录音", "录音结束了");
				} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
					if (event.getY() < -1 * button.getHeight()) { // 进入取消状态
						if (!mIsDeleStatus) {
							mVolumeArea.setVisibility(View.GONE);
							mRcdCancel.setVisibility(View.VISIBLE);
							mRcdTips.setText(R.string.rcd_up_cancel);
							mIsDeleStatus = true;
						}
					} else { // 非取消状态
						if (mIsDeleStatus) {
							mVolumeArea.setVisibility(View.VISIBLE);
							mRcdCancel.setVisibility(View.GONE);
							mRcdTips.setText(R.string.rcd_move_cancel);
							mIsDeleStatus = false;
						}
					}
				}
				return false;
			}
		});
	}

	/**
	 * 录音文件存储路径
	 * 
	 * @param path
	 */
	public void setPath(String path) {
		mPath = path;
	}

	/*
	 * 录音
	 */
	private AudioRecordTask2 getAudioRecord() {
		return new AudioRecordTask2(mPath) {

			@Override
			protected void onStart() {
				/* 初始化控件状态 */
				mRcdCancel.setVisibility(View.GONE);
				mVolumeArea.setVisibility(View.VISIBLE);
				mRcdArea.setVisibility(View.VISIBLE);
				mRcdNotify.setVisibility(View.GONE);
				getWindow().addFlags(
						WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
				if (bolShowRcdArea) {
					RecordVoiceDialog.this.show();
				}
			}

			@Override
			protected void onFinish() {
				if (RecordVoiceDialog.this != null
						&& RecordVoiceDialog.this.isShowing())
					RecordVoiceDialog.this.dismiss();
				Log.v("录音", "窗体消失了");
			}

			@Override
			protected void onSuccess(int rcdTime, String mFileName) {
				mOnRecordVoiceListener.onSuccess(rcdTime, mFileName);
				Log.v("录音", "调用了success方法");
			}

			@Override
			protected void onFailed() {
				mOnRecordVoiceListener.onFailed();
			}

			@Override
			protected void onShort() {
				Toast.makeText(getContext(), "您的录音时间过短，请重新录音", 0).show();
				mRcdArea.setVisibility(View.GONE);
				mRcdNotify.setVisibility(View.GONE);
			}

			@Override
			protected void onRecordProgress(int rcdTime) {
				mOnRecordVoiceListener.onRecordProgress(rcdTime);
			}

			@Override
			protected void onVolume(int volume) {
				switch (volume) {
				case 0:
				case 1:
					mVoiceRcdVolume.setImageResource(R.drawable.amp1);
					break;
				case 2:
					mVoiceRcdVolume.setImageResource(R.drawable.amp2);
					break;
				case 3:
					mVoiceRcdVolume.setImageResource(R.drawable.amp3);
					break;
				case 4:
					mVoiceRcdVolume.setImageResource(R.drawable.amp4);
					break;
				}
			}
		};
	}

	/**
	 * 录音结果回调
	 * 
	 * @author mitbbs
	 * 
	 */
	public interface OnRecordVoiceListener {
		/**
		 * 录音开始之前的判断
		 */
		public boolean mayStart();

		/**
		 * 录制成功
		 * 
		 * @param fileName
		 * @param rcdTime
		 */
		public void onSuccess(int rcdTime, String fileName);

		/**
		 * 失败
		 */
		public void onFailed();

		/**
		 * 录音进度回调
		 * 
		 * @param rcdTime
		 */
		public void onRecordProgress(int rcdTime);

	}

	// cxd 发文页面由于空间小，不能一直按着录音，改为点击后计时录音
	private boolean bolShowRcdArea = true;

	// 开始录音
	public void recordStart() {
		if (!SystemUtil.isExitsSdcard()) {
			SystemUtil.ToastMessageLong(R.string.check_sdcard);
			return;
		}
		bolShowRcdArea = false;
		mRecorder = getAudioRecord();
		mRecorder.start();
	}

	// 取消录音
	public void recordCancel() {
		mRecorder.stopRcd(false, true);
	}

	// 结束录音
	public void recordEnd() {
		mRecorder.stopRcd(false, false);
	}

	// 监听录音
	public void setRecordListener(OnRecordVoiceListener listener) {
		mOnRecordVoiceListener = listener;
	}
}
