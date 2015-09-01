package com.icar.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.icar.adapter.ResucePhoneAdapter;
import com.icar.base.AbstractTitleActivity;
import com.icar.base.HeadClick;
import com.icar.bean.ResucePhoneEntity;
import com.icar.view.CallDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class ResucePhoneActivity extends AbstractTitleActivity implements
		HeadClick {

	@ViewInject(R.id.drive_listView)
	ListView listView;

	private List<ResucePhoneEntity> datas;

	private ResucePhoneAdapter adapter;

	private CallDialog dialog;

	private Dialog addDialog;

	@ViewInject(R.id.resuce_add_name)
	EditText et_add_name;
	@ViewInject(R.id.resuce_add_phone)
	EditText et_add_phone;
	@ViewInject(R.id.dialog_cancel)
	TextView tv_cancel;
	@ViewInject(R.id.dialog_submit)
	TextView tv_submit;

	@OnClick(R.id.dialog_cancel)
	public void dialogCancelonClick(View v) {
		if (addDialog.isShowing()) {
			addDialog.dismiss();
		}
	}

	@OnClick(R.id.dialog_submit)
	public void dialogSubmitonClick(View v) {
		String nameStr = et_add_name.getText().toString().trim();
		String phoneStr = et_add_phone.getText().toString().trim();
		if (nameStr.equals("")) {
			Toast.makeText(this, "名称不能为空", 2000).show();
		} else if (phoneStr.equals("")) {
			Toast.makeText(this, "电话不能为空", 2000).show();
		} else {
			ResucePhoneEntity bean = new ResucePhoneEntity(nameStr, "米其林",
					phoneStr);
			datas.add(bean);
			adapter.notifyDataSetChanged();
			addDialog.dismiss();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_drive);
		setTitle("救援电话");
		setHeadClick(this);
		ViewUtils.inject(this);
		setRightBackgorund(R.drawable.resuce_add);
		setRightText("");

		dialog = new CallDialog(this);
		setDatas();
		setListViewAdapter();
	}

	private void setDatas() {
		// TODO Auto-generated method stub
		datas = new ArrayList<ResucePhoneEntity>();

		ResucePhoneEntity entity1 = new ResucePhoneEntity("大陆汽车俱乐部救援中心",
				"全国第一家汽车俱乐部，24小时", "400-818-1010");
		ResucePhoneEntity entity2 = new ResucePhoneEntity("米其林随你行",
				"针对\"随你行\"用户", "400-889-0088");
		ResucePhoneEntity entity3 = new ResucePhoneEntity("中石化",
				"针对记名加油卡用户，24小时", "400-889-8899");
		ResucePhoneEntity entity4 = new ResucePhoneEntity("太平洋保险",
				"针对车险用户，24小时", "95500");
		ResucePhoneEntity entity5 = new ResucePhoneEntity("平安车险",
				"针对车险用户，24小时", "400-800-0000");

		datas.add(entity1);
		datas.add(entity2);
		datas.add(entity3);
		datas.add(entity4);
		datas.add(entity5);
	}

	private void setListViewAdapter() {
		adapter = new ResucePhoneAdapter(this, datas);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ResucePhoneEntity bean = (ResucePhoneEntity) parent
						.getAdapter().getItem(position);
				dialog.setTitle(bean.getPhoneNumber());
				dialog.showDialog();
			}
		});
	}

	@Override
	public void left() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void right() {
		// TODO Auto-generated method stub
		addDialog = new Dialog(this, R.style.dialog);
		View view = LayoutInflater.from(this).inflate(
				R.layout.dialog_add_resuce_phone, null);
		addDialog.setContentView(view);
		addDialog.setCanceledOnTouchOutside(false);
		ViewUtils.inject(this, view);
		addDialog.show();

	}

}
