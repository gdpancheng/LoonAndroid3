package com.loonandroid.pc.interfaces;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;

import com.google.dexmaker.Constants;
import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.ioc.config.LoonConfig;


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
public class BeanFactory {

	/**
	 * 判断是否依赖包
	 * TODO(这里用一句话描述这个方法的作用)
	 * @author gdpancheng@gmail.com 2015年1月11日 下午4:33:48
	 * @param clazz
	 * @return
	 * @return T
	 */
	private static <T> T checkDepend(Class<T> clazz) {
		if (!LoonConfig.instance().isDepend()) {
			Ioc.getIoc().getLogger().e("依赖包dex.jar不存在，所有拦截功能将无法使用");
			try {
				return clazz.newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}


	/**
	 * 创建一个继承Fragment的代理对象
	 * @author gdpancheng@gmail.com 2014年12月21日 上午12:39:01
	 * @param clazz
	 * @param params 要创建的Fragment的参数
	 * @return T
	 */
	public static <T> T instanceFragment(Class<T> clazz, Object... params) {
		T t = checkDepend(clazz);
		if (null != t) {
			return t;
		}
		return Bean.instanceFragment(clazz, params);
	}

	/**
	 * 
	 * @author gdpancheng@gmail.com 2015年1月11日 下午4:34:42
	 * @param clazz
	 * @param infterface
	 * @param handler
	 * @param params
	 * @return
	 * @return T
	 */
	public static <T> T instance(Class<T> clazz, Class<?>[] infterface, InvocationHandler handler, Object... params) {
		T t = checkDepend(clazz);
		if (null != t) {
			return t;
		}
		return Bean.instance(clazz, infterface, handler, params);
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
	public static <T> Class<? extends T> load(Class<T> clazz, Class[] infterface, InvocationHandler handler) {
		T t = checkDepend(clazz);
		if (null != t) {
			return clazz;
		}
		return Bean.load(clazz, infterface, handler);
	}

	public static <T> T load(Class<T> clazz, Class[] infterface, Class[] clazzes, Object[] params, InvocationHandler handler) {
		T t = checkDepend(clazz);
		if (null != t) {
			return t;
		}
		return Bean.load(clazz, infterface, clazzes, params, handler);
	}

	public static <T> Class<? extends T> load(Class<T> clazz) {
		T t = checkDepend(clazz);
		if (null != t) {
			return clazz;
		}
		return Bean.load(clazz);
	}

	
	public static  void setAnnotation(Class<?  extends Annotation> clazz) {
		Object t = checkDepend(clazz);
		if (null != t) {
			return ;
		}
		Constants.annotation.add(clazz);
	}
}
