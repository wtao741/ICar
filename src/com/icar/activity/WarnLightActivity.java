package com.icar.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

import com.icar.adapter.WarnLightAdapter;
import com.icar.base.AbstractTitleActivity;
import com.icar.base.HeadClick;
import com.icar.bean.WarnLightEntity;
import com.icar.view.WarnLightDialog;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class WarnLightActivity extends AbstractTitleActivity implements HeadClick{

	@ViewInject(R.id.warn_light_gridView) GridView gridView;
	
    private String[] titles;
	
    private String[] dess;
    
    private String[] answers;
    
    private List<WarnLightEntity> datas;
    
    private int[] icons = {R.drawable.warn_light1,R.drawable.warn_light2,R.drawable.warn_light3,R.drawable.warn_light4,
    		R.drawable.warn_light5,R.drawable.warn_light6,R.drawable.warn_light7,R.drawable.warn_light8,
    		R.drawable.warn_light9,R.drawable.warn_light10,R.drawable.warn_light11,R.drawable.warn_light12,
    		R.drawable.warn_light13,R.drawable.warn_light14,R.drawable.warn_light15,R.drawable.warn_light16,
    		R.drawable.warn_light17,R.drawable.warn_light18,R.drawable.warn_light19,R.drawable.warn_light20,
    		R.drawable.warn_light21,R.drawable.warn_light22,R.drawable.warn_light23,R.drawable.warn_light24,
    		R.drawable.warn_light25,R.drawable.warn_light26,R.drawable.warn_light27,R.drawable.warn_light28,
    		R.drawable.warn_light29,R.drawable.warn_light30,R.drawable.warn_light31,R.drawable.warn_light32,
    		R.drawable.warn_light33,R.drawable.warn_light34,R.drawable.warn_light35,R.drawable.warn_light36,
    		R.drawable.warn_light37,R.drawable.warn_light38,R.drawable.warn_light39,R.drawable.warn_light40,};
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		
		setContentView(R.layout.activity_warn_light);
		setTitle(R.string.home_bt5);
		isShowRightView(0, false);
		ViewUtils.inject(this);
		
		setDataes();
		
		//gridView.setAdapter(new WarnLightAdapter(this,datas));
		setGridViewAdapter();
	}

	/*
	 * 有点卡
	 */
	private void setGridViewAdapter() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();

		for (int i = 0; i < titles.length; i++) {
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("image", icons[i]);
			temp.put("str", titles[i]);
			map.add(temp);
		}

		SimpleAdapter adapter = new SimpleAdapter(this, map,
				R.layout.warn_light_item, new String[] { "image", "str" },
				new int[] { R.id.home_bt_iv, R.id.home_bt_tv });
		gridView.setAdapter(adapter);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				WarnLightDialog dialog = new WarnLightDialog(WarnLightActivity.this,datas.get(position));
				dialog.showDialog();
			}
		});
	}

	@Override
	public void left() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void right() {
		// TODO Auto-generated method stub
		
	}
	
	public void setDataes(){
		titles = getResources().getStringArray(R.array.warn_light_title);
		dess = getResources().getStringArray(R.array.warn_light_des);
		answers = getResources().getStringArray(R.array.warn_light_answer);
		
		datas = new ArrayList<WarnLightEntity>();
		int len = titles.length;
		for(int i=0;i<len;i++){
			WarnLightEntity bean = new WarnLightEntity(icons[i], titles[i], dess[i], answers[i]);
			datas.add(bean);
		}
	}
}
