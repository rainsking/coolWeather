package com.coolweather.app.activity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coolweather.app.R;
import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;
import com.coolweather.app.util.HttpCallbackListener;
import com.coolweather.app.util.HttpUtil;
import com.coolweather.app.util.ParseXML;
import com.coolweather.app.util.Utility;

public class MainActivity extends Activity{

	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTY = 2;
	
	private ProgressDialog progressDialog;
	private TextView titleText;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private CoolWeatherDB coolWeatherDB;
	private List<String> dataList = new ArrayList<String>();
	
	//ʡ�б�
	private List<Province> provinceList;
	//���б�
	private List<City> cityList;
	//���б�
	private List<County> countyList;
	//ѡ�е�ʡ��
	private Province selectedProvince;
	//ѡ�е���
	private City selectedCity;
	//��ǰѡ�еļ���
	private int currentLevel;
	
	//@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();  
		//StrictMode.setThreadPolicy(policy);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		listView = (ListView) findViewById(R.id.list_view);
		titleText = (TextView) findViewById(R.id.title_text);
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList);
		listView.setAdapter(adapter);
		coolWeatherDB = CoolWeatherDB.getInstance(this);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> arg0, View view,
					int index, long arg3) {
				if(currentLevel == LEVEL_PROVINCE){
					selectedProvince = provinceList.get(index);
					queryCities();
				} else if(currentLevel == LEVEL_CITY){
					selectedCity = cityList.get(index);
					queryCounties();
				}
			}
		});
		queryProvinces();  //����ʡ������
	}
	
	/**
	 * ��ѯȫ�����е�ʡ�����ȴ����ݿ��ѯ�����û�в�ѯ����ȥ�������ϲ�ѯ
	 * @throws  
	 */
	private void queryProvinces(){
		provinceList = coolWeatherDB.loadProvinces();
		if(provinceList.size() > 0){
			dataList.clear();
			for(Province province : provinceList){
				dataList.add(province.getId() + province.getProvinceName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText("�й�");
			currentLevel = LEVEL_PROVINCE;
		} else {
			initProvincialCitiesAndCounties();
		}
	}
	
	/**
	 * ��ѯѡ��ʡ�����е��У����ȴ����ݿ��ѯ�����û�в�ѯ����ȥ�������ϲ�ѯ
	 */
	private void queryCities(){
		cityList = coolWeatherDB.loadCities(selectedProvince.getId());
		if(cityList.size() > 0){
			dataList.clear();
			for(City city : cityList){
				dataList.add(city.getCityName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText(selectedProvince.getProvinceName());
			currentLevel =LEVEL_CITY;
		} else {
			initProvincialCitiesAndCounties();
		}
	}
	
	/**
	 * ��ѯѡ�����ڵ��أ����ȴ����ݿ��ѯ�����û�в�ѯ����ȥ�������ϲ�ѯ
	 */
	private void queryCounties(){
		countyList = coolWeatherDB.loadCounties(selectedCity.getId());
		if(countyList.size() > 0){
			dataList.clear();
			for(County county : countyList){
				dataList.add(county.getCountyName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText(selectedCity.getCityName());
			currentLevel = LEVEL_COUNTY;
		} else {
			initProvincialCitiesAndCounties();
		}
	}
	
	public void initProvincialCitiesAndCounties(){
		String address = "http://apis.eolinker.com/common/postcode/getAddrs";
		String key = "5hTarHCd28edb8da9dc583b67b686568101df90b45293b0";
		address += "?productKey=" + key;
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			@Override
			public void onFinish(InputStream inStream) throws Exception {
				boolean result = false;
				result = Utility.handleProvincesCitiesAndCountiesResponse(coolWeatherDB, inStream);
				if(result){
					//ͨ��runOnUiThread()�����ص����̴߳����߼�
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							closeProgressDialog();
							queryProvinces();
						}
					});
				}
			}
			
			@Override
			public void onError(Exception e) {
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						closeProgressDialog();
						Toast.makeText(MainActivity.this, "����ʧ��", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
		showProgressDialog();
	}
	
	/**
	 * ��ʾ���ȶԻ���
	 */
	private void showProgressDialog(){
		if(progressDialog == null){
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("���ڼ���...");
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}
	
	/**
	 * �رս��ȶԻ���
	 */
	private void closeProgressDialog(){
		if(progressDialog != null){
			progressDialog.dismiss();
		}
	}
	
	/**
	 * ����Back���������ݵ�ǰ�ļ������жϣ���ʱӦ�÷������б�ʡ�б�����ֱ���˳���
	 */
	@Override
	public void onBackPressed() {
		if(currentLevel == LEVEL_COUNTY){
			queryCities();
		} else if (currentLevel == LEVEL_CITY) {
			queryProvinces();
		} else {
			finish();
		}
	}
}


/**
 * ���ݴ���Ĵ��ź����ʹӷ������ϲ�ѯʡ��������
 * @param pyName ��ַƴ������
 * @param type ����
 */
//private void queryFromServer(final String pyName, final String type){
//	String address = "";
//	String key = "8d2f6aae5270454ebb88f4bdb4b801fd";
//	if("province".equals(type)){
//		address = "http://flash.weather.com.cn/wmaps/xml/china.xml";
//	} else if("city".equals(type)){
//		address = "http://flash.weather.com.cn/wmaps/xml/" + pyName + ".xml";
//	} else {
//		address = "https://free-api.heweather.com/s6/weather/forecast?location=" + pyName + "&key=" + key;
//	}
//	showProgressDialog();
//	HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
//		@Override
//		public void onFinish(InputStream inStream) throws Exception {
//			boolean result = false;
//			if("province".equals(type)){
//				result = Utility.handleProvincesResponse(coolWeatherDB, inStream);
//			} else if("city".equals(type)){
//				result = Utility.handleCitiesResponse(coolWeatherDB, inStream, selectedProvince.getId());
//			} else if("county".equals(type)) {
//				result = Utility.handleCountiesResponse(coolWeatherDB, inStream, selectedCity.getId());
//			}
//			if(result){
//				//ͨ��runOnUiThread()�����ص����̴߳����߼�
//				runOnUiThread(new Runnable() {
//					@Override
//					public void run() {
//						closeProgressDialog();
//						if("province".equals(type)){
//							queryProvinces();
//						} else if("city".equals(type)){
//							queryCities();
//						} else if("county".equals(type)){
//							queryCounties();
//						}
//					}
//					
//				});
//			}
//		}
//		
//		@Override
//		public void onError(Exception e) {
//			runOnUiThread(new Runnable() {
//				@Override
//				public void run() {
//					closeProgressDialog();
//					Toast.makeText(ChooseAreaActivity.this, "����ʧ��", Toast.LENGTH_SHORT).show();
//				}
//			});
//		}
//	});
//}

//HttpURLConnection connection = null;
//boolean result = false;
//try {
//	URL url = new URL(address);
//	System.out.println("11111111111111111111");
//	connection = (HttpURLConnection) url.openConnection();
//	System.out.println("8888888888888888");
//	connection.setRequestMethod("GET");
//	connection.setConnectTimeout(8000);
//	connection.setReadTimeout(8000);
//	InputStream in = connection.getInputStream();
//	result = Utility.handleProvincesResponse(coolWeatherDB, in);
//	in.close();
//}catch(Exception e){
//	e.printStackTrace();
//}finally{
//	closeProgressDialog();
//	if(result){
//		if("province".equals(type)){
//			queryProvinces();
//		} else if("city".equals(type)){
//			queryCities();
//		} else if("county".equals(type)){
//			queryCounties();
//		}
//	}else{
//		Toast.makeText(ChooseAreaActivity.this, "����ʧ��", Toast.LENGTH_SHORT).show();
//	}
//	if(connection != null){
//		connection.disconnect();
//	}
//}