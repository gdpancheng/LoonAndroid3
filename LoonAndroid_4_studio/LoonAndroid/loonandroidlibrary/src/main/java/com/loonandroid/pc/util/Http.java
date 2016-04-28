package com.loonandroid.pc.util;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

import com.loonandroid.pc.annotation.InNet;
import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.net.IocHttpInvocationHandler;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2015年1月7日
 * Copyright @ 2015 BU
 * Description: 类描述
 *
 * History:
 */
public class Http<T> {

	private Class<T> clazz;
	private T proxy;

	public Http(Class<T> clazz) {
		this.clazz = clazz;
	}

	@SuppressWarnings("unchecked")
	public T u(Object object) {
		if (clazz == null) {
			Ioc.getIoc().getLogger().e("请创建Http对象");
			return null;
		}

		InNet inNet = clazz.getAnnotation(InNet.class);
		if (inNet == null || inNet.value() == null) {
			return null;
		}

		if (proxy == null) {
			IocHttpInvocationHandler netInvoHandler = new IocHttpInvocationHandler();
			netInvoHandler.setObject(object);
			proxy = (T) Proxy.newProxyInstance(Http.class.getClassLoader(), new Class[] { clazz}, netInvoHandler);
		} else {
			try {
				Field field = proxy.getClass().getSuperclass().getDeclaredField("h");
				field.setAccessible(true);
				IocHttpInvocationHandler netInvoHandler = (IocHttpInvocationHandler) field.get(proxy);
				netInvoHandler.setObject(object);
				field.set(proxy, netInvoHandler);
			} catch (Exception e) {
				Ioc.getIoc().getLogger().e("代理创建失败，无法使用接口");
			}
		}
		return proxy;
	}
}
