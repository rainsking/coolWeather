package com.coolweather.app.thread;

import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;

public class XMLAsyncTask extends AsyncTask<Integer, Integer, String>{

	private TextView textView;
	private ListView listView;
	
	public XMLAsyncTask(TextView textView, ListView listView) {
		super();
		this.textView = textView;
		this.listView = listView;
	}
	
	//�÷�����������UI�߳���,��Ҫ�����첽����,ͨ������publishProgress()����  
    //����quertFromServer��UI���в���  
	@Override
	protected String doInBackground(Integer... params) {
		
		return null;
	}

	@Override
	protected void onPreExecute() {
		//textView.sett
	}
}
