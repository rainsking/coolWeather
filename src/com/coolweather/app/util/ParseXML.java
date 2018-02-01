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
     * �������������ص�Э�飬�õ�ʡ��
     * @param inStream
     * @return
     * @throws Exception
     */
    public static List<Province> parseProvinceXML(InputStream inStream) throws Exception
    {
        List<Province> provinces = null;
        Province province = null;
        //ʹ��XmlPullParser
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inStream, "UTF-8");
        int eventType = parser.getEventType();//������һ���¼�
        //ֻҪ�����ĵ������¼�
        while(eventType!=XmlPullParser.END_DOCUMENT)
        {
            switch (eventType)
            {
                case XmlPullParser.START_DOCUMENT:
                	provinces = new ArrayList<Province>();
                    break;
                case XmlPullParser.START_TAG:
                    //��ȡ��������ǰָ���Ԫ�ص�����
                    String name = parser.getName();
                    if("city".equals(name))
                    {
                    	province = new Province();
                        //��id����д��
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
     * �������������ص�Э�飬�õ���
     * @param inStream
     * @return
     * @throws Exception
     */
    public static List<City> parseCityXML(InputStream inStream , int provinceId) throws Exception
    {
        List<City> cities = null;
        City city = null;
        //ʹ��XmlPullParser
        XmlPullParser parser = Xml.newPullParser();
        parser.setInput(inStream, "UTF-8");
        int eventType = parser.getEventType();//������һ���¼�
        //ֻҪ�����ĵ������¼�
        while(eventType!=XmlPullParser.END_DOCUMENT)
        {
            switch (eventType)
            {
                case XmlPullParser.START_DOCUMENT:
                	cities = new ArrayList<City>();
                    break;
                case XmlPullParser.START_TAG:
                    //��ȡ��������ǰָ���Ԫ�ص�����
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
