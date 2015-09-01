package com.icar.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.icar.activity.BuildConfig;
import com.icar.base.BaseApplication;

import android.R.integer;
import android.content.Context;
import android.content.Entity;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

public class LogicProxy {
	String test;
	Boolean flag = true;
	final String rawkey = "mitbbs.c";

	// 国内、国外发送短信验证码的参数
		private String accountId = "aaf98f894a85eee5014a998477fe08f6";
		private String authkoten = "fd7b80ae865343eaa7d6e7fe82bd018f";
		private String appId = "aaf98f894a85eee5014a998a9ee208fa";
		private String key = "7da4832a";
		private String secret = "eb6ee7b3";
		
	/**
	 * 发送请求，获取json数据
	 * @param c 请求里的参数数据，是json类型
	 * @param cookieFlag 是否发送cookie
	 * @return 请求的结果
	 * @throws WSError
	 */
	private JSONObject doPost(JSONObject c, Boolean cookieFlag) throws WSError {
		BasicHttpParams httpParams = new BasicHttpParams();  
		HttpConnectionParams.setConnectionTimeout(httpParams, 10000);  
		HttpConnectionParams.setSoTimeout(httpParams, 30000);
		
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		
		//加密des
//---------------------------------------------------------------------------		
//		String encstr = null ;
//		
//		try {
//			encstr = DesEncrypt.encryptDES(c.toString(), rawkey);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//			Log.e("LogicProxy","加密失败");
//			
//			throw new WSError("加密失败", 0);
//		}
//		
//		try {
//			String decstr = DesEncrypt.decryptDES(encstr.toString(), rawkey);
//			
//			Log.e("LogicProxy","解密后    "  + decstr);
//		} catch (Exception e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//			Log.e("LogicProxy","解密失败");
//			throw new WSError("解密失败", 0);
//		}
//		list.add(new BasicNameValuePair("msg", encstr));
//		list.add(new BasicNameValuePair("encryptFlag",1+""));
//		
		
//----------------------------------------------------------------------------------------
		
		list.add(new BasicNameValuePair("msg", c.toString()));
		HttpClient httpclient = new DefaultHttpClient(httpParams);
		String url = "http://api.iucars.com/index.php?g=App&m=Member&a=register&from=android";
		
		HttpPost request = new HttpPost(url);
		Log.e("banben",""+request.getProtocolVersion());
		request.setHeader("Charset", "UTF-8");
		request.setHeader("Content-Type", "application/x-www-form-urlencoded");
		// request.setHeader( "Content-Length","1");
		if (cookieFlag) {
			String cookieStr = String.format(
					"UTMPKEY=%s;UTMPNUM=%s;UTMPUSERID=%s"
//					StaticString.appData.getUTMPKEY(),
//					StaticString.appData.getUTMPNUM(),
//					StaticString.appData.getUTMPUSERID()
					);
			request.setHeader("Cookie", cookieStr);			
		}
		
		JSONObject result = null;
		try {
			for(int i=0 ; i < list.size();i++){
				Log.e("LogicProxy", "requestaes: list.object  " + list.get(i).toString());
			}
			request.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
			if (BuildConfig.DEBUG) {
				Log.v("LogicProxy", "\n#########################begin########################################################");
				Log.v("LogicProxy", "request: " + list);
			}
			//执行请求,获取服务器传过来的数据
			HttpResponse response = httpclient.execute(request);
			//将数据装换成字符串
			String stre = EntityUtils.toString(response.getEntity());
			
//			//20141225新加,用流的方式获取
//			HttpEntity entity = response.getEntity();
//			String stre = null;
//			if(entity!=null){
//				BufferedReader bf = new BufferedReader(new InputStreamReader(entity.getContent()));
//				String line = null;
//				int count = 0;
//				while((line = bf.readLine())!=null){
//					if(count==0){
//						stre = line;
//					}else {
//						stre+=line;
//					}
//					count++;
//				}				
//			}
			
			//打印获取的数据
			Log.v("xy LogicProxy", "etiry: " + stre);
			String etiry = null;
			//解密
//			etiry = DesEncrypt.decryptDES(stre,rawkey);
//			test = etiry;
			
			etiry = stre;
			test = stre;
			
			Log.e("LogicProxy", "request: com  httpcode  " + response.getStatusLine().getStatusCode());
			Log.e("LogicProxy", "request: com" + test  + "  httpcode  " + response.getStatusLine().getStatusCode());
			//String version = response.getProtocolVersion().getProtocol();
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = new JSONObject(etiry);
				if (BuildConfig.DEBUG) {
					Log.d("LogicProxy", "result:\n" + result.toString());
					JSONArray Log_s = result.names();
					if (Log_s != null) {
						Log.d("LogicProxy", "receive from service: ");
						for (int i = 0; i < Log_s.length() ; i++) {
							Log.d("LogicProxy", Log_s.getString(i) + ": " + result.getString(Log_s.getString(i)));
						}
					}
					Log.v("LogicProxy", "\n**********************end*****************************************************");
				}
				if (!cookieFlag) {
					this.processSession(result);
				}
			} else {
				throw new WSError("http返回状态异常", 0);
			}
		} catch (WSError e) {  /*捕获各种异常，然后统一封装到自定义的异常类中*/
			throw e;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new WSError(e.getMessage(), 0);
		} catch (IOException e) {
			throw new WSError(e.getMessage(), 0);
		} catch (ParseException e) {
			throw new WSError(e.getMessage(), 0);
		} catch (JSONException e) {
			throw new WSError(e.getMessage(), 1);
		} catch (NullPointerException e) {
			throw new WSError(e.getMessage(), 1);
		} catch (Exception e) {
			throw new WSError(e.getMessage(), 1);
		}
		
