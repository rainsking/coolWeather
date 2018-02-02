package com.coolweather.app.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.coolweather.app.util.Utility;

public class ChooseAreaActivity extends Activity{

	public static final int LEVEL_PROVINCE = 0;
	public static final int LEVEL_CITY = 1;
	public static final int LEVEL_COUNTY = 2;
	
	private ProgressDialog progressDialog;
	private TextView titleText;
	private ListView listView;
	private ArrayAdapter<String> adapter;
	private CoolWeatherDB coolWeatherDB;
	private List<String> dataList = new ArrayList<String>();
	
	//省列表
	private List<Province> provinceList;
	//市列表
	private List<City> cityList;
	//县列表
	private List<County> countyList;
	//选中的省份
	private Province selectedProvince;
	//选中的市
	private City selectedCity;
	//当前选中的级别
	private int currentLevel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.choose_area);
		
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
		queryProvinces();  //加载省级数据
	}
	
	/**
	 * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询
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
			titleText.setText("中国");
			currentLevel = LEVEL_PROVINCE;
		} else {
			quertFromServer(null, "province");
		}
	}
	
	/**
	 * 查询选中省内所有的市，优先从数据库查询，如果没有查询到再去服务器上查询
	 */
	private void queryCities(){
		cityList = coolWeatherDB.loadCities(selectedProvince.getId());
		System.out.println("11111111111111");
		if(cityList.size() > 0){
			System.out.println("22222222222222");
			dataList.clear();
			for(City city : cityList){
				dataList.add(city.getCityName());
			}
			System.out.println("33333333333s");
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText(selectedProvince.getProvinceName());
			currentLevel =LEVEL_CITY;
		} else {
			System.out.println("33333333333333333333"+selectedProvince.getProvincePyName());
			quertFromServer(selectedProvince.getProvincePyName(), "city");
		}
	}
	
	/**
	 * 查询选中市内的县，优先从数据库查询，如果没有查询到再去服务器上查询
	 */
	private void queryCounties(){
		countyList = coolWeatherDB.loadCounties(selectedCity.getId());
		if(cityList.size() > 0){
			dataList.clear();
			for(County county : countyList){
				dataList.add(county.getCountyName());
			}
			adapter.notifyDataSetChanged();
			listView.setSelection(0);
			titleText.setText(selectedCity.getCityName());
			currentLevel = LEVEL_COUNTY;
		} else {
			quertFromServer(selectedCity.getCityPyName(), "county");
		}
	}
	
	/**
	 * 根据传入的代号和类型从服务器上查询省市县数据
	 * @param pyName 地址拼音名称
	 * @param type 类型
	 */
	private void quertFromServer(final String pyName, final String type){
		String address = "";
		String key = "8d2f6aae5270454ebb88f4bdb4b801fd";
		if("province".equals(type)){
			address = "http://flash.weather.com.cn/wmaps/xml/china.xml";
		} else if("city".equals(type)){
			address = "http://flash.weather.com.cn/wmaps/xml/" + pyName + ".xml";
			//address = "http://flash.weather.com.cn/wmaps/xml/hunan.xml";
		} else {
			//https://free-api.heweather.com/s6/weather/forecast?location=xiangyin&key=8d2f6aae5270454ebb88f4bdb4b801fd
			address = "https://free-api.heweather.com/s6/weather/forecast?location=" + pyName + "&key=" + key;
		}
		showProgressDialog();
		System.out.println("444444444444");
		System.out.println(address);
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {
			
			@Override
			public void onFinish(InputStream inStream) throws Exception {
				System.out.println("888888888888888");
				boolean result = false;
				if("province".equals(type)){
					result = Utility.handleProvincesResponse(coolWeatherDB, inStream);
				} else if("city".equals(type)){
					System.out.println(selectedProvince.getId()+"-----------------------------------------------");
					result = Utility.handleCitiesResponse(coolWeatherDB, inStream, selectedProvince.getId());
				} else if("county".equals(type)) {
					result = Utility.handleCountiesResponse(coolWeatherDB, inStream, selectedCity.getId());
				}
				if(result){
					
					//通过runOnUiThread()方法回到主线程处理逻辑
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							closeProgressDialog();
							if("province".equals(type)){
								queryProvinces();
							} else if("city".equals(type)){
								queryCities();
							} else if("county".equals(type)){
								queryCounties();
							}
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
						Toast.makeText(ChooseAreaActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
	
	/**
	 * 显示进度对话框
	 */
	private void showProgressDialog(){
		if(progressDialog == null){
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("正在加载...");
			progressDialog.setCanceledOnTouchOutside(false);
		}
		progressDialog.show();
	}
	
	/**
	 * 关闭进度对话框
	 */
	private void closeProgressDialog(){
		if(progressDialog != null){
			progressDialog.dismiss();
		}
	}
	
	/**
	 * 捕获Back按键，根据当前的级别来判断，此时应该返回市列表、省列表、还是直接退出。
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
