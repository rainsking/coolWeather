package com.coolweather.app.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.coolweather.app.model.Province;

public class StreamTool {
	//从流中读取数据
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
     * 解析服务器返回的协议，得到省份
     * @param inStream
     * @return
     * @throws Exception
     */
    public static List<Province> parseXML(InputStream inStream) throws Exception
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
