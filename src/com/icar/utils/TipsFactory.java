package com.icar.utils;

import com.icar.view.LoadingDialog;

import android.content.Context;

public class TipsFactory {

	private static TipsFactory tipsFactory = null;
	
	private LoadingDialog loadingDialog = null;
	
	/**
	 * 单例模式
	 * @param contxt
	 * @return
	 */
	public static TipsFactory getInstance(){
		if(tipsFactory == null){
			tipsFactory = new TipsFactory();
		}
		
		return tipsFactory;
	}
	
	/**
	 * 显示加载框
	 */
	public void showLoadingDialog(Context context){
		loadingDialog = new LoadingDialog(context);
		loadingDialog.show();
	}
	
	
	/**
	 * 使加载框消失
	 */
	public void dismissLoadingDialog(){
		if(loadingDialog != null && loadingDialog.isShowing()){
			loadingDialog.dismiss();
		}
	}
	
}
