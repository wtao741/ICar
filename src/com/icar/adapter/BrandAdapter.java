package com.icar.adapter;

import java.util.List;

import com.icar.activity.R;
import com.icar.base.BaseApplication;
import com.icar.bean.BrandEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class BrandAdapter extends BaseAdapter {

	private Context context;
	private List<BrandEntity> datas;

	public BrandAdapter(Context context, List<BrandEntity> datas) {
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
		// TODO Auto-generated method stub
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.brand_item, null);
			viewHolder.rela = (RelativeLayout) convertView
					.findViewById(R.id.brand_item_rela);
			viewHolder.text = (TextView) convertView
					.findViewById(R.id.brand_item_tv);
			
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		LayoutParams params = (LayoutParams) viewHolder.rela.getLayoutParams();
		params.width = BaseApplication.WIDTH;
		params.height = (int) (BaseApplication.WIDTH / 1.5);
		viewHolder.rela.setLayoutParams(params);
		
		BrandEntity entity = datas.get(position);
		viewHolder.rela.setBackgroundResource(entity.getUrl());
		viewHolder.text.setText(entity.getDes());
		return convertView;
	}

	class ViewHolder {
		RelativeLayout rela;
		TextView text;
	}

}
