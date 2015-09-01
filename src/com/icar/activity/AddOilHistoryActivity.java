package com.icar.activity;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.icar.base.AbstractTitleActivity;
import com.icar.datatimewheel.DatePopupWindow;
import com.icar.datatimewheel.DatePopupWindow.OnDateSelectListener;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class AddOilHistoryActivity extends AbstractTitleActivity {

	@ViewInject(R.id.add_oil_date)
	TextView tv_date;
	@ViewInject(R.id.add_oil_odomentr)
	EditText et_odomentr;
	@ViewInject(R.id.add_oil_money)
	EditText et_money;
	@ViewInject(R.id.add_oil_price)
	EditText et_price;
	@ViewInject(R.id.add_oil_amount)
	EditText et_amount;

	@OnClick(R.id.add_oil_date)
	public void addOilDateonClick(View v) {
		showDataView();
	}

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_add_oil_history);
		setTitle("添加油耗记录");
		isShowLeftView(true);
		isShowRightView(R.string.save, true);
		ViewUtils.inject(this);
		setRightTextColor(R.color.white);

		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		tv_date.setText(format.format(new Date()));
	}

	private DatePopupWindow window;

	private void showDataView() {
		window = new DatePopupWindow(this, "", new OnDateSelectListener() {

			@Override
			public void onDateSelect(String value) {
				tv_date.setText(value);
			}
		});
		window.showWindow(tv_date);
	}
}
