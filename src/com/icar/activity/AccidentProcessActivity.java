package com.icar.activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.icar.base.AbstractTitleActivity;
import com.icar.utils.SystemUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class AccidentProcessActivity extends AbstractTitleActivity {

	@ViewInject(R.id.progressBar1)
	ProgressBar progressBar;
	@ViewInject(R.id.webView1)
	WebView webView;

	private int type;
	private String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_show);

		type = getIntent().getIntExtra("type", 0);

		if (type == 4) {
			setTitle("事故处理流程");
			url = "http://api.iucars.com/data/App/accident.html";
		} else {
			setTitle("保险理赔");
			url = "http://api.iucars.com/data/App/safe.html";
		}

		isShowRightView(0, false);
		isShowCollect(false);

		ViewUtils.inject(this);

		String url = "http://api.iucars.com/data/App/accident.html";

		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setJavaScriptEnabled(true);
		// 有网络时使用默认模式
		if (SystemUtil.getInstance().isOnLine(this)) {
			webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		}
		// 无网络时使用优先从缓存读取模式
		else {
			webView.getSettings().setCacheMode(
					WebSettings.LOAD_CACHE_ELSE_NETWORK);
		}
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
}
