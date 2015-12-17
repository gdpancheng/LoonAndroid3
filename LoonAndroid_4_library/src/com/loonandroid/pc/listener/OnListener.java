/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-5-2 上午9:39:01
 */
package com.loonandroid.pc.listener;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import android.view.View;

import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.ioc.kernel.KernelString;

/**
 * @author absir
 * 
 */
public abstract class OnListener implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = -6399843374657588134L;

	/** target */
	transient protected WeakReference<Object> target;

	Class clazz;
	
	Class listener;

	/** method */
	String method;

	Class<?>[] parameterTypes;

	private boolean noArgs;

	transient Method targetMethod = null;

	/**
	 * @param view
	 * @param target
	 * @param method
	 */
	public final void listener(View view, Object target) {
		if (!KernelString.isEmpty(method)) {
			this.target = new WeakReference<Object>(target);
			listener(view);
		}
	}

	/**
	 * @param args
	 * @throws RuntimeException
	 */
	public Object invoke(Object... args) {
		try {
			if (args == null || method == null || target == null || target.get() == null) {
				return null;
			}
			if (targetMethod != null) {
				targetMethod.setAccessible(true);
				if (noArgs) {
					return targetMethod.invoke(target.get());
				} else {
					return targetMethod.invoke(target.get(), args);
				}
			}
		} catch (Exception e) {
			if (e instanceof InvocationTargetException) {
				StringWriter buf = new StringWriter();
				PrintWriter w = new PrintWriter(buf);
				e.getCause().printStackTrace(w);
				Ioc.getIoc().getLogger().e(target.getClass().getSimpleName() + " 方法 " + method + "里面出错了 请检查\n" + buf.toString());
				return null;
			} else {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * @param view
	 */
	protected abstract void listener(View view);

	public Class getClazz() {
		return clazz;
	}

	public void setClazz(Class clazz) {
		this.clazz = clazz;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
		getMethodFromClass();
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject(); // 序列化所有非transient字段,必须是该方法的第一个操作
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject(); // 反序列化所有非transient字段,必须是该方法的第一个操作
		getMethodFromClass();
	}

	protected void getMethodFromClass() {
		try {
			getParameterTypes();
			if (parameterTypes != null) {
				targetMethod = clazz.getDeclaredMethod(method, parameterTypes);
			} else {
				noArgs = true;
				targetMethod = clazz.getDeclaredMethod(method);
			}
		} catch (NoSuchMethodException e) {
			Ioc.getIoc().getLogger().e("在" + clazz + "类中不存在参数为：" + Arrays.toString(parameterTypes) + "方法" + method);
		}
	}

	public Class getListener() {
		return listener;
	}

	public void setListener(Class listener) {
		this.listener = listener;
	}

	public abstract void getParameterTypes();

	@Override
    public String toString() {
	    return "OnListener [clazz=" + clazz + ", listener=" + listener + ", method=" + method + ", parameterTypes=" + Arrays.toString(parameterTypes) + ", noArgs=" + noArgs + "]";
    }
}
