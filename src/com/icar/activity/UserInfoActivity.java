package com.icar.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.icar.base.AbstractTitleActivity;
import com.icar.base.BaseApplication;
import com.icar.base.HeadClick;
import com.icar.utils.HttpCallBack;
import com.icar.utils.HttpUtil;
import com.icar.view.RoundImageView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class UserInfoActivity extends AbstractTitleActivity implements
		HeadClick,HttpCallBack {

	private static final int ONE = 1;
	private static final int TWO = 2;
	private static final int THERE = 3;
	private static final int FOUR = 4;
	private static final int FIVE = 5;
	private int crop = 300;
	private PopupWindow selectPicPopupWindow = null;
	private Uri imageUri;//to store the big bitmap
	private static final String IMAGE_FILE_LOCATION = "file:///sdcard/temp.jpg";
	
	private File tempFile;
	private String srcPath;
	private File sdcardTempFile;

	private HttpUtil http;
	
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	
	@ViewInject(R.id.user_usericon)
	RoundImageView iv_icon;
	@ViewInject(R.id.zmit_id_change_framelay_above)
	LinearLayout frameLayout;
	@ViewInject(R.id.user_name)
	TextView tv_name;
	@ViewInject(R.id.user_sex)
	TextView tv_sex;
	@ViewInject(R.id.user_address)
	TextView tv_address;

	@OnClick(R.id.real_user_icon)
	public void realUserIcononClick(View v) {
		initSelectPicPopupWindow();
	}

	@OnClick(R.id.real_user_name)
	public void realUserNameonClick(View v) {
		Intent intent = new Intent(this, UserNameActivity.class);
		intent.putExtra("name", tv_name.getText().toString());
		Log.e("tag", tv_name.getText().toString());
		startActivityForResult(intent, THERE);
	}

	@OnClick(R.id.real_user_sex)
	public void realUserSexonClick(View v) {
		Intent intent = new Intent(this, UserSexActivity.class);
		intent.putExtra("sex", tv_sex.getText().toString());
		startActivityForResult(intent, FOUR);
	}

	@OnClick(R.id.user_exit)
	public void userExitonClick(View v) {
		BaseApplication.saveUsername("", "");
//		BaseApplication.user.setCity("");
//		BaseApplication.user.setCityId(0);
//		BaseApplication.user.setHead_url("");
//		BaseApplication.user.setName("");
//		BaseApplication.user.setPassword("");
//		BaseApplication.user.setUserName("");
//		BaseApplication.user.setUserSex("");
		showShortToast("退出成功");
		finish();
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		imageUri = Uri.parse(IMAGE_FILE_LOCATION);
		setContentView(R.layout.activity_userinfo);
		setTitle("个人信息");
		setHeadClick(this);
		isShowRightView(0, false);
		ViewUtils.inject(this);
		http = new HttpUtil(this);
		http.setHttpCallBack(this);
		
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder().showImageOnLoading(R.drawable.userinfo)
				.showImageOnFail(R.drawable.userinfo).showImageForEmptyUri(R.drawable.userinfo).build();
		
		initView();
		http = new HttpUtil(this);
		http.setHttpCallBack(this);
		//http.getUserInfo(BaseApplication.getUserName());
	}

	private void initView() {
		imageLoader.displayImage(BaseApplication.user.getHead_url(), iv_icon, options);
		
		String nameStr = BaseApplication.user.getUserName();
		if(nameStr == null){
			tv_name.setHint("请设置昵称");
		}else{
			tv_name.setText(nameStr);
		}
		
		String sexStr = BaseApplication.user.getUserSex();
		if(sexStr == null){
			tv_sex.setHint("请选择您的性别");
		}else{
			tv_sex.setText(sexStr);
		}
		
		String addressStr = BaseApplication.user.getCity();
		if(addressStr == null){
			tv_address.setHint("请选择城市");
		}else{
			tv_address.setText(addressStr);
		}
	}

	@Override
	public void left() {
		// TODO Auto-generated method stub
		finish();
	}

	@Override
	public void right() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			switch (requestCode) {
			case THERE:
				tv_name.setText(data.getStringExtra("name"));
				break;
			case FOUR:
				Log.e("tag", data.getStringExtra("sex"));
				tv_sex.setText(data.getStringExtra("sex"));
				break;
			case 100:
				Bitmap bmp = BitmapFactory.decodeFile(sdcardTempFile
						.getAbsolutePath());

				if (bmp == null) {
					return;
				}
				iv_icon.setImageBitmap(bmp);
				srcPath = sdcardTempFile.toString();
				Log.e("tag", srcPath);
				// BitmapDrawable bd = new BitmapDrawable(bmp);
				http.uploadHeadIcon(srcPath);
				break;
			case 101:
				startPhotoZoom(Uri.fromFile(tempFile));
				break;
			case 102:
				if (data != null)
					sentPicToNext(data);
				break;
			}
		
	}

	// 将进行剪裁后的图片传递到下一个界面上
	private void sentPicToNext(Intent picdata) {
		Bundle bundle = picdata.getExtras();
		if (bundle != null) {
			Bitmap photo = bundle.getParcelable("data");
			if (photo == null) {
				return;
			} else {
				iv_icon.setImageBitmap(photo);
				String f = getPhotoFileNameUser();
				srcPath = Environment.getExternalStorageDirectory()
						+ "/mitbbs/except_chat/img/thumbnail/" + f;
				// imageUtil.writeToSdcardDD(f, photo);
				// sendImage();
			}
		}
	}

	protected void toTakePhoto() {
		// 指定调用相机拍照后照片的储存路径
		 Intent intentFromCapture = new Intent(  
                 MediaStore.ACTION_IMAGE_CAPTURE);  
		 intentFromCapture.addCategory(Intent.CATEGORY_DEFAULT);
		 intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
         startActivityForResult(intentFromCapture,  
                 101);  
	}

	protected void pickPhoto() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
				"image/*");
		intent.putExtra("output", Uri.fromFile(sdcardTempFile));
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);// 裁剪框比例
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", crop);// 输出图片大小
		intent.putExtra("outputY", crop);
		startActivityForResult(intent, 100);
	}

	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyy_MM_dd_HHmmss");
		return dateFormat.format(date) + ".jpg";
	}

	private String getPhotoFileNameUser() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"'IMG'_yyyyMMdd_HHmmss");
		return dateFormat.format(date) + ".jpg";
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
				tempFile = new File(Environment.getExternalStorageDirectory()
						+"/DCIM/Camera/", getPhotoFileName());
				toTakePhoto();
				dismissPopupWindow();
			}
		});

		picBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				sdcardTempFile = new File(Environment
						.getExternalStorageDirectory()
						+ "/mitbbs/except_chat/img/thumbnail", "tmp_pic_"
						+ SystemClock.currentThreadTimeMillis() + ".jpg");
				pickPhoto();
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
	 * @param uri
	 *            Intent intent = new Intent("com.android.camera.action.CROP");
	 *            crop String 发送裁剪信号 aspectX int X方向上的比例 aspectY int Y方向上的比例
	 *            outputX int 裁剪区的宽 outputY int 裁剪区的高 scale boolean 是否保留比例
	 *            return-data boolean 是否将数据保留在Bitmap中返回 data Parcelable
	 *            相应的Bitmap数据 circleCrop String 圆形裁剪区域？ MediaStore.EXTRA_OUTPUT
	 *            ("output") URI 将URI指向相应的file:///...，详见代码示例
	 */
	private void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);
		intent.putExtra("scale", true);
		startActivityForResult(intent, 102);
	}
	
	public void take(){
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//action is capture
		intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
		intent.addCategory(Intent.CATEGORY_DEFAULT);
		startActivityForResult(intent, 101);
	}
	
	private void cropImageUri(Uri uri, int outputX, int outputY, int requestCode){
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 2);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", outputX);
		intent.putExtra("outputY", outputY);
		intent.putExtra("scale", true);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		intent.putExtra("return-data", false);
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		intent.putExtra("noFaceDetection", true); // no face detection
		startActivityForResult(intent, requestCode);
	}
	
	private Bitmap decodeUriAsBitmap(Uri uri){
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}

	@Override
	public void onFailure(int requestCode, HttpException arg0, String arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(int requestCode, ResponseInfo<String> arg0) {
		// TODO Auto-generated method stub
		switch (requestCode) {
		case 0:
			String result = arg0.result;
			Log.e("login", result);
			try {
				JSONObject object = new JSONObject(result);
				String code = object.getString("code");
				if(code.equals("200")){
					showShortToast("登录成功");
					JSONObject userObject = object.getJSONObject("data");
					BaseApplication.user.setHead_url(userObject.getString("avatar"));
					finish();
				}else if(result.equals("0")){
					showShortToast("登录失败");
				}else{
					showShortToast("登录失败,请填入正确的用户名或密码");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e("tag", e.getMessage());
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}
}
