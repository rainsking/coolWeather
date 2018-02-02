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
	
	//该方法不运行在UI线程中,主要用于异步操作,通过调用publishProgress()方法  
    //触发quertFromServer对UI进行操作  
	@Override
	protected String doInBackground(Integer... params) {
		
		return null;
	}

	@Override
	protected void onPreExecute() {
		//textView.sett
	}
}
