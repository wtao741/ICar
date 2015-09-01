package com.icar.activity;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.icar.base.AbstractTitleActivity;
import com.icar.base.BaseApplication;
import com.icar.base.HeadClick;
import com.icar.bean.ShakeImgEntity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ShakeNextActivity extends AbstractTitleActivity implements HeadClick{

	private Parcelable[] imgDatas;
	@ViewInject(R.id.shake_next_iv) ViewPager viewPager;
	@ViewInject(R.id.shake_next_tv) TextView tv;
	
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
        setContentView(R.layout.activity_shake_next);
		
		setTitle(R.string.shake_tips);
		ViewUtils.inject(this);
		isShowRightView(R.string.null_tips, true);
		setRightBackgorund(R.drawable.delect);
		isShowCollect(true);
		setHeadClick(this);
		
		LayoutParams parmas = (LayoutParams) viewPager.getLayoutParams();
		parmas.height = (int) (BaseApplication.WIDTH/1.5);
		
		imgDatas = (Parcelable[]) getIntent().getExtras().getParcelableArray("imgs");
		ShakeImgEntity bean = (ShakeImgEntity) imgDatas[0];
		tv.setText(BaseApplication.ToDBC(bean.getDes()));
		
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.car_normal_max)
				.showImageOnFail(R.drawable.car_normal_max).showImageForEmptyUri(R.drawable.car_normal_max).
				cacheInMemory(true).
				build();
		
	
	    viewPager.setAdapter(new MyViewPagerAdapter());
	    viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				ShakeImgEntity bean = (ShakeImgEntity) imgDatas[arg0];
				tv.setText(BaseApplication.ToDBC(bean.getDes()));
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	class MyViewPagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imgDatas.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == (View)arg1;
		}
		
		@Override
		public void destroyItem(View container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager)container).removeView((View)object);
		}
		
		@Override
		public Object instantiateItem(View container, int position) {
			// TODO Auto-generated method stub
			ShakeImgEntity bean = (ShakeImgEntity) imgDatas[position];
			
			ImageView iv = new ImageView(ShakeNextActivity.this);
			iv.setLayoutParams( new LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.MATCH_PARENT));
			imageLoader.displayImage(bean.getUrl(), iv, options);
			((ViewPager)container).addView(iv);
			return iv;
		}
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
}
