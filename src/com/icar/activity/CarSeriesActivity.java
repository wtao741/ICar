package com.icar.activity;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.icar.adapter.util.CommonAdapter;
import com.icar.adapter.util.ViewHolder;
import com.icar.base.AbstractTitleActivity;
import com.icar.base.BaseApplication;
import com.icar.bean.CarBrandEntity;
import com.icar.bean.CarSeriesEntity;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/*
 *  如果和你选中的车型相等就显示右边选中的符号
 * 
 */
public class CarSeriesActivity extends AbstractTitleActivity implements HttpCallBack{

	@ViewInject(R.id.listview) ListView listView;
	
	private String car ;
	
	private ArrayList<String> datas;
	
	private String tag = "CarYearsActivity";
	
	private CarSeriesEntity bean;
	
	private HttpUtil http;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.listview_no_driver);
		ViewUtils.inject(this);
		
		car = getIntent().getStringExtra("car");
		bean = (CarSeriesEntity) getIntent().getSerializableExtra("bean");
		
		setTitle(car);
		isShowLeftView(true);
		isShowRightView(0, false);
		
		http = new HttpUtil(this);
		http.modelByIdServlet(bean.getId());
		http.setHttpCallBack(this);
		datas = new ArrayList<String>();
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				BaseApplication.bean.setType(car);
				BaseApplication.bean.setYear(datas.get(position));
				BaseApplication.bean.setSeriesBean(bean);
				
				CarBrandEntity bean = new CarBrandEntity();
				bean.setIcon(BaseApplication.bean.getIcon());
				bean.setName(BaseApplication.bean.getName());
				bean.setType(BaseApplication.bean.getType());
                bean.setYear(BaseApplication.bean.getYear());
                bean.setSeriesBean(BaseApplication.bean.getSeriesBean());
                BaseApplication.myLikes.add(0, bean);
                BaseApplication.bean.setType("");
				BaseApplication.bean.setSeriesBean(null);
				BaseApplication.bean.setIcon("");
				BaseApplication.bean.setName("");
				
				finish();
				for(Activity act : BaseApplication.acts){
					act.finish();
				}
			}
		});
	}

	public void setAdapter(){
		listView.setAdapter(new CommonAdapter<String>(this,datas,R.layout.car_years_item) {

			@Override
			public void convert(ViewHolder helper, String item) {
				// TODO Auto-generated method stub
				helper.setText(R.id.tv_years, item);
			}
		});
	}
	
	@Override
	public void onFailure(int requestCode, HttpException arg0, String arg1) {
		
	}

	@Override
	public void onSuccess(int requestCode, ResponseInfo<String> arg0) {
		String result = arg0.result;
		Log.e("tag", result);
		try {
			JSONObject object = new JSONObject(result);
			JSONArray array = object.getJSONArray("data");
			int len = array.length();
			for(int i=0;i<len;i++){
				JSONObject serObject = array.getJSONObject(i);
				String str = serObject.getString("m_name");
				datas.add(str);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setAdapter();
	}
	
	
}
