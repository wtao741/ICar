package com.icar.view;

import com.icar.activity.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ClearMemoryDialog implements OnClickListener{

	private Context context;
	
	private static ClearMemoryDialog callDialog;
	
	private View view;
	
	private TextView tv_cancel,tv_submit;
	
	private Dialog dialog;
	
	public ClearMemoryDialog(Context context){
		this.context = context;
		
		view = LayoutInflater.from(context).inflate(R.layout.dialog_clear_memory,null);
		
		dialog = new Dialog(context,R.style.dialog);
		dialog.setContentView(view);
		
		tv_cancel = (TextView) view.findViewById(R.id.dialog_cancel);
		tv_submit = (TextView) view.findViewById(R.id.dialog_submit);
		
		tv_cancel.setOnClickListener(this);
		tv_submit.setOnClickListener(this);
	}
	
	public void showDialog(){
		dialog.show();
	}
	
	public void dismissDialog(){
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.dialog_cancel:
			dismissDialog();
			break;
        case R.id.dialog_submit:
			
			break;
		default:
			break;
		}
	}
}
