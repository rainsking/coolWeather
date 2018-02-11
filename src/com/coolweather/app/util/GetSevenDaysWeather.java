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
        //����������������
        BufferedReader br = new BufferedReader(new InputStreamReader(inStream));
        StringBuilder sb = new StringBuilder();
        String inputStr;
        while ((inputStr = br.readLine()) != null){
            sb.append(inputStr);
        }
        //����JSON�������
        JSONObject jsonObject = new JSONObject(sb.toString());
        JSONArray jsonArray = new JSONArray(jsonObject.get("HeWeather6").toString());
        if(jsonArray.length()>0){
        	//��JSON��������ȡ����
        	for(int i = 0; i < jsonArray.length(); i ++){
        		JSONObject countyObj = jsonArray.getJSONObject(i);  // ���� jsonarray ���飬��ÿһ������ת�� json ����
        	    //System.out.println(job.get("name")+"=") ;  // �õ� ÿ�������е�����ֵ
        	}
        }
        return counties;
    }
}
