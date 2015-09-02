package com.icar.activity;

import android.os.Bundle;

import com.icar.base.AbstractTitleActivity;

public class AccidentDesActivity extends AbstractTitleActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setTitle("事故描述");
		isShowRightView(R.string.null_tips, false);
	}
}
