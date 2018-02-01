package com.coolweather.app.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.coolweather.app.model.Province;

public class ParseJSON {
	/**
     * 以Json方式返回获取最新的资讯，不需要手动解析，JSON自己会进行解析
     * @return
     * @throws Exception
     */
    public static List<Province> getJSONLastVideos(InputStream inStream) throws Exception
    {
        List<Province> provinces = new ArrayList<Province>();
        //调用数据流处理方法
        byte[] data = StreamTool.read(inStream);
        String json = new String(data);
        //构建JSON数组对象
        JSONArray array = new JSONArray(json);
        //从JSON数组对象读取数据
        for(int i=0 ; i < array.length() ; i++)
        {
            //获取各个属性的值
            JSONObject item = array.getJSONObject(i);
            Province province = new Province();
            province.setId(item.getInt("id"));
            //构造的对象加入集合当中
            provinces.add(province);
        }
        return provinces;
    }
}
