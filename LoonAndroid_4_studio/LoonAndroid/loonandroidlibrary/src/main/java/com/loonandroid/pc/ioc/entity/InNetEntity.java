package com.loonandroid.pc.ioc.entity;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-10
 * Copyright @ 2014 BU
 * Description: 类描述
 * History:
 */

/**
 * 停止
 * 
 * @author gdpancheng@gmail.com 2014-11-13 下午6:31:18
 */
public class InNetEntity extends Invoker implements InjectInvoker {

	private Class netClass;

	public Class getNetClass() {
		return netClass;
	}

	public void setNetClass(Class netClass) {
		this.netClass = netClass;
	}

}
