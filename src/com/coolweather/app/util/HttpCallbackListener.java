package com.coolweather.app.util;

import java.io.InputStream;

public interface HttpCallbackListener {
	void onFinish(InputStream inStream) throws Exception;
	void onError(Exception e);
}
