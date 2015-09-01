package com.icar.adapter;

import java.util.List;

import com.icar.activity.R;
import com.icar.bean.WarnLightEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WarnLightAdapter extends BaseAdapter {

	private Context context;
	private List<WarnLightEntity> datas;
	private LayoutInflater inflater;
	
	public WarnLightAdapter(Context context, List<WarnLightEntity> datas) {
		super();
		this.context = context;
		this.datas = datas;
		inflater = LayoutInflater.from(context);
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
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.warn_light_item, null);
			viewHolder.iv = (ImageView) convertView
					.findViewById(R.id.home_bt_iv);
			viewHolder.tv = (TextView) convertView
					.findViewById(R.id.home_bt_tv);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		WarnLightEntity bean = datas.get(position);
		viewHolder.iv.setImageResource(bean.getIcon());
		viewHolder.tv.setText(bean.getTitle());
		return convertView;
	}

	class ViewHolder {
		ImageView iv;
		TextView tv;
	}
}
