package com.icar.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.icar.adapter.MyExpandableListViewAdapter;
import com.icar.base.AbstractTitleActivity;
import com.icar.bean.OfficialEntity;
import com.icar.bean.OfficialSecEntity;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

public class OfficialActivity extends AbstractTitleActivity implements HttpCallBack{

	@ViewInject(R.id.official_expand) ExpandableListView expandListView;
	
	private List<OfficialEntity> data;
	private MyExpandableListViewAdapter adapter;
	private HttpUtil http;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_offical);
		setTitle(R.string.tab_official);
		isShowLeftView(false);
		isShowRightView(0, false);
		ViewUtils.inject(this);
		
		http = new HttpUtil(this);
		http.setHttpCallBack(this);
		http.getBookCatalog(2859);
		
		data = new ArrayList<OfficialEntity>();
		
		expandListView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent intent = new Intent(OfficialActivity.this,OfficialItemActivity.class);
				startActivity(intent);
				return false;
			}
		});
	}

	@Override
	public void onFailure(int requestCode, HttpException arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(int requestCode, ResponseInfo<String> arg0) {
		// TODO Auto-generated method stub
		String result = arg0.result;
		try {
			JSONObject dataObject = new JSONObject(result);
			JSONArray dataArray = dataObject.getJSONArray("data");
			int dataLen = dataArray.length();
			for(int i=0;i<dataLen;i++){
				JSONObject object = dataArray.getJSONObject(i);
				int infoid = object.getInt("infoid");
				int id = object.getInt("id");
				String classname = object.getString("classname");
				//int pagesum = object.getInt("pagesum");
				int zipsize = object.optInt("zipsize");
				
				List<OfficialSecEntity> secData = new ArrayList<OfficialSecEntity>();
				JSONArray secArray = object.getJSONArray("sec");
				int secLen = secArray.length();
				for(int j=0;j<secLen;j++){
					JSONObject secObject = secArray.getJSONObject(j);
			        int secId = object.getInt("id"); 
					String secClassname = object.getString("classname");
					int parentid = object.optInt("parentid");
					int startpage = object.optInt("startpage");
					int endpage = object.optInt("endpage");
					OfficialSecEntity secBean = new OfficialSecEntity(secId, secClassname, parentid, startpage, endpage);
					secData.add(secBean);
				}
				OfficialEntity bean = new OfficialEntity(infoid, id, classname, 0, zipsize, secData);
				data.add(bean);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("Tag", e.getMessage());
			e.printStackTrace();
		}
		adapter = new MyExpandableListViewAdapter(this,data);
		expandListView.setAdapter(adapter);		
	}
		
}
