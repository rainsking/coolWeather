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
     * ��Json��ʽ���ػ�ȡ���µ���Ѷ������Ҫ�ֶ�������JSON�Լ�����н���
     * @return
     * @throws Exception
     */
	public static List<County> parseCountyXML(InputStream inStream , int cityId) throws Exception
    {
        List<County> counties = new ArrayList<County>();
        //����������������
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
        		JSONObject countyObj = jsonArray.getJSONObject(i);  // ���� jsonarray ���飬��ÿһ������ת�� json ����
        	    //System.out.println(job.get("name")+"=") ;  // �õ� ÿ�������е�����ֵ
        	}
        }
        //����JSON�������
//        System.out.println(jsonObject.get("HeWeather6"));
//        //JSONObject basicObj = heWeatherObj.getJSONObject("basic");
//        JSONArray heWeatherJSON = 
//        System.out.println(heWeatherObj);
        //��JSON��������ȡ����
//        for(int i=0 ; i < array.length() ; i++)
//        {
//            //��ȡ�������Ե�ֵ
//            JSONObject item = array.getJSONObject(i);
//            System.out.println(item.g);
//        }
        return counties;
    }
	
	public static Map<String, Map<String, Object>> parseProvincesCitiesAndCountiesJSON(InputStream inStream) throws Exception
    {
		Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
        //����������������
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
        		JSONObject countyObj = jsonArray.getJSONObject(i);  // ���� jsonarray ���飬��ÿһ������ת�� json ����
        	    //System.out.println(job.get("name")+"=") ;  // �õ� ÿ�������е�����ֵ
        	}
        }
        //����JSON�������
//        System.out.println(jsonObject.get("HeWeather6"));
//        //JSONObject basicObj = heWeatherObj.getJSONObject("basic");
//        JSONArray heWeatherJSON = 
//        System.out.println(heWeatherObj);
        //��JSON��������ȡ����
//        for(int i=0 ; i < array.length() ; i++)
//        {
//            //��ȡ�������Ե�ֵ
//            JSONObject item = array.getJSONObject(i);
//            System.out.println(item.g);
//        }
        return map;
    }
}
