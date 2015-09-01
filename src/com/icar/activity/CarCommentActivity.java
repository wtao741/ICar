package com.icar.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.icar.adapter.CarCommentAdapter;
import com.icar.base.AbstractTitleActivity;
import com.icar.base.HeadClick;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class CarCommentActivity extends AbstractTitleActivity implements HeadClick{

	@ViewInject(R.id.car_comment_listview) ListView listView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_car_comment);
		
		setTitle("车辆使用满意度");
		setHeadClick(this);
		isShowRightView(0, false);
		ViewUtils.inject(this);
		
		List<String> datas = new ArrayList<String>();
		datas.add("油耗水平");
		datas.add("车内空气质量");
		datas.add("停车入位");
		datas.add("空调效果");
		datas.add("车内空间");
		datas.add("车辆耐用度");
		datas.add("维修保养");
		datas.add("iUcars车震指数");
		
		listView.setAdapter(new CarCommentAdapter(this,datas));
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
