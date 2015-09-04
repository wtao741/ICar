package com.icar.adapter;

import java.util.List;

import com.icar.activity.R;
import com.icar.bean.CarCommentEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class CarCommentAdapter1 extends BaseAdapter {

	private List<CarCommentEntity> datas;

	private Context context;

	public CarCommentAdapter1(Context context, List<CarCommentEntity> datas) {
		// TODO Auto-generated constructor stub
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_car_comment1, null);
			viewHolder.tv_tips = (TextView) convertView.findViewById(R.id.tips);
			viewHolder.tv_score = (TextView) convertView
					.findViewById(R.id.score);
			viewHolder.bar = (SeekBar) convertView.findViewById(R.id.seekbar);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tv_tips.setText(datas.get(position).getTitle());
		viewHolder.tv_score.setText(""+datas.get(position).getScore());
		viewHolder.bar.setProgress(datas.get(position).getScore());
		
		return convertView;
	}

	class ViewHolder {
		public TextView tv_tips;
		public SeekBar bar;
		public TextView tv_score;
	}
	
}
