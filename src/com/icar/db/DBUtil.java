package com.icar.db;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

public class DBUtil {
	// 数据库存储路径
	String filePath = Environment.getExternalStorageDirectory()
			+ "/icar/except_chat/img/thumbnail/city.db";
	// 数据库存放的文件夹 data/data/com.main.jh 下面
	String pathStr = Environment.getExternalStorageDirectory()
			+ "/icar/except_chat/img/thumbnail/";

	SQLiteDatabase database;

	public DBUtil() {
		super();
		Log.e("Tag", "open");
	}

	public SQLiteDatabase openDatabase(Context context) {
		File jhPath = new File(filePath);
		// 查看数据库文件是否存在
		if (jhPath.exists()) {
			// 存在则直接返回打开的数据库
			return SQLiteDatabase.openOrCreateDatabase(jhPath, null);
		} else {
			// 不存在先创建文件夹
			File path = new File(pathStr);
			if (path.mkdir()) {
				Log.i("test", "创建成功");
			} else {
				Log.i("test", "创建失败");
			}
			try {
				// 得到资源
				AssetManager am = context.getAssets();
				// 得到数据库的输入流
				InputStream is = am.open("city1.db");
				// 用输出流写到SDcard上面
				FileOutputStream fos = new FileOutputStream(jhPath);
				// 创建byte数组 用于1KB写一次
				byte[] buffer = new byte[1024];
				int count = 0;
				while ((count = is.read(buffer)) > 0) {
					fos.write(buffer, 0, count);
				}
				// 最后关闭就可以了
				fos.flush();
				fos.close();
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Log.e("tag", e.getMessage());
				return null;
			}
			// 如果没有这个数据库 我们已经把他写到SD卡上了，然后在执行一次这个方法 就可以返回数据库了
			Log.e("Tag", "create");
			return openDatabase(context);
		}
	}
}
