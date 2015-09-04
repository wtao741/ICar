package com.icar.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.icar.adapter.CarCommentAdapter;
import com.icar.adapter.CarCommentAdapter1;
import com.icar.base.AbstractTitleActivity;
import com.icar.base.BaseApplication;
import com.icar.base.HeadClick;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class CarCommentActivity extends AbstractTitleActivity implements HeadClick,HttpCallBack{

	@ViewInject(R.id.car_comment_listview) ListView listView;
	
	@OnClick(R.id.submit)
	public void submitonClick(View v){
		openActivity(AddCarCommentActivity.class);
	}
	
	
	private HttpUtil http ;
	private CarCommentAdapter1 adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_car_comment);
		
		setTitle("车辆使用满意度");
		setHeadClick(this);
		isShowRightView(0, false);
		ViewUtils.inject(this);
		
		http = new HttpUtil(this);
		http.setHttpCallBack(this);
		
		List<String> datas = new ArrayList<String>();
		datas.add("油耗水平");
		datas.add("车内空气质量");
		datas.add("停车入位");
		datas.add("空调效果");
		datas.add("车内空间");
		datas.add("车辆耐用度");
		datas.add("维修保养");
		datas.add("iUcars车震指数");
		
		adapter = new CarCommentAdapter1(this,datas);
		listView.setAdapter(adapter);
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

	@Override
	public void onFailure(int requestCode, HttpException arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(int requestCode, ResponseInfo<String> arg0) {
		// TODO Auto-generated method stub
		Log.e("Tag", arg0.result);
	}
}
