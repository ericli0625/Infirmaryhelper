package com.example.infirmaryhelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class ActivitySearch extends Activity {

	private Spinner spinner, spinner2, spinner3;
	private ArrayAdapter<CharSequence> adapter;
	private String str2 = "全部地區", str3 = "牙科", selectedCities;

	private Button myButton;
	private ListView myListView;
	private String telephone, address, name, category;

	public ProgressDialog myDialog = null;

	// http
	private String result = new String();
	private SimpleAdapter adapterHTTP;
	protected List<Infirmary> Infirmarys;
	private Infirmary Infirmary;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		myButton = (Button) findViewById(R.id.button1);
		myListView = (ListView) findViewById(R.id.myListView);
		spinner = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		spinner3 = (Spinner) findViewById(R.id.spinner3);

		// 判斷是否有上網
		if (AppStatus.getInstance(this).isOnline(this)) {

			processSpinner();
			visitExternalLinks();

			myButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// Perform action on click

					String sql;
					String strText = new String("全部地區");
					if (str2.equals(strText)) {
						sql = "SELECT * FROM " + selectedCities
								+ " where category like '" + str3 + "'";
						result = DBConnector.executeQuery(sql);
					} else {
						sql = "SELECT * FROM " + selectedCities
								+ " where city like '" + str2
								+ "' and category like '" + str3 + "'";
						result = DBConnector.executeQuery(sql);
					}
					ShowListView(result);

				}
			});

		} else {
			openOptionsDialogIsNetworkAvailable();
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search, menu);
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
			intent.setClass(ActivitySearch.this, ActivityFavor.class);
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

	private void openOptionsDialogIsNetworkAvailable() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.app_connect)
				.setMessage(R.string.app_connect_msg)
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

	private void openOptionsDialogIsNoneResult() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.app_none_result)
				.setMessage(R.string.app_none_result_msg)
				.setPositiveButton(R.string.str_ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub

							}
						}).show();

	}

	private void ShowListView(String result) {

		// 判斷是否有搜尋到診所
		if (result.length() > 5) {
			Infirmarys = JsonToList(result);
			setInAdapter();
			Toast.makeText(this, "搜尋完成", Toast.LENGTH_LONG).show();
		} else {
			List<Map<String, String>> lists = new ArrayList<Map<String, String>>();
			String[] from = { "name", "address" };
			int[] to = { R.id.listTextView1, R.id.listTextView2 };
			adapterHTTP = new SimpleAdapter(this, lists,
					R.layout.activity_list, from, to);
			adapterHTTP.notifyDataSetChanged();
			openOptionsDialogIsNoneResult();
		}
		myListView.setAdapter(adapterHTTP);

		myListView
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {

					@SuppressWarnings("unchecked")
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// arg0就是ListView，arg1表示Item視圖，arg2表示資料index

						ListView lv = (ListView) arg0;
						// SimpleAdapter返回Map
						HashMap<String, String> infirmary = (HashMap<String, String>) lv
								.getItemAtPosition(arg2);

						name = infirmary.get("name");
						address = infirmary.get("address");
						telephone = infirmary.get("telephone");
						category = infirmary.get("category");

						Intent intent = new Intent();
						intent.setClass(ActivitySearch.this, ActivityMenu.class);

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
	}

	private void processSpinner() {

		spinner = CreateSpinner(spinner, R.id.spinner1, R.array.cities_init);
		spinner2 = CreateSpinner(spinner2, R.id.spinner2, R.array.city_init);
		spinner3 = CreateSpinner(spinner3, R.id.spinner3, R.array.category_init);
	}

	private Spinner CreateSpinner(Spinner spinner, int value, int array) {
		// 将可选内容与ArrayAdapter连接起来
		ArrayAdapter<CharSequence> adapterTemp = ArrayAdapter
				.createFromResource(this, array,
						android.R.layout.simple_spinner_item);

		// 设置下拉列表的风格
		adapterTemp
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

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
			choeseCities(pos);
			spinner2.setAdapter(adapter);
			// myCursor = DH.getData(selectedCities,str2, str3,0);
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
			// myCursor = DH.getData(selectedCities,str2, str3,0);
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
			// myCursor = DH.getData(selectedCities,str2, str3,0);
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {

		}
	};

	private void choeseCities(int pos) {

		int value = 0;

		switch (pos) {
		case 0:
			value = R.array.keelung_city;
			selectedCities = new String("keelung_city");
			break;
		case 1:
			value = R.array.taipei_city;
			selectedCities = new String("taipei_city");
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
			selectedCities = new String("taichung_city");
			break;
		case 8:
			value = R.array.changhua_county;
			selectedCities = new String("changhua_county");
			break;
		case 9:
			value = R.array.nantou_county;
			selectedCities = new String("nantou_county");
			break;
		case 10:
			value = R.array.yunlin_county;
			selectedCities = new String("yunlin_county");
			break;
		case 11:
			value = R.array.chiayi_city;
			selectedCities = new String("chiayi_city");
			break;
		case 12:
			value = R.array.chiayi_county;
			selectedCities = new String("chiayi_county");
			break;
		case 13:
			value = R.array.tainan_city;
			selectedCities = new String("tainan_city");
			break;
		case 14:
			value = R.array.kaohsiung_city;
			selectedCities = new String("kaohsiung_city");
			break;
		case 15:
			value = R.array.pingtung_county;
			selectedCities = new String("pingtung_county");
			break;
		case 16:
			value = R.array.yilan_county;
			selectedCities = new String("yilan_county");
			break;
		case 17:
			value = R.array.hualien_county;
			selectedCities = new String("hualien_county");
			break;
		case 18:
			value = R.array.taitung_county;
			selectedCities = new String("taitung_county");
			break;
		case 19:
			value = R.array.penghu_county;
			selectedCities = new String("penghu_county");
			break;
		case 20:
			value = R.array.kinmen_county;
			selectedCities = new String("kinmen_county");
			break;
		case 21:
			value = R.array.lianjiang_county;
			selectedCities = new String("lianjiang_county");
			break;
		default:
			break;
		}

		adapter = ArrayAdapter.createFromResource(this, value,
				android.R.layout.simple_spinner_item);
	}

	private void visitExternalLinks() {
		// 發送Http請求
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
				.build());
	}

	protected void setInAdapter() {
		List<Map<String, String>> lists = new ArrayList<Map<String, String>>();

		Map<String, String> map;
		for (Infirmary p : Infirmarys) {
			map = new HashMap<String, String>();

			map.put("_id", p.getId());
			map.put("name", p.getName());
			map.put("category", p.getCategory());
			map.put("cities", p.getCities());
			map.put("city", p.getCity());
			map.put("address", p.getAddress());
			map.put("telephone", p.getTelephone());

			lists.add(map);
		}

		// HashMap<String, String>中的key
		String[] from = { "name", "address" };

		int[] to = { R.id.listTextView1, R.id.listTextView2 };

		adapterHTTP = new SimpleAdapter(this, lists, R.layout.activity_list,
				from, to);

	}

	protected List<Infirmary> JsonToList(String response) {
		List<Infirmary> list = new ArrayList<Infirmary>();

		try {
			// 將字符串轉換為Json數組
			JSONArray array = new JSONArray(response);

			int length = array.length();
			for (int i = 0; i < length; i++) {
				// 將每一個數組再轉換成Json對象
				JSONObject obj = array.getJSONObject(i);

				Infirmary = new Infirmary();
				Infirmary.setId(obj.getString("_id"));
				Infirmary.setName(obj.getString("name"));
				Infirmary.setCategory(obj.getString("category"));
				Infirmary.setCities(obj.getString("cities"));
				Infirmary.setCity(obj.getString("city"));
				Infirmary.setAddress(obj.getString("address"));
				Infirmary.setTelephone(obj.getString("telephone"));

				list.add(Infirmary);
			}

			return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}