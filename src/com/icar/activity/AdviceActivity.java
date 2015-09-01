package com.icar.activity;

import java.util.List;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.icar.base.AbstractTitleActivity;
import com.icar.view.HorizontalListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

public class AdviceActivity extends AbstractTitleActivity {

	@ViewInject(R.id.advice_problem) EditText et_problem; 
	@ViewInject(R.id.problem_listView) HorizontalListView listView;
	@ViewInject(R.id.advice_problem_mobile) EditText et_mobile;
	@ViewInject(R.id.advice_problem_submit) Button bt_submit;
	
	
	private List<String> datas;
	private PopupWindow selectPicPopupWindow = null;
	private ContentResolver cr;      
	private Uri photoUri;
	private static final int PIC = 1;   // 选择图片
	private static final int PHO = 2;   // 照相
	private static final int ALTER = 3; // 修改
	private Bitmap photo;               // 选择好的图片的bitmap形式
	private String picPath;             //获取到的图片路径
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_advice);
		
		setTitle("意见与反馈");
		isShowRightView(0, false);
		ViewUtils.inject(this);
	}
	
	/**
	 * 加载发图的popupwindow,适配器中调用
	 */
	public void initSelectPicPopupWindow() {

		View v = LayoutInflater.from(this).inflate(
				R.layout.zmit_popupwindow_pick_pic, null);
		Button cancelBtn = (Button) v.findViewById(R.id.cancel);
		Button photoBtn = (Button) v.findViewById(R.id.photo);
		Button picBtn = (Button) v.findViewById(R.id.pic);

		selectPicPopupWindow = new PopupWindow(v, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);
		selectPicPopupWindow.showAtLocation(findViewById(R.id.parent),
				Gravity.CENTER, 0, 0);
		selectPicPopupWindow.update();

		photoBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				dismissPopupWindow();
			}
		});

		picBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				dismissPopupWindow();
			}
		});

		cancelBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				dismissPopupWindow();
			}
		});
	}

	/**
	 * popupwindow消失
	 */
	private void dismissPopupWindow() {
		if (selectPicPopupWindow != null && selectPicPopupWindow.isShowing()) {
			selectPicPopupWindow.dismiss();
		}
	}
	
	/**
	 * 照相
	 */
	private void takePhoto() {
		// 执行拍照前，应该先判断SD卡是否存在
		String SDState = Environment.getExternalStorageState();
		if (SDState.equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"
			/***
			 * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
			 * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
			 */
			ContentValues values = new ContentValues();
			photoUri = this.getContentResolver().insert(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, photoUri);
			/** ----------------- */
			startActivityForResult(intent, PHO);
		} else {
			Toast.makeText(this, "内存卡不存在", Toast.LENGTH_SHORT).show();
		}
	}
}
