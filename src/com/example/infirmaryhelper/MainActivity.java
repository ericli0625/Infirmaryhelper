package com.example.infirmaryhelper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteCursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class MainActivity extends Activity {

	private String[] cities = new String[] { "基隆市", "台北市", "新北市", "桃園縣", "新竹市",
			"新竹縣", "苗栗縣", "台中市", "彰化縣", "南投縣", "雲林縣", "嘉義市", "嘉義縣", "台南市",
			"高雄市", "屏東縣", "宜蘭縣", "花蓮縣", "台東縣", "澎湖縣", "金門縣", "連江縣" };// 載入第一下拉選單

	private String[] city = new String[] { "松山區", "大安區", "大同區", "中山區", "內湖區",
			"南港區", "士林區", "北投區", "信義區", "中正區", "萬華區", "文山區" };// 起始畫面時預先載入第二下拉選單

	private String[] category = new String[] { "中醫", "牙科", "小兒科", "婦產科", "骨科",
			"整形外科", "泌尿科", "皮膚科", "耳鼻喉科", "復健科" };// 起始畫面時預先載入第三下拉選單

	// 第一下拉選取後載入第二下拉選單
	private String[][] type2 = new String[][] {
			{ "仁愛區", "中正區", "信義區", "中山區", "安樂區", "暖暖區", "七堵區" },
			{ "松山區", "大安區", "大同區", "中山區", "內湖區", "南港區", "士林區", "北投區", "信義區",
					"中正區", "萬華區", "文山區" },
			{ "板橋區", "三重區", "永和區", "中和區", "新莊區", "新店區", "土城區", "蘆洲區", "汐止區",
					"樹林區", "鶯歌區", "三峽區", "淡水區", "瑞芳區", "五股區", "泰山區", "林口區",
					"深坑區", "石碇區", "坪林區", "三芝區", "石門區", "八里區", "平溪區", "雙溪區",
					"貢寮區", "金山區", "萬里區", "烏來區" },
			{ "桃園市", "龜山鄉", "八德市", "蘆竹鄉", "大園鄉", "大溪鎮", "復興鄉", "中壢市", "平鎮市",
					"楊梅鎮", "龍潭鄉", "新屋鄉", "觀音鄉" } };

	private Spinner spinner, spinner2, spinner3;
	private ArrayAdapter<String> adapter;
	private Context context;
	private String str1, str2, str3;

	private ListView myListView;
	private Cursor myCursor;
	private int _id;
	private String telephone, address,name;

	private DBHelper DH = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		DH = new DBHelper(this);

		findControl();
		processSpinner();

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

		spinner = CreateSpinner(spinner, R.id.spinner1, cities);
		spinner2 = CreateSpinner(spinner2, R.id.spinner2, city);
		spinner3 = CreateSpinner(spinner3, R.id.spinner3, category);
	}

	private Spinner CreateSpinner(Spinner spinner, int value, String[] array) {
		// 将可选内容与ArrayAdapter连接起来
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, array);

		// 设置下拉列表的风格
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// 将adapter 添加到spinner中
		spinner.setAdapter(adapter);

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
			// 重新產生新的Adapter，用的是二維陣列type2[pos]
			adapter = new ArrayAdapter<String>(context,
					android.R.layout.simple_spinner_item, type2[pos]);
			// 載入第二個下拉選單Spinner
			spinner2.setAdapter(adapter);
			str1 = parent.getSelectedItem().toString();
			myCursor = DH.getData(str2, str3);
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
			myCursor = DH.getData(str2, str3);
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
			myCursor = DH.getData(str2, str3);
			ShowListView();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};

}
