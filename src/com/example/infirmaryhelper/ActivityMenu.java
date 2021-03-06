package com.example.infirmaryhelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityMenu extends Activity implements LocationListener {

	private TextView myTextView1;
	private TextView myTextView2;
	private TextView myTextView3;
	private Button myButton1, myButton2;
	private ImageButton myButton3;
	private String name, address, telephone, category;
	
	Intent myintent;
	Bundle bundleSend;

	private DBHelper DH = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		DH = new DBHelper(this);

		myTextView1 = (TextView) findViewById(R.id.listTextView3);
		myTextView2 = (TextView) findViewById(R.id.listTextView4);
		myTextView3 = (TextView) findViewById(R.id.listTextView5);
		myButton1 = (Button) findViewById(R.id.displaymapButton);
		myButton2 = (Button) findViewById(R.id.callButton);
		myButton3 = (ImageButton) findViewById(R.id.favorButton);

		Bundle bundle = this.getIntent().getExtras();

		name = bundle.getString("name");
		address = bundle.getString("address");
		telephone = bundle.getString("telephone");
		category = bundle.getString("category");

		myTextView1.setText("名稱:" + name);
		myTextView2.setText("地址:" + address);
		myTextView3.setText("電話:" + telephone);

		myButton1.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				String mapUri = "geo:0,0?q=" + name + "," + address + "";
				myintent = new Intent(Intent.ACTION_VIEW, Uri.parse(mapUri));
				startActivity(myintent);
			}
		});

		myButton2.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				openOptionsDialogCall();
			}
		});

		myButton3.setOnClickListener(new ImageButton.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				Cursor cursor = DH.matchData(name, category, address, telephone);

				int rows_num= cursor.getCount();
				
				if (rows_num==1) {
					Toast.makeText(v.getContext(), "您已經新增過了", Toast.LENGTH_LONG)
							.show();
				} else {
					DH.insert(name, category, address, telephone);
					Toast.makeText(v.getContext(), "新增至我的最愛", Toast.LENGTH_LONG)
							.show();
				}

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
		case R.id.item_favor:
			Intent intent = new Intent();
			intent.setClass(ActivityMenu.this, ActivityFavor.class);
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void openOptionsDialogCall() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.app_call)
				.setMessage(R.string.app_call_msg)
				.setPositiveButton(R.string.str_ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Intent call = new Intent(
										"android.intent.action.CALL", Uri
												.parse("tel:" + telephone));
								startActivity(call);
							}
						})
				.setNegativeButton(R.string.str_no,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						}).show();

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
