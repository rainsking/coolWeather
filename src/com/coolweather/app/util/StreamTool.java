package com.coolweather.app.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.coolweather.app.model.Province;

public class StreamTool {
	//�����ж�ȡ����
    public static byte[] read(InputStream inStream) throws Exception{
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while((len = inStream.read(buffer)) != -1)
        {
            outStream.write(buffer,0,len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * �������������ص�Э�飬�õ�ʡ��
     * @param inStream
     * @return
     * @throws Exception
     */
    public static List<Province> parseXML(InputStream inStream) throws Exception
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
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if("city".equals(parser.getName()))
                    {
                        provinces.add(province);
                        province = null;
                    }
                    break;
            }
            eventType = parser.next();
        }
        return provinces;
    }
}
