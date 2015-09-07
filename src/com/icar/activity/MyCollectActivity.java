package com.icar.activity;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.icar.adapter.CountChangeImp;
import com.icar.adapter.MyCollectAdapter;
import com.icar.base.AbstractTitleActivity;
import com.icar.base.BaseApplication;
import com.icar.bean.MyCollectEntity;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class MyCollectActivity extends AbstractTitleActivity implements
		CountChangeImp,HttpCallBack {

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

	private List<MyCollectEntity> datas;

	private MyCollectAdapter adapter;

	private HttpUtil http;
	private Gson gson ;
	
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

		setTitle(R.string.my_collect);
		isShowRightView(R.string.edit, false);
		setRightTextColor(R.color.white);

		ViewUtils.inject(this);
        http = new HttpUtil(this);
        http.setHttpCallBack(this);
		gson = new Gson();
        http.getCollect(BaseApplication.getUserName(), 3805);
	}

	private void setAdapter() {
		isShowRightView(R.string.edit, true);
		adapter = new MyCollectAdapter(this, datas);
		listView.setAdapter(adapter);
		adapter.setCount(this);
	}

	@Override
	public void getCount() {
		// TODO Auto-generated method stub
		int count = adapter.getCheckedSize();
		if (count > 0 && count != datas.size()) {
			tv_delect.setText("删除(" + adapter.getCheckedSize() + ")");
			tv_delect.setBackgroundResource(R.color.home_text_color);
			tv_delect.setTextColor(getResources().getColor(R.color.white));
		} else if (count == 0) {
			tv_delect.setText("删除");
			tv_delect.setBackgroundResource(R.color.white);
			tv_delect.setTextColor(getResources().getColor(R.color.need_666));
		}else if(count == datas.size()){
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
						Log.e("tag", "index:"+datas.get(index).getId());
						id += datas.get(index).getId()+",";
						datas.remove(index);
						adapter.setRemove(index);
						Log.e("tag", ":" + index);
					}
					id = id.substring(0, id.length()-1);
					Log.e("tag", "id:"+id);
					http.collectDel(id);
					adapter.notifyDataSetChanged();
				}
				
				getCount();
				dialog.dismiss();
			}
		});
	}

	@Override
	public void onFailure(int requestCode, HttpException arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(int requestCode, ResponseInfo<String> arg0) {
		// TODO Auto-generated method stub
		String result = arg0.result;
		String code;
		JSONObject resultObject = null;
		try {
			 resultObject = new JSONObject(result);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		switch (requestCode) {
		case 0:
			Log.e("tag", "get:"+result);
			try {
				code = resultObject.getString("code");
				if(code.equals("200")){
					try{
					JSONArray dataArray = resultObject.getJSONArray("data");
					datas = gson.fromJson(dataArray.toString(), new TypeToken<List<MyCollectEntity>>(){}.getType());
				    setAdapter();
					}catch(Exception e){
						tv_null.setVisibility(View.VISIBLE);
						listView.setVisibility(View.GONE);
					}
				}else{
					showShortToast("失败");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 1:
			Log.e("tag", "delect:"+result);
			if (datas.size() == 0) {
				tv_null.setVisibility(View.VISIBLE);
				listView.setVisibility(View.GONE);
				linearLayout.setVisibility(View.GONE);
				isShowRightView(R.string.null_tips, false);
			}
			break;
		default:
			break;
		}
	}

}
