package com.icar.activity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.icar.base.AbstractTitleActivity;
import com.icar.base.HeadClick;
import com.lidroid.xutils.view.annotation.ViewInject;

public class AccidentRecordActivity extends AbstractTitleActivity implements HeadClick{

	@ViewInject(R.id.date_select) ImageView iv_date; //选择日期
	@ViewInject(R.id.date) TextView tv_date; //选择日期
	@ViewInject(R.id.address_location) ImageView iv_location; //定位
	@ViewInject(R.id.address) EditText et_address; //地址
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_accident_record);
		setTitle("事故记录");
		isShowLeftView(true);
		isShowRightView(R.string.accident_record, true);
		setHeadClick(this);
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
