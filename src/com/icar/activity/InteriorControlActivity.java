package com.icar.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.icar.adapter.MultiItemRowListAdapter;
import com.icar.adapter.util.CommonAdapter;
import com.icar.adapter.util.ViewHolder;
import com.icar.base.AbstractTitleActivity;
import com.icar.bean.InteriorControlEntity;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/*
 * 内饰中控
 */
public class InteriorControlActivity extends AbstractTitleActivity implements HttpCallBack{

	@ViewInject(R.id.gridView)
	GridView gridView;

	private CommonAdapter<InteriorControlEntity> adapter;

	private List<InteriorControlEntity> datas;

	private HttpUtil http;
	
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.gridview);
		setTitle("内饰中控");
		isShowLeftView(true);
		isShowRightView(0, false);
		ViewUtils.inject(this);

		http = new HttpUtil(this);
		http.setHttpCallBack(this);
		http.getClassContent(3805, 1);
		datas = new ArrayList<InteriorControlEntity>();
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.userinfo)
				.showImageOnFail(R.drawable.userinfo).showImageForEmptyUri(R.drawable.userinfo).
				cacheInMemory(true).build();
		
	}

	public void setAdapter() {
		adapter = new CommonAdapter<InteriorControlEntity>(this, datas,
				R.layout.interior_control_item) {

			@Override
			public void convert(ViewHolder helper, InteriorControlEntity item) {
				// TODO Auto-generated method stub
				TextView tv = helper.getView(R.id.item_interior_tv);
				tv.setText(item.getName());
				ImageView iv = helper.getView(R.id.item_interior_iv);
				imageLoader.displayImage(item.getIcons(), iv, options);
			}
		};
		
		gridView.setAdapter(adapter);
	}

	@Override
	public void onFailure(int requestCode,HttpException arg0, String arg1) {
		// TODO Auto-generated method stub
		Log.e("tag", arg0.toString());
	}

	@Override
	public void onSuccess(int requestCode,ResponseInfo<String> arg0) {
		// TODO Auto-generated method stub
		String result = arg0.result;
		try {
			JSONObject object = new JSONObject(result);
			String code = object.getString("code");
			if(code.equals("200")){
				JSONArray array = object.getJSONArray("data");
				int len = array.length();
				for(int i=0;i<len;i++){
					JSONObject itemObject = array.getJSONObject(i);
					int id = itemObject.getInt("classid");
					String classname = itemObject.getString("classname");
					String imgurl = itemObject.getString("imgurl");
					String url = itemObject.getString("url");
					InteriorControlEntity bean = new InteriorControlEntity(id,imgurl,classname,url);
					datas.add(bean);
				}
			}else{
				showShortToast(code);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setAdapter();
	}
}
