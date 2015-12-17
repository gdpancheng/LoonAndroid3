package com.loonandroid.pc.plug;

import java.lang.reflect.Method;

/*
* Author: pancheng Email:gdpancheng@gmail.com
* Created Date:2015年11月6日
* Copyright @ 2015 BU
* Description: 类描述
*
* History:
*/
public interface PlugInCallBack {
	public Object callback(Object object,Method method,Object[] args);
}
