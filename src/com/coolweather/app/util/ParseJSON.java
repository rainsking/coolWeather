package com.coolweather.app.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.coolweather.app.model.Province;

public class ParseJSON {
	/**
     * ��Json��ʽ���ػ�ȡ���µ���Ѷ������Ҫ�ֶ�������JSON�Լ�����н���
     * @return
     * @throws Exception
     */
    public static List<Province> getJSONLastVideos(InputStream inStream) throws Exception
    {
        List<Province> provinces = new ArrayList<Province>();
        //����������������
        byte[] data = StreamTool.read(inStream);
        String json = new String(data);
        //����JSON�������
        JSONArray array = new JSONArray(json);
        //��JSON��������ȡ����
        for(int i=0 ; i < array.length() ; i++)
        {
            //��ȡ�������Ե�ֵ
            JSONObject item = array.getJSONObject(i);
            Province province = new Province();
            province.setId(item.getInt("id"));
            //����Ķ�����뼯�ϵ���
            provinces.add(province);
        }
        return provinces;
    }
}
