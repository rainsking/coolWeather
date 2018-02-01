package com.coolweather.app.util;

import java.io.InputStream;
import java.util.List;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.Province;

public class Utility {
	public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, InputStream inStream) throws Exception{
		if(inStream != null){
			List<Province> provinces = StreamTool.parseXML(inStream);
			for(Province p : provinces){
				coolWeatherDB.saveProvince(p);
			}
			return true;
		}
		return false;
	}
	
	public synchronized static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB, InputStream inStream , int provinceId){
		
		return false;
	}
	
	public synchronized static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB, InputStream inStream , int cityId){
		
		return false;
	}
}
