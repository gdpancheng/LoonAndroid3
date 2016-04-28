package com.loonandroid.pc.net;

import org.json.JSONException;
import org.json.JSONObject;


/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014年12月25日
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class IocListener {

	private ImageListener imageListener = new ImageListener();
	private static IocListener listener;

	private IocHttpListener<?> httpListener = null;
	

	private void sync(NetConfig config) {
		String params = config.getParam();
		if (params.length() == 0 && config.getParams() != null) {
			for (String key : config.getParams().keySet()) {
				if (params != null && params.trim().length() > 0) {
					params = params + "&";
				}
				params = params + key + "=" + config.getParams().get(key);
			}
		}
	}

	public static IocListener newInstance() {
		if (listener == null) {
			listener = new IocListener();
		}
		return listener;
	}

	public void registImageListener(ImageListener imageListener) {
		this.imageListener = imageListener;
	};

	public ImageListener getImageListener() {
		return imageListener;
	}

	public IocHttpListener<?> getHttpListener() {
		return httpListener;
	}

	public void setHttpListener(IocHttpListener<?> httpListener) {
		this.httpListener = httpListener;
	}
}
