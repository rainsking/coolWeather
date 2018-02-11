package com.coolweather.app.util;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil{
	private static boolean iswook = false;
	public static void sendHttpRequest(final String address , final HttpCallbackListener listener){
		iswook = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				if(iswook) {
					HttpURLConnection connection = null;
					try {
						URL url = new URL(address);
						connection = (HttpURLConnection) url.openConnection();
						connection.setRequestMethod("GET");
						connection.setConnectTimeout(8000);
						connection.setReadTimeout(8000);
						InputStream in = connection.getInputStream();
						if(listener != null){
							listener.onFinish(in);
						}
						in.close();
						
					} catch (Exception e) {
						if(listener != null){
							listener.onError(e);
						}
					} finally {
						if(connection != null){
							connection.disconnect();
						}
						iswook = false;
					}
				}
			}
		}).start();
	}
	
	public static void stop() {  
	    if (iswook) {  
	    	iswook = false;  
	    }  
	}  
}
