package com.icar.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.icar.adapter.CarBrandAdapter;
import com.icar.adapter.CarTypeAdapter;
import com.icar.base.AbstractTitleActivity;
import com.icar.base.BaseApplication;
import com.icar.bean.CarBrandEntity;
import com.icar.bean.CarSeriesEntity;
import com.icar.bean.CarTypeEntity;
import com.icar.bean.CarYearEntity;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

public class CarTypeActivity extends AbstractTitleActivity implements HttpCallBack{

	@ViewInject(R.id.listView) ListView listView;
	@ViewInject(R.id.listView1) ListView listView1;

	private List<CarBrandEntity> datas;
	
	private CarBrandAdapter adapter;
	
	private CarTypeAdapter adapter1;
	
	private HttpUtil http;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_car_type);
		setTitle("车型选择");
		isShowLeftView(true);
		isShowRightView(0, false);
		ViewUtils.inject(this);
		
		//addDatas();
		datas = new ArrayList<CarBrandEntity>();
		BaseApplication.acts.add(this);
		http = new HttpUtil(this);
		http.brandServlet();
		http.setHttpCallBack(this);
	}

	private void setAdapter() {
        adapter = new CarBrandAdapter(this, datas);
        listView.setAdapter(adapter);
        
        //如果用户没有选择车型，默认第一个
        BaseApplication.bean.setIcon(datas.get(0).getIcon());
		BaseApplication.bean.setName(datas.get(0).getName());
		
        adapter1 = new CarTypeAdapter(this, datas.get(0).getTypes());
        listView1.setAdapter(adapter1);
        
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				int len = datas.size();
				for (int i = 0; i < len; i++) {
					if (i != position)
						datas.get(i).setChecked(false);
					else
						datas.get(i).setChecked(true);
				}
				adapter.notifyDataSetChanged();
				adapter1.setData(datas.get(position).getTypes());

				BaseApplication.bean.setIcon(datas.get(position).getIcon());
				BaseApplication.bean.setName(datas.get(position).getName());
			}
		});
	}


	@Override
	public void onFailure(int requestCode,HttpException arg0, String arg1) {
		// TODO Auto-generated method stub
		showShortToast(""+arg0.getExceptionCode());
	}

	@Override
	public void onSuccess(int requestCode,ResponseInfo<String> arg0) {
		String result = arg0.result;
		Log.e("result", result);
		JSONObject object;
		try {
			object = new JSONObject(result);
			JSONArray array = object.getJSONArray("data");
			for(int i=0;i<array.length();i++){
				JSONObject object1 = array.getJSONObject(i);                 //左边CarBrandEntity
				
				int id = object1.getInt("id");
				String name = object1.getString("name");
				String logo = object1.getString("logo");
				
				List<CarTypeEntity> typesDatas = new ArrayList<CarTypeEntity>() ;
				JSONArray brandArray = object1.getJSONArray("brand");       //右边数据CarTypeEntity
			
				for(int j=0;j<brandArray.length();j++){
					JSONObject object2 = brandArray.getJSONObject(j);
					int brand_id = object2.getInt("brand_id");
					String brand_name = object2.getString("name");         //右边名字
					
					List<CarSeriesEntity> seriesDatas = new ArrayList<CarSeriesEntity>();
					JSONArray seriesArray = object2.getJSONArray("series");
					for(int k=0;k<seriesArray.length();k++){               //右边图片
						JSONObject seriesObject = seriesArray.getJSONObject(k);
						int bseries_id = seriesObject.getInt("bseries_id");
						String showname = seriesObject.getString("showname");
						String cspic = seriesObject.getString("cspic");
						String infoid = seriesObject.getString("infoid");
						CarSeriesEntity seriesBean = new CarSeriesEntity(bseries_id, showname, cspic, infoid);
					    seriesDatas.add(seriesBean);
					}
					CarTypeEntity typeBean = new CarTypeEntity(brand_id,brand_name,seriesDatas);
                    typesDatas.add(typeBean);
				}
				boolean isCheck;
				if(i == 0){
					isCheck = true;
				}else{
					isCheck = false;
				}
				CarBrandEntity brandBean = new CarBrandEntity(id,logo, name, isCheck, typesDatas);
				datas.add(brandBean);
			}
		} catch (JSONException e) {
			Log.e("tag", e.getMessage());
			e.printStackTrace();
		}
		setAdapter();
	}
	
	
}
