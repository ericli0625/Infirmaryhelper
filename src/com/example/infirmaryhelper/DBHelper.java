package com.example.infirmaryhelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

	private final static String DATABASE_NAME = "todo.db";
	private final static int DATABASE_VERSION = 1;
	private final static String TABLE_NAME = "todo_table";
	private final static String FIELD_Id = "_id";
	public final static String FIELD_Name = "name";
	public final static String FIELD_Cities = "cities";
	public final static String FIELD_City = "city";
	public final static String FIELD_Category = "category";
	public final static String FIELD_Address = "address";
	public final static String FIELD_Telephone = "telephone";

	private static final String ACTIVITY_TAG = "LogDemo";

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// onCreate 指的是如果 Android 載入時找不到生成的資料庫檔案，就會觸發 onCreate
	@Override
	public void onCreate(SQLiteDatabase db) {

		Log.i(DBHelper.ACTIVITY_TAG, "onCreate");

		String sql = "CREATE TABLE " + TABLE_NAME + " ( " + FIELD_Id
				+ " INTEGER primary key autoincrement, " + " " + FIELD_Name
				+ " text," + FIELD_Cities + " text," + FIELD_City + " text,"
				+ FIELD_Category + " text," + FIELD_Address + " text,"
				+ FIELD_Telephone + " text)";
		/*
		 * String sql = "CREATE TABLE " + TABLE_NAME + " (" + FIELD_Id +
		 * " INTEGER primary key autoincrement, " + " " + FIELD_Name + " text)";
		 */
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

}
