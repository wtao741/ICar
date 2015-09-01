package com.icar.utils;

import java.io.File;

import org.apache.http.protocol.HTTP;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.icar.activity.LoginActivity;
import com.icar.activity.RegisterActivity;
import com.icar.base.BaseApplication;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.http.client.entity.BodyParamsEntity;

public class HttpUtil {

	HttpUtils httpUtils = new HttpUtils();
	
	public static String url = "http://api.iucars.com/index.php?g=App&";
			
	private Context context;
	
	public HttpUtil(Context context){
		this.context = context;
		tips = new TipsFactory().getInstance();
	}
	
	int error = 0;
	
	private TipsFactory tips;
	
	private HttpCallBack httpCallBack;
	
	public void setHttpCallBack(HttpCallBack httpCallBack){
		this.httpCallBack = httpCallBack;
	}
	
	/*
	 * 获取验证码
	 * 注册时判断用户是否已注册
	 * 修改密码时判断用户是否未注册
	 */
	public  void getAuthCode(String mobile){
		tips.showLoadingDialog(context);
		url = url+"m=Member&a=authcode&mobile="+mobile;
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

				@Override
				public void onFailure(HttpException arg0, String arg1) {
					// TODO Auto-generated method stub
					errorTips(arg0.getExceptionCode());
					tips.dismissLoadingDialog();
					httpCallBack.onFailure(0,arg0, arg1);
				}

				public void onSuccess(ResponseInfo<String> arg0) {
					// TODO Auto-generated method stub
					tips.dismissLoadingDialog();
					httpCallBack.onSuccess(0,arg0);
				}
			});
	}
	
	/*
	 * 注册
	 */
	public void register(String mobile,String auth,String password){
		tips.showLoadingDialog(context);
		String url = "http://api.iucars.com/index.php?g=App&m=Member&a=register&from=android";
		RequestParams params = new RequestParams();
		params.addBodyParameter("", mobile);
		params.addBodyParameter("", auth);
		params.addBodyParameter("", password);
		
		BodyParamsEntity params1 = new BodyParamsEntity();
		
		httpUtils.send(HttpMethod.POST, url, params, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				tips.dismissLoadingDialog();
				
				showShortToast(""+arg0.getExceptionCode());
				Log.e("tag", arg0.getMessage());
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				tips.dismissLoadingDialog();
				Log.e("tag", arg0.result);
				String result = arg0.result;
				if(result.equals("1")){
					showShortToast("注册成功");
					((RegisterActivity)context).finish();
				}else if(result.equals("0")){
					showShortToast("注册失败");
				}else if(result.equals("0")){
					showShortToast("验证码错误");
				}else{
					showShortToast(arg0.result);
				}
				((RegisterActivity)context).finish();
			}
		});
	}
	
	/*
	 * 参数： mobile(手机号) password(密码)
	 */
	public void login(final String mobile,final String password){
		String url = "http://api.iucars.com/index.php?g=App&m=Member&a=login&from=android";
		//key写成空，
	    RequestParams params = new RequestParams();
	    params.addBodyParameter("", mobile);
		params.addBodyParameter("", password);
		tips.showLoadingDialog(context);
		
		httpUtils.send(HttpMethod.POST, url, params,new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				showShortToast(""+arg0.getExceptionCode());
				tips.dismissLoadingDialog();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				tips.dismissLoadingDialog();
				httpCallBack.onSuccess(0, arg0);
			}
		});
	}
	
	/*
	 * 参数： mobile(手机号) password(密码)
	 */
	public void mainLogin(final String mobile,final String password){
		String url = "http://api.iucars.com/index.php?g=App&m=Member&a=login&from=android";
		//key写成空，
	    RequestParams params = new RequestParams();
	    params.addBodyParameter("", mobile);
		params.addBodyParameter("", password);
		
		httpUtils.send(HttpMethod.POST, url, params,new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				showShortToast(""+arg0.getExceptionCode());
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				httpCallBack.onSuccess(0, arg0);
			}
		});
	}
	/*
	 * GET 参数 mobile(手机号码) ， code(验证码)
	 */
	public void findPassword(int mobile,int code) {
		String url = "http://api.iucars.com/index.php?g=App&m=Member&a=FindPassword";
	    tips.showLoadingDialog(context);
	    httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				showShortToast(""+arg0.getExceptionCode());
				tips.dismissLoadingDialog();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				tips.dismissLoadingDialog();
			}
		});
	}
	
	
	/**
	 * 得到用户信息，没用到
	 */
	public void getUserInfo(String mobile){
		String url = "http://api.iucars.com/index.php?g=App&m=Member&a=editInfo&mobile"+mobile;
		tips.showLoadingDialog(context);
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				tips.dismissLoadingDialog();
				showShortToast("error:"+arg0.getExceptionCode());
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				tips.dismissLoadingDialog();
				httpCallBack.onSuccess(0, arg0);
			}
		});
	}
	
	
	public void uploadHeadIcon(String path){
		String url = "http://api.iucars.com/index.php?g=App&m=Member&a=editInfo&do=edit";
		tips.showLoadingDialog(context);
		RequestParams params = new RequestParams();
		params.addBodyParameter("mobile", BaseApplication.getUserName());
		params.addBodyParameter("avatar", new File(path));  
		httpUtils.send(HttpMethod.POST, url, params,new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				tips.dismissLoadingDialog();
				showShortToast(""+arg0.getExceptionCode());
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				tips.dismissLoadingDialog();
				Log.e("upload", arg0.result);
			}
		});
	}
	
	
	public void updateUserInfo(String type,String message){
		String url = "http://api.iucars.com/index.php?g=App&m=Member&a=editInfo&do=edit";
		RequestParams params = new RequestParams();
		params.addBodyParameter("mobile", BaseApplication.getUserName());
		params.addBodyParameter(type, message);
		httpUtils.send(HttpMethod.POST, url, params,new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				showShortToast(""+arg0.getExceptionCode());
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				Log.e("tag", "userinfo"+arg0.result);
			}
		});
	}
	/*
	 * 八大类
	 */
	public void getClassContent(int seriesid,int classid){
		String url = "http://api.iucars.com/index.php?g=App&m=Api&a=getClassContent&seriesid="+seriesid+"&classid="+classid+"&callback=";
		tips.showLoadingDialog(context);
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				tips.dismissLoadingDialog();
				httpCallBack.onFailure(0,arg0, arg1);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				tips.dismissLoadingDialog();
				httpCallBack.onSuccess(0,arg0);
			}
		});
	}
	
	/*
	 * 查询车型。从我关注的车进去
	 */
	public void brandServlet(){
		String url = "http://api.iucars.com/index.php?g=App&m=Api&a=brandServlet&callback=?";
		tips.showLoadingDialog(context);
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				tips.dismissLoadingDialog();
				httpCallBack.onFailure(0,arg0, arg1);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				tips.dismissLoadingDialog();
				httpCallBack.onSuccess(0,arg0);
			}
		});
	}		
	

	/*
	 * 根据车系获取车型
	 * 参数：id(车系id)
	 */
    public void modelByIdServlet(int series_id){
    	String url = "http://api.iucars.com/index.php?g=App&m=Api&a=modelByIdServlet&id="+series_id+"&callback=?";
        tips.showLoadingDialog(context);
        httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				showShortToast(""+arg0.getExceptionCode());
				tips.dismissLoadingDialog();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				tips.dismissLoadingDialog();
				httpCallBack.onSuccess(0, arg0);
			}
		});
    }
    
	/*
	 * 首页搜索
	 * GET 参数：seriesid(车系id)  ,keywords(关键字)，callback=?(自定义函数)
	 */
	public void homeSearch(int seriesid,String keywords){
		String url = "http://api.iucars.com/index.php?g=App&m=Api&a=Search&seriesid="+seriesid+"&keywords="+keywords+"&callback=";
		tips.showLoadingDialog(context);
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Log.e("tag", "search fail:"+arg1);
				tips.dismissLoadingDialog();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				Log.e("tag", "search succ:"+arg0.result);
				tips.dismissLoadingDialog();
				httpCallBack.onSuccess(0, arg0);
			}
		});
	}
	
	/*
	 * (获取车震得分信息)	
                    访问方式：GET 参数 seriesid(车系id)
	 */
	public void getVehicleList(int seriesid){
		String url = "http://api.iucars.com/index.php?g=App&m=Api&a=getVehicleList&seriesid="+seriesid;
		tips.showLoadingDialog(context);
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				tips.dismissLoadingDialog();
				showShortToast(arg1);
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				tips.dismissLoadingDialog();
				httpCallBack.onSuccess(0,arg0);
			}
		});
	}

	
	/*
	 * (获取官方手册目录)
	 * 参数： seriesid(车系id)
	 */
	public void getBookCatalog(int seriesid){
		String url = "http://api.iucars.com/index.php?g=App&m=Api&a=getBookCatalog&seriesid="+seriesid+"&callback=?";
	    tips.showLoadingDialog(context);
	    httpUtils.send(HttpMethod.POST, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				showShortToast(""+arg0.getExceptionCode());
				tips.dismissLoadingDialog();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				tips.dismissLoadingDialog();
				httpCallBack.onSuccess(0, arg0);
			}
		});
	}
	
	/**
	 * 得到车震信息
	 * @param seriesid
	 */
	public void getShakeInfo(int seriesid){
		String url = "http://api.iucars.com/index.php?g=App&m=Api&a=getVehicleInfos&seriesid="+seriesid;
		tips.showLoadingDialog(context);
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				showShortToast(""+arg0.getExceptionCode());
				tips.dismissLoadingDialog();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				httpCallBack.onSuccess(0, arg0);
				tips.dismissLoadingDialog();
			}
		});
	}
	
	public void hotSearch(int seriesid){
		String url = "http://api.iucars.com/index.php?g=App&m=Api&a=http://api.iucars.com/index.php?g=App&m=Api&a=searchHot&seriesid="+seriesid;
		tips.showLoadingDialog(context);
		httpUtils.send(HttpMethod.GET, url, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				showShortToast(""+arg0.getExceptionCode());
				tips.dismissLoadingDialog();
			}

			@Override
			public void onSuccess(ResponseInfo<String> arg0) {
				// TODO Auto-generated method stub
				httpCallBack.onSuccess(0, arg0);
				tips.dismissLoadingDialog();
			}
		});
	}
	
	public void getHtmlContent(int seriesid,int classid){
		String url = "http://api.iucars.com/index.php?g=App&m=Api&a=getHtmlContent&seriesid="+seriesid+"&classid="+classid;
	    
	}
	
	public void showShortToast(String message){
		Toast.makeText(context, message, 2000).show();
	}

	public void errorTips(int code){
		switch (code) {
		case 0:
			showShortToast("联网失败，请开启网络");
			break;

		default:
			break;
		}
	}
	
}
