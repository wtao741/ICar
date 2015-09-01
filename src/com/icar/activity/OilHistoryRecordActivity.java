package com.icar.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.widget.ListView;

import com.icar.adapter.util.CommonAdapter;
import com.icar.adapter.util.ViewHolder;
import com.icar.base.AbstractTitleActivity;
import com.icar.bean.OilHistoryEntity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class OilHistoryRecordActivity extends AbstractTitleActivity {

	@ViewInject(R.id.drive_listView)
	ListView listView;

	private List<OilHistoryEntity> datas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_drive);
		setTitle("历史记录");
		isShowLeftView(true);
		isShowRightView(0, false);
		ViewUtils.inject(this);

		addData();
		setAdapter();
	}

	private void setAdapter() {
		// TODO Auto-generated method stub
		// 设置适配器
		listView.setAdapter(new CommonAdapter<OilHistoryEntity>(
				getApplicationContext(), datas, R.layout.oil_record_item) {
			@Override
			public void convert(ViewHolder helper, OilHistoryEntity item) {
				helper.setText(R.id.oil_record_date, item.getDate());
				helper.setText(R.id.oil_record_money, item.getMoney());
				helper.setText(R.id.oil_record_avg, item.getAvg());
			}
		});
	}

	private void addData() {
		// TODO Auto-generated method stub
		datas = new ArrayList<OilHistoryEntity>();
		OilHistoryEntity bean = new OilHistoryEntity("2015-07-08", "加油￥150元",
				"8.56升/百公里");
		OilHistoryEntity bean1 = new OilHistoryEntity("2015-07-14", "加油￥200元",
				"11.34升/百公里");
		OilHistoryEntity bean2 = new OilHistoryEntity("2015-07-16", "加油￥250元",
				"12.95升/百公里");
		OilHistoryEntity bean3 = new OilHistoryEntity("2015-07-19",
				"加油￥350.50元", "14.89升/百公里");
		OilHistoryEntity bean4 = new OilHistoryEntity("2015-07-20", "加油￥450元",
				"13.24升/百公里");

		datas.add(bean);
		datas.add(bean1);
		datas.add(bean2);
		datas.add(bean3);
		datas.add(bean4);
	}
}
