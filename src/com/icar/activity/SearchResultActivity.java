package com.icar.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.icar.adapter.SearchResultAdapter;
import com.icar.bean.SearchEntity;
import com.icar.bean.SearchHistoryEntity;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SearchResultActivity extends Activity implements HttpCallBack {

	@ViewInject(R.id.search_result_listView)
	ListView listView;
	@ViewInject(R.id.search_et)
	EditText editText;
	@ViewInject(R.id.tv_null)
	TextView tv_null;

	@OnClick(R.id.search_cancel)
	public void searchCancelonClick(View v) {
		finish();
	}

	private List<SearchEntity> datas = new ArrayList<SearchEntity>();

	private HttpUtil http;   //网络
    private DbUtils db;      //数据库
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
		http.homeSearch(2859, keys);

		setClick();
		
		db = DbUtils.create(this);
		save(keys);
	}

	private void setClick() {
		// 键盘输入名字后直接点击“搜索”按钮触发
		editText.setOnKeyListener(new android.view.View.OnKeyListener() {

			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER
						&& event.getAction() == KeyEvent.ACTION_DOWN) {

					String key = editText.getText().toString().trim();
					Log.e("tag", key);
					http.homeSearch(2859, key);
					save(keys);
					listView.setVisibility(View.VISIBLE);
					tv_null.setVisibility(View.GONE);
					return true;
				}
				return false;
			}
		});
	}

	
	/**保存数据到数据库
	 * @param keys
	 */
	public void save(String keys){
		Log.e("tag", "keys:"+keys);
		SearchHistoryEntity bean = new SearchHistoryEntity();
		bean.setMsg(keys);
		List<SearchHistoryEntity> datas;
		try {
			datas = db.findAll(Selector.from(SearchHistoryEntity.class).where("msg", "=", keys));
			if(datas.size() == 0){
				Log.e("tag", "null");
				db.save(bean);
			}else{
				Log.e("tag", "save:"+datas.size());
			}
		} catch (DbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("tag", "save");
	}
	
	@Override
	public void onFailure(int requestCode, HttpException arg0, String arg1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSuccess(int requestCode, ResponseInfo<String> arg0) {
		// TODO Auto-generated method stub
		String result = arg0.result;
		Log.e("tag", result);
		try {
			JSONObject object = new JSONObject(result);
			String code = object.getString("code");
			if (code.equals("200")) {
				JSONArray dataArray = object.getJSONArray("data");
				int len = dataArray.length();
				for (int i = 0; i < len; i++) {
					JSONObject item = dataArray.getJSONObject(i);
					int id = item.getInt("classid");
					String classname = item.getString("classname");
					String description = item.getString("description");
					String url = item.getString("url");

					SearchEntity bean = new SearchEntity(id, classname,
							description, url);
					datas.add(bean);
				}
				setAdater();
			} else {
				Toast.makeText(SearchResultActivity.this, code, 2000).show();
			}
		} catch (JSONException e) {
			listView.setVisibility(View.GONE);
			tv_null.setVisibility(View.VISIBLE);
			e.printStackTrace();
		}
	}

	public void setAdater() {
		listView.setAdapter(new SearchResultAdapter(this, datas));

		ImageView iv = new ImageView(this);
		iv.setBackgroundResource(R.drawable.car1);
		listView.setEmptyView(iv);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Bundle bundle = new Bundle();
				bundle.putString("type", "search");
				bundle.putSerializable("bean", datas.get(position));
				Intent intent = new Intent(SearchResultActivity.this,
						ShowActivity.class);
				intent.putExtras(bundle);
				startActivity(intent);
			}
		});
	}
}
