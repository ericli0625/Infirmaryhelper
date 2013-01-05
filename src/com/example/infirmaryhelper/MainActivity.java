package com.example.infirmaryhelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends Activity {

	private Spinner spinner, spinner2, spinner3;
	private ArrayAdapter<CharSequence> adapter;
	private Context context;
	private String str1, str2, str3,selectedCities;

	private ListView myListView;
	private Cursor myCursor;
	private int _id;
	private String telephone, address, name;

	private DBHelper DH = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DH = new DBHelper(this);

		findControl();
		processSpinner();

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

	private void ShowListView() {

		SimpleCursorAdapter adapterDB = new SimpleCursorAdapter(context,
				R.layout.activity_list, myCursor, new String[] {
						DBHelper.FIELD_Name, DBHelper.FIELD_Address },
				new int[] { R.id.listTextView1, R.id.listTextView2 }, 0);

		myListView.setAdapter(adapterDB);

		myListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						// myCursor移到選取的值
						myCursor.moveToPosition(arg2);
						_id = myCursor.getInt(0);
						address = myCursor.getString(5);
						telephone = myCursor.getString(6);
						name = myCursor.getString(1);

						Intent intent = new Intent();
						intent.setClass(MainActivity.this, ActivityMenu.class);

						Bundle bundle = new Bundle();
						bundle.putInt("id", _id);

						bundle.putString("name", name);
						bundle.putString("address", address);
						bundle.putString("telephone", telephone);

						// 把bundle物件指派給Intent
						intent.putExtras(bundle);

						// Activity (ActivityMenu)
						startActivity(intent);

					}

				});
		myListView
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

						SQLiteCursor sc = (SQLiteCursor) arg0.getSelectedItem();
						_id = sc.getInt(0);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {

					}

				});
	}

	private void findControl() {
		myListView = (ListView) findViewById(R.id.myListView);
		spinner = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner3 = (Spinner) findViewById(R.id.spinner3);
	}

	private void processSpinner() {
		context = this;

		spinner = CreateSpinner(spinner, R.id.spinner1, R.array.cities_init);
		spinner2 = CreateSpinner(spinner2, R.id.spinner2, R.array.city_init);
		spinner3 = CreateSpinner(spinner3, R.id.spinner3, R.array.category_init);
	}

	private Spinner CreateSpinner(Spinner spinner, int value, int array) {
		// 将可选内容与ArrayAdapter连接起来
		ArrayAdapter<CharSequence> adapterTemp = ArrayAdapter.createFromResource(this, array,
				android.R.layout.simple_spinner_item);

		// 设置下拉列表的风格
		adapterTemp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		spinner.setAdapter(adapterTemp);

		switch (value) {
		case R.id.spinner1:
			spinner.setOnItemSelectedListener(selectListener);
			break;
		case R.id.spinner2:
			spinner.setOnItemSelectedListener(selectListener2);
			break;
		case R.id.spinner3:
			spinner.setOnItemSelectedListener(selectListener3);
			break;
		default:
			break;
		}

		return spinner;
	}

	// 第一個下拉類別的監看式
	int pos = 0;
	private OnItemSelectedListener selectListener = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			// 讀取第一個下拉選單是選擇第幾個
			pos = spinner.getSelectedItemPosition();
			// 載入第二個下拉選單Spinner
			selectedCities = choeseCities(pos);
			
			spinner2.setAdapter(adapter);
			str1 = parent.getSelectedItem().toString();
			myCursor = DH.getData(selectedCities,str2, str3,0);
			ShowListView();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};

	private OnItemSelectedListener selectListener2 = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			str2 = parent.getSelectedItem().toString();
			myCursor = DH.getData(selectedCities,str2, str3,0);
			ShowListView();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};

	private OnItemSelectedListener selectListener3 = new OnItemSelectedListener() {
		@Override
		public void onItemSelected(AdapterView<?> parent, View v, int position,
				long id) {
			str3 = parent.getSelectedItem().toString();
			myCursor = DH.getData(selectedCities,str2, str3,0);
			ShowListView();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};

	private String choeseCities(int pos) {

		int value = 0;
		
		switch (pos) {
		case 0:
			value = R.array.keelung_city;
			selectedCities = new String("keelung_city");
			break;
		case 1:
			value = R.array.taipei_city;
//			selectedCities = new String("taipei_city");
			break;
		case 2:
			value = R.array.xinbei_city;
			selectedCities = new String("xinbei_city");
			break;
		case 3:
			value = R.array.taoyuan_county;
			selectedCities = new String("taoyuan_county");
			break;
		case 4:
			value = R.array.hsinchu_city;
			selectedCities = new String("hsinchu_city");
			break;
		case 5:
			value = R.array.hsinchu_county;
			selectedCities = new String("hsinchu_county");
			break;
		case 6:
			value = R.array.miaoli_county;
			selectedCities = new String("miaoli_county");
			break;
		case 7:
			value = R.array.taichung_city;
//			selectedCities = new String("taichung_city");
			break;
		case 8:
			value = R.array.changhua_county;
//			selectedCities = new String("changhua_county");
			break;
		case 9:
			value = R.array.nantou_county;
			selectedCities = new String("nantou_county");
			break;
		case 10:
			value = R.array.yunlin_county;
//			selectedCities = new String("yunlin_county");
			break;
		case 11:
			value = R.array.chiayi_city;
//			selectedCities = new String("chiayi_city");
			break;
		case 12:
			value = R.array.chiayi_county;
//			selectedCities = new String("changhua_county");
			break;
		case 13:
			value = R.array.tainan_city;
//			selectedCities = new String("tainan_city");
			break;
		case 14:
			value = R.array.Kaohsiung_city;
//			selectedCities = new String("Kaohsiung_city");
			break;
		case 15:
			value = R.array.pingtung_county;
//			selectedCities = new String("pingtung_county");
			break;
		case 16:
			value = R.array.yilan_county;
//			selectedCities = new String("yilan_county");
			break;
		case 17:
			value = R.array.hualien_county;
//			selectedCities = new String("hualien_county");
			break;
		case 18:
			value = R.array.taitung_county;
//			selectedCities = new String("taitung_county");
			break;
		case 19:
			value = R.array.penghu_county;
//			selectedCities = new String("penghu_county");
			break;
		case 20:
			value = R.array.kinmen_county;
//			selectedCities = new String("kinmen_county");
			break;
		case 21:
			value = R.array.lianjiang_county;
//			selectedCities = new String("lianjiang_county");
			break;
		default:
			break;
		}

		adapter = ArrayAdapter.createFromResource(this, value,android.R.layout.simple_spinner_item);

		return selectedCities;
	}

}