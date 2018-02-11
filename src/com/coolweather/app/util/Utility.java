package com.coolweather.app.util;

import java.io.InputStream;
import java.util.List;

import com.coolweather.app.db.CoolWeatherDB;
import com.coolweather.app.model.City;
import com.coolweather.app.model.County;
import com.coolweather.app.model.Province;

public class Utility {
	
	public synchronized static boolean handleProvincesCitiesAndCountiesResponse(CoolWeatherDB coolWeatherDB, InputStream inStream) throws Exception{
		if(inStream != null){
			List<Province> provinces = ParseXML.parseProvinceXML(inStream);
			for(Province p : provinces){
				coolWeatherDB.saveProvince(p);
			}
			if(provinces.size()>0){
				return true;
			}
		}
		return false;
	}
	
	public synchronized static boolean handleProvincesResponse(CoolWeatherDB coolWeatherDB, InputStream inStream) throws Exception{
		if(inStream != null){
			List<Province> provinces = ParseXML.parseProvinceXML(inStream);
			for(Province p : provinces){
				coolWeatherDB.saveProvince(p);
			}
			if(provinces.size()>0){
				return true;
			}
		}
		return false;
	}
	
	public synchronized static boolean handleCitiesResponse(CoolWeatherDB coolWeatherDB, InputStream inStream , int provinceId) throws Exception{
		if(inStream != null){
			List<City> cities = ParseXML.parseCityXML(inStream, provinceId);
			for(City c : cities){
				coolWeatherDB.saveCity(c);
			}
			if(cities.size()>0){
				return true;
			}
		}
		return false;
	}
	
	public synchronized static boolean handleCountiesResponse(CoolWeatherDB coolWeatherDB, InputStream inStream , int cityId) throws Exception{
		if(inStream != null){
			List<County> counties = ParseJSON.parseCountyXML(inStream, cityId);
			for(County c : counties){
				coolWeatherDB.saveCounty(c);
			}
			if(counties.size()>0){
				return true;
			}
		}
		return false;
	}
}
