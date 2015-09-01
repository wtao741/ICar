package com.icar.activity;

import com.icar.base.AbstractTitleActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class ShowActivity extends AbstractTitleActivity {

	private WebView webView;
	private ProgressBar progressBar;

	private int classid ; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);

		classid = getIntent().getExtras().getInt("classid");
		String url = "http://api.iucars.com/index.php?g=App&m=Api&a=getHtmlContent&seriesid=3805&classid="+classid;
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		webView = (WebView) findViewById(R.id.webView1);
		// 有网络时使用默认模式
//		if (SystemUtil.getInstance(this).isOnline()) {
//			webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
//		}
//		// 无网络时使用优先从缓存读取模式
//		else {
			webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
	//	}
		WebChromeClient client = new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				super.onProgressChanged(view, newProgress);
				progressBar.setProgress(newProgress);
				if (newProgress >= 100) {
					progressBar.setVisibility(View.GONE);
				}
			}
		};
		webView.setWebChromeClient(client);
		webView.loadUrl(url);
	}

//	@Override
//	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// TODO Auto-generated method stub
//		if (keyCode == KeyEvent.KEYCODE_BACK) {
//			if (event.getAction() == KeyEvent.ACTION_DOWN) {
//				finishDefault(CHANG_MODE_BACK);
//				return true;
//			}
//		}
//		return super.onKeyDown(keyCode, event);
//	}
}
