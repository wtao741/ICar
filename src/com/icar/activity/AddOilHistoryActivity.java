package com.icar.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;
import com.icar.base.AbstractTitleActivity;
import com.icar.base.BaseApplication;
import com.icar.base.HeadClick;
import com.icar.bean.OilRecordEntity;
import com.icar.datatimewheel.DatePopupWindow;
import com.icar.datatimewheel.DatePopupWindow.OnDateSelectListener;
import com.icar.datatimewheel.NumericWheelAdapter;
import com.icar.datatimewheel.OnWheelScrollListener;
import com.icar.datatimewheel.WheelView;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class AddOilHistoryActivity extends AbstractTitleActivity implements HeadClick{

	@ViewInject(R.id.add_oil_date)
	TextView tv_date;
	@ViewInject(R.id.add_oil_odomentr)
	EditText et_odomentr;
	@ViewInject(R.id.add_oil_money)
	EditText et_money;
	@ViewInject(R.id.add_oil_price)
	EditText et_price;
	@ViewInject(R.id.add_oil_amount)
	EditText et_amount;
	@ViewInject(R.id.light_yes) Button bt_light_yes;
	@ViewInject(R.id.light_no) Button bt_light_no;
	@ViewInject(R.id.all_yes) Button bt_all_yes;
	@ViewInject(R.id.all_no) Button bt_all_no;

	@OnClick(R.id.add_oil_date)
	public void addOilDateonClick(View v) {
		//showDataView();
		if(type == 1)
		showPopwindow(getDataPick());
	}

	@SuppressLint("NewApi")
	@OnClick(R.id.light_yes)
	public void lightYesonClick(View v){
		light = 1;
		bt_light_yes.setBackgroundResource(R.drawable.login_submit_normal);
	    bt_light_no.setBackground(null);
	    bt_all_yes.setTextColor(getResources().getColor(R.color.white));
	    bt_light_no.setTextColor(getResources().getColor(R.color.home_title_bgm));
	}
	
	@SuppressLint("NewApi")
	@OnClick(R.id.light_no)
	public void lightNoonClick(View v){
		light = 0;
		bt_light_no.setBackgroundResource(R.drawable.login_submit_normal);
	    bt_light_yes.setBackground(null);
	    bt_light_yes.setTextColor(getResources().getColor(R.color.home_title_bgm));
	    bt_light_no.setTextColor(getResources().getColor(R.color.white));
	}
	
	@SuppressLint("NewApi")
	@OnClick(R.id.all_yes)
	public void allYesonClick(View v){
		all = 1;
		bt_all_yes.setBackgroundResource(R.drawable.login_submit_normal);
	    bt_all_no.setBackground(null);
	    bt_all_yes.setTextColor(getResources().getColor(R.color.white));
	    bt_all_no.setTextColor(getResources().getColor(R.color.home_title_bgm));
	}
	
	@SuppressLint("NewApi")
	@OnClick(R.id.all_no)
	public void allNoonClick(View v){
		all = 0;
		bt_all_no.setBackgroundResource(R.drawable.login_submit_normal);
	    bt_all_yes.setBackground(null);
	    bt_all_yes.setTextColor(getResources().getColor(R.color.home_title_bgm));
	    bt_all_no.setTextColor(getResources().getColor(R.color.white));
	}
	private DatePopupWindow window;
	private PopupWindow menuWindow;
	private LayoutInflater inflater = null;
	private WheelView year;
	private WheelView month;
	private WheelView day;
	private int light = 1;   //是否亮。1：亮，0：不亮
	private int all = 1;
	private int forget = 0;  //是否遗忘 1：遗忘  0：未遗忘
	private HttpUtil http;
	
	private OilRecordEntity bean;
	private int type;
	
	@SuppressLint("ResourceAsColor")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
		setContentView(R.layout.activity_add_oil_history);
		setTitle("添加油耗记录");
		isShowLeftView(true);
		isShowRightView(R.string.save, true);
		ViewUtils.inject(this);
		setRightTextColor(R.color.white);
        setHeadClick(this);
		http = new HttpUtil(this);
        
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
		tv_date.setText(format.format(new Date()));
		
		type = getIntent().getIntExtra("type", 1);
		if(type == 2){
			bean = (OilRecordEntity) getIntent().getSerializableExtra("bean");
			initView();
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	@SuppressLint("NewApi")
	public void initView(){
		
		isShowRightView(R.string.null_tips, false);
		tv_date.setText(bean.getChargedate());
		et_odomentr.setText(bean.getMileage());
		et_money.setText(bean.getMoney());
		et_price.setText(bean.getPrice());
		et_amount.setText(bean.getChargeoil());
		
		et_odomentr.setEnabled(false);
		et_money.setEnabled(false);
		et_price.setEnabled(false);
		et_amount.setEnabled(false);
		
		bt_light_no.setClickable(false);
		bt_light_yes.setClickable(false);
		bt_all_no.setClickable(false);
		bt_all_yes.setClickable(false);
		
		int light = bean.getLight();
		if(light == 0){
			bt_light_no.setBackgroundResource(R.drawable.login_submit_normal);
		    bt_light_yes.setBackground(null);
		    bt_light_yes.setTextColor(getResources().getColor(R.color.home_title_bgm));
		    bt_light_no.setTextColor(getResources().getColor(R.color.white));
		}
		
		int full = bean.getFull();
		if(full == 0){
			bt_all_no.setBackgroundResource(R.drawable.login_submit_normal);
		    bt_all_yes.setBackground(null);
		    bt_all_yes.setTextColor(getResources().getColor(R.color.home_title_bgm));
		    bt_all_no.setTextColor(getResources().getColor(R.color.white));
		}
	}
	
	/**
	 * 初始化popupWindow
	 * @param view
	 */
	private void showPopwindow(View view) {
		menuWindow = new PopupWindow(view,LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		menuWindow.setFocusable(true);
		menuWindow.setBackgroundDrawable(new BitmapDrawable());
		menuWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM, 0, 0);
		menuWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				menuWindow=null;
			}
		});
	}
	
	/**
	 * 
	 * @return
	 */
	private View getDataPick() {
		Calendar c = Calendar.getInstance();
		int curYear = c.get(Calendar.YEAR);
		int curMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
		int curDate = c.get(Calendar.DATE);
		final View view = inflater.inflate(R.layout.datapick, null);
		
		year = (WheelView) view.findViewById(R.id.year);
		year.setAdapter(new NumericWheelAdapter(1980, curYear+10));
		year.setLabel("年");
		year.setCyclic(true);
		year.addScrollingListener(scrollListener);
		
		month = (WheelView) view.findViewById(R.id.month);
		month.setAdapter(new NumericWheelAdapter(1, 12));
		month.setLabel("月");
		month.setCyclic(true);
		month.addScrollingListener(scrollListener);
		
		day = (WheelView) view.findViewById(R.id.day);
		initDay(curYear,curMonth);
		day.setLabel("日");
		day.setCyclic(true);

		year.setCurrentItem(curYear - 1980);
		month.setCurrentItem(curMonth - 1);
		day.setCurrentItem(curDate - 1);
		
		Button bt = (Button) view.findViewById(R.id.set);
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String str = (year.getCurrentItem()+1980) + "年";
				if(month.getCurrentItem()+1<10){
					str += "0"+(month.getCurrentItem()+1)+"月";
				}else{
					str += (month.getCurrentItem()+1)+"月";
				}
				if(day.getCurrentItem()+1<10){
					str += "0"+(day.getCurrentItem()+1)+"日";
				}else{
					str += (day.getCurrentItem()+1)+"日";
				}
				tv_date.setText(str);
				menuWindow.dismiss();
			}
		});
		Button cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				menuWindow.dismiss();
			}
		});
		return view;
	}
	
	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {

		@Override
		public void onScrollingStarted(WheelView wheel) {

		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			// TODO Auto-generated method stub
			int n_year = year.getCurrentItem() + 1950;// 楠烇拷
			int n_month = month.getCurrentItem() + 1;// 閺堬拷
			initDay(n_year,n_month);
		}
	};
	
	/**
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
		case 0:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			day = flag ? 29 : 28;
			break;
		default:
			day = 30;
			break;
		}
		return day;
	}

	/**
	 */
	private void initDay(int arg1, int arg2) {
		day.setAdapter(new NumericWheelAdapter(1, getDay(arg1, arg2), "%02d"));
	}

	@Override
	public void left() {
		
	}

	@Override
	public void right() {
		String date = tv_date.getText().toString().trim();
        String odomentr = et_odomentr.getText().toString().trim();	
        String money = et_money.getText().toString().trim();
        String price = et_price.getText().toString().trim();
        String amount = et_amount.getText().toString().trim();
        http.addOilRecord( BaseApplication.getTime(date), odomentr, money, price, amount, light, all, forget);
	}
}
