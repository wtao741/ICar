package com.icar.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

public class DBUtil {
	public static void copyDatabase(Context context, AssetManager assets,
			String fileName) {
		try {
			InputStream is = assets.open(fileName);
			File file = context.getDatabasePath(fileName);
			if (file.exists()) {
				return;
			}
			file.getParentFile().mkdirs();
			FileOutputStream fos = new FileOutputStream(file);
			byte[] buffer = new byte[1024];
			int len = -1;
			while ((len = is.read(buffer))!=-1) {
				fos.write(buffer, 0, len);
			}
			fos.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e("TGA", "数据库文件拷贝出错"+fileName);
		}
	}
}
