package com.loonandroid.pc.interfaces;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.google.dexmaker.Constants;
import com.google.dexmaker.ProxyBuilder;
import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.ioc.IocFragmentHandler;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-18
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */

/**
 * 自动化注入拦截 <br>
 * 创建代理类 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月21日 上午12:36:11
 */
public class Bean {

	/**
	 * 创建一个继承Fragment的代理对象 TODO(这里用一句话描述这个方法的作用)
	 * 
	 * @author gdpancheng@gmail.com 2014年12月21日 上午12:39:01
	 * @param clazz
	 * @param params
	 * @return T
	 */
	protected static <T> T instanceFragment(Class<T> clazz, Object... params) {
		IocFragmentHandler invocationHandler = new IocFragmentHandler();
		Object object = instance(clazz, new Class[] { LoonFragment.class }, invocationHandler, params);
		return (T) object;
	}

	protected static <T> T instance(Class<T> clazz, Class<?>[] infterface, InvocationHandler handler, Object... params) {
		try {
			if (null == params || params.length == 0) {
				return ProxyBuilder.forClass(clazz).handler(handler).implementing(infterface).dexCache(Ioc.getIoc().getApplication().getDir("dex", 0)).build(Ioc.getIoc().getApplication());
			}
			Class<?>[] clazzes = new Class[params.length];
			for (int i = 0; i < params.length; i++) {
				if (params[i] != null) {
					clazzes[i] = params[i].getClass();
				}
			}
			return ProxyBuilder.forClass(clazz).implementing(infterface).constructorArgTypes(clazzes).constructorArgValues(params).handler(handler).dexCache(Ioc.getIoc().getApplication().getDir("dex", 0)).build(Ioc.getIoc().getApplication());
		} catch (Exception e) {
			Ioc.getIoc().getLogger().e("**************无法使用注入" + clazz + "，请使用正常模式**************");
			e.printStackTrace();
		}
		return (T) clazz;
	}

	/**
	 * 类的预加载
	 * 
	 * @author gdpancheng@gmail.com 2014年12月21日 上午12:40:33
	 * @param clazz
	 * @param infterface
	 * @param handler
	 * @return Class<? extends T>
	 */
	protected static <T> Class<? extends T> load(Class<T> clazz, Class[] infterface, InvocationHandler handler) {
		try {
			return ProxyBuilder.forClass(clazz).handler(handler).implementing(infterface).dexCache(Ioc.getIoc().getApplication().getDir("dex", 0)).buildProxyClass(Ioc.getIoc().getApplication());
		} catch (Exception e) {
			Ioc.getIoc().getLogger().e("**************无法使用注入" + clazz + "，请使用正常模式**************");
			e.printStackTrace();
		}
		return clazz;
	}

	protected static <T> T load(Class<T> clazz, Class[] infterface, Class[] clazzes, Object[] params, InvocationHandler handler) {
		try {
			return ProxyBuilder.forClass(clazz).implementing(infterface).constructorArgTypes(clazzes).constructorArgValues(params).handler(handler).dexCache(Ioc.getIoc().getApplication().getDir("dex", 0)).build(Ioc.getIoc().getApplication());
		} catch (Exception e) {
			Ioc.getIoc().getLogger().e("**************无法使用注入" + clazz + "，请使用正常模式**************");
			e.printStackTrace();
		}
		return (T) clazz;
	}

	protected static <T> Class<? extends T> load(Class<T> clazz) {
		return load(clazz, null, null);
	}

	public static void initData() {
		Set<String> method = new HashSet<String>();
		// ----------------------------------------------------------------------
		// adapter
		method.add("getView");
		method.add("getItemId");
		method.add("getItem");
		method.add("getCount");
		method.add("getData");
		method.add("setDeal");
		method.add("setData");
		method.add("dealView");
		// ----------------------------------------------------------------------
		// fragment
		method.add("onCreateView");
		method.add("getSavedInstanceState");
		// ----------------------------------------------------------------------
		// adapter
		method.add("setCallBack");
		method.add("getCallBack");
		// ----------------------------------------------------------------------
		// bus fragment
		method.add("onStart");
		method.add("onStop");
		// ----------------------------------------------------------------------
		// plug
		Constants.setMethods(method);
	}

	public static void setFilter(Class<? extends Annotation>... clazz) {
		Constants.annotation.addAll(Arrays.asList(clazz));
	}
}