		return result;
	}
	
	//------------------------------------------------------------------------------------------------------------
	//上传图片
	private JSONObject doPostUserImage(JSONObject c,String str,Boolean cookieFlag,String username) throws WSError {
		BasicHttpParams httpParams = new BasicHttpParams();  
		HttpConnectionParams.setConnectionTimeout(httpParams, 10000);  
		HttpConnectionParams.setSoTimeout(httpParams, 30000); 
		Log.e("wt","tupianshagnchuan post");
		HttpClient httpclient = new DefaultHttpClient();
		
//		HttpPost post = new HttpPost("http://192.168.1.105:8080/club/weclub.php/user/userregister");
		HttpPost post = new HttpPost(BaseApplication.URL);
        MultipartEntity entity = new MultipartEntity();
//        entity.addPart("userimage", new FileBody(new File(str)));
        try {
			entity.addPart("msg", new StringBody(c.toString()));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        entity.addPart(username, new FileBody(new File(str)));
        
        if (cookieFlag) {
			String cookieStr = String.format(
					"UTMPKEY=%s;UTMPNUM=%s;UTMPUSERID=%s"
//					StaticString.appData.getUTMPKEY(),
//					StaticString.appData.getUTMPNUM(),
//					StaticString.appData.getUTMPUSERID()
					);
			post.setHeader("Cookie", cookieStr);			
		}
        
		post.setEntity(entity);
		
		JSONObject result = null;
		try {
			HttpResponse response = httpclient.execute(post);
			String stre = EntityUtils.toString(response.getEntity());
			
			Log.e("","tupianshagnchuan tupian");
			String etiry = null;
			
			etiry = stre;
			test = stre;
			
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				result = new JSONObject(etiry);
				if (BuildConfig.DEBUG) {
					Log.d("LogicProxy", "result:\n" + result.toString());
					JSONArray Log_s = result.names();
					if (Log_s != null) {
						Log.d("LogicProxy", "receive from service: ");
						for (int i = 0; i < Log_s.length() ; i++) {
							Log.d("LogicProxy", Log_s.getString(i) + ": " + result.getString(Log_s.getString(i)));
						}
					}
					Log.v("LogicProxy", "\n**********************end*****************************************************");
				}
				if (!cookieFlag) {
					this.processSession(result);
				}
			} else {
				Log.e("","tupianshagnchuan type exception");
				throw new WSError("http返回状态异常", 0);
			}
		} catch (WSError e) {  /*捕获各种异常，然后统一封装到自定义的异常类中*/
			throw e;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new WSError(e.getMessage(), 0);
		} catch (IOException e) {
			throw new WSError(e.getMessage(), 0);
		} catch (ParseException e) {
			throw new WSError(e.getMessage(), 0);
		} catch (JSONException e) {
			throw new WSError(e.getMessage(), 1);
		} catch (NullPointerException e) {
			throw new WSError(e.getMessage(), 1);
		} catch (Exception e) {
			throw new WSError(e.getMessage(), 1);
		}
		
		return result;
	}
	
	
	//-----------------------------------------------------------------------------------------------------------------------------
	/**
	 * 发送json请求中间部分，加了些请求认证数据
	 */
	private JSONObject sendRequestJson(JSONObject c) throws WSError {
		if (BaseApplication.appData != null) {
			try {
				c.put("sessionID", BaseApplication.appData.getSessionID());
				if(!c.getString("reqType").equals("100007")){
					c.put("userID", BaseApplication.appData.getUTMPUSERID());
					c.put("userID", BaseApplication.USER_NAME);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 当登录或者申请sessionID的时候不发送cookie,其余的时候都发送cookie
		Boolean cFlag = true;
		try {
			if (c.getString("reqType").equals(RequestType.REQUEST_TYPE_LOGIN)
					|| c.getString("reqType").equals(
							RequestType.REQUEST_TYPE_SESSIONID)) {
				cFlag = false;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return this.doPost(c, cFlag);
	}
	
	
	//发图片
	public JSONObject sendUserImage(String type,String str,String imagename) throws WSError {
		JSONObject js = new JSONObject();
		try {
			js.put("reqType", type);
			if("attachfile".equals(imagename)){
				String[] strs = str.split("/");			
				js.put("act", "add");
				js.put("attachname", strs[strs.length - 1]);
				Log.e("act", strs[strs.length -1]);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (BaseApplication.appData != null) {
			try {
				js.put("sessionID", BaseApplication.appData.getSessionID());
				js.put("userID", BaseApplication.appData.getUTMPUSERID());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// 当登录或者申请sessionID的时候不发送cookie,其余的时候都发送cookie
		Boolean cFlag = true;
		try {
			if (js.getString("reqType").equals(RequestType.REQUEST_TYPE_LOGIN)
					|| js.getString("reqType").equals(
							RequestType.REQUEST_TYPE_SESSIONID)) {
				cFlag = false;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return this.doPostUserImage(js, str,cFlag,imagename);
	}
	
	//未登录时guest 账号登陆请求，cookie信息
	public JSONObject requestSessionID(String requestType)throws WSError{
		JSONObject js = new JSONObject();
		try {
			js.put("reqType", requestType);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return sendRequestJson(js);
	}
	
	//未登录时guest 账号登陆请求，cookie信息
		public JSONObject register(String mobile,String auth,String password)throws WSError{
			JSONObject js = new JSONObject();
			try {
				js.put("mobile", mobile);
				js.put("auth", auth);
				js.put("password", password);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			return sendRequestJson(js);
		}
		
	//忘记密码
	public JSONObject getPsdByEmail(String CodeNum,String userName,String emailAddress)throws WSError{
		JSONObject js = new JSONObject();
		try {
			js.put("reqType", RequestType.REQUEST_TYPE_LOST_PASSWORD);
			js.put("userid",userName);
			js.put("mail",emailAddress);
			js.put("verify",CodeNum);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return sendRequestJson(js);
	}
	
	private void processSession(JSONObject js) throws JSONException {
		if (js == null) {
			return;
		}
		String data = js.optString("data");
		if (data == null) {
			return;
		}
		
		Log.e("","cookieee data " + data.toString());
		JSONArray jsonArray = new JSONArray(data);
		JSONObject jsonObject = (JSONObject) jsonArray.opt(0);
		if (jsonObject == null) {
			return;
		}
		Log.e("","cookieee data reqtype" +js.optString("reqType").toString() 
				 + " UTMPKEY  " + jsonObject.optInt("UTMPKEY")
				 + " UTMPNUM  " + jsonObject.optInt("UTMPNUM")
				 + " LOGINTIME  " + jsonObject.optInt("LOGINTIME")
				 + " UTMPUSERID  " + jsonObject.optString("UTMPUSERID"));

		if (js.optString("reqType").equals(RequestType.REQUEST_TYPE_SESSIONID)) {
			BaseApplication.appData.setUTMPKEY(jsonObject.optInt("UTMPKEY"));
			BaseApplication.appData.setUTMPNUM(jsonObject.optInt("UTMPNUM"));
			BaseApplication.appData.setLOGINTIME(jsonObject.optInt("LOGINTIME"));
			BaseApplication.appData.setUTMPUSERID(jsonObject
					.optString("UTMPUSERID"));
			BaseApplication.appData.LASTACCESS = null;
		} else if (js.optString("reqType").equals(
				RequestType.REQUEST_TYPE_LOGIN)) {
			if (jsonObject.has("loginResult")
					&& jsonObject.optString("loginResult").equals(
							RequestType.LOGIN_RESULT_OK)) {
				BaseApplication.appData.setUTMPKEY(jsonObject.optInt("UTMPKEY"));
				BaseApplication.appData.setUTMPNUM(jsonObject.optInt("UTMPNUM"));
				BaseApplication.appData.setUTMPUSERID(jsonObject
						.optString("UTMPUSERID"));
				BaseApplication.appData.setLOGINTIME(jsonObject
						.optInt("LOGINTIME"));
				BaseApplication.appData.LASTACCESS = null;
				BaseApplication.appData.setUserName(js.optString("username"));
			} else {
				return;
			}
		}
	}
	
}
	