package com.example.infirmaryhelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.CheckedTextView;

public class ActivityMenu extends Activity {

	private CheckedTextView myCheckedTextView1;
	private CheckedTextView myCheckedTextView2;
	private CheckedTextView myCheckedTextView3;
	private int id;
	private String name,address,telephone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		myCheckedTextView1 = (CheckedTextView) findViewById(R.id.listTextView3);
		myCheckedTextView2 = (CheckedTextView) findViewById(R.id.listTextView4);
		myCheckedTextView3 = (CheckedTextView) findViewById(R.id.listTextView5);
		
		Bundle bundle = this.getIntent().getExtras();
		
		id = bundle.getInt("id");
		name = bundle.getString("name");
		address = bundle.getString("address");
		telephone = bundle.getString("telephone");;
		
		myCheckedTextView1.setText("名稱:"+name);
		myCheckedTextView2.setText("地址:"+address);
		myCheckedTextView3.setText("電話:"+telephone);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_menu, menu);
		return true;
	}

}
