package com.icar.adapter;

import java.util.List;

import com.icar.activity.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class AdviceAdapter extends BaseAdapter {

	private Context context ;
	private List<String> datas;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private LayoutInflater inflater;
	
	public AdviceAdapter(Context context, List<String> datas) {
		super();
		this.context = context;
		this.datas = datas;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size()+1;
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
		if(datas.size() == position){
			ImageView view = new ImageView(context);
			view.setImageResource(R.drawable.advice_icon);
			return view;
		}
		
		return null;
	}

}
