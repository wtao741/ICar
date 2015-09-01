package com.icar.adapter;

import java.util.List;

import com.icar.activity.R;
import com.icar.bean.OfficialEntity;
import com.icar.bean.OfficialSecEntity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

	private Context context;
    private List<OfficialEntity> groupData;
	
	public MyExpandableListViewAdapter(Context context, List<OfficialEntity> groupData){
		super();
		this.context = context;
		this.groupData = groupData;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return groupData.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return groupData.get(groupPosition).getSecDatas().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return groupData.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return groupData.get(groupPosition).getSecDatas().get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = LayoutInflater.from(context).inflate(
				R.layout.official_item1, null);
		View dot = view.findViewById(R.id.official_item_dot);
		switch (groupPosition % 5) {
		case 0:
            dot.setBackgroundResource(R.drawable.official_corner);
			break;
		case 1:
			 dot.setBackgroundResource(R.drawable.official_corner2);
			break;
		case 2:
			 dot.setBackgroundResource(R.drawable.official_corner3);
			break;
		case 3:
			 dot.setBackgroundResource(R.drawable.official_corner4);
			break;
		case 4:
			 dot.setBackgroundResource(R.drawable.official_corner5);
			break;
		}
		OfficialEntity bean = groupData.get(groupPosition);
		RelativeLayout rela = (RelativeLayout) view.findViewById(R.id.official_item_real);
		TextView tv = (TextView) view.findViewById(R.id.official_item_tv);
		tv.setText(bean.getClassname());
		ImageView iv = (ImageView) view.findViewById(R.id.official_item_arrow);
		if(isExpanded){
			iv.setImageResource(R.drawable.arrow_down);
			rela.setBackgroundColor(context.getResources().getColor(R.color.home_text_color));
			tv.setTextColor(context.getResources().getColor(R.color.white));
		}else{
			iv.setImageResource(R.drawable.arrow_right);
			rela.setBackgroundColor(context.getResources().getColor(R.color.white));
			tv.setTextColor(context.getResources().getColor(R.color.home_title_bgm));
		}
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.official_item2, null);
		OfficialSecEntity bean = groupData.get(groupPosition).getSecDatas().get(childPosition);
		TextView tv = (TextView) view.findViewById(R.id.official_item1_tv);
		TextView tv1 = (TextView) view.findViewById(R.id.official_item1_tv1);
		tv.setText(bean.getClassname());
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}

}
