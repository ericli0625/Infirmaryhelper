package com.example.infirmaryhelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityMenu extends Activity implements LocationListener {

	private TextView myTextView1;
	private TextView myTextView2;
	private TextView myTextView3;
	private Button myButton1;
	private String name, address, telephone;
	Intent myintent;
	Bundle bundleSend;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		myTextView1 = (TextView) findViewById(R.id.listTextView3);
		myTextView2 = (TextView) findViewById(R.id.listTextView4);
		myTextView3 = (TextView) findViewById(R.id.listTextView5);
		myButton1 = (Button) findViewById(R.id.displaymapButton);

		Bundle bundle = this.getIntent().getExtras();

		name = bundle.getString("name");
		address = bundle.getString("address");
		telephone = bundle.getString("telephone");

		myTextView1.setText("名稱:" + name);
		myTextView2.setText("地址:" + address);
		myTextView3.setText("電話:" + telephone);

		myButton1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click

				String mapUri = "geo:0,0?q=" + name + ","+address+"";
				
				myintent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUri));

				startActivity(myintent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case 0:

			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
