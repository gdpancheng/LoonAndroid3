package com.loonandroid.pc.plug;

import java.lang.reflect.Method;
import java.util.ArrayList;

import com.google.dexmaker.Constants;
/*
* Author: pancheng Email:gdpancheng@gmail.com
* Created Date:2015年11月6日
* Copyright @ 2015 BU
* Description: 类描述
*
* History:
*/
public class PlugConstants {

	public static final String INTERCEPT_NO ="loonandroid_intercept_no";
	public static final String INTERCEPT_YES ="loonandroid_intercept_yes";
	
	private static final ArrayList<PlugEntity> entities = new ArrayList<PlugEntity>();
	
	public static  PlugInCallBack isIntercept(Object proxy,Method method){
		int count = entities.size();
		for (int i = 0; i < count; i++) {
	        if (entities.get(i).getMethodName().contains(method.getName())) {
	        	Class<?>[] inClasses = proxy.getClass().getSuperclass().getInterfaces();
	        	for (int j = 0; j < inClasses.length; j++) {
	                Class<?> clazz = inClasses[j];
	                if (clazz.getName().equals(entities.get(i).getClazz().getName())) {
	                	return entities.get(i).getCallBack();
                    }
                }
            }
        }
		return null;
	};
	
	public static ArrayList<PlugEntity> getEntities() {
		return entities;
	}

	public static void setPlugIn(PlugParameter parameter) {
		entities.add(parameter.getEntity());
		Constants.method.addAll(parameter.getEntity().getMethodName());
	}
}
