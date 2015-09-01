package com.icar.adapter;

import java.util.List;

import com.icar.activity.R;
import com.icar.bean.FunctionItemEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CarScoreAdapter extends BaseAdapter {

	private Context context;
	private List<FunctionItemEntity> datas;
	
	
	public CarScoreAdapter(Context context, List<FunctionItemEntity> datas) {
		super();
		this.context = context;
		this.datas = datas;
	}

	public void refresh(List<FunctionItemEntity> newDatas){
		this.datas = newDatas;
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
		ViewHolder viewHolder;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.score_item, null);
			viewHolder.tv_name = (TextView) convertView.findViewById(R.id.score_item_name);
			viewHolder.tv_score = (TextView) convertView.findViewById(R.id.score_item_score);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		FunctionItemEntity bean = datas.get(position);
		if(bean.isChange()){
			viewHolder.tv_name.setTextColor(context.getResources().getColor(R.color.home_text_color));
			viewHolder.tv_score.setTextColor(context.getResources().getColor(R.color.home_text_color));
		}else{
			viewHolder.tv_name.setTextColor(context.getResources().getColor(R.color.home_title_bgm));
			viewHolder.tv_score.setTextColor(context.getResources().getColor(R.color.home_title_bgm));
		}
		viewHolder.tv_name.setText(bean.getName());
		viewHolder.tv_score.setText(bean.getScore());
		return convertView;
	}

	class ViewHolder{
		TextView tv_name;
		TextView tv_score;
	}
}
