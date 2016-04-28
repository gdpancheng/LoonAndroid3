package com.loonandroid.pc.ioc.entity;

/*
 * Author: pancheng Email:gdpancheng@gmail.com
 * Created Date:2015年11月10日
 * Copyright @ 2015 BU
 * Description: 类描述
 *
 * History:
 */
public class InPlugInitEntity extends Invoker implements InjectInvoker {

	private Object[] parameters;
	private String[] fields;

	public Object[] getParameters() {
		return parameters;
	}

	public void setParameters(Object[] parameters) {
		this.parameters = parameters;
	}

	public String[] getFields() {
		return fields;
	}

	public void setFields(String[] fields) {
		this.fields = fields;
	}
	
	@Override
	public <T> void invoke(T beanObject, Object... args) {
	    super.invoke(beanObject, args);
	}
}
