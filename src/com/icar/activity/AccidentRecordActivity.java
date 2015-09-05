package com.icar.activity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;

import com.easemob.util.EMLog;
import com.easemob.util.PathUtil;
import com.icar.adapter.AccidentAdapter;
import com.icar.base.AbstractTitleActivity;
import com.icar.base.HeadClick;
import com.icar.bean.ImageBean;
import com.icar.datatimewheel.NumericWheelAdapter;
import com.icar.datatimewheel.OnWheelScrollListener;
import com.icar.datatimewheel.WheelView;
import com.icar.view.HorizontalListView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

public class AccidentRecordActivity extends AbstractTitleActivity implements
		HeadClick {

	@ViewInject(R.id.date_select)
	ImageView iv_date; // 选择日期
	@ViewInject(R.id.date)
	TextView tv_date; // 选择日期
	@ViewInject(R.id.address_location)
	ImageView iv_location; // 定位
	@ViewInject(R.id.address)
	EditText et_address; // 地址
	@ViewInject(R.id.parent)
	View view; // 父类布局
	@ViewInject(R.id.people1)
	EditText et_people1;
	@ViewInject(R.id.people2)
	EditText et_people2;
	@ViewInject(R.id.people3)
	EditText et_people3;
	@ViewInject(R.id.mobile1)
	EditText et_mobile1;
	@ViewInject(R.id.mobile2)
	EditText et_mobile2;
	@ViewInject(R.id.mobile3)
	EditText et_mobile3;
	@ViewInject(R.id.listView)
	HorizontalListView listView;

	@OnClick(R.id.date_select)
	public void dateSelectonClick(View v) {
		showPopwindow(getDataPick());
	}

	@OnClick(R.id.des)
	public void desonClick(View v) {
		openActivity(AccidentDesActivity.class);
	}

	private PopupWindow menuWindow;
	private LayoutInflater inflater = null;
	private WheelView year;
	private WheelView month;
	private WheelView day;
	private WheelView hour;
	private WheelView mins;

	private PopupWindow selectPicPopupWindow = null;

	private List<ImageBean> icons = new ArrayList<ImageBean>(); // 上传图片数据
	private AccidentAdapter adapter;

	private static final int PIC = 1; // 选择图片
	private static final int PHO = 2; // 照相
	private static final int TV = 3; // 视屏
	private String picPath; // 获取到的图片路径

	private ContentResolver cr;
	private Uri photoUri;
	private Bitmap photo;// 选择好的图片的bitmap形式
	private AccidentAdapter adpater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);
		setContentView(R.layout.activity_accident_record);
		setTitle("事故记录");
		isShowLeftView(true);
		isShowRightView(R.string.null_tips, false);
		setHeadClick(this);
		ViewUtils.inject(this);

		adapter = new AccidentAdapter(this, icons);
		listView.setAdapter(adapter);
	}

	/**
	 * 加载发图的popupwindow,适配器中调用
	 */
	public void initSelectPicPopupWindow() {

		cr = this.getContentResolver();

		View v = LayoutInflater.from(this).inflate(
				R.layout.zmit_popupwindow_pick_pic, null);
		Button cancelBtn = (Button) v.findViewById(R.id.cancel);
		Button photoBtn = (Button) v.findViewById(R.id.photo);
		Button picBtn = (Button) v.findViewById(R.id.pic);
		Button tvBtn = (Button) v.findViewById(R.id.tv);

		selectPicPopupWindow = new PopupWindow(v, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);
		selectPicPopupWindow.showAtLocation(findViewById(R.id.parent),
				Gravity.CENTER, 0, 0);
		selectPicPopupWindow.update();

		photoBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				takePhoto();
				dismissPopupWindow();
			}
		});

		picBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				pickPhoto();
				dismissPopupWindow();
			}
		});

		tvBtn.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(AccidentRecordActivity.this,
						ImageGridActivity.class);
				startActivityForResult(intent, TV);
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

	private void pickTv() {
		// TODO Auto-generated method stub

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

	public void pickPhoto() {
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, PIC);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.e("Tag", requestCode+"  "+resultCode+"  "+data);
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode != TV){
				doPhoto(requestCode, data);
			}
			else {
				Log.e("Tag", "tv");
				int duration = data.getIntExtra("dur", 0);
				String videoPath = data.getStringExtra("path");
				 Log.e("Tag", videoPath);
				File file = new File(PathUtil.getInstance().getImagePath(),
						"thvideo" + System.currentTimeMillis());
				 Log.e("Tag", file.getAbsolutePath());
				Bitmap bitmap = null;
				FileOutputStream fos = null;
				try {
					if (!file.getParentFile().exists()) {
						file.getParentFile().mkdirs();
					}
					bitmap = ThumbnailUtils.createVideoThumbnail(videoPath, 3);
					if (bitmap == null) {
						EMLog.d("chatactivity",
								"problem load video thumbnail bitmap,use default icon");
						bitmap = BitmapFactory.decodeResource(getResources(),
								R.drawable.app_panel_video_icon);
					}
					fos = new FileOutputStream(file);

					bitmap.compress(CompressFormat.JPEG, 100, fos);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (fos != null) {
						try {
							fos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						fos = null;
					}
					if (bitmap != null) {
						bitmap.recycle();
						bitmap = null;
					}

				}
				  ImageBean bean = new ImageBean();
                  bean.setPicPath(videoPath);
                  bean.setType("1");
                  //bean.setImgBitmap(bitmap);
                  icons.add(0,bean);
                  adapter.notifyDataSetChanged();
				// sendVideo(videoPath, file.getAbsolutePath(), duration /
				// 1000);
                
			}

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void doPhoto(int requestCode, Intent data) {
		String path;
		if (requestCode == PIC){ // 从相册取图片，有些手机有异常情况，请注意
		
			if (data == null) {
				Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
				return;
			}
			photoUri = data.getData();
			if (photoUri == null) {
				Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
				return;
			}
		}
		String[] pojo = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(photoUri, pojo, null, null, null);
		if (cursor != null) {
			int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
			cursor.moveToFirst();
			picPath = cursor.getString(columnIndex);
			try {
				// 4.0以上的版本会自动关闭 (4.0--14;; 4.0.3--15)
				if (Integer.parseInt(Build.VERSION.SDK) < 14) {
					cursor.close();
				}
			} catch (Exception e) {
				// Log.error(, "error:" + e);
			}
		}
		if (picPath != null
				&& (picPath.endsWith(".png") || picPath.endsWith(".PNG")
						|| picPath.endsWith(".jpg") || picPath.endsWith(".JPG")
						|| picPath.endsWith(".jpeg") || picPath
							.endsWith(".JPEG"))) {
			ImageBean imageBean = new ImageBean();
			File f = new File(picPath);
			long picSize = f.length();
			long picMinSize = 1 * 1024 * 1024 * 2;
			Log.e("tag", picPath);
			if (picSize > picMinSize) {
				imageBean = compressPic(picPath, picMinSize);
			} else {
				imageBean.setPicPath(picPath);
				try {
					photo = BitmapFactory.decodeStream(cr
							.openInputStream(photoUri));
					if (photo != null) {

						imageBean.setImgBitmap(photo);

					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			imageBean.setType("0");
			icons.add(0,imageBean);
			adapter.notifyDataSetChanged();
		} else {
			Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_LONG).show();
		}
	}

	/**
	 * 压缩图片（先按比例压缩大小，在按10%衰减压缩质量）
	 * 
	 * @param picPath
	 *            返回图片在手机中的地址
	 * @return
	 */
	private ImageBean compressPic(String picPath, long picMinSize) {

		ImageBean imageBean = new ImageBean();
		String newPicPath = Environment.getExternalStorageDirectory()
				+ "/mitbbsclub/except_chat/img/thumbnail" + "/" + "tmp_pic_"
				+ SystemClock.currentThreadTimeMillis() + ".jpeg";
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		Bitmap bitmap = BitmapFactory.decodeFile(picPath, options); // 此时返回bm为空
		options.inJustDecodeBounds = false;
		// 缩放比 
		//700
//		int be = (int) (options.outWidth / 500f);
		
		int be = (int) (options.outWidth);
		
		if (be <= 0) {
			be = 1;
		}
//		options.inSampleSize = be;
		options.inPreferredConfig = Config.RGB_565;// 降低图片从ARGB888到RGB565
		// 重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
		bitmap = BitmapFactory.decodeFile(picPath, options);
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		// Toast.makeText(PostingActivity.this, w+","+h,
		// Toast.LENGTH_LONG).show();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
		int quality = 100;
		while (baos.toByteArray().length > picMinSize) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			quality -= 10;// 每次都减少10
			bitmap.compress(Bitmap.CompressFormat.JPEG, quality, baos);// 这里压缩options%，把压缩后的数据存放到baos中
		}
		File file = new File(newPicPath);
		try {
			FileOutputStream out = new FileOutputStream(file);
			baos.writeTo(out);
			baos.flush();
			baos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.e("压缩后的图片大小", file.length() + "");

		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap newBitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		imageBean.setPicPath(newPicPath);
		imageBean.setImgBitmap(newBitmap);
		return imageBean;
		// return newPicPath;
	}

	@Override
	public void left() {
	}

	@Override
	public void right() {
		openActivity(AddicentHistoryActivity.class);
	}

	/**
	 * 初始化popupWindow
	 * 
	 * @param view
	 */
	private void showPopwindow(View view) {
		menuWindow = new PopupWindow(view, LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT);
		menuWindow.setFocusable(true);
		menuWindow.setBackgroundDrawable(new BitmapDrawable());
		menuWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL
				| Gravity.BOTTOM, 0, 0);
		menuWindow.setOnDismissListener(new OnDismissListener() {
			@Override
			public void onDismiss() {
				menuWindow = null;
			}
		});
	}

	/**
	 * 
	 * @return
	 */
	private View getDataPick() {
		Calendar c = Calendar.getInstance();
		int curYear = c.get(Calendar.YEAR);
		int curMonth = c.get(Calendar.MONTH) + 1;// 通过Calendar算出的月数要+1
		int curDate = c.get(Calendar.DATE);
		int currentHour = c.get(Calendar.HOUR);
		int currentMins = c.get(Calendar.MINUTE);

		final View view = inflater.inflate(R.layout.timepick, null);

		year = (WheelView) view.findViewById(R.id.year);
		year.setAdapter(new NumericWheelAdapter(1980, curYear + 10));
		year.setLabel("年");
		year.setCyclic(true);
		year.addScrollingListener(scrollListener);

		month = (WheelView) view.findViewById(R.id.month);
		month.setAdapter(new NumericWheelAdapter(1, 12));
		month.setLabel("月");
		month.setCyclic(true);
		month.addScrollingListener(scrollListener);

		day = (WheelView) view.findViewById(R.id.day);
		initDay(curYear, curMonth);
		day.setLabel("日");
		day.setCyclic(true);

		hour = (WheelView) view.findViewById(R.id.hour);
		hour.setAdapter(new NumericWheelAdapter(0, 23));
		hour.setLabel("时");
		hour.setCyclic(true);

		mins = (WheelView) view.findViewById(R.id.mins);
		mins.setAdapter(new NumericWheelAdapter(0, 59));
		mins.setLabel("分");
		mins.setCyclic(true);

		year.setCurrentItem(curYear - 1980);
		month.setCurrentItem(curMonth - 1);
		day.setCurrentItem(curDate - 1);
		hour.setCurrentItem(currentHour);
		mins.setCurrentItem(currentMins);

		Button bt = (Button) view.findViewById(R.id.set);
		bt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String str = (year.getCurrentItem() + 1980) + "-"
						+ (month.getCurrentItem() + 1) + "-"
						+ (day.getCurrentItem() + 1) + "    "
						+ (hour.getCurrentItem()) + ":"
						+ (mins.getCurrentItem());
				tv_date.setText(str);
				menuWindow.dismiss();
			}
		});
		Button cancel = (Button) view.findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				menuWindow.dismiss();
			}
		});
		return view;
	}

	OnWheelScrollListener scrollListener = new OnWheelScrollListener() {

		@Override
		public void onScrollingStarted(WheelView wheel) {

		}

		@Override
		public void onScrollingFinished(WheelView wheel) {
			// TODO Auto-generated method stub
			int n_year = year.getCurrentItem() + 1950;// 楠烇拷
			int n_month = month.getCurrentItem() + 1;// 閺堬拷
			initDay(n_year, n_month);
		}
	};

	/**
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	private int getDay(int year, int month) {
		int day = 30;
		boolean flag = false;
		switch (year % 4) {
		case 0:
			flag = true;
			break;
		default:
			flag = false;
			break;
		}
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			day = 31;
			break;
		case 2:
			day = flag ? 29 : 28;
			break;
		default:
			day = 30;
			break;
		}
		return day;
	}

	/**
	 */
	private void initDay(int arg1, int arg2) {
		day.setAdapter(new NumericWheelAdapter(1, getDay(arg1, arg2), "%02d"));
	}
}
