package com.icar.activity;

import org.apache.http.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.icar.base.BaseApplication;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.*;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.Toast;

/**
 * @author Administrator ʹ��Tabhost+Groupʵ�ֽ��浼�� ÿ�ν�������¼һ�� ��ȡ��γ�Ⱥ͵�ַ
 */
public class MainActivity extends TabActivity {

	private TabHost mTabHost;
	private RadioGroup mTabButtonGroup;
    private RadioButton homeButton,iCarButton;
	
	public static final String TAB_HOME = "HOME";
	public static final String TAB_OFFICIAL = "OFFICIAL";
	public static final String TAB_DRIVE = "DRIVE";
	public static final String TAB_BRAND = "BRAND";
	public static final String TAB_ICAR = "ICAR";

	public SharedPreferences sp;
	private SharedPreferences share; // �õ�����ĳ���
	private Editor edit;

	String name;
	String pass;

	private HttpUtil http;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView(savedInstanceState);

		//MyLocation locaton = new MyLocation(this);


		// ��ȡ�û��������¼
		sp = getSharedPreferences("login", MODE_PRIVATE);
		name = sp.getString("username", "");
		pass = sp.getString("password", "");
//		if (!TextUtils.isEmpty(name)) {
//			new Thread() {
//				public void run() {
//					login(name, pass);
//				}
//			}.start();
//		}

		share = getSharedPreferences("city", MODE_PRIVATE);
		edit = share.edit();
//		StaticString.CITY = share.getString("cityName", "");
//		StaticString.ENNAME = share.getString("cityEnName", "");

//		// �Ӽ�ҵ����
//		Bundle bundle = getIntent().getExtras();
//		if (bundle.containsKey("flag")) {
//			String flag = bundle.getString("flag");
//			if (flag.equals("1")) {
//				mTabHost.setCurrentTabByTag(TAB_MY);
//				lawyerButton.setChecked(false);
//				myButton.setChecked(true);
//			}
//		}
		
		Toast.makeText(this, BaseApplication.WIDTH+"  "+BaseApplication.HEIGHT+"user:"+BaseApplication.getUserName()+"pass:"+BaseApplication.getPassword(), 2000).show();
	    http = new HttpUtil(this);
	    http.mainLogin(BaseApplication.getUserName(), BaseApplication.getPassword());
	    http.setHttpCallBack(new HttpCallBack() {
			
			@Override
			public void onSuccess(int requestCode, ResponseInfo<String> arg0) {
				Log.e("tag", "login:"+arg0.result);
				// TODO Auto-generated method stub
				String result = arg0.result;
				try {
					JSONObject object = new JSONObject(result);
					String code = object.getString("code");
					if(code.equals("200")){
						BaseApplication.saveUsername(BaseApplication.getUserName(), BaseApplication.getPassword());
						JSONObject userObject = object.getJSONObject("data");
						BaseApplication.user.setName(BaseApplication.getUserName());
						BaseApplication.user.setUserName(userObject.getString("nickname"));
						//BaseApplication.user.setCityId(userObject.getString("cityid"));
						BaseApplication.user.setCity(userObject.getString("cityname"));
						String url = userObject.getString("avatar");
						url = url.replace("./", "/");
						BaseApplication.user.setHead_url("http://api.iucars.com"+url);
						BaseApplication.user.setPassword(BaseApplication.getPassword());
					}else if(result.equals("0")){
					}else{
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					Log.e("tag", e.getMessage());
					e.printStackTrace();
				}
			}
			
			@Override
			public void onFailure(int requestCode, HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void initView(Bundle savedInStanceState) {
		mTabHost = getTabHost();
		mTabButtonGroup = (RadioGroup) findViewById(R.id.home_radio_button_group);

		homeButton = (RadioButton) findViewById(R.id.home_tab_home);
		iCarButton = (RadioButton) findViewById(R.id.home_tab_icar);
		
		Intent lawery = new Intent(this, HomeActivity.class);
		Intent talk = new Intent(this, OfficialActivity.class);
		Intent joinLawery = new Intent(this, DriveActivity.class);
		Intent more = new Intent(this, BrandActivity.class);
		Intent my = new Intent(this, ICarActivity.class);

		mTabHost.addTab(mTabHost.newTabSpec(TAB_HOME)
				.setIndicator(TAB_HOME).setContent(lawery));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_OFFICIAL).setIndicator(TAB_OFFICIAL)
				.setContent(talk));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_DRIVE)
				.setIndicator(TAB_DRIVE).setContent(joinLawery));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_BRAND).setIndicator(TAB_BRAND)
				.setContent(more));
		mTabHost.addTab(mTabHost.newTabSpec(TAB_ICAR).setIndicator(TAB_ICAR)
				.setContent(my));

		if (savedInStanceState != null) {
			mTabHost.setCurrentTabByTag(savedInStanceState.getString("tab"));
		}

		mTabButtonGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						switch (checkedId) {
						case R.id.home_tab_home:
							mTabHost.setCurrentTabByTag(TAB_HOME);
							break;
						case R.id.home_tab_official:
							mTabHost.setCurrentTabByTag(TAB_OFFICIAL);
							break;
						case R.id.home_tab_drive:
							mTabHost.setCurrentTabByTag(TAB_DRIVE);
							break;
						case R.id.home_tab_brand:
							mTabHost.setCurrentTabByTag(TAB_BRAND);
							break;
						case R.id.home_tab_icar:
							mTabHost.setCurrentTabByTag(TAB_ICAR);
							break;
						}
					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
	}
	
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("tab", mTabHost.getCurrentTabTag());
	}


	/**
	 * �õ��汾��Ϣ
	 * 
	 * @return
	 */
	private String getVersion() {
		PackageManager pm = getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
			String version = info.versionName;
			return version;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "����ʧ��";
		}
	}

}
