package com.icar.activity;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class OilRecordActivity extends Activity {

	@ViewInject(R.id.head_left) LinearLayout head_left;
	@ViewInject(R.id.head_title) TextView head_title;
	@ViewInject(R.id.oil_recore_recently) Button bt_recently;
	@ViewInject(R.id.oil_recore_month) Button bt_month;
	@ViewInject(R.id.oil_recore_year) Button bt_year;
	@ViewInject(R.id.oil_record_viewPager) ViewPager viewPager;
	@ViewInject(R.id.oil_distance_tv) TextView tv_distance;
	@ViewInject(R.id.oil_sum_tv) TextView tv_sum;
	@ViewInject(R.id.oil_money_tv) TextView tv_money;
	@ViewInject(R.id.oil_recently_tv) TextView tv_recently;
	@ViewInject(R.id.oil_avg_tv) TextView tv_avg;
	@ViewInject(R.id.oil_avg_money_tv) TextView avg_money;
	
	@OnClick(R.id.head_left) 
	public void headLeftonClick(View v){
		finish();
	}
	
	@OnClick(R.id.add_oil_record) 
	public void addOilRecordonClick(View v){
		Intent intent = new Intent(this,AddOilHistoryActivity.class);
		startActivity(intent);
	}
	
	@OnClick(R.id.oil_record_real) 
	public void addOilRecordRealonClick(View v){
		Intent intent = new Intent(this,OilHistoryRecordActivity.class);
		startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_oil_record);
		ViewUtils.inject(this);
		
		head_title.setText("油耗记录");
	}

	
	
	
}
