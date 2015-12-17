package com.loonandroid.pc.ioc.entity;

import java.lang.reflect.Field;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

import com.loonandroid.pc.core.AnalysisManager;
import com.loonandroid.pc.interfaces.BeanFactory;
import com.loonandroid.pc.interfaces.LoonAdapter;
import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.ioc.IocAdapterHandler;
import com.loonandroid.pc.ioc.IocCustomAdapterHandler;
import com.loonandroid.pc.ioc.IocFragmentHandler;
import com.loonandroid.pc.util.LoonConstant;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-10
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */

/**
 * adapter 以及 组件的注解
 * 
 * @author gdpancheng@gmail.com 2014-11-13 下午6:24:46
 */
public class InBeanEntity extends Invoker implements InjectInvoker {

	private String fieldName;
	private int id;
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 6649469240581758392L;

	@Override
	public <T> void invoke(T beanObject, Object... args) {
		try {
			Context context = null;
			if (Context.class.isAssignableFrom(beanObject.getClass())) {
				context = (Context) beanObject;
			} else if (View.class.isAssignableFrom(beanObject.getClass())) {
				context = ((View) beanObject).getContext();
			}

			if (context == null) {
				Ioc.getIoc().getLogger().e(" 无法获取到当前页面的上下文 ");
				return;
			}
			if (this.object == null||this.object.get()==null) {
	            Ioc.getIoc().getLogger().e("当前上下文已被回收");
	            return;
            }
			Field field = clazz.getDeclaredField(fieldName);
			Class type = field.getType();
			Class v4Fragment = AnalysisManager.getClass(LoonConstant.ClassName.V4_FRAGMENT);
			Class fragment = AnalysisManager.getClass(LoonConstant.ClassName.FRAGMENT);
			// ------------------------------------------------------------------------------------------
			// 适配器基类
			if (type == BaseAdapter.class) {
				IocAdapterHandler invocationHandler = new IocAdapterHandler();
				invocationHandler.setId(id);
				invocationHandler.setInflater(LayoutInflater.from(context));
				BaseAdapter adapter = BeanFactory.instance(type, new Class[] { LoonAdapter.class }, invocationHandler);
				field.setAccessible(true);
				field.set(object.get() != null ? object.get() : beanObject, adapter);
			} else if (BaseAdapter.class.isAssignableFrom(type)) {
				IocCustomAdapterHandler invocationHandler = new IocCustomAdapterHandler();
				invocationHandler.setInflater(LayoutInflater.from(context));
				invocationHandler.setId(id);
				BaseAdapter adapter = BeanFactory.instance(type, new Class[] { LoonAdapter.class }, invocationHandler);
				invocationHandler.setAdapter(adapter);
				field.setAccessible(true);
				field.set(object.get() != null ? object.get() : beanObject, adapter);
			} else if ((v4Fragment != null && v4Fragment.isAssignableFrom(type)) || fragment != null && fragment.isAssignableFrom(type)) {
				IocFragmentHandler invocationHandler = new IocFragmentHandler();
				Object mFragment = BeanFactory.instanceFragment(type);
				field.setAccessible(true);
				field.set(object.get() != null ? object.get() : beanObject, mFragment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.object = null;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "InBeanEntity [fieldName=" + fieldName + ", id=" + id + "]";
	}

}
