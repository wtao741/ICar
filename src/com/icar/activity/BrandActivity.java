package com.icar.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.icar.adapter.BrandAdapter;
import com.icar.base.AbstractTitleActivity;
import com.icar.bean.BrandEntity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;


public class BrandActivity extends AbstractTitleActivity {

	@ViewInject(R.id.brand_listview) ListView listView;
	
	private List<BrandEntity> datas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_brand);
		setTitle("品牌文化");
		isShowLeftView(false);
		isShowRightView(0, false);
		
		ViewUtils.inject(this);
		
		setListView();
	}
	
	public void setListView(){
		datas = new ArrayList<BrandEntity>();
		
		BrandEntity entity = new BrandEntity();
		entity.setUrl(R.drawable.brand_icon1);
		entity.setDes("hello");
		
		BrandEntity entity1 = new BrandEntity();
		entity1.setUrl(R.drawable.brand_icon2);
		entity1.setDes("world");
		
		BrandEntity entity2 = new BrandEntity();
		entity2.setUrl(R.drawable.brand_icon1);
		entity2.setDes("凹凸曼");
		
		BrandEntity entity3 = new BrandEntity();
		entity3.setUrl(R.drawable.brand_icon2);
		entity3.setDes("黄色闪光");
		
		BrandEntity entity4 = new BrandEntity();
		entity4.setUrl(R.drawable.brand_icon1);
		entity4.setDes("桃地再不斩");
		
		datas.add(entity);
		datas.add(entity1);
		datas.add(entity2);
		datas.add(entity3);
		datas.add(entity4);
		listView.setAdapter(new BrandAdapter(this, datas));
	}
}
