package com.example.infirmaryhelper;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.google.android.maps.MapActivity;

public class ActivityMenu extends Activity {

	private TextView myTextView1;
	private TextView myTextView2;
	private TextView myTextView3;
	private int id;
	private String name,address,telephone;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);
		
		myTextView1 = (TextView) findViewById(R.id.listTextView3);
		myTextView2 = (TextView) findViewById(R.id.listTextView4);
		myTextView3 = (TextView) findViewById(R.id.listTextView5);
		
		Bundle bundle = this.getIntent().getExtras();
		
		id = bundle.getInt("id");
		name = bundle.getString("name");
		address = bundle.getString("address");
		telephone = bundle.getString("telephone");;
		
//		String[] myStrings = new String [] {name, address, telephone};
		
		myTextView1.setText("名稱:"+name);
		myTextView2.setText("地址:"+address);
		myTextView3.setText("電話:"+telephone);
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_menu, menu);
		return true;
	}

}
