package com.loonandroid.pc.ioc.entity;

public interface InjectInvoker{

	public  <T> void invoke(T beanObject,Object... args);
}
