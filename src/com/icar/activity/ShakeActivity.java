package com.icar.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icar.adapter.ShakeAdpater;
import com.icar.base.AbstractTitleActivity;
import com.icar.base.HeadClick;
import com.icar.bean.ShakeEditEntity;
import com.icar.bean.ShakeEntity;
import com.icar.bean.ShakeImgEntity;
import com.icar.bean.ShakeVideoEntity;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.icar.view.MyScrollView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ShakeActivity extends AbstractTitleActivity implements HeadClick,HttpCallBack {

	@ViewInject(R.id.shake_score)
	TextView tv_score;
	@ViewInject(R.id.shake_des)
	TextView tv_des;
	@ViewInject(R.id.shake_listView)
	ListView listView;
	@ViewInject(R.id.head_collect)
	ImageView head_collect;
	@ViewInject(R.id.shake_icon)
	ImageView iv_img;
	@ViewInject(R.id.shake_scrollView)
	MyScrollView scrollView;
	
	private List<ShakeEntity> datas;

	private HttpUtil http;
	
	private String point ="0";
	private ShakeImgEntity[] imgDatas;
	private ShakeVideoEntity videoBean;
	private List<ShakeEditEntity> editDatas = new ArrayList<ShakeEditEntity>();
	
	@OnClick(R.id.shake_icon)
	public void shakeIcononClick(View v) {
		Intent intent = new Intent(this, ShakeNextActivity.class);
		Bundle bundle = new Bundle();
		bundle.putParcelableArray("imgs", imgDatas);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	@OnClick(R.id.shake_icon_tv)
	public void shakeIconTvonClick(View v) {
		Intent intent = new Intent(this, ShakeNextActivity.class);
		startActivity(intent);
	}

	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_shake);

		setTitle(R.string.shake_tips);
		ViewUtils.inject(this);
		isShowRightView(0, false);
		setRightBackgorund(R.drawable.delect);
		isShowCollect(false);
		setHeadClick(this);
		tv_des.setText(ToDBC(getResources().getString(R.string.shake_des)));

		setDesClick();
		http = new HttpUtil(this);
		http.setHttpCallBack(this);
		http.getShakeInfo(3805);
		
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.car1)
				.showImageOnFail(R.drawable.car1).showImageForEmptyUri(R.drawable.car1).build();
	}

	private void setDesClick() {
		// TODO Auto-generated method stub
		String text = tv_des.getText().toString().trim();
		String searchStr = "点击查看》";

		SpannableStringBuilder cao2 = new SpannableStringBuilder(text);
		Pattern pattern2 = Pattern.compile("点击查看》");
		Matcher matcher2 = pattern2.matcher(text);
		while (matcher2.find()) {
			final String group = matcher2.group();
			NoLineClickSpan what = new NoLineClickSpan();
			cao2.setSpan(what, matcher2.start(), matcher2.end(), 0);
		}
		tv_des.setText(cao2);
		tv_des.setMovementMethod(LinkMovementMethod.getInstance());
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

	/*
	 * 将textview中的字符全角化。即将所有的数字、字母及标点全部转为全角字符，
	 * 使它们与汉字同占两个字节，这样就可以避免由于占位导致的排版混乱问题了。
	 */
	public static String ToDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}

	/*
	 * 详解http://blog.csdn.net/janronehoo/article/details/7238337
	 */
	class NoLineClickSpan extends ClickableSpan {

		public NoLineClickSpan() {
			super();
		}

		@Override
		public void updateDrawState(TextPaint ds) {
			ds.setColor(getResources().getColor(R.color.home_text_color));
			ds.setUnderlineText(false); // 去掉下划线
		}

		@Override
		public void onClick(View widget) {
			Intent intent = new Intent(ShakeActivity.this,
					ShakeScoreActivity.class);
			startActivity(intent);
		}
	}

	@Override
	public void onFailure(int requestCode, HttpException arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(int requestCode, ResponseInfo<String> arg0) {
		// TODO Auto-generated method stub
		String result = arg0.result;
		try {
			JSONObject object = new JSONObject(result);
			String code = object.getString("code");
			if(code.equals("200")){
				JSONObject dataObject = object.getJSONObject("data");
				point = dataObject.getString("point");
				
				//图片
				JSONArray imgs = dataObject.getJSONArray("imgs");
				int imgsLen = imgs.length();
				imgDatas = new ShakeImgEntity[imgsLen];
				for(int i=0;i<imgsLen;i++){
					JSONObject imgsObject = imgs.getJSONObject(i);
					String imgUrl = imgsObject.getString("imgurl");
					String des = imgsObject.getString("description");
					ShakeImgEntity imgBean = new ShakeImgEntity(imgUrl, des);
					imgDatas[i]=imgBean;
				}
				
				//视屏
				JSONObject videoObject = dataObject.getJSONObject("video");
				String uu = videoObject.getString("uu");
				String vu = videoObject.getString("vu");
				String pu = videoObject.getString("pu");
				videoBean = new ShakeVideoEntity(uu, vu, pu);
				
				//得到编辑说
				//图片
				JSONArray editArray = dataObject.getJSONArray("velist");
				int editLen = editArray.length();
				for(int i=0;i<editLen;i++){
					JSONObject editObject = editArray.getJSONObject(i);
					String author = editObject.optString("author");
					String avatar = editObject.getString("avatar");
					String content = editObject.getString("content");
					ShakeEditEntity editBean = new ShakeEditEntity(author, avatar, content);
					editDatas.add(editBean);
				}
				
				initView();
			}else{
				showShortToast("错误");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("tag", e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void initView(){
		scrollView.scrollTo(0, 0);
		tv_score.setText(point);
		listView.setAdapter(new ShakeAdpater(this, editDatas));
		imageLoader.displayImage(imgDatas[0].getUrl(), iv_img, options);
	}
}
