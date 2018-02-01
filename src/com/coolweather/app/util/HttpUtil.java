package com.coolweather.app.util;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpUtil {
	public static void sendHttpRequest(final String address , final HttpCallbackListener listener){
		new Thread(new Runnable() {
			@Override
			public void run() {
				HttpsURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpsURLConnection) url.openConnection();
					connection.setRequestMethod("GET");
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					InputStream in = connection.getInputStream();
			
					if(listener != null){
						listener.onFinish(in);
					}
				} catch (Exception e) {
					if(listener != null){
						listener.onError(e);
					}
				} finally {
					if(connection != null){
						connection.disconnect();
					}
				}
			}
		}).start();
	}
}
