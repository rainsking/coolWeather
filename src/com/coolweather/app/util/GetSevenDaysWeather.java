package com.coolweather.app.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.coolweather.app.model.County;

public class GetSevenDaysWeather {
	public static List<County> getWeatherXML(InputStream inStream , int cityId) throws Exception{
        List<County> counties = new ArrayList<County>();
        //调用数据流处理方法
        BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
        StringBuilder sb = new StringBuilder();
        String inputStr;
        while ((inputStr = br.readLine()) != null){
            sb.append(inputStr);
        }
        //构建JSON数组对象
        JSONObject jsonObject = new JSONObject(sb.toString());
        JSONArray jsonArray = new JSONArray(jsonObject.get("HeWeather6").toString());
        if(jsonArray.length()>0){
        	//从JSON数组对象读取数据
        	for(int i = 0; i < jsonArray.length(); i ++){
        		JSONObject countyObj = jsonArray.getJSONObject(i);  // 遍历 jsonarray 数组，把每一个对象转成 json 对象
        	    //System.out.println(job.get("name")+"=") ;  // 得到 每个对象中的属性值
        	}
        }
        return counties;
    }
}
