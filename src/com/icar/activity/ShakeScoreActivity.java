package com.icar.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

import com.icar.adapter.CarScoreAdapter;
import com.icar.base.AbstractTitleActivity;
import com.icar.base.BaseApplication;
import com.icar.bean.CarScoreEntity;
import com.icar.bean.FunctionItemEntity;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;

/*
 * RadioButton 选择器要加checked属性
 */
public class ShakeScoreActivity extends AbstractTitleActivity implements
		HttpCallBack {

	@ViewInject(R.id.car_score_radioGroup)
	RadioGroup radioGroup;
	@ViewInject(R.id.score_tips)
	TextView tv_tips;
	@ViewInject(R.id.score_listview)
	ListView listView;
	@ViewInject(R.id.score)
	TextView tv_score;
	@ViewInject(R.id.car_score)
	TextView tv_sumscore;
	
	private List<CarScoreEntity> datas;
	private List<FunctionItemEntity> params1 = new ArrayList<FunctionItemEntity>(); // 功能数据
	private List<FunctionItemEntity> params2 = new ArrayList<FunctionItemEntity>(); // 品牌数据
	private List<FunctionItemEntity> params3 = new ArrayList<FunctionItemEntity>(); // 舒适度数据
	private List<FunctionItemEntity> params4 = new ArrayList<FunctionItemEntity>(); // 空间数据
    private String score;
	
	private String str1 = "功能：选项内的15项功能基本涵盖了市场上各种车型附加配置，配置越多，无论车震还是驾乘都能提供更完美的感受。该项目满分15分，少一项配置减1分。";
	private String str2 = "品牌：据调查，越豪华的车型约MM车震的几率越大，或许是豪华车的座椅更舒适，配置更高空间更大，最重要是车主更有身份！因此开豪车是车震第一神器！";
	private String str3 = "车震舒适度：模特分别在前后座及后备厢各位置的实际车震感受，根据座椅舒适度、车内空间能否发挥多种姿势等因素决定，是MM给车辆最直接的评价，满分30分。";
	private String str4 = "使用空间：使用空间是指车辆内部的实际可使用空间，该尺寸决定着车震时你的满足感以及能否达到更多姿势的重要参数，满分40分。";

	private CarScoreAdapter adapter;
	private List<FunctionItemEntity> dataTemp;
	private HttpUtil http;
	private String tag = "shake";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_shake_score);
		setTitle(R.string.shake_tips);
		ViewUtils.inject(this);
		isShowRightView(0, false);

		http = new HttpUtil(this);
		http.setHttpCallBack(this);
		http.getVehicleList(2859);

		// addDatas();
		datas = new ArrayList<CarScoreEntity>();
	}

	public void init() {
		
		tv_sumscore.setText(score);
		
		tv_tips.setText(BaseApplication.ToDBC(datas.get(0).getDes()));
		tv_score.setText("得分 :" + datas.get(0).getScore() + "分");

		dataTemp = datas.get(0).getMaps();
		adapter = new CarScoreAdapter(this, dataTemp);
		listView.setAdapter(adapter);

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.car_function:
					tv_tips.setText(BaseApplication
							.ToDBC(datas.get(0).getDes()));
					tv_score.setText("得分 :" + datas.get(0).getScore() + "分");
					dataTemp = datas.get(0).getMaps();
					adapter.refresh(dataTemp);
					break;
				case R.id.car_brand:
					tv_tips.setText(BaseApplication
							.ToDBC(datas.get(1).getDes()));
					tv_score.setText("得分 :" + datas.get(1).getScore() + "分");
					dataTemp = datas.get(1).getMaps();
					adapter.refresh(dataTemp);
					break;
				case R.id.car_comfort:
					tv_tips.setText(BaseApplication
							.ToDBC(datas.get(2).getDes()));
					tv_score.setText("得分 :" + datas.get(2).getScore() + "分");
					dataTemp = datas.get(2).getMaps();
					adapter.refresh(dataTemp);
					break;
				case R.id.car_space:
					tv_tips.setText(BaseApplication
							.ToDBC(datas.get(3).getDes()));
					tv_score.setText("得分 :" + datas.get(3).getScore() + "分");
					dataTemp = datas.get(3).getMaps();
					adapter.refresh(dataTemp);
					break;
				}
			}
		});

	}

	private void addDatas() {
		// TODO Auto-generated method stub

		FunctionItemEntity item1 = new FunctionItemEntity("自动空调", "1分", true);
		FunctionItemEntity item2 = new FunctionItemEntity("高端音响", "1分", true);
		FunctionItemEntity item3 = new FunctionItemEntity("天窗", "1分", true);
		FunctionItemEntity item4 = new FunctionItemEntity("DVD影音", "1分", true);
		FunctionItemEntity item5 = new FunctionItemEntity("座椅加热", "1分", true);
		FunctionItemEntity item6 = new FunctionItemEntity("车载冰箱", "1分", true);
		FunctionItemEntity item7 = new FunctionItemEntity("后排空调", "1分", true);
		FunctionItemEntity item8 = new FunctionItemEntity("记忆座椅", "1分", true);
		FunctionItemEntity item9 = new FunctionItemEntity("座椅通风", "1分", true);
		FunctionItemEntity item10 = new FunctionItemEntity("氛围灯", "1分", true);
		FunctionItemEntity item11 = new FunctionItemEntity("老板键", "1分", false);
		FunctionItemEntity item12 = new FunctionItemEntity("遮阳帘", "1分", false);
		FunctionItemEntity item13 = new FunctionItemEntity("电动座椅", "1分", false);
		FunctionItemEntity item14 = new FunctionItemEntity("私密玻璃", "1分", false);
		FunctionItemEntity item15 = new FunctionItemEntity("后排整体放平", "1分",
				false);

		List<FunctionItemEntity> params1 = new ArrayList<FunctionItemEntity>();
		params1.add(item1);
		params1.add(item2);
		params1.add(item3);
		params1.add(item4);
		params1.add(item5);
		params1.add(item6);
		params1.add(item7);
		params1.add(item8);
		params1.add(item9);
		params1.add(item10);
		params1.add(item11);
		params1.add(item12);
		params1.add(item13);
		params1.add(item14);
		params1.add(item15);

		CarScoreEntity bean = new CarScoreEntity(str1, params1, 10);
		datas.add(bean);

		FunctionItemEntity item21 = new FunctionItemEntity("自主品牌", "3分", false);
		FunctionItemEntity item22 = new FunctionItemEntity("普通合资", "6分", false);
		FunctionItemEntity item23 = new FunctionItemEntity("国产豪华", "9分", false);
		FunctionItemEntity item24 = new FunctionItemEntity("进口豪华", "12分", true);
		FunctionItemEntity item25 = new FunctionItemEntity("奢华品牌", "15分", false);

		List<FunctionItemEntity> params2 = new ArrayList<FunctionItemEntity>();
		params2.add(item21);
		params2.add(item22);
		params2.add(item23);
		params2.add(item24);
		params2.add(item25);

		CarScoreEntity bean1 = new CarScoreEntity(str2, params2, 12);
		datas.add(bean1);

		FunctionItemEntity item31 = new FunctionItemEntity("第一排", "7分", false);
		FunctionItemEntity item32 = new FunctionItemEntity("第二排", "7分", false);
		FunctionItemEntity item33 = new FunctionItemEntity("后备箱", "7分", false);

		List<FunctionItemEntity> params3 = new ArrayList<FunctionItemEntity>();
		params3.add(item31);
		params3.add(item32);
		params3.add(item33);

		CarScoreEntity bean2 = new CarScoreEntity(str3, params3, 21);
		datas.add(bean2);

		FunctionItemEntity item41 = new FunctionItemEntity("储物空间", "18分", false);
		FunctionItemEntity item42 = new FunctionItemEntity("使用空间", "18分", false);
		FunctionItemEntity item43 = new FunctionItemEntity("长：", "1200mm",
				false);
		FunctionItemEntity item44 = new FunctionItemEntity("宽：", "1000mm",
				false);
		FunctionItemEntity item45 = new FunctionItemEntity("高：", "800mm", false);

		List<FunctionItemEntity> params4 = new ArrayList<FunctionItemEntity>();
		params4.add(item41);
		params4.add(item42);
		params4.add(item43);
		params4.add(item44);
		params4.add(item45);

		CarScoreEntity bean3 = new CarScoreEntity(str4, params4, 36);
		datas.add(bean3);
	}

	@Override
	public void onFailure(int requestCode,HttpException arg0, String arg1) {

	}

	@Override
	public void onSuccess(int requestCode,ResponseInfo<String> arg0) {
		String result = arg0.result;
		try {
			JSONObject object = new JSONObject(result);
			JSONObject data = object.getJSONObject("data");
			int featureselnums = data.getInt("featureselnums");
			int featurepoint = data.getInt("featurepoint");
			int space1 = data.getInt("space1"); // 第一排得分
			int space2 = data.getInt("space2"); // 第二排得分
			int space3 = data.getInt("space3"); // 后备箱
			int space4 = data.getInt("space4"); // 储物空间
			int space5 = data.getInt("space5"); // 使用空间总得分
			int sumpoint = data.getInt("sumpoint"); // 车震总得分
 
			score = "车震指数"+sumpoint+"分";
			
			boolean isChecked = false;
			JSONArray featureArray = data.getJSONArray("feature");
			for (int i = 0; i < featureArray.length(); i++) {
				isChecked = false;
				if (i <= featureselnums - 1) {
					isChecked = true;
				}
				String item = featureArray.getString(i);
				FunctionItemEntity bean = new FunctionItemEntity(item, "1分",
						isChecked);
				params1.add(bean);
			}
			isChecked = false;
			CarScoreEntity bean = new CarScoreEntity(str1, params1,
					featurepoint); // 功能数据

			int brandtypesel = data.getInt("brandtypesel"); // 品牌第几项变蓝
			int brandpoint = data.getInt("brandpoint"); // 品牌得分

			JSONArray brandArray = data.getJSONArray("brandtype");
			for (int i = 0; i < brandArray.length(); i++) {
				isChecked = false;
				if (i == brandtypesel - 1) {
					isChecked = true;
				}
				String item = brandArray.getString(i);
				FunctionItemEntity bean1 = new FunctionItemEntity(item, (i+1)
						* 3 + "分", isChecked);
				params2.add(bean1);
			}
			CarScoreEntity bean1 = new CarScoreEntity(str2, params2, brandpoint); // 品牌数据

			
			FunctionItemEntity item31 = new FunctionItemEntity("第一排", space1
					+ "分", false);
			FunctionItemEntity item32 = new FunctionItemEntity("第二排", space2
					+ "分", false);
			FunctionItemEntity item33 = new FunctionItemEntity("后备箱", space3
					+ "分", false);
			params3.add(item31);
			params3.add(item32);
			params3.add(item33);
			CarScoreEntity bean2 = new CarScoreEntity(str3, params3, space1
					+ space2 + space3);

			
			FunctionItemEntity item41 = new FunctionItemEntity("储物空间", space4
					+ "分", false);
			FunctionItemEntity item42 = new FunctionItemEntity("使用空间", space5
					+ "分", false);
			params4.add(item41);
			params4.add(item42);
			CarScoreEntity bean3 = new CarScoreEntity(str4, params4, space4
					+ space5);

			datas.add(bean);
			datas.add(bean1);
			datas.add(bean2);
			datas.add(bean3);

			init();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
