package com.icar.adapter;

import java.util.List;

import com.icar.activity.CarTypeActivity;
import com.icar.activity.R;
import com.icar.bean.CarBrandEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyLikeCarAdapter extends BaseAdapter {

	private Context context;
	private List<CarBrandEntity> datas;
	private LayoutInflater inflater;
	private boolean isChekced; // 是否显示
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	public boolean isChekced() {
		return isChekced;
	}

	public void setChekced(boolean isChekced) {
		this.isChekced = isChekced;
		notifyDataSetChanged();
	}

	public MyLikeCarAdapter(Context context, List<CarBrandEntity> datas) {
		super();
		this.context = context;
		this.datas = datas;
		inflater = LayoutInflater.from(context);

		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().build();

		for (int i = 0; i < datas.size(); i++) {
			Log.e("tag", "adapter:" + datas.get(i).getType());
		}
	}

	public void refresh(List<CarBrandEntity> datas) {
		this.datas = datas;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size() + 1;
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
		if (datas.size() == position) {
			ImageView view = new ImageView(context);
			view.setImageResource(R.drawable.add_mylike_car);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, CarTypeActivity.class);
					context.startActivity(intent);
				}
			});
			return view;
		}
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.my_likecar_item, null);
			viewHolder.tv = (TextView) convertView
					.findViewById(R.id.my_like_tv);
			viewHolder.iv = (ImageView) convertView
					.findViewById(R.id.my_like_iv);
			viewHolder.iv_delect = (ImageView) convertView
					.findViewById(R.id.iv_delete);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		if (isChekced) {
			viewHolder.iv_delect.setVisibility(View.VISIBLE);
		} else {
			viewHolder.iv_delect.setVisibility(View.GONE);
		}

		CarBrandEntity bean = datas.get(position);
		viewHolder.tv.setText(bean.getType());
		imageLoader.displayImage(bean.getIcon(), viewHolder.iv, options);
		imageLoader.displayImage(bean.getIcon(), viewHolder.iv, options);
		boolean isChecked = bean.isChecked();
		if (isChecked)
			viewHolder.iv.setBackgroundResource(R.drawable.corner1);
		else
			viewHolder.iv.setBackgroundResource(R.drawable.corner);
		return convertView;
	}

	class ViewHolder {
		ImageView iv;
		ImageView iv_delect;
		TextView tv;
	}
}
