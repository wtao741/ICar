package com.icar.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.icar.adapter.SearchResultAdapter;
import com.icar.bean.SearchEntity;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class SearchResultActivity extends Activity implements HttpCallBack{

	@ViewInject(R.id.search_result_listView) ListView listView;
	@ViewInject(R.id.search_et)
	EditText editText;
	
	@OnClick(R.id.search_cancel)
	public void searchCancelonClick(View v){
		finish();
	}
			
	private List<SearchEntity> datas = new ArrayList<SearchEntity>();
	
	//private List<String> datas;
	
	private HttpUtil http;
	
	private String keys;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		
		setContentView(R.layout.activity_search_result);
		ViewUtils.inject(this);
		super.onCreate(savedInstanceState);
		
		http = new HttpUtil(this);
		http.setHttpCallBack(this);
		keys = getIntent().getStringExtra("keys");
		editText.setText(keys);
		editText.setSelection(keys.length());
		http.homeSearch(3805, keys);
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
			JSONObject object = new JSONObject(result);
			String code = object.getString("code");
			if(code.equals("200")){
				JSONArray dataArray = object.getJSONArray("data");
				int len = dataArray.length();
				for(int i=0;i<len;i++){
					JSONObject item = dataArray.getJSONObject(i);
					int id = item.getInt("classid");
					String classname = item.getString("classname");
					String description = item.getString("description");
					String url = item.getString("url");
					
					SearchEntity bean = new SearchEntity(id, classname, description, url);
					datas.add(bean);
				}
				setAdater();
			}else{
				Toast.makeText(SearchResultActivity.this, code, 2000).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			Log.e("tag", e.getMessage());
			e.printStackTrace();
		}
	}
	
	public void setAdater(){
		listView.setAdapter(new SearchResultAdapter(this,datas));
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
