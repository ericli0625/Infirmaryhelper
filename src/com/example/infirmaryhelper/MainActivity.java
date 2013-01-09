package com.example.infirmaryhelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button myButton, myButtonAdven;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myButton = (Button) findViewById(R.id.buttonSearch);
		myButtonAdven = (Button) findViewById(R.id.buttonSearchAdven);

		myButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ActivitySearch.class);
				startActivity(intent);
			}
		});

		myButtonAdven.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on click
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, ActivitySearchAdven.class);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case R.id.item1:
			openOptionsDialogAbout();
			break;
		case R.id.item2:
			openOptionsDialogEmail();
			break;
		case R.id.item3:
			openOptionsDialogExit();
			break;
		case R.id.item_favor:
			Intent intent = new Intent();
			intent.setClass(MainActivity.this, ActivityFavor.class);
			startActivity(intent);
			return true;
		default:
			break;
		}

		return true;
	}

	private void openOptionsDialogEmail() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.app_email)
				.setMessage(R.string.app_email_msg)
				.setNegativeButton(R.string.str_no_mail,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						})
				.setPositiveButton(R.string.str_ok_mail,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Uri uri = Uri
										.parse("mailto:ericli0625@gmail.com");
								Intent it = new Intent(Intent.ACTION_SENDTO,
										uri);
								startActivity(it);

							}
						}).show();

	}

	private void openOptionsDialogAbout() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.app_about)
				.setMessage(R.string.app_about_msg)
				.setPositiveButton(R.string.str_ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						}).show();

	}

	private void openOptionsDialogExit() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.app_exit)
				.setMessage(R.string.app_exit_msg)
				.setNegativeButton(R.string.str_no,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						})
				.setPositiveButton(R.string.str_ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								finish();
							}
						}).show();

	}

}