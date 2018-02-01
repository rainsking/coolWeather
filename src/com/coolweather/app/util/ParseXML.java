package com.coolweather.app.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.coolweather.app.model.City;
import com.coolweather.app.model.Province;

public class ParseXML {
	/**
     * 解析服务器返回的协议，得到省份
     * @param inStream
     * @return
     * @throws Exception
     */
    public static List<Province> parseProvinceXML(InputStream inStream) throws Exception
    {
        List<Province> provinces = null;
        Province province = null;
        //使用XmlPullParser
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inStream, "UTF-8");
        int eventType = parser.getEventType();//产生第一个事件
        //只要不是文档结束事件
        while(eventType!=XmlPullParser.END_DOCUMENT)
        {
            switch (eventType)
            {
                case XmlPullParser.START_DOCUMENT:
                	provinces = new ArrayList<Province>();
                    break;
                case XmlPullParser.START_TAG:
                    //获取解析器当前指向的元素的名称
                    String name = parser.getName();
                    if("city".equals(name))
                    {
                    	province = new Province();
                        //把id属性写入
                    	province.setProvinceName(parser.getAttributeValue(0));
                    	province.setProvincePyName(parser.getAttributeValue(1));
                    	provinces.add(province);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    break;
            }
            eventType = parser.next();
        }
        return provinces;
    }
    
    /**
     * 解析服务器返回的协议，得到市
     * @param inStream
     * @return
     * @throws Exception
     */
    public static List<City> parseCityXML(InputStream inStream , int provinceId) throws Exception
    {
        List<City> cities = null;
        City city = null;
        //使用XmlPullParser
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inStream, "UTF-8");
        int eventType = parser.getEventType();//产生第一个事件
        //只要不是文档结束事件
        while(eventType!=XmlPullParser.END_DOCUMENT)
        {
            switch (eventType)
            {
                case XmlPullParser.START_DOCUMENT:
                	cities = new ArrayList<City>();
                    break;
                case XmlPullParser.START_TAG:
                    //获取解析器当前指向的元素的名称
                    String name = parser.getName();
            
                    
                    if("city".equals(name))
                    {
                    	city = new City();
                    	city.setCityName(parser.getAttributeValue(10));
                    	city.setCityPyName(parser.getAttributeValue(11));
                    	city.setProvinceId(provinceId);
                    	cities.add(city);
                    }
                    break;
                case XmlPullParser.END_TAG:
                    break;
            }
            eventType = parser.next();
        }
        return cities;
    }
}
