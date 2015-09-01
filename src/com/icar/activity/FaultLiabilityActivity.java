package com.icar.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.icar.adapter.util.CommonAdapter;
import com.icar.adapter.util.ViewHolder;
import com.icar.base.AbstractTitleActivity;
import com.icar.bean.FaultLiabilityEntity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class FaultLiabilityActivity extends AbstractTitleActivity {

	@ViewInject(R.id.drive_listView)
	ListView listView;

	private List<FaultLiabilityEntity> datas;

	private CommonAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_drive);
		setTitle("事故判责");
		isShowLeftView(true);
		isShowRightView(0, false);
		ViewUtils.inject(this);

		addDatas();
		setAdapter();
	}

	private void setAdapter() {

		adapter = new CommonAdapter<FaultLiabilityEntity>(this, datas,
				R.layout.fault_liability_item) {
			@Override
			public void convert(ViewHolder helper,
					final FaultLiabilityEntity item) {
				helper.setText(R.id.fault_des, item.getDes());
				final ImageView iv = helper.getView(R.id.fault_icon_right);
				final TextView tv = helper.getView(R.id.fault_des);
				final RelativeLayout rela = helper.getView(R.id.fault_real);
				iv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						int line = tv.getLineCount();
						Log.e("tag", ""+line);
						if (line > 2) {
							if (item.isClose()) {
								iv.setImageResource(R.drawable.fault_click);
								tv.setLines(line);
								item.setClose(false);
								rela.setBackgroundResource(R.color.fault_bgm);
							} else {
								iv.setImageResource(R.drawable.fault_normal);
								tv.setLines(2);
								item.setClose(true);
								rela.setBackgroundResource(R.color.white);
							}
						}
					}
				});
			}

		};

		// 设置适配器
		listView.setAdapter(adapter);
	}

	private void addDatas() {
		datas = new ArrayList<FaultLiabilityEntity>();

		FaultLiabilityEntity bean1 = new FaultLiabilityEntity(R.drawable.fault,
				"有一次我决定要戒烟，就跟一个过命的朋友说:你看到我一抽烟就扇我脸知道吗？他点了点头。一个月后我们绝交了", true);
		FaultLiabilityEntity bean2 = new FaultLiabilityEntity(R.drawable.fault,
				"有一次我决定要戒烟，就跟一个过命的朋友说", true);
		FaultLiabilityEntity bean3 = new FaultLiabilityEntity(R.drawable.fault,
				"有一次我决定要戒烟，就跟一个过命的朋友说:", true);
		FaultLiabilityEntity bean4 = new FaultLiabilityEntity(R.drawable.fault,
				"大河向东流啊，天下的情侣都分手呀，嘿嘿嘿嘿都分手呀，过了今天都分手呀。一辈子做单身狗呀。嘿呀咦儿呀，嘿嘿嘿嘿咦儿呀。",
				true);
		FaultLiabilityEntity bean5 = new FaultLiabilityEntity(R.drawable.fault,
				"有一次我决定要戒烟，就跟一个过命的朋友说", true);
		FaultLiabilityEntity bean6 = new FaultLiabilityEntity(R.drawable.fault,
				"有一次我决定要戒烟，就跟一个过命的朋友说", true);
		FaultLiabilityEntity bean7 = new FaultLiabilityEntity(R.drawable.fault,
				"有一次我决定要戒烟，就跟一个过命的朋友说", true);

		datas.add(bean1);
		datas.add(bean2);
		datas.add(bean3);
		datas.add(bean4);
		datas.add(bean5);
		datas.add(bean6);
		datas.add(bean7);
	}

}
