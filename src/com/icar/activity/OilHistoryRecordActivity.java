package com.icar.activity;

import java.util.LinkedHashSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icar.adapter.CountChangeImp;
import com.icar.adapter.OilHistoryAdapter;
import com.icar.base.AbstractTitleActivity;
import com.icar.bean.OilRecordEntity;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class OilHistoryRecordActivity extends AbstractTitleActivity implements
		HttpCallBack, CountChangeImp {

	@ViewInject(R.id.head_right)
	TextView head_right;
	@ViewInject(R.id.no_data)
	TextView tv_null;
	@ViewInject(R.id.all)
	TextView tv_all;
	@ViewInject(R.id.delect)
	TextView tv_delect;
	@ViewInject(R.id.my_collect_listView)
	ListView listView;
	@ViewInject(R.id.my_collect_bottom)
	LinearLayout linearLayout;
	private List<OilRecordEntity> datas;

	private HttpUtil http;
	private Gson gson;
	private OilHistoryAdapter adapter;

	@OnClick(R.id.head_right)
	public void headRightonClick(View v) {
		String action = head_right.getText().toString().trim();
		if (action.equals("编辑")) {
			adapter.setEdit(true);
			linearLayout.setVisibility(View.VISIBLE);
		} else if (action.equals("完成")) {
			adapter.setEdit(false);
			adapter.markClear();
			adapter.cancelAllChecked();
			linearLayout.setVisibility(View.GONE);
			head_right.setText("编辑");
			tv_all.setText("全选");
		}
	}

	@OnClick(R.id.all)
	public void allonClick(View v) {
		String action = tv_all.getText().toString().trim();
		if (action.equals("全选")) {
			adapter.allChecked();
			tv_all.setText("取消全选");
		} else if (action.equals("取消全选")) {
			adapter.cancelAllChecked();
			tv_all.setText("全选");
		}
	}

	@OnClick(R.id.delect)
	public void delectonClick(View v) {
		int count = adapter.getCheckedSize();
		if (count == 0){
			showShortToast("请选择删除条目");
		}else{
		delectDialog();
		}
	}

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_mycollect);
		setTitle("历史记录");
		isShowLeftView(true);
		isShowRightView(R.string.edit, false);
		setRightTextColor(R.color.white);
		ViewUtils.inject(this);

		http = new HttpUtil(this);
		http.setHttpCallBack(this);
		gson = new Gson();
		http.getOilHistory();
	}

	private void setAdapter() {
		isShowRightView(R.string.edit, true);
		adapter = new OilHistoryAdapter(this, datas);
		listView.setAdapter(adapter);
		adapter.setCount(this);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(OilHistoryRecordActivity.this,AddOilHistoryActivity.class);
				intent.putExtra("type", 2);
				intent.putExtra("bean", datas.get(position));
			    startActivity(intent);
			}
		});
	}

	@Override
	public void onFailure(int requestCode, HttpException arg0, String arg1) {

	}

	@Override
	public void onSuccess(int requestCode, ResponseInfo<String> arg0) {
		String code;
		JSONObject object;
		switch (requestCode) {
		case 0:
			try {
				object = new JSONObject(arg0.result);
				code = object.getString("code");
				if (code.equals("200")) {
					JSONArray array = object.getJSONArray("data");
					datas = gson.fromJson(array.toString(),
							new TypeToken<List<OilRecordEntity>>() {
							}.getType());
					setAdapter();
				} else {
					showShortToast(code);
				}
			} catch (JSONException e) {
				tv_null.setVisibility(View.VISIBLE);
				listView.setVisibility(View.GONE);
				e.printStackTrace();
			}
			break;
		case 1:
			try {
				object = new JSONObject(arg0.result);
				code = object.getString("code");
				if (code.equals("200")) {
					showShortToast("删除成功");
					successDel();
				} else {
					showShortToast("删除失败");
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

	@Override
	public void getCount() {
		int count = adapter.getCheckedSize();
		if (count > 0 && count != datas.size()) {
			tv_delect.setText("删除(" + adapter.getCheckedSize() + ")");
			tv_delect.setBackgroundResource(R.color.home_text_color);
			tv_delect.setTextColor(getResources().getColor(R.color.white));
		} else if (count == 0) {
			tv_delect.setText("删除");
			tv_delect.setBackgroundResource(R.color.white);
			tv_delect.setTextColor(getResources().getColor(R.color.need_666));
		} else if (count == datas.size()) {
			tv_delect.setText("删除(" + adapter.getCheckedSize() + ")");
			tv_all.setText("取消全选");
			tv_delect.setBackgroundResource(R.color.home_text_color);
			tv_delect.setTextColor(getResources().getColor(R.color.white));
		}
		isShowRightView(R.string.complete, true);
	}

	public void delectDialog() {
		View view = LayoutInflater.from(this).inflate(R.layout.dialog_call,
				null);

		final Dialog dialog = new Dialog(this, R.style.dialog);
		dialog.setContentView(view);
		dialog.show();

		TextView tv_title = (TextView) view
				.findViewById(R.id.dialog_call_title);
		TextView tv_cancel = (TextView) view.findViewById(R.id.dialog_cancel);
		TextView tv_submit = (TextView) view.findViewById(R.id.dialog_submit);

		tv_title.setText("确定要删除吗?");
		tv_submit.setText("确定");
		tv_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		tv_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String id = "";
				Object[] checks = adapter.getCheckedSet();
				int size = checks.length;
				if (checks.length == 0) {
					showShortToast("选择数据");
				} else if (checks.length > 0) {
					for (int i = checks.length - 1; i >= 0; i--) {
						int index = ((Integer) checks[i]).intValue();
						Log.e("tag", "index:" + datas.get(index).getId());
						id += datas.get(index).getId() + ".";
//						datas.remove(index);
//						adapter.setRemove(index);
					}
					id = id.substring(0, id.length() - 1);
					Log.e("tag", "id:" + id);
					http.oilRecordDel(id);
					//adapter.notifyDataSetChanged();
				}
				if (datas.size() == 0) {
					tv_null.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
					linearLayout.setVisibility(View.GONE);
					isShowRightView(R.string.null_tips, false);
				}
				//getCount();
				dialog.dismiss();
			}
		});
	}

	public void successDel() {
		Object[] checks = adapter.getCheckedSet();
		int size = checks.length;
		for (int i = checks.length - 1; i >= 0; i--) {
			int index = ((Integer) checks[i]).intValue();
			datas.remove(index);
			adapter.setRemove(index);
		}
		adapter.notifyDataSetChanged();
		if (datas.size() == 0) {
			tv_null.setVisibility(View.VISIBLE);
			listView.setVisibility(View.GONE);
			linearLayout.setVisibility(View.GONE);
			isShowRightView(R.string.null_tips, false);
		}
		getCount();
	}
}
