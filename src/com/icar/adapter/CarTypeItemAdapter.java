package com.icar.adapter;

import java.util.List;

import com.icar.activity.CarSeriesActivity;
import com.icar.activity.R;
import com.icar.bean.CarSeriesEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CarTypeItemAdapter extends BaseAdapter {

	private Context context;
	private LayoutInflater inflater;
    private List<CarSeriesEntity> datas;
    private ImageLoader imageLoader; 
    private DisplayImageOptions options;
    
	public CarTypeItemAdapter(Context context, List<CarSeriesEntity> datas) {
		super();
		this.context = context;
		this.datas = datas;
		inflater = LayoutInflater.from(context);
		
		options = new DisplayImageOptions.Builder().
				cacheInMemory(true).
				showImageOnLoading(R.drawable.car1).
				showImageOnFail(R.drawable.car1).build();
		imageLoader = ImageLoader.getInstance();
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
		final ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.car_type_item1, null);
			viewHolder.rela = (RelativeLayout) convertView
					.findViewById(R.id.car_brand_real);
			viewHolder.iv = (ImageView) convertView
					.findViewById(R.id.car_brand_icon);
			viewHolder.tv = (TextView) convertView
					.findViewById(R.id.car_brand_name);
			viewHolder.tv_bottom = (TextView) convertView
					.findViewById(R.id.tv_bottom);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final CarSeriesEntity bean = datas.get(position);
		
		viewHolder.tv.setText(bean.getShowname());
		imageLoader.displayImage(bean.getImgUrl(), viewHolder.iv, options);
        viewHolder.tv_bottom.setVisibility(View.GONE);
		viewHolder.rela.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,CarSeriesActivity.class);
				intent.putExtra("car", bean.getShowname());
				intent.putExtra("bean", bean);
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	class ViewHolder {
		RelativeLayout rela;
		ImageView iv;
		TextView tv;
		TextView tv_bottom;
	}
}
