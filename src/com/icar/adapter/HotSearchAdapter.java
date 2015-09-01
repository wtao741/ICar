package com.icar.adapter;

import java.util.List;

import com.icar.activity.R;
import com.icar.activity.SearchActivity;
import com.icar.activity.SearchResultActivity;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class HotSearchAdapter extends BaseAdapter {

	private Context context;
	private List<String> datas ;
	
	
	public HotSearchAdapter(Context context, List<String> datas) {
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
		convertView = LayoutInflater.from(context).inflate(R.layout.item_hot_search, null);
		
		Button tv = (Button) convertView.findViewById(R.id.hot_search_bt);
		tv.setText(datas.get(position));
		
		tv.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(context,
						SearchResultActivity.class);
				intent.putExtra("keys", datas.get(position));
				context.startActivity(intent);
			}
		});
		return convertView;
	}

}
