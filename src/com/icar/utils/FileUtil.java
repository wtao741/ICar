package com.icar.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;


/**
 * �ļ����?����
 * 
 * @author mitbbs
 * 
 */
public class FileUtil {

	/**
	 * 从外部文件中获取图片
	 * @param filePath		读取的图片的路径
	 * @param opts		对读取图片进行的加工
	 * @return
	 */
	public static Bitmap getBitmapFromFile(String path,BitmapFactory.Options opts) {
		//BitmapFactory.Options 类,  允许我们定义图片以何种方式如何读到内存
		Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
		return bitmap;

	}

	public static Bitmap getBigBitmapFromFile(String path,
			BitmapFactory.Options opts) {
		Bitmap bitmap = BitmapFactory.decodeFile(path, opts);
		return bitmap;

	}

//	public static int[] getScalValueFrom(String url) {
//		float width_Proportion = (float) (WeClubApplication.getInstance()
//				.getScreenWidth() / 320.0);
//		float height_Proportion = (float) (WeClubApplication.getInstance()
//				.getScreenHeight() / 480.0);
//		BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inJustDecodeBounds = true;
//		// ��ȡ���ͼƬ�Ŀ�͸�
//		BitmapFactory.decodeFile(url, options); // ��ʱ����bmΪ��
//		options.inJustDecodeBounds = false;
//		int[] scals = new int[2];
//		int width = options.outWidth;
//		int height = options.outHeight;
//		float scale = ((float) width) / (float) height;
//		if (scale > 1.1) {
//			scals[0] = (int) (230 * width_Proportion);
//			scals[1] = (int) (170 * height_Proportion);
//		} else if (scale <= 1.1 && scale >= 0.9) {
//			scals[0] = (int) (200 *
//
//			width_Proportion);
//			scals[1] = (int) (200 * height_Proportion);
//		} else {
//			scals[0] = (int) (170 * width_Proportion);
//			scals[1] = (int) (230 * height_Proportion);
//		}
//		return scals;
//	}
//
//	public static int[] getScalValue(String url) {
//
//		float width_Proportion = (float) (WeClubApplication.getInstance()
//				.getScreenWidth() / 320.0);
//		float height_Proportion = (float) (WeClubApplication.getInstance()
//				.getScreenHeight() / 480.0);
//		BitmapFactory.Options options = new BitmapFactory.Options();
//		options.inJustDecodeBounds = true;
//		// ��ȡ���ͼƬ�Ŀ�͸�
//		BitmapFactory.decodeFile(url, options); // ��ʱ����bmΪ��
//		options.inJustDecodeBounds = false;
//		int[] scals = new int[2];
//		int width = options.outWidth;
//		int height = options.outHeight;
//		float scale = ((float) width) / (float) height;
//		if (scale > 1.1) {
//			scals[0] = (int) (110 * width_Proportion);
//			scals[1] = (int) (90 * height_Proportion);
//		} else if (scale <= 1.1 && scale >= 0.9) {
//			scals[0] = (int) (90 *
//
//			width_Proportion);
//			scals[1] = (int) (90 * height_Proportion);
//		} else {
//			scals[0] = (int) (90 * width_Proportion);
//			scals[1] = (int) (110 * height_Proportion);
//		}
//		return scals;
//	}

	public static Bitmap getBitmapFromFile(String url, int width, int height) {
		File dst = new File(url);
		if (null != dst && dst.exists()) {
			BitmapFactory.Options opts = null;
			if (width > 0 && height > 0) {
				opts = new BitmapFactory.Options();
				opts.inJustDecodeBounds = true;
				BitmapFactory.decodeFile(dst.getPath(), opts);
				// ����ͼƬ���ű���
				final int minSideLength = Math.min(width, height);
				opts.inSampleSize = computeSampleSize(opts, minSideLength,
						width * height);
				opts.inJustDecodeBounds = false;
				opts.inInputShareable = true;
				opts.inPurgeable = true;
			}
			try {
				return BitmapFactory.decodeFile(dst.getPath(), opts);
			} catch (OutOfMemoryError e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static int computeSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		int initialSize = computeInitialSampleSize(options, minSideLength,
				maxNumOfPixels);

		int roundedSize;
		if (initialSize <= 8) {
			roundedSize = 1;
			while (roundedSize < initialSize) {
				roundedSize <<= 1;
			}
		} else {
			roundedSize = (initialSize + 7) / 8 * 8;
		}

		return roundedSize;
	}

	private static int computeInitialSampleSize(BitmapFactory.Options options,
			int minSideLength, int maxNumOfPixels) {
		double w = options.outWidth;
		double h = options.outHeight;

		int lowerBound = (maxNumOfPixels == -1) ? 1 : (int) Math.ceil(Math
				.sqrt(w * h / maxNumOfPixels));
		int upperBound = (minSideLength == -1) ? 128 : (int) Math.min(
				Math.floor(w / minSideLength), Math.floor(h / minSideLength));

		if (upperBound < lowerBound) {
			// return the larger one when there is no overlapping zone.
			return lowerBound;
		}

		if ((maxNumOfPixels == -1) && (minSideLength == -1)) {
			return 1;
		} else if (minSideLength == -1) {
			return lowerBound;
		} else {
			return upperBound;
		}
	}

	/**
	 * ��Bitmapд���ļ�
	 * 
	 * @param path
	 * @param bitmap
	 */
	public static void writeBitmapToFile(String path, Bitmap bitmap) {
		try {
			FileOutputStream file_out = new FileOutputStream(path);
			bitmap.compress(CompressFormat.PNG, 100, file_out);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���ļ��ж�ȡ�ֽ���
	 * 
	 * @param path
	 * @return
	 */
	public static byte[] getBytesFromFile(String path) {
		byte[] bytes = null;
		BufferedInputStream bis = null;
		try {
			bis = new BufferedInputStream(new FileInputStream(path));
			bytes = new byte[bis.available()];
			bis.read(bytes, 0, bytes.length);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bis.close();
			} catch (Exception e) {
			}
		}
		return bytes;
	}

	/**
	 * ��bytesд���ļ�
	 * 
	 * @param bytes
	 * @return
	 */
	public static void writeBytesToFile(String path, byte[] bytes) {
		BufferedOutputStream bos = null;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(path));
			bos.write(bytes, 0, bytes.length);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (Exception e) {
			}
		}
	}

}
