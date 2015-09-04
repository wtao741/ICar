package com.icar.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.icar.adapter.CarCommentAdapter1;
import com.icar.base.AbstractTitleActivity;
import com.icar.base.HeadClick;
import com.icar.bean.CarCommentEntity;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class CarCommentActivity extends AbstractTitleActivity implements HeadClick,HttpCallBack{

	@ViewInject(R.id.car_comment_listview) ListView listView;
	@ViewInject(R.id.tv_score) TextView tv_socre;
	
	@OnClick(R.id.submit)
	public void submitonClick(View v){
		Bundle bundle = new Bundle();
		bundle.putIntArray("score", scores);
		openActivity(AddCarCommentActivity.class,bundle);
	}
	
	
	private HttpUtil http ;
	private CarCommentAdapter1 adapter;
	List<CarCommentEntity> datas ;
	
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
		http.getUserGrade(3805);
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
	   Log.e("tag", arg0.result);
		try {
		JSONObject object = new JSONObject(arg0.result);
		String code = object.getString("code");
		if(code.equals("200") || code.equals("100")){
			JSONObject dataObject = object.getJSONObject("data");
			scores[0] = dataObject.getInt("oil");
			scores[1] = dataObject.getInt("air");
			scores[2] = dataObject.getInt("park");
			scores[3] = dataObject.getInt("airconditioner");
			scores[4] = dataObject.getInt("space");
			scores[5] = dataObject.getInt("userful");
			scores[6] = dataObject.getInt("service");
			scores[7] = dataObject.getInt("vehicle");
			score = dataObject.getString("sumavg");
			
			setAdapter();
		}
	} catch (JSONException e) {
		Log.e("Tag", e.getMessage());
		e.printStackTrace();
	}
	}
	
	
	public void setAdapter(){
		datas = new ArrayList<CarCommentEntity>();
		datas.add(new CarCommentEntity("油耗水平",scores[0]));
		datas.add(new CarCommentEntity("车内空气质量",scores[1]));
		datas.add(new CarCommentEntity("停车入位",scores[2]));
		datas.add(new CarCommentEntity("空调效果",scores[3]));
		datas.add(new CarCommentEntity("车内空间",scores[4]));
		datas.add(new CarCommentEntity("车辆耐用度",scores[5]));
		datas.add(new CarCommentEntity("维修保养",scores[6]));
		datas.add(new CarCommentEntity("iUcars车震指数",scores[7]));
		adapter = new CarCommentAdapter1(this,datas);
		listView.setAdapter(adapter);
		
		tv_socre.setText(score);
	}
	
	int[]  scores = new int[8];
	
	String score;
}
