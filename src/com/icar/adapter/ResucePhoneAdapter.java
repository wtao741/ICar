package com.icar.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.icar.activity.R;
import com.icar.bean.ResucePhoneEntity;

public class ResucePhoneAdapter extends BaseAdapter {

	private Context context;
	private List<ResucePhoneEntity> datas;
	
	public ResucePhoneAdapter(Context context, List<ResucePhoneEntity> datas) {
		super();
		this.context = context;
		this.datas = datas;
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
		ViewHolder viewHolder = null;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.resuce_phone_item, null);
			viewHolder.tv_name = (TextView) convertView.findViewById(R.id.resuce_item_name);
			viewHolder.tv_des = (TextView) convertView.findViewById(R.id.resuce_item_des);
			viewHolder.tv_phone = (TextView) convertView.findViewById(R.id.resuce_item_phoneNumber);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		
		ResucePhoneEntity bean = datas.get(position);
		viewHolder.tv_name.setText(bean.getName());
		viewHolder.tv_des.setText(bean.getDes());
		viewHolder.tv_phone.setText(bean.getPhoneNumber());
		return convertView;
	}

	class ViewHolder {
		public TextView tv_name;
		public TextView tv_des;
		public TextView tv_phone;
	}
}

