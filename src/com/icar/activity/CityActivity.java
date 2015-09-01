package com.icar.activity;

import java.util.List;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.icar.base.AbstractTitleActivity;
import com.icar.bean.CityEntity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class CityActivity extends AbstractTitleActivity {

	@ViewInject(R.id.listview) ListView listView;
	@ViewInject(R.id.hot_city) ListView hotListView;
	
	private View view;
	
	private List<CityEntity> hotDatas ;
	private List<CityEntity> datas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.listview_no_driver);
		setTitle("选择城市");
		isShowRightView(0, false);
		isShowLeftView(true);
		
		ViewUtils.inject(this);
		
		view = LayoutInflater.from(this).inflate(R.layout.hot_city, null);
		ViewUtils.inject(this, view);
	}
}

