package com.icar.utils;

import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;

public interface HttpCallBack {
	public void onFailure(int requestCode,HttpException arg0, String arg1);
	public void onSuccess(int requestCode,ResponseInfo<String> arg0);
}
