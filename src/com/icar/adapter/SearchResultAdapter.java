package com.icar.adapter;

import java.util.List;

import com.icar.activity.R;
import com.icar.bean.SearchEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchResultAdapter extends BaseAdapter {

	private Context context;
	private List<SearchEntity> datas;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	public SearchResultAdapter(Context context, List<SearchEntity> datas) {
		super();
		this.context = context;
		this.datas = datas;
		
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.car_normal_low)
				.showImageOnFail(R.drawable.car_normal_low).showImageForEmptyUri(R.drawable.car_normal_low)
				.cacheInMemory(true).build();
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
		ViewHolder viewHodler;
		if(convertView == null){
			viewHodler = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.search_result_item, null);
			viewHodler.tv = (TextView) convertView.findViewById(R.id.search_result_tv);
			viewHodler.iv = (ImageView) convertView.findViewById(R.id.search_result_iv);
			convertView.setTag(viewHodler);
		}else{
			viewHodler = (ViewHolder) convertView.getTag();
		}
		SearchEntity bean = datas.get(position);
		viewHodler.tv.setText(bean.getDescription());
		imageLoader.displayImage(bean.getUrl(), viewHodler.iv, options);
		return convertView;
	}

	class ViewHolder{
		ImageView iv;
		TextView tv;
	}
}
