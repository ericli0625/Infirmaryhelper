package com.example.infirmaryhelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivitySearchAdven extends Activity {

	private Button myButtonSubmit, myButtonReset;
	private EditText editText_Search;
	private TextView myTextView;
	private Spinner spinner;
	private ListView myListView_Search;
	
	private SimpleAdapter adapterHTTP;
	protected List<Infirmary> Infirmarys;
	private Infirmary Infirmary;
	private String result = new String();
	
	private String selectedCities = new String("keelung_city");
	private String telephone, address, name, category;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_adven);

		visitExternalLinks();
		
		myButtonSubmit = (Button) findViewById(R.id.button_Search_Submit);
		myButtonReset = (Button) findViewById(R.id.button_Search_Reset);
		editText_Search = (EditText) findViewById(R.id.editText_Search);
		spinner = (Spinner) findViewById(R.id.spinner4);
		myListView_Search = (ListView) findViewById(R.id.myListView_Search);
		myTextView = (TextView) findViewById(R.id.textView2_search);
		
		ArrayAdapter<CharSequence> adapterTemp = ArrayAdapter
				.createFromResource(this, R.array.cities_init,
						android.R.layout.simple_spinner_item);

		adapterTemp
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner.setAdapter(adapterTemp);

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				int pos = spinner.getSelectedItemPosition();
				choeseCities(pos);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});

		if (AppStatus.getInstance(this).isOnline(this)) {

			myButtonSubmit.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// Perform action on click
					String str1 = editText_Search.getText().toString();
					
					if (!str1.equals("")) {
						String sql = "SELECT * FROM " + selectedCities
								+ " where name like '%" + str1 + "%'";
						result = DBConnector.executeQuery(sql);
						
						ShowListView(result);
					} else {
						openOptionsDialogIsNotContext();
					}
					
				}
			});

			myButtonReset.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					// Perform action on click
					editText_Search.setText("");
				}
			});

		} else {
			openOptionsDialogIsNetworkAvailable();
		}

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

	private void openOptionsDialogIsNotContext() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.app_no_context)
				.setMessage(R.string.app_no_context_msg)
				.setPositiveButton(R.string.str_ok,
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
							}
						}).show();

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_search_adven, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case R.id.item_favor:
			Intent intent = new Intent();
			intent.setClass(ActivitySearchAdven.this, ActivityFavor.class);
			startActivity(intent);
			return true;
		default:
			break;
		}

		return true;
	}

	private void choeseCities(int pos) {

		switch (pos) {
		case 0:
			selectedCities = new String("keelung_city");
			break;
		case 1:
			selectedCities = new String("taipei_city");
			break;
		case 2:
			selectedCities = new String("xinbei_city");
			break;
		case 3:
			selectedCities = new String("taoyuan_county");
			break;
		case 4:
			selectedCities = new String("hsinchu_city");
			break;
		case 5:
			selectedCities = new String("hsinchu_county");
			break;
		case 6:
			selectedCities = new String("miaoli_county");
			break;
		case 7:
			selectedCities = new String("taichung_city");
			break;
		case 8:
			selectedCities = new String("changhua_county");
			break;
		case 9:
			selectedCities = new String("nantou_county");
			break;
		case 10:
			selectedCities = new String("yunlin_county");
			break;
		case 11:
			selectedCities = new String("chiayi_city");
			break;
		case 12:
			selectedCities = new String("chiayi_county");
			break;
		case 13:
			selectedCities = new String("tainan_city");
			break;
		case 14:
			selectedCities = new String("kaohsiung_city");
			break;
		case 15:
			selectedCities = new String("pingtung_county");
			break;
		case 16:
			selectedCities = new String("yilan_county");
			break;
		case 17:
			selectedCities = new String("hualien_county");
			break;
		case 18:
			selectedCities = new String("taitung_county");
			break;
		case 19:
			selectedCities = new String("penghu_county");
			break;
		case 20:
			selectedCities = new String("kinmen_county");
			break;
		case 21:
			selectedCities = new String("lianjiang_county");
			break;
		default:
			break;
		}

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
		myListView_Search.setAdapter(adapterHTTP);

		myListView_Search
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
						intent.setClass(ActivitySearchAdven.this, ActivityMenu.class);

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
	
	private void visitExternalLinks() {
		// 發送Http請求
		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectDiskReads().detectDiskWrites().detectNetwork()
				.penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
				.detectLeakedSqlLiteObjects().penaltyLog().penaltyDeath()
				.build());
	}
	
}
