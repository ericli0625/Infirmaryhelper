package com.example.infirmaryhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private final static String DATABASE_NAME = "todo.db";
	private final static int DATABASE_VERSION = 1;
	private final static String TABLE_NAME = "taoyuan";
	private final static String FIELD_Id = "_id";
	public final static String FIELD_Name = "name";
	private final static String FIELD_Cities = "cities";
	private final static String FIELD_City = "city";
	private final static String FIELD_Category = "category";
	public final static String FIELD_Address = "address";
	private final static String FIELD_Telephone = "telephone";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// onCreate 指的是如果 Android 載入時找不到生成的資料庫檔案，就會觸發 onCreate
	@Override
	public void onCreate(SQLiteDatabase db) {

		String sql = "CREATE TABLE " + TABLE_NAME + " ( " + FIELD_Id
				+ " INTEGER primary key autoincrement, " + " " + FIELD_Name
				+ " text," + FIELD_Cities + " text," + FIELD_City + " text,"
				+ FIELD_Category + " text," + FIELD_Address + " text,"
				+ FIELD_Telephone + " text)";
		db.execSQL(sql);
	}

	// onUpgrade 則是如果資料庫結構有改變了就會觸發 onUpgrade
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

	public Cursor select() {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db
				.query(TABLE_NAME, null, null, null, null, null, null);
		return cursor;
	}

	// 取得一筆紀錄
	/*
	 * rawQuery()方法的第一个参数为select语句；第二个参数为select语句中占位符参数的值，如果select语句没有使用占位符，
	 * 该参数可以设置为null。带占位符参数的select语句使用例子如下：
	 */
	public Cursor getData(String citesTag, String cityTag, String categoryTag) {
		SQLiteDatabase db = this.getReadableDatabase();

		String sql = "select * from " + TABLE_NAME + " where " + FIELD_Cities
				+ " like ? and " + FIELD_City + " like ? and " + FIELD_Category
				+ " like ?";

		Cursor cursor = db.rawQuery(sql, new String[] { citesTag, cityTag,
				categoryTag });

		// 注意：不寫會出錯
		if (cursor != null) {
			cursor.moveToFirst(); // 將指標移到第一筆資料
		}
		return cursor;
	}

	public Cursor getData(String cityTag, String categoryTag) {
		SQLiteDatabase db = this.getReadableDatabase();

		String sql = "select * from " + TABLE_NAME + " where " + FIELD_City
				+ " like '"+cityTag+"' and "+FIELD_Category+" like '"+categoryTag+"' ";

		Cursor cursor = db.rawQuery(sql, null);

		// 注意：不寫會出錯
		if (cursor != null) {
			cursor.moveToFirst(); // 將指標移到第一筆資料
		}
		return cursor;
	}

}
