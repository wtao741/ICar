package com.icar.adapter;

import java.util.List;

import com.icar.activity.R;
import com.icar.bean.CarBrandEntity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CarBrandAdapter extends BaseAdapter {

	private Context context;
	private List<CarBrandEntity> datas;
	private LayoutInflater inflater;
	private ImageLoader imageLoader; 
    private DisplayImageOptions options;
    
	public CarBrandAdapter(Context context, List<CarBrandEntity> datas) {
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
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
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
	    CarBrandEntity bean = datas.get(position);
		viewHolder.tv.setText(bean.getName());
        imageLoader.displayImage(bean.getIcon(), viewHolder.iv, options);
		boolean isChecked = bean.isChecked();
		if (isChecked) {
			viewHolder.rela.setBackgroundColor(context.getResources().getColor(
					R.color.white));
		} else {
			viewHolder.rela.setBackgroundResource(R.drawable.car_type_shape);
		}
		return convertView;
	}

	class ViewHolder {
		RelativeLayout rela;
		ImageView iv;
		TextView tv;
	}

	public void setAllFalse() {
		int len = datas.size();
		for (int i = 0; i < len; i++) {
			datas.get(i).setChecked(false);
		}
	}
}
