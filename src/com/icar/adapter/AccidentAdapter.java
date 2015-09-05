package com.icar.adapter;

import java.util.List;

import com.icar.activity.AccidentRecordActivity;
import com.icar.activity.CarTypeActivity;
import com.icar.activity.R;
import com.icar.bean.CarBrandEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class AccidentAdapter extends BaseAdapter {

	private Context context;
	private List<String> datas;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	public AccidentAdapter(Context context, List<String> datas) {
		super();
		this.context = context;
		this.datas = datas;
		
		inflater = LayoutInflater.from(context);

		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().build();
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
		if(position == datas.size()){
			ImageView view = new ImageView(context);
			view.setImageResource(R.drawable.add_mylike_car);
			view.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					((AccidentRecordActivity)context).initSelectPicPopupWindow();
				}
			});
			return view;
		}
		return null;
	}

}
