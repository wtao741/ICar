package com.icar.activity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.icar.base.AbstractTitleActivity;
import com.icar.base.BaseApplication;
import com.icar.bean.InteriorControlEntity;
import com.icar.bean.SearchEntity;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ShowActivity extends AbstractTitleActivity implements HttpCallBack{

	private WebView webView;
	private ProgressBar progressBar;

	private String type; // 类型
	private InteriorControlEntity bean;
	private String url;
	private SearchEntity searchBean;
    private HttpUtil http;
	
    private boolean isCollect;
    
	@ViewInject(R.id.head_collect) ImageView iv_collect;
	@OnClick(R.id.head_collect)
	public void headCollectonClick(View v){
		int classid = 0;
		if(type.equals("interior")){
			classid = bean.getClassid();
		}else if (type.equals("search")){
			classid = searchBean.getClassid();
		}
		if(!isCollect){
		   http.collect(BaseApplication.getUserName(), 3805, classid);
		}else{
			int[] ints = {classid};
			http.delectCollect(BaseApplication.getUserName(), 3805, ints);
		}
	}
	
	@OnClick(R.id.head_right)
	public void headRightonClick(View v){
		showShare();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);

		Bundle bundle = getIntent().getExtras();
		type = bundle.getString("type");

		ViewUtils.inject(this);
		http = new HttpUtil(this);
		http.setHttpCallBack(this);
		
		if (type.equals("interior")) {
			bean = (InteriorControlEntity) bundle.getSerializable("bean");
			url = "http://api.iucars.com/index.php?g=App&m=Api&a=getHtmlContent&seriesid=3805&classid="
					+ bean.getClassid();
			setTitle(bean.getName());
		} else if (type.equals("search")) {
			searchBean = (SearchEntity) bundle.getSerializable("bean");
			url = "http://api.iucars.com/index.php?g=App&m=Api&a=getHtmlContent&seriesid=3805&classid="
					+ searchBean.getClassid();
			setTitle(searchBean.getClassname());
		}
        
		setRightBackgorund(R.drawable.share_icon);
		isShowCollect(true);
        isShowRightView(R.string.null_tips, true);
        
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);
		webView = (WebView) findViewById(R.id.webView1);
		webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setJavaScriptEnabled(true);
		// 有网络时使用默认模式
		// if (SystemUtil.getInstance(this).isOnline()) {
		// webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
		// }
		// // 无网络时使用优先从缓存读取模式
		// else {
		webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		// }
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

	@Override
	public void onFailure(int requestCode, HttpException arg0, String arg1) {
		
	}

	@Override
	public void onSuccess(int requestCode, ResponseInfo<String> arg0) {
		Log.e("tag", arg0.result);
		String result = arg0.result;
		
		switch (requestCode) {
		case 0:
			try {
				JSONObject object = new JSONObject(result);
				String code = object.getString("code");
				if(code.equals("200")){
					showShortToast("收藏成功");
					isCollect = true;
					iv_collect.setImageResource(R.drawable.collection_click);
				}else if(code.equals("101")){
					showShortToast("该文章已被收藏");
				}else{
					showShortToast("收藏失败");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 1:
			Log.e("tag", "delect");
			break;
		case 2:
			if(result.equals("101")){
				isCollect = true;
				iv_collect.setImageResource(R.drawable.collection_click);
			}else{
				isCollect = false;
				iv_collect.setImageResource(R.drawable.collection_normal);
			}
			break;
		default:
			break;
		}
		
		
	}

	private void showShare() {
		 ShareSDK.initSDK(this);
		 OnekeyShare oks = new OnekeyShare();
		 //关闭sso授权
		 oks.disableSSOWhenAuthorize(); 
		 
		// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		 oks.setTitle(getString(R.string.share));
		 // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		 oks.setTitleUrl("http://sharesdk.cn");
		 // text是分享文本，所有平台都需要这个字段
		 oks.setText("我是分享文本");
		 // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		 oks.setImageUrl("http://7xlcte.dl1.z0.glb.clouddn.com/@/data/upload/book/50/4.jpg");
		 //oks.setImagePath();//确保SDcard下面存在此张图片
		 // url仅在微信（包括好友和朋友圈）中使用
		 oks.setUrl("http://sharesdk.cn");
		 // comment是我对这条分享的评论，仅在人人网和QQ空间使用
		 oks.setComment("我是测试评论文本");
		 // site是分享此内容的网站名称，仅在QQ空间使用
		 oks.setSite(getString(R.string.app_name));
		 // siteUrl是分享此内容的网站地址，仅在QQ空间使用
		 oks.setSiteUrl("http://sharesdk.cn");
		 
		// 启动分享GUI
		 oks.show(this);
		 }
	// @Override
	// public boolean onKeyDown(int keyCode, KeyEvent event) {
	// // TODO Auto-generated method stub
	// if (keyCode == KeyEvent.KEYCODE_BACK) {
	// if (event.getAction() == KeyEvent.ACTION_DOWN) {
	// finishDefault(CHANG_MODE_BACK);
	// return true;
	// }
	// }
	// return super.onKeyDown(keyCode, event);
	// }
}
