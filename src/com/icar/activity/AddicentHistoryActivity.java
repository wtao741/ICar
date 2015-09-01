package com.icar.activity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.icar.adapter.AddicentHistoryAdapter;
import com.icar.adapter.CountChangeImp;
import com.icar.adapter.MyCollectAdapter;
import com.icar.base.AbstractTitleActivity;
import com.icar.bean.AddicentHistoryEntity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class AddicentHistoryActivity extends AbstractTitleActivity implements
		CountChangeImp {

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

	private List<AddicentHistoryEntity> datas;

	private AddicentHistoryAdapter adapter;

	@OnClick(R.id.no_data)
	public void noDataonClick(View v) {
		tv_null.setVisibility(View.GONE);
		listView.setVisibility(View.VISIBLE);
		isShowRightView(R.string.edit, true);
	}

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
		delectDialog();
	}

	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_mycollect);

		setTitle("历史记录");
		isShowRightView(R.string.edit, false);
		setRightTextColor(R.color.white);

		ViewUtils.inject(this);

		tv_null.setText("没有历史记录");
		addDatas();
		setAdapter();
	}

	private void setAdapter() {
		adapter = new AddicentHistoryAdapter(this, datas);
		listView.setAdapter(adapter);
		adapter.setCount(this);
	}

	private void addDatas() {
		// TODO Auto-generated method stub
		datas = new ArrayList<AddicentHistoryEntity>();
		AddicentHistoryEntity bean = new AddicentHistoryEntity(R.drawable.car1, "2015-08-24  12:25",
				"朝阳区酒仙桥路电子科技城大厦", false);
		AddicentHistoryEntity bean1 = new AddicentHistoryEntity(R.drawable.car2, "2015-07-12  13:24",
				"朝阳区酒仙桥路电子科技城大厦", false);
		AddicentHistoryEntity bean2 = new AddicentHistoryEntity(R.drawable.car3, "2014-03-15   14:56",
				"朝阳区酒仙桥路电子科技城大厦", false);

		datas.add(bean);
		datas.add(bean1);
		datas.add(bean2);
	}

	@Override
	public void getCount() {
		// TODO Auto-generated method stub
		int count = adapter.getCheckedSize();
		if (count > 0) {
			tv_delect.setText("删除(" + adapter.getCheckedSize() + ")");
			tv_delect.setBackgroundResource(R.color.home_text_color);
			tv_delect.setTextColor(getResources().getColor(R.color.white));
		} else if (count == 0) {
			tv_delect.setText("删除");
			tv_delect.setBackgroundResource(R.color.white);
			tv_delect.setTextColor(getResources().getColor(R.color.need_666));
		}
		isShowRightView(R.string.complete, true);
	}
	
	public void delectDialog(){
	    View view = LayoutInflater.from(this).inflate(R.layout.dialog_call,null);
			
		final Dialog	dialog = new Dialog(this,R.style.dialog);
			dialog.setContentView(view);
			dialog.show();
			
		TextView	tv_title = (TextView) view.findViewById(R.id.dialog_call_title);
		TextView	tv_cancel = (TextView) view.findViewById(R.id.dialog_cancel);
		TextView	tv_submit = (TextView) view.findViewById(R.id.dialog_submit);
			
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
				Object[] checks = adapter.getCheckedSet();
				int size = checks.length;
				if (checks.length == 0) {
					showShortToast("选择数据");
				} else if(checks.length > 0){
					for(int i=checks.length-1;i>=0;i--){
						int index = ((Integer) checks[i]).intValue();
						datas.remove(index);
						adapter.setRemove(index);
						Log.e("tag", ":"+index);
					}
					adapter.notifyDataSetChanged();
					showShortToast("删除成功");
				}
				if(datas.size() == 0){
					tv_null.setVisibility(View.VISIBLE);
					listView.setVisibility(View.GONE);
					linearLayout.setVisibility(View.GONE);
				}
				getCount();
				dialog.dismiss();
			}
		});
		}
		
}
