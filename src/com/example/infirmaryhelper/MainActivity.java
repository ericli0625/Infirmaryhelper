package com.example.infirmaryhelper;

import java.util.ArrayList;
import java.util.logging.Handler;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.infirmaryhelper.ConnectMySQL.MyObj;

public class MainActivity extends Activity {
    
    private String[] cities = new String[] {"台北市", "桃園縣"};//載入第一下拉選單
    private String[] city = new String[]{"中正區","萬華區"};//起始畫面時預先載入第二下拉選單
    private String[] category = new String[]{"中醫","牙科"};//起始畫面時預先載入第三下拉選單
    //第一下拉選取後載入第二下拉選單
    private String[][] type2 = new String[][]{{"中正區","萬華區"},{"柳丁汁","西瓜汁","烏梅汁"}};
    //第二下拉選取後載入第三下拉選單
    private String[][][] type3 = new String[][][]{{{"紅茶超好喝","紅茶喝完臉會紅"},{"綠茶超好喝","綠茶喝完臉會綠"},{"烏龍茶超好喝","烏龍茶超烏龍"},{"青茶超好喝","青茶很尚青"}},{{"柳丁汁超好喝","柳丁汁好棒"},{"西瓜汁超好喝","西瓜汁還可以"},{"烏梅汁超好喝","烏梅汁還可以"}}};
    
    private Spinner spinner,spinner2,spinner3;  
    private ArrayAdapter<String> adapter,adapter2,adapter3;
    private Context context;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}
	
	private void init(){
		
		context = this;
		
		spinner = CreateSpinner(spinner,cities,R.id.spinner1);
		spinner2 = CreateSpinner(spinner2,city,R.id.spinner2);
		spinner3 = CreateSpinner(spinner3,category,R.id.spinner3);
		
//    	Button b = (Button) findViewById(R.id.button1);
//    	b.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//						
//			}
//		});
    	
    	
    }
    
	public Spinner CreateSpinner(Spinner spinner,String [] item,int value){
        //将可选内容与ArrayAdapter连接起来  
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,item);  
          
        //设置下拉列表的风格  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  
        
        spinner = (Spinner) findViewById(value); 
        
        //将adapter 添加到spinner中  
        spinner.setAdapter(adapter);  
        
        switch (value) {
		case R.id.spinner1:
			spinner.setOnItemSelectedListener(selectListener);
			break;
		case R.id.spinner2:
			spinner.setOnItemSelectedListener(selectListener2);
			break;
		default:
			break;
		}

        return spinner;
	}
    
	//第一個下拉類別的監看式
    private OnItemSelectedListener selectListener = new OnItemSelectedListener(){
        public void onItemSelected(AdapterView<?> parent, View v, int position,long id){
         //讀取第一個下拉選單是選擇第幾個
            int pos = spinner.getSelectedItemPosition();
            //重新產生新的Adapter，用的是二維陣列type2[pos]
            adapter2 = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, type2[pos]);
            //載入第二個下拉選單Spinner
            spinner2.setAdapter(adapter2);
        }
       
        public void onNothingSelected(AdapterView<?> arg0){
 
        }

    };
	
    //第二個下拉類別的監看式
    private OnItemSelectedListener selectListener2 = new OnItemSelectedListener(){
        public void onItemSelected(AdapterView<?> parent, View v, int position,long id){
         //讀取第一個下拉選單是選擇第幾個
            int pos = spinner.getSelectedItemPosition();
         //讀取第二個下拉選單是選擇第幾個
            int pos2 = spinner2.getSelectedItemPosition();
            //重新產生新的Adapter，用的是三維陣列type3[pos][pos2]
            adapter3 = new ArrayAdapter<String>(context,android.R.layout.simple_spinner_item, type3[pos][pos2]);
            //載入第三個下拉選單Spinner
            spinner3.setAdapter(adapter3);
        }
       
        public void onNothingSelected(AdapterView<?> arg0){
 
        }
 
    };
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
