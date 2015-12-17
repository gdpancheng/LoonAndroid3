package com.loonandroid.pc.ioc.entity;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import com.loonandroid.pc.ioc.Ioc;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-13
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class Invoker implements Serializable {

	@Override
	public String toString() {
		return "Invoker [methodName=" + methodName + ", parameter=" + Arrays.toString(parameter) + ", clazz=" + clazz + ", object=" + object + "]";
	}

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -7980272254065221195L;

	String methodName;
	Class<?>[] parameter;
	protected Class<?> clazz;
	/** 不同的地方有不同的含义 **/

	transient protected WeakReference<Object> object;

	public Class[] getParameter() {
		return parameter;
	}

	public void setParameter(Class[] parameter) {
		this.parameter = parameter;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Method getMethod(Object... args) throws NoSuchMethodException {
		if (parameter == null || parameter.length == 0) {
			Method method = clazz.getDeclaredMethod(methodName);
			return method;
		} else {
			Method method = clazz.getDeclaredMethod(methodName, parameter);
			Class<?>[] classes = method.getParameterTypes();
			if (classes != null && classes.length != 0 && args.length > 0 && !classes[0].isAssignableFrom(args[0].getClass())) {
				Ioc.getIoc().getLogger().e(clazz.getSimpleName() + " 方法 " + methodName + " 参数不对");
				return null;
			}
			return method;
		}
	}

	public <T> void invoke(T beanObject, Object... args) {
		try {
			Method method = getMethod(args);
			if (method == null) {
	            return;
            }
			method.setAccessible(true);
			if (parameter == null || parameter.length == 0) {
				method.invoke(beanObject);
			} else {
				method.invoke(beanObject, args);
			}
			//
			// if (parameter == null || parameter.length == 0) {
			// Method method = clazz.getDeclaredMethod(methodName);
			// method.setAccessible(true);
			// method.invoke(beanObject);
			// } else {
			// Method method = clazz.getDeclaredMethod(methodName, parameter);
			// Class<?>[] classes = method.getParameterTypes();
			// if (classes!=null&&classes.length!=0&&args.length>0&&!classes[0].isAssignableFrom(args[0].getClass())) {
			// Ioc.getIoc().getLogger().e(clazz.getSimpleName() + " 方法 " + methodName + " 参数不对");
			// return;
			// }
			//
			// method.setAccessible(true);
			// method.invoke(beanObject, args);
			// }
		} catch (Exception e) {
			if (e instanceof InvocationTargetException) {
				StringWriter buf = new StringWriter();
				PrintWriter w = new PrintWriter(buf);
				e.getCause().printStackTrace(w);
				Ioc.getIoc().getLogger().e(clazz.getSimpleName() + " 方法 " + methodName + "里面出错了 请检查\n" + buf.toString());
			} else {
				Ioc.getIoc().getLogger().e(clazz.getSimpleName() + " 方法 " + methodName + "里面出错了 请检查\n" + e.getMessage());
			}
		}
	}

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public Object getObject() {
		if (object == null) {
			return null;
		}
		return object.get();
	}

	public void setObject(Object object) {
		this.object = new WeakReference<Object>(object);
	}
}
