package com.icar.db;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.icar.bean.City;

public class DBHelper extends SQLiteOpenHelper {
	public static final String TABLE_NAME = "users";
	Context context;

	public DBHelper(Context context) {
		//super(context, "city1.db", null, 1);
		super(context, "city1.db", null, 1);
		this.context = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		Log.e("TGA", "建表开始");
//		StringBuilder builder = new StringBuilder();
//		AssetManager assets = context.getAssets();
//		try {
//			InputStream open = assets.open("city1.db");
//			BufferedReader reader = new BufferedReader(new InputStreamReader(
//					open));
//			String line;
//			while ((line = reader.readLine()) != null) {
//				builder.append(line + "\n");
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		} finally {
//			assets.close();
//		}
//		Log.e("TGA", builder.toString());
//		db.execSQL(builder.toString());
//		Log.e("TGA", "建表结束");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

	/*
	 * "Id" integer NOT NULL, "name" text, "pId" integer, "lat" text, "lon"
	 * text, "letter" text,
	 */
	public ArrayList<City> getAll() {
		SQLiteDatabase db = null;
		ArrayList<City> list;
		Cursor cursor = null;
		try {
			list = new ArrayList<City>();
			db = getReadableDatabase();
			String[] columns = { "Id", "name", "pId", "lat", "lon", "letter" };
			cursor = db.query(true, "jiche_city", null, null, null, null, null,
					null, null);
			while (cursor.moveToNext()) {
				String id = cursor.getInt(0) + "";
				String name = cursor.getString(1);
				int pId = cursor.getInt(2);
				String lat = cursor.getString(3);
				String lon = cursor.getString(4);
				String letter = cursor.getString(5);
				list.add(new City(id, name, pId, lat, lon, letter));
			}
		} finally {
			if (cursor != null)
				cursor.close();
			if (db != null)
				db.close();
		}
		return list;
	}

	public City getCityByName(String name) {
		City city = null;
		SQLiteDatabase db = null;
		Cursor cursor = null;
		try {
			db = getReadableDatabase();
			String[] columns = { "Id", "name", "pId", "lat", "lon", "letter" };
			cursor = db.query("jiche_city", null, "name = ?",
					new String[] { name }, null, null, null);
			if (cursor.moveToNext()) {
				String id = cursor.getInt(0) + "";
				int pId = cursor.getInt(2);
				String lat = cursor.getString(3);
				String lon = cursor.getString(4);
				String letter = cursor.getString(5);
				city = new City(id, name, pId, lat, lon, letter);
			}
		} finally {
			if (cursor != null)
				cursor.close();
			if (cursor != null)
				db.close();
		}
		return city;
	}

	/**
	 * Cursor query(boolean distinct, String table, String[] columns, String
	 * selection, String[] selectionArgs, String groupBy, String having, String
	 * orderBy, String limit) 参数: distinct:是否限制为不重复列,true列不可重复 table:表名
	 * columns:列名称数组 selection:条件字句,相当于where,带占位符 selectionArgs:填充占位符
	 * groupBy:分组列 having:分组条件 orderBy;排列序 limit:分页查询限制 返回:Cursor 结果集
	 */
}
