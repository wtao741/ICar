package com.icar.activity;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
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

	private int[] imgs = {R.drawable.fault1,R.drawable.fault2,R.drawable.fault3,R.drawable.fault4,R.drawable.fault5,
			R.drawable.fault6,R.drawable.fault7,R.drawable.fault8,R.drawable.fault9,R.drawable.fault10,
			R.drawable.fault11,R.drawable.fault12,R.drawable.fault13,R.drawable.fault14,
			R.drawable.fault16,R.drawable.fault17,R.drawable.fault18,R.drawable.fault19,R.drawable.fault20,
			R.drawable.fault21,R.drawable.fault22,R.drawable.fault23,R.drawable.fault24,R.drawable.fault25,
			R.drawable.fault26,R.drawable.fault27,R.drawable.fault28,R.drawable.fault29,R.drawable.fault30,
			R.drawable.fault31,R.drawable.fault32,R.drawable.fault33,R.drawable.fault34,R.drawable.fault35,
			R.drawable.fault36,R.drawable.fault37,R.drawable.fault38};
	
	private String[] strs = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_drive);
		setTitle("事故判责");
		isShowLeftView(true);
		isShowRightView(0, false);
		ViewUtils.inject(this);

		strs = getResources().getStringArray(R.array.fault);
		
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
				helper.setImageResource(R.id.fault_icon, item.getIcon());
				final ImageView iv = helper.getView(R.id.fault_icon_right);
				final TextView tv = helper.getView(R.id.fault_des);
				final RelativeLayout rela = helper.getView(R.id.fault_real);

				int len = item.getDes().length();
				if(len < 30){
					iv.setVisibility(View.INVISIBLE);
				}else{
					iv.setVisibility(View.VISIBLE);
				}
				
				iv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {

						int line = tv.getLineCount();
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
		
		for(int i=0;i<strs.length;i++){
			FaultLiabilityEntity bean = new FaultLiabilityEntity(imgs[i],
					strs[i], true);
			datas.add(bean);
		}
	}

}
