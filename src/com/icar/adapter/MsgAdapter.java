package com.icar.adapter;

import java.util.List;

import com.icar.activity.R;
import com.icar.base.BaseApplication;
import com.icar.bean.Msg;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MsgAdapter extends BaseAdapter{

	private Context context;
	private List<Msg> datas;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	public MsgAdapter(Context context, List<Msg> datas) {
		super();
		this.context = context;
		this.datas = datas;
		
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.car_normal_low)
				.showImageOnFail(R.drawable.car_normal_low).build();
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
		ViewHolder viewHolder ;
		if(convertView == null){
			convertView =LayoutInflater.from(context).inflate(R.layout.item_msg, null);
			viewHolder = new ViewHolder();
			viewHolder.iv = (ImageView) convertView.findViewById(R.id.msg_iv);
            viewHolder.tv = (TextView) convertView.findViewById(R.id.msg);
            viewHolder.time = (TextView) convertView.findViewById(R.id.msg_time);
            convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		imageLoader.displayImage(BaseApplication.myLikeIcon, viewHolder.iv, options);
		Msg msg = datas.get(position);
		viewHolder.tv.setText(msg.getContent());
		return convertView;
	}

	class ViewHolder{
		ImageView iv;
		TextView tv;
		TextView time;
	}
}
