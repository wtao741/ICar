package com.icar.view;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import com.icar.utils.SystemUtil;

import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


/**
 * ¼��amr��ʽ
 * 
 * @author mitbbs
 * 
 */
public abstract class AudioRecordTask2 {
	private MediaRecorder mRecorder;
	private Timer mRcdTimer = new Timer();
	private boolean mIsRecording = true;
	private Handler mHandler;
	private long mStartRcdTime; // ��ʼ¼��ʱ��
	private String mFileName; // ¼���ļ���
	private String mPath; // ¼���ļ��洢·��

	private static final int AUDIO_RECORD_START = 0x00;
	private static final int AUDIO_RECORD_FINISH = 0x01;
	private static final int AUDIO_RECORD_SUCCESS = 0x02;
	private static final int AUDIO_RECORD_FAILED = 0x03;
	private static final int AUDIO_RECORD_PROGRESS = 0x04;
	private static final int AUDIO_RECORD_VOLUME = 0x05;
	private static final int AUDIO_RECORD_SHORT = 0x06;

	public static final int MAX_RECORD_TIME = 30000; // ���¼��ʱ��30000����

	public AudioRecordTask2(String path) {
		/*
		 * UI�߳��е�Handler
		 */
		mHandler = new Handler() {
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case AUDIO_RECORD_START:
					onStart();
					break;
				case AUDIO_RECORD_FINISH:
					onFinish();
					break;
				case AUDIO_RECORD_SUCCESS:
					onSuccess(msg.arg1, mFileName);
					break;
				case AUDIO_RECORD_FAILED:
					onFailed();
					break;
				case AUDIO_RECORD_PROGRESS:
					onRecordProgress(msg.arg1);
					break;
				case AUDIO_RECORD_VOLUME:
					onVolume(msg.arg1);
					Log.e("timer", "timer" + msg.arg1);
					break;
				case AUDIO_RECORD_SHORT:
					onShort();
					break;
				}
			};
		};
		mPath = path;
		mFileName = SystemUtil.createFileNameUnique();
	}

	public void start() {

		mRecorder = new MediaRecorder();
		mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		mRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
		mRecorder.setOutputFile(mPath + mFileName + ".amr");
		mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		mRecorder.setAudioSamplingRate(8000);
		mRecorder.setAudioEncodingBitRate(16);

		try {
			mRecorder.prepare();
			mRecorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			mHandler.sendEmptyMessage(AUDIO_RECORD_FAILED);
			return;
		}

		mHandler.sendEmptyMessage(AUDIO_RECORD_START);
		mStartRcdTime = System.currentTimeMillis();

		/*
		 * ��ʱ��ȡMIC����
		 */
		mRcdTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				// http://www.cnblogs.com/lqminn/archive/2012/10/10/2717904.html
				// ���32767���ֳ�7�ݣ�ÿ�����4681
				try {
					Message.obtain(mHandler, AUDIO_RECORD_VOLUME,
							mRecorder.getMaxAmplitude() / 4681, 0)
							.sendToTarget();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}, 0, 500);

		/*
		 * ��ʱ��ȡ¼��ʱ��
		 */
		mRcdTimer.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				int rcdTime = (int) (System.currentTimeMillis() - mStartRcdTime);
				if (rcdTime >= MAX_RECORD_TIME) {
					rcdTime = MAX_RECORD_TIME;
					stopRcd(true, false);
					Log.e("finish", "yes");
				}
				Message.obtain(mHandler, AUDIO_RECORD_PROGRESS, rcdTime, 0)
						.sendToTarget();
			}
		}, 0, 1000);
	}

	/**
	 * 
	 * @param reachMaxTime
	 *            �������¼��ʱ��
	 * @param cancelRcd
	 *            ȡ��¼��
	 */
	public synchronized void stopRcd(boolean reachMaxTime, boolean cancelRcd) {
		int rcdTime = (int) (System.currentTimeMillis() - mStartRcdTime);
		if (reachMaxTime) {
			rcdTime = MAX_RECORD_TIME;

		}

		if (mIsRecording) {
			mIsRecording = false;
			mRecorder.stop();
			mRecorder.release();
			mRcdTimer.cancel();
			mHandler.sendEmptyMessage(AUDIO_RECORD_FINISH);
			if (cancelRcd) {
				new File(mPath + mFileName).delete();
			} else {
				if (rcdTime < 1000) {
					mHandler.sendEmptyMessage(AUDIO_RECORD_SHORT);
					new File(mPath + mFileName).delete();
				} else {
					Message.obtain(mHandler, AUDIO_RECORD_SUCCESS, rcdTime, 0)
							.sendToTarget();
				}
			}
		}
	}

	/**
	 * ¼����ʼ
	 */
	protected abstract void onStart();

	/**
	 * ¼������
	 */
	protected abstract void onFinish();

	/**
	 * ¼���ɹ�
	 */
	protected abstract void onSuccess(int rcdTime, String mFileName);

	/**
	 * ¼���������
	 */
	protected abstract void onFailed();

	/**
	 * ¼��ʱ���̣�����1��
	 */
	protected abstract void onShort();

	/**
	 * ʵʱ¼��ʱ��
	 */
	protected abstract void onRecordProgress(int rcdTime);

	/**
	 * ʵʱMIC����
	 * 
	 * @param volume
	 */
	protected abstract void onVolume(int volume);
}
