package com.coolweather.app.util;

import java.io.InputStream;
import java.util.List;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.Province;

public class Utility {
	public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, InputStream inStream) throws Exception{
		if(inStream != null){
			List<Province> provinces = ParseXML.parseProvinceXML(inStream);
			for(Province p : provinces){
				coolWeatherDB.saveProvince(p);
			}
			return true;
		}
		return false;
	}
	
	public synchronized static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB, InputStream inStream , int provinceId) throws Exception{
		if(inStream != null){
			List<City> cities = ParseXML.parseCityXML(inStream, provinceId);
			for(City c : cities){
				coolWeatherDB.saveCity(c);
			}
			return true;
		}
		return false;
	}
	
	public synchronized static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB, InputStream inStream , int cityId) throws Exception{
		
		return false;
	}
}
