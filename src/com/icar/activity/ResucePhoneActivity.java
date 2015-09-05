package com.icar.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icar.adapter.ResucePhoneAdapter;
import com.icar.base.AbstractTitleActivity;
import com.icar.base.BaseApplication;
import com.icar.base.HeadClick;
import com.icar.bean.ResucePhoneEntity;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.icar.view.CallDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class ResucePhoneActivity extends AbstractTitleActivity implements
		HeadClick, HttpCallBack {

	@ViewInject(R.id.drive_listView)
	ListView listView;
	@ViewInject(R.id.no_data)
	TextView tv_noData;

	private List<ResucePhoneEntity> datas;

	private ResucePhoneAdapter adapter;

	private CallDialog dialog;

	private Dialog addDialog;

	private HttpUtil http;
	private Gson gson;

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

	ResucePhoneEntity bean;

	@OnClick(R.id.dialog_submit)
	public void dialogSubmitonClick(View v) {
		String nameStr = et_add_name.getText().toString().trim();
		String phoneStr = et_add_phone.getText().toString().trim();
		if (nameStr.equals("")) {
			Toast.makeText(this, "名称不能为空", 2000).show();
		} else if (phoneStr.equals("")) {
			Toast.makeText(this, "电话不能为空", 2000).show();
		} else {
			bean = new ResucePhoneEntity(BaseApplication.getUserName(),
					nameStr, "米其林", phoneStr);
			http.addResucePhone(nameStr, "米其林", phoneStr);
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

		http = new HttpUtil(this);
		http.setHttpCallBack(this);
		gson = new Gson();
		http.getResucePhone();

		datas = new ArrayList<ResucePhoneEntity>();
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

	@Override
	public void onFailure(int requestCode, HttpException arg0, String arg1) {

	}

	@Override
	public void onSuccess(int requestCode, ResponseInfo<String> arg0) {
		Log.e("tag", arg0.result);
		JSONObject result;
		String code;
		try {
			result = new JSONObject(arg0.result);
			code = result.getString("code");
			switch (requestCode) {
			case 0:
				JSONObject dataObject;
				try {
					dataObject = result.getJSONObject("data");
					gson.fromJson(dataObject.toString(),
							new TypeToken<List<ResucePhoneEntity>>() {
							}.getType());

				} catch (Exception e) {
					listView.setVisibility(View.GONE);
					tv_noData.setVisibility(View.VISIBLE);
				}
				setListViewAdapter();
				break;
			case 1:
				if (code.equals("200")) {
					showShortToast("添加成功");
					datas.add(bean);
					adapter.notifyDataSetChanged();
					addDialog.dismiss();
					if (datas.size() == 1) {
						listView.setVisibility(View.VISIBLE);
						tv_noData.setVisibility(View.GONE);
					} else if (code.equals("102")) {
						showShortToast("该条记录已存在，请勿重复提交");
					} else {
						showShortToast("添加失败");
					}
				}
				break;
			case 2:

				break;
			default:
				break;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
