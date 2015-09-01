package com.icar.adapter;

import java.util.List;

import com.icar.activity.R;
import com.icar.bean.CarTypeEntity;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

public class CarTypeAdapter extends BaseAdapter {

	private Context context;
	private List<CarTypeEntity> datas;
	private LayoutInflater inflater;
	private CarTypeItemAdapter adapter;
	
	public CarTypeAdapter(Context context, List<CarTypeEntity> datas) {
		super();
		this.context = context;
		this.datas = datas;
		inflater = LayoutInflater.from(context);
	}

	public void setData(List<CarTypeEntity> datas){
		this.datas = datas;
		notifyDataSetChanged();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.car_type_item, null);
			viewHolder.gridView = (GridView) convertView
					.findViewById(R.id.car_type_gridView);
			viewHolder.tv = (TextView) convertView
					.findViewById(R.id.car_type_name);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final CarTypeEntity bean = datas.get(position);
		viewHolder.tv.setText(bean.getName());
        adapter = new CarTypeItemAdapter(context, bean.getDatas());
		viewHolder.gridView.setAdapter(adapter);
		return convertView;
	}

	class ViewHolder {
		TextView tv;
		GridView gridView;
	}
}
