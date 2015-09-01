package com.icar.view;

import com.icar.activity.R;
import com.icar.bean.WarnLightEntity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WarnLightDialog{

	private Context context;
	
	private static WarnLightDialog callDialog;
	
	private View view;
	
	private Dialog dialog;
	
	private ImageView iv_icon;
	
	private TextView tv_title;
	
	private TextView tv_des;
	
	private TextView tv_answer;
	
	@ViewInject(R.id.warn_light_delect) ImageView iv_det;
	   
	public WarnLightDialog(Context context,WarnLightEntity bean){
		this.context = context;
		
		view = LayoutInflater.from(context).inflate(R.layout.dialog_warn_light,null);
		
		initView();
		
		iv_icon.setImageResource(bean.getIcon());
		tv_title.setText(bean.getTitle());
		tv_des.setText(bean.getDes());
		tv_answer.setText(bean.getAnswer());
		
		dialog = new AlertDialog.Builder(context).create();
		
		dialog.setCanceledOnTouchOutside(false);
		
		iv_det.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismissDialog();
			}
		});
	}
	
	public void initView(){
		iv_det = (ImageView) view.findViewById(R.id.warn_light_delect);
		iv_icon = (ImageView) view.findViewById(R.id.warn_light_dialog_icon);
		tv_title = (TextView) view.findViewById(R.id.warn_light_dialog_name);
		tv_des = (TextView) view.findViewById(R.id.warn_light_dialog_name_des);
		tv_answer = (TextView) view.findViewById(R.id.warn_light_dialog_respone_des);
	}
	
	public void showDialog(){
		// 两句的顺序不能调换
		if(!dialog.isShowing())
		dialog.show();
		dialog.getWindow().setContentView((RelativeLayout) view);
	}
	
	public void dismissDialog(){
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}
	
}
