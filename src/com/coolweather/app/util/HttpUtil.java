package com.coolweather.app.util;

import java.io.InputStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpUtil {
	public static void sendHttpRequest(final String address , final HttpCallbackListener listener){
		new Thread(new Runnable() {
			private volatile boolean stop = false;
			public void stopThread() {
	            this.stop = true;
	        }
			
			@Override
			public void run() {
				while (!stop) {
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
						System.out.println("66666666666666666666");
						//in.close();
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
			}
		}).start();
	}
}
