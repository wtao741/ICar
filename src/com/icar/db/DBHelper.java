package com.icar.db;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.icar.bean.City;
import com.icar.utils.AssetsDatabaseManager;

public class DBHelper {
	public static final String TABLE_NAME = "users";
	Context context;
	DBUtil myDb ;
	// 获取管理对象，因为数据库需要通过管理对象才能够获取  
	AssetsDatabaseManager mg ;  
	// 通过管理对象获取数据库 
	public DBHelper(Context context) {
		this.context = context;
		mg = AssetsDatabaseManager.getManager();  
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
			db =  mg.getDatabase("city1.db");
			list = new ArrayList<City>();
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
//			if (db != null)
//				db.close();
		}
		return list;
	}

	public City getCityByName(String name) {
		SQLiteDatabase db = null;
		City city = null;
		Cursor cursor = null;
		try {
			db =  mg.getDatabase("city1.db");
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
//			if (db != null)
//				db.close();
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
