package com.loonandroid.pc.plug;

import java.util.HashSet;
import java.util.Set;

/*
* Author: pancheng Email:gdpancheng@gmail.com
* Created Date:2015年11月6日
* Copyright @ 2015 BU
* Description: 类描述
*
* History:
*/
public class PlugEntity {

	@Override
    public String toString() {
	    return "PlugEntity [methodName=" + methodName + ", callBack=" + callBack + ", clazz=" + clazz + "]";
    }
	private Set<String> methodName = new HashSet<String>();
	private PlugInCallBack callBack;
	private Class clazz;
	
	public Set<String> getMethodName() {
		return methodName;
	}
	public void addMethodName(String methodName) {
		this.methodName.add(methodName);
	}
	public void addMethodNames(Set<String> methodNames) {
		this.methodName = methodNames;
	}
	public PlugInCallBack getCallBack() {
		return callBack;
	}
	public void setCallBack(PlugInCallBack callBack) {
		this.callBack = callBack;
	}
	public Class getClazz() {
		return clazz;
	}
	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}
}
