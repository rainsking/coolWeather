package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.coolweather.app.model.County;

public class ParseJSON {
	/**
     * 以Json方式返回获取最新的资讯，不需要手动解析，JSON自己会进行解析
     * @return
     * @throws Exception
     */
	public static List<County> parseCountyXML(InputStream inStream , int cityId) throws Exception
    {
        List<County> counties = new ArrayList<County>();
        //调用数据流处理方法
        BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
        StringBuilder sb = new StringBuilder();
        String inputStr;
        while ((inputStr = br.readLine()) != null){
            sb.append(inputStr);
        }
        JSONObject jsonObject = new JSONObject(sb.toString());
        JSONArray jsonArray = new JSONArray(jsonObject.get("HeWeather6").toString());
        if(jsonArray.length()>0){
        	for(int i = 0; i < jsonArray.length(); i ++){
        		JSONObject countyObj = jsonArray.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
        	    //System.out.println(job.get("name")+"=") ;  // 得到 每个对象中的属性值
        	}
        }
        //构建JSON数组对象
//        System.out.println(jsonObject.get("HeWeather6"));
//        //JSONObject basicObj = heWeatherObj.getJSONObject("basic");
//        JSONArray heWeatherJSON = 
//        System.out.println(heWeatherObj);
        //从JSON数组对象读取数据
//        for(int i=0 ; i < array.length() ; i++)
//        {
//            //获取各个属性的值
//            JSONObject item = array.getJSONObject(i);
//            System.out.println(item.g);
//        }
        return counties;
    }
	
	public static Map<String, Map<String, Object>> parseProvincesCitiesAndCountiesJSON(InputStream inStream) throws Exception
    {
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
        //调用数据流处理方法
        BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
        StringBuilder sb = new StringBuilder();
        String inputStr;
        while ((inputStr = br.readLine()) != null){
            sb.append(inputStr);
        }
        JSONObject jsonObject = new JSONObject(sb.toString());
        JSONArray jsonArray = new JSONArray(jsonObject.get("result").toString());
        if(jsonArray.length()>0){
        	for(int i = 0; i < jsonArray.length(); i ++){
        		JSONObject countyObj = jsonArray.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
        	    //System.out.println(job.get("name")+"=") ;  // 得到 每个对象中的属性值
        	}
        }
        //构建JSON数组对象
//        System.out.println(jsonObject.get("HeWeather6"));
//        //JSONObject basicObj = heWeatherObj.getJSONObject("basic");
//        JSONArray heWeatherJSON = 
//        System.out.println(heWeatherObj);
        //从JSON数组对象读取数据
//        for(int i=0 ; i < array.length() ; i++)
//        {
//            //获取各个属性的值
//            JSONObject item = array.getJSONObject(i);
//            System.out.println(item.g);
//        }
        return map;
    }
}
