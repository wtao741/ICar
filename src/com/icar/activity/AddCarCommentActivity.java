package com.icar.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.icar.adapter.CarCommentAdapter;
import com.icar.base.AbstractTitleActivity;
import com.icar.base.BaseApplication;
import com.icar.base.HeadClick;
import com.icar.bean.CarCommentEntity;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class AddCarCommentActivity extends AbstractTitleActivity implements HeadClick,HttpCallBack{

	@ViewInject(R.id.car_comment_listview) ListView listView;
	@ViewInject(R.id.score) LinearLayout score_linear;
	@ViewInject(R.id.submit) Button submit;
	
	
	@OnClick(R.id.submit)
	public void submitonClick(View v){
		int[] scores = adapter.getScore();
		for(int temp:scores){
			Log.e("tag", ""+temp);
		}
		Log.e("tag", BaseApplication.getUserName());
		http.userGradeAdd(BaseApplication.getUserName(), ""+2859, ""+scores[0], ""+scores[1], ""+scores[2], ""+scores[3]
				, ""+scores[4], ""+scores[5], ""+scores[6], ""+scores[7]);
	}
	
	
	private HttpUtil http ;
	private CarCommentAdapter adapter;
	private int[] scores; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_car_comment);
		
		setTitle("车辆使用满意度");
		setHeadClick(this);
		isShowRightView(0, false);
		ViewUtils.inject(this);
		
		score_linear.setVisibility(View.GONE);
		submit.setText("提交");
		
		http = new HttpUtil(this);
		http.setHttpCallBack(this);
		
		scores = getIntent().getExtras().getIntArray("score");
		
		List<CarCommentEntity> datas = new ArrayList<CarCommentEntity>();
		datas.add(new CarCommentEntity("油耗水平",scores[0]));
		datas.add(new CarCommentEntity("车内空气质量",scores[1]));
		datas.add(new CarCommentEntity("停车入位",scores[2]));
		datas.add(new CarCommentEntity("空调效果",scores[3]));
		datas.add(new CarCommentEntity("车内空间",scores[4]));
		datas.add(new CarCommentEntity("车辆耐用度",scores[5]));
		datas.add(new CarCommentEntity("维修保养",scores[6]));
		datas.add(new CarCommentEntity("iUcars车震指数",scores[7]));
		
		adapter = new CarCommentAdapter(this,datas);
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
		try {
			JSONObject object = new JSONObject(arg0.result);
			String code = object.getString("code");
			if(code.equals("200")){
				showShortToast("成功");
			}else if(code.equals("202")){
				showShortToast("已打过分");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
