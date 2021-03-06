package com.icar.view;

import com.icar.activity.R;
import com.icar.base.BaseApplication;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CallDialog implements OnClickListener {

	private Context context;

	private static CallDialog callDialog;

	private View view;

	private TextView tv_cancel, tv_submit;

	private TextView tv_title;

	private Dialog dialog;

	private String addString = "-";

	public CallDialog(Context context) {
		this.context = context;

		view = LayoutInflater.from(context).inflate(R.layout.dialog_call, null);

		dialog = new Dialog(context, R.style.dialog);
		dialog.setContentView(view);

		tv_title = (TextView) view.findViewById(R.id.dialog_call_title);
		tv_cancel = (TextView) view.findViewById(R.id.dialog_cancel);
		tv_submit = (TextView) view.findViewById(R.id.dialog_submit);

		String telString = BaseApplication.hotline.toString();
		String finalString = "";
		int index = 0;
		if ((index + 3) < telString.length()) {
			finalString += (telString.substring(index, index + 3) + addString);
			index += 3;
		}
		while ((index + 4) < telString.length()) {
			finalString += (telString.substring(index, index + 3) + addString);
			index += 3;
		}
		finalString += telString.substring(index, telString.length());
		tv_title.setText(finalString);
		tv_cancel.setOnClickListener(this);
		tv_submit.setOnClickListener(this);
	}

	public void showDialog() {
		dialog.show();
	}

	public void dismissDialog() {
		if (dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	public void setTitle(String str) {
		tv_title.setText(str);
	}

	public String getTitle() {
		return tv_title.getText().toString().trim();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.dialog_cancel:
			dismissDialog();
			break;
		case R.id.dialog_submit:
			Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"
					+ BaseApplication.hotline));
			context.startActivity(intent);
			break;
		default:
			break;
		}
	}
}
