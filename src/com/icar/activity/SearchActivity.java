package com.icar.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.icar.adapter.HotSearchAdapter;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends Activity implements OnClickListener,HttpCallBack {

	@ViewInject(R.id.search_gridview)
	GridView gridView;
	@ViewInject(R.id.search_history_listview)
	ListView listView;
	@ViewInject(R.id.search_et)
	EditText editText;
	@ViewInject(R.id.search_cancel)
	Button bt_cancel;
	@ViewInject(R.id.search_referch)
	ImageView iv_refersh;
	@ViewInject(R.id.search_cancel)
	ImageView iv_delect;
	@ViewInject(R.id.no_search)
	TextView tv_no;

	@OnClick(R.id.search_referch)
	public void searchReferchonClick(View v){
		iv_refersh.setAnimation(refershAnim);
		refershAnim.start();
		http.hotSearch(2859);
	}
	
	private HotSearchAdapter hotSearchAdapter;
     
	
	private List<String> datas;

	private String[] strs = { "美女车震", "罗生门" };

	private HttpUtil http ; 
	
	private Animation refershAnim;   //刷新动画
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_search);
		ViewUtils.inject(this);

		setOnClick();

		setHistoryAdapter();
		
		http = new HttpUtil(this);
		http.setHttpCallBack(this);
		http.hotSearch(2859);
		
		refershAnim = AnimationUtils.loadAnimation(this, R.anim.search_refersh);
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SearchActivity.this,
						SearchResultActivity.class);
				intent.putExtra("keys", datas.get(position));
				startActivity(intent);
			}
		});
		
		
	}

	private void setHistoryAdapter() {
		// TODO Auto-generated method stub
		List<Map<String, Object>> map = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < strs.length; i++) {
			Map<String, Object> temp = new HashMap<String, Object>();
			temp.put("str", strs[i]);
			map.add(temp);
		}
		SimpleAdapter adapter = new SimpleAdapter(this, map,
				R.layout.search_history_item, new String[] { "str" },
				new int[] { R.id.searhc_history_item_tv });
		listView.setAdapter(adapter);
	}

	private void setOnClick() {
		// TODO Auto-generated method stub
		bt_cancel.setOnClickListener(this);

		// 键盘输入名字后直接点击“搜索”按钮触发
		editText.setOnKeyListener(new android.view.View.OnKeyListener() {

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER
						&& event.getAction() == KeyEvent.ACTION_DOWN) {
					Intent intent = new Intent(SearchActivity.this,
							SearchResultActivity.class);
					String key = editText.getText().toString().trim();
					intent.putExtra("keys", key);
					startActivity(intent);
					return true;
				}
				return false;
			}
		});
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.search_cancel:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onFailure(int requestCode, HttpException arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(int requestCode, ResponseInfo<String> arg0) {
		// TODO Auto-generated method stub
		datas = new ArrayList<String>();
		String result = arg0.result;
		JSONObject object;
		try {
			object = new JSONObject(result);
			String code = object.getString("code");
			if(code.equals("200")){
				JSONArray array = object.getJSONArray("data");
				int len = array.length();
				for(int i=0;i<len;i++){
					String str = array.getString(i);
					datas.add(str);
				}
				setAdapter();
			}else{
				Toast.makeText(SearchActivity.this, code, 2000).show();
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		refershAnim.cancel();
	}
	
	public void setAdapter(){
		hotSearchAdapter = new HotSearchAdapter(this, datas);
		gridView.setAdapter(hotSearchAdapter);
		
	}
}
