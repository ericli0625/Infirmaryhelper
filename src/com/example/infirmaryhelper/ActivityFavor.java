package com.example.infirmaryhelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.AdapterView;
import android.widget.ListView;

public class ActivityFavor extends Activity {

	private DBHelper DH;
	private Cursor myCursor;

	SimpleCursorAdapter adapter;

	ListView myListViewFavor;
	private Context context;

	private int _id;
	private String name, address, telephone, category;

	private static final int DELETE_ID = 0;
	private static final int CAN_DELETE_ID = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favor);

		context = this;

		myListViewFavor = (ListView) findViewById(R.id.myListViewFavor);

		DH = new DBHelper(this);
		myCursor = DH.select();

		adapter = new SimpleCursorAdapter(context,
				R.layout.activity_list_favor, myCursor, new String[] {
						DH.FIELD_Name, DH.FIELD_Category, DH.FIELD_Address,
						DH.FIELD_Telephone }, new int[] {
						R.id.listTextNameFavor, R.id.listTextCategoryFavor,
						R.id.listTextAddressFavor, R.id.listTextPhoneFavor }, 0);

		myListViewFavor.setAdapter(adapter);

		registerForContextMenu(myListViewFavor);

		myListViewFavor
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						myCursor.moveToPosition(arg2);

						name = myCursor.getString(1);
						category = myCursor.getString(2);
						address = myCursor.getString(3);
						telephone = myCursor.getString(4);

						Intent intent = new Intent();
						intent.setClass(ActivityFavor.this,
								ActivityMenuFavor.class);

						Bundle bundle = new Bundle();

						bundle.putString("name", name);
						bundle.putString("address", address);
						bundle.putString("telephone", telephone);
						bundle.putString("category", category);

						// 把bundle物件指派給Intent
						intent.putExtras(bundle);

						// Activity (ActivityMenu)
						startActivity(intent);

					}

				});

		myListViewFavor
				.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {
					@Override
					public void onCreateContextMenu(ContextMenu menu, View v,
							ContextMenu.ContextMenuInfo menuInfo) {
						menu.setHeaderTitle("確定刪除此項目");
						menu.add(0, DELETE_ID, 0, "確定");
						menu.add(0, CAN_DELETE_ID, 0, "取消");
					}
				});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_favor, menu);
		return true;
	}

	public boolean onContextItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case DELETE_ID:
			AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item
					.getMenuInfo();
			deleteFavor(menuInfo.position);
			break;

		case CAN_DELETE_ID:
			break;

		default:
			return super.onContextItemSelected(item);
		}
		return true;
	}

	private void deleteFavor(int id) {
		myCursor.moveToPosition(id);

		_id = myCursor.getInt(0);
		DH.delete(_id);

		Cursor myCursor_temp = DH.select();

		adapter = new SimpleCursorAdapter(this, R.layout.activity_list_favor,
				myCursor_temp, new String[] { DH.FIELD_Name, DH.FIELD_Category,
						DH.FIELD_Address, DH.FIELD_Telephone }, new int[] {
						R.id.listTextNameFavor, R.id.listTextCategoryFavor,
						R.id.listTextAddressFavor, R.id.listTextPhoneFavor }, 0);

		adapter.notifyDataSetChanged();
		myListViewFavor.setAdapter(adapter);

		_id = 0;
	}

}
