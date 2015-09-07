package com.icar.activity;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.icar.base.AbstractTitleActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class UserProtocolActivity extends AbstractTitleActivity {

	@ViewInject(R.id.webView1) WebView webView;
	@ViewInject(R.id.progressBar1) ProgressBar progressBar;
	
	private String url = "http://api.iucars.com/data/App/notice.html";
	
	public void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		setTitle("用户协议");
		isShowLeftView(true);
		isShowRightView(R.string.null_tips, false);
		
		ViewUtils.inject(this);
		
		
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
	};
}
