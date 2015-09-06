package com.icar.adapter;

import java.util.List;

import com.icar.activity.AccidentRecordActivity;
import com.icar.activity.CarTypeActivity;
import com.icar.activity.R;
import com.icar.bean.CarBrandEntity;
import com.icar.bean.ImageBean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class AccidentAdapter extends BaseAdapter {

	private Context context;
	private List<ImageBean> datas;
	private LayoutInflater inflater;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	public AccidentAdapter(Context context, List<ImageBean> datas) {
		super();
		this.context = context;
		this.datas = datas;
		
		inflater = LayoutInflater.from(context);

		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().showImageOnFail(R.drawable.car_normal_low)
				.showImageOnLoading(R.drawable.car_normal_low).build();
	}

	@Override
	public int getCount() {
		return datas.size()+1;
	}

	@Override
	public Object getItem(int position) {
		return datas.get(position);
	}

	@Override
	public long getItemId(int position) {
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
		ViewHolder holder;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.item_accident, null);
		    holder = new ViewHolder();
		    holder.iv = (ImageView) convertView.findViewById(R.id.iv);
		    holder.play = (ImageView) convertView.findViewById(R.id.tv_play);
		    convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		ImageBean bean = datas.get(position);
		Log.e("tag", bean.getPicPath());
		if(bean.getType().equals("1")){
			holder.play.setVisibility(View.VISIBLE);
			Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(bean.getPicPath(), 3);
			holder.iv.setImageBitmap(bitmap);
		}else{
			holder.play.setVisibility(View.GONE);
			holder.iv.setImageBitmap(datas.get(position).getImgBitmap());
		}
		//holder.iv.setImageBitmap(datas.get(position).getImgBitmap());
		//imageLoader.displayImage("file://"+datas.get(position).getImgBitmap(), holder.iv, options);
		return convertView;
	}

	class ViewHolder{
		ImageView iv;
		ImageView play;
	}
}
