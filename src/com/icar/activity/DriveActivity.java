package com.icar.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.icar.base.AbstractTitleActivity;
import com.icar.view.CallDialog;

public class DriveActivity extends AbstractTitleActivity {

	private ListView listView;

	private String[] strs;
	private SimpleAdapter adapter;
	private CallDialog dialog;

	private int[] icons = { R.drawable.drive_icon1, R.drawable.drive_icon2,
			R.drawable.drive_icon3, R.drawable.drive_icon4,
			R.drawable.drive_icon5, R.drawable.drive_icon6,
			R.drawable.drive_icon7, R.drawable.drive_icon8 };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_drive);
		setTitle(R.string.tab_drive);
		isShowLeftView(false);
		isShowRightView(0, false);

		initView();

		strs = getResources().getStringArray(R.array.drive_str);

		createAdapter();
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Class classType = null;
				switch (position) {
				case 6:
					classType = ResucePhoneActivity.class;
					break;
				case 7:
					dialog.showDialog();
					break;
				case 1:
					classType = OilRecordActivity.class;
					break;
				case 3:
					classType = FaultLiabilityActivity.class;
					break;
				case 0:
					classType = AccidentRecordActivity.class;
					break;
				case 2:
					classType = CarCommentActivity.class;
					break;
				}
				if (classType != null) {
					Intent intent = new Intent(DriveActivity.this, classType);
					startActivity(intent);
				}
			}
		});
	}

	public void createAdapter() {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < strs.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("image", icons[i]);
			map.put("text", strs[i]);
			lists.add(map);
		}

		adapter = new SimpleAdapter(this, lists, R.layout.item_driver,
				new String[] { "image", "text" }, new int[] { R.id.drive_iv,
						R.id.drive_tv });
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.drive_listView);
		listView.setHeaderDividersEnabled(false);
		listView.setFooterDividersEnabled(true);
		dialog = new CallDialog(this);
	}

}
