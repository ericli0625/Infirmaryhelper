package com.example.infirmaryhelper;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ActivityFavor extends Activity {

	private DBHelper DH;
	private Cursor myCursor;

	private String name, address, telephone, category;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favor);

		ListView myListViewFavor = (ListView) findViewById(R.id.myListViewFavor);

		DH = new DBHelper(this);

		myCursor = DH.select();

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
				android.R.layout.simple_expandable_list_item_1, myCursor,
				new String[] { DH.FIELD_Name, DH.FIELD_Category,
						DH.FIELD_Address, DH.FIELD_Telephone }, new int[] {
						R.id.listTextNameFavor, R.id.listTextCategoryFavor,
						R.id.listTextAddressFavor, R.id.listTextPhoneFavor }, 0);

		myListViewFavor.setAdapter(adapter);

		myListViewFavor
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						myCursor.moveToPosition(arg2);
						
						
						
					}

				});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_favor, menu);

		return true;
	}

}
