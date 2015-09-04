package com.icar.adapter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.icar.activity.R;
import com.icar.base.BaseApplication;
import com.icar.bean.MyCollectEntity;
import com.icar.bean.OilHistoryEntity;
import com.icar.bean.OilRecordEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class OilHistoryAdapter extends BaseAdapter {

	private Context context;
	private List<OilRecordEntity> datas;

	private LayoutInflater layoutInflater;

	public Set<Integer> checks;

	private CountChangeImp count;

	public void setCount(CountChangeImp count) {
		this.count = count;
	}

	private boolean isEdit; // 是否显示checkbox

	public boolean isEdit() {
		return isEdit;
	}

	public void setEdit(boolean isEdit) {
		this.isEdit = isEdit;
		notifyDataSetChanged();
	}

	public OilHistoryAdapter(Context context, List<OilRecordEntity> datas) {
		super();
		this.context = context;
		this.datas = datas;
		layoutInflater = LayoutInflater.from(context);
		checks = new HashSet<Integer>();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = layoutInflater
					.inflate(R.layout.oil_record_item, null);
			viewHolder.box = (CheckBox) convertView.findViewById(R.id.checked);
			viewHolder.tv_date = (TextView) convertView
					.findViewById(R.id.oil_record_date);
			viewHolder.tv_money = (TextView) convertView
					.findViewById(R.id.oil_record_money);
			viewHolder.tv_avg = (TextView) convertView
					.findViewById(R.id.oil_record_avg);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (isEdit) {
			viewHolder.box.setVisibility(View.VISIBLE);
		} else {
			viewHolder.box.setVisibility(View.GONE);
		}

		OilRecordEntity bean = datas.get(position);
		viewHolder.tv_date.setText(bean.getChargedate());
		viewHolder.tv_money.setText("加油￥"+bean.getMoney()+"元");
		float avg = Integer.parseInt(bean.getMoney())/Integer.parseInt(bean.getMileage());
		viewHolder.tv_avg.setText(avg+"升/百公里");

		if (bean.isChecked()) {
			viewHolder.box.setChecked(true);
			checks.add(position);
		} else {
			viewHolder.box.setChecked(false);
		}

		viewHolder.box.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckBox check = (CheckBox) v;
				if (check.isChecked()) {
					checks.add(position);
				} else {
					checks.remove((Integer) position);
				}
				if (count != null) {
					count.getCount();
				}
			}
		});

		return convertView;
	}

	public Object[] getCheckedSet(){
		return checks.toArray();
	}
	
	// 选中数量
	public int getCheckedSize() {
		return checks.size();
	}

	// 全选
	public void allChecked() {
		checks.clear();
		for (int i = 0; i < datas.size(); i++) {
			datas.get(i).setChecked(true);
			checks.add(i);
		}
		if (count != null) {
			count.getCount();
		}
		notifyDataSetChanged();
	}

	// 取消全选
	public void cancelAllChecked() {
		for (int i = 0; i < datas.size(); i++) {
			datas.get(i).setChecked(false);
		}
		checks.clear();
		if (count != null) {
			count.getCount();
		}
		notifyDataSetChanged();
	}
	
	public void setRemove(int index){
		checks.remove(index);
	}

	public void markClear(){
		if(checks.size()>0){
			checks.clear();
		}
	}
	
	class ViewHolder {
		TextView tv_date;
		TextView tv_money;
		TextView tv_avg;
		CheckBox box;
	}
}
