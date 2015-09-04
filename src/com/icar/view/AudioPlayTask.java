package com.icar.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import com.icar.activity.R;
import com.icar.base.BaseApplication;
import com.icar.utils.SystemUtil;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


/**
 * 音频播放
 * 
 * @author mitbbs
 * 
 */
public class AudioPlayTask {
	private MediaPlayer mPlayer;
	private String mPath;
	private String id;// 所播放媒体文件的唯一标识
	private final String AUDIO_SUFFIX = ".amr";

	private boolean isStop = false;
	private String mFilePath = SystemUtil.getAudioDir();

	public AudioPlayTask(String audioFilePath) {
		mPath = audioFilePath;
	}

	public AudioPlayTask(String id, String audioFilePath, String filePath) {
		this.id = id;
		mPath = audioFilePath;
		mFilePath = filePath;
	}

	// 用于更新主界面
	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (msg.what == 0) {
				SystemUtil.ToastMessageShort(R.string.media_play_error);
				onFinish();
			} else if (msg.what == 1) { // 显示加载动画
				onDownloading();
			} else if (msg.what == 2) { // 显示播放动画
				onStart();
			} else if (msg.what == 3) { // 停止播放动画
				onFinish();
			} else if (msg.what == 4) { // 暂停播放动画
				onPause();
			}
			super.handleMessage(msg);
		}
	};

	public void start() {
		if (!SystemUtil.isExitsSdcard()) {
			SystemUtil.ToastMessageLong(R.string.check_sdcard);
			return;
		}

		Log.e("", "oplain.audioplaytask.start");
		// 停止上一个播放，不允许同时播放多个声音
		AudioPlayTask preTask = BaseApplication.getInstance()
				.getAudioPlayTask();
		if (preTask != null) {
			Log.e("", "oplain.audioplaytask.complete3");
			preTask.stop();
		}

		String audioName = SystemUtil.ShortText(mPath)[0] + AUDIO_SUFFIX;
		File file = new File(mFilePath + audioName);
		File localFile = new File(mPath);
		try {
			if (file.exists()) {
				Log.e("", "oplain.audioplayertask.file.exist");
				playAudio(mFilePath + audioName);
			} else if (localFile.exists()) {
				Log.e("", "oplain.audioplayertask.file.local.exist");
				playAudio(mPath);
			} else {
				Log.e("", "oplain.audioplayertask.download");
				handler.sendEmptyMessage(1);
				new Thread(new Runnable() {
					@Override
					public void run() {
						Log.e("", "oplain.audioplayertask.download.url="
								+ mPath);
						downloadAudio(mPath);
					}
				}).start();
			}
		} catch (Exception e) {
			handler.sendEmptyMessage(0);
			file.delete();
			stop();
			e.printStackTrace();
			return;
		}

		// try {
		// mPlayer = new MediaPlayer();
		// mPlayer.setDataSource(mPath);
		// mPlayer.prepare();
		// mPlayer.start();
		// onStart();
		// WeClubApplication.getInstance().setAudioPlayTask(this);
		//
		// mPlayer.setOnCompletionListener(new OnCompletionListener() {
		//
		// @Override
		// public void onCompletion(MediaPlayer mp) {
		// Log.e("", "oplain.audioplaytask.complete");
		// stop();
		// }
		// });
		//
		// } catch (IOException e) {
		// Log.e("", "oplain.audioplaytask.complete2");
		// e.printStackTrace();
		// stop();
		// }
	}

	public void downloadAudio(String audioUrl) {
		File audioFile = null;
		try {
			InputStream inputStream = null;
			BasicHttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 5000);
			HttpConnectionParams.setSoTimeout(httpParams, 30000);
			DefaultHttpClient client = new DefaultHttpClient(httpParams);
			client.setCookieStore(BaseApplication.getInstance()
					.getCookieStore());
			HttpGet httpGet = new HttpGet(audioUrl);
			HttpResponse response = client.execute(httpGet);
			int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				handler.sendEmptyMessage(0);
				return;
			}
			final HttpEntity entity = response.getEntity();
			if (entity == null) {
				handler.sendEmptyMessage(0);
				return;
			} else {
				inputStream = entity.getContent();
			}
			String audioName = SystemUtil.ShortText(audioUrl)[0] + AUDIO_SUFFIX;
			audioFile = new File(mFilePath + audioName);
			FileOutputStream outputStream = new FileOutputStream(audioFile);
			byte buf[] = new byte[16384];// 一次读取16K 16*1024
			int totalReadBytes = 0;
			int readBytes = 0;
			while ((readBytes = inputStream.read(buf)) > 0) {
				outputStream.write(buf, 0, readBytes);
				totalReadBytes += readBytes;
			}
			Log.e("", "oplain.audioplayer.totalReadBytes=" + totalReadBytes);
			inputStream.close();
			if (totalReadBytes < 200) {
				handler.sendEmptyMessage(0);
				audioFile.delete();
				return;
			}
			playAudio(mFilePath + audioName);
		} catch (Exception e) {
			if (audioFile != null && audioFile.exists()) {
				audioFile.delete();
			}
			handler.sendEmptyMessage(0);
			e.printStackTrace();
		}
	}

	public void playAudio(String audioPath) throws IOException {
		File file = new File(audioPath);
		if (file.exists()) {
			mPlayer = createMediaPlayer(file);
			mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mPlayer.start();
			BaseApplication.getInstance().setAudioPlayTask(this);
			handler.sendEmptyMessage(2);
		}
	}

	private MediaPlayer createMediaPlayer(File audioFile) throws IOException {
		final MediaPlayer mPlayer = new MediaPlayer();
		mPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
			public boolean onError(MediaPlayer mp, int what, int extra) {
				stop();
				return false;
			}
		});
		mPlayer.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer arg0) {
				stop();
			}
		});
		FileInputStream fis = new FileInputStream(audioFile);
		mPlayer.setDataSource(fis.getFD());// 此方法返回与流相关联的文件说明符。
		mPlayer.prepare();
		return mPlayer;
	}

	public void stop() {
		Log.e("", "oplain.audioplaytask.stop");
		if (mPlayer != null) {
			mPlayer.reset();
		}
		handler.sendEmptyMessage(3);
		// mPlayer.release();
		// mPlayer = null;
		// onFinish();
	}

	public void restartOrPause() {
		if (mPlayer != null) {
			if (!mPlayer.isPlaying()) {
				restart();
			} else if (mPlayer.isPlaying()) {
				pause();
			}
		}
	}

	// 暂停播放
	public void pause() {
		Log.e("", "oplain.audioplaytask.pause");
		mPlayer.pause();
		handler.sendEmptyMessage(4);
	}

	// 重启播放
	public void restart() {
		Log.e("", "oplain.audioplaytask.restart");
		mPlayer.start();
		handler.sendEmptyMessage(2);
	}

	/**
	 * 下载中...
	 */
	public void onDownloading() {
	};

	/**
	 * 用于控制动画的显示
	 */
	public void onStart() {
	};

	public void onPause() {
	};

	public void onFinish() {
	}

	public String getId() {
		return id;
	}

	public MediaPlayer getmPlayer() {
		return mPlayer;
	}

}
