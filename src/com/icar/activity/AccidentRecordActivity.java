package com.icar.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.icar.base.AbstractTitleActivity;
import com.icar.base.HeadClick;
import com.icar.datatimewheel.DatePopupWindow;
import com.icar.datatimewheel.DatePopupWindow.OnDateSelectListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class AccidentRecordActivity extends AbstractTitleActivity implements HeadClick{

	@ViewInject(R.id.date_select) ImageView iv_date; //选择日期
	@ViewInject(R.id.date) TextView tv_date; //选择日期
	@ViewInject(R.id.address_location) ImageView iv_location; //定位
	@ViewInject(R.id.address) EditText et_address; //地址
	@ViewInject(R.id.parent) View view;   //父类布局
	@ViewInject(R.id.people1) EditText et_people1;
	@ViewInject(R.id.people2) EditText et_people2;
	@ViewInject(R.id.people3) EditText et_people3;
	@ViewInject(R.id.mobile1) EditText et_mobile1;
	@ViewInject(R.id.mobile2) EditText et_mobile2;
	@ViewInject(R.id.mobile3) EditText et_mobile3;
	
	
	@OnClick(R.id.date_select)
	public void dateSelectonClick(View v){
		DatePopupWindow popupWindow = new DatePopupWindow(this, "选择时间", new OnDateSelectListener() {
			
			@Override
			public void onDateSelect(String value) {
				// TODO Auto-generated method stub
				tv_date.setText(value);
			}
		});
		popupWindow.showWindow(view);
	}
	
	@OnClick(R.id.des)
	public void desonClick(View v){
		openActivity(AccidentDesActivity.class);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_accident_record);
		setTitle("事故记录");
		isShowLeftView(true);
		isShowRightView(R.string.null_tips, false);
		setHeadClick(this);
		ViewUtils.inject(this);
	}

	@Override
	public void left() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void right() {
		openActivity(AddicentHistoryActivity.class);
	}
	
	
}
