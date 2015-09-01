package com.icar.adapter;

import java.util.List;

import com.icar.activity.R;
import com.icar.activity.ShakeActivity;
import com.icar.bean.ShakeEditEntity;
import com.icar.view.RoundImageView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ShakeAdpater extends BaseAdapter {

	private Context context;
	
	private List<ShakeEditEntity> datas;
	
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	public ShakeAdpater(Context context, List<ShakeEditEntity> datas) {
		super();
		this.context = context;
		this.datas = datas;
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.userinfo)
				.showImageOnFail(R.drawable.userinfo).showImageForEmptyUri(R.drawable.userinfo).build();
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
		ViewHolder viewHolder ;
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(R.layout.shake_item, null);
			viewHolder.iv = (RoundImageView) convertView.findViewById(R.id.shake_item_iv);
			viewHolder.tv = (TextView) convertView.findViewById(R.id.shake_item_tv);
			convertView.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		ShakeEditEntity bean = datas.get(position);
		imageLoader.displayImage(bean.getAvatar(), viewHolder.iv, options);
		SpannableString text = new SpannableString(((ShakeActivity)context).ToDBC(bean.getAuthor()+":"+bean.getContent()));
		text.setSpan(new ForegroundColorSpan(Color.parseColor("#293296")), 0, 4, 0);
		viewHolder.tv.setText(text);
		return convertView;
	}

	class ViewHolder{
		RoundImageView iv;
		TextView tv;
	}
}
