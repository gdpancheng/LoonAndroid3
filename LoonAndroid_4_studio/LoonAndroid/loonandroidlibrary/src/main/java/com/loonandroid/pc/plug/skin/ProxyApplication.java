package com.loonandroid.pc.plug.skin;

import android.app.Application;
import android.content.Context;

/*
 * Author: pancheng Email:gdpancheng@gmail.com
 * Created Date:2015年11月13日
 * Copyright @ 2015 BU
 * Description: 类描述
 *
 * History:
 */
public abstract class ProxyApplication extends Application {
	
	protected abstract void initProxyApplication();

	@Override
	protected void attachBaseContext(Context context) {
		super.attachBaseContext(context);
		initProxyApplication();
	}
}
