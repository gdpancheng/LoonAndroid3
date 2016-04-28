package com.loonandroid.pc.ioc.entity;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.ioc.kernel.KernelReflect;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-10
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */

/**
 * 内部类的初始化后注入
 * 
 * @author gdpancheng@gmail.com 2014-11-13 下午6:24:57
 */
public class InAllEntity extends Invoker implements InjectInvoker {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 4821497192743018067L;

	private boolean isActivity;
	private String fieldName;
	private Class<?> innerClass;
	private ArrayList<InViewEntity> viewEntities;
	private ArrayList<InSourceEntity> resourceEntities;
	
	public InAllEntity(boolean isActivity) {
		this.isActivity = isActivity;
	}

	@Override
	public void invoke(Object beanObject, Object... args) {
		try {
			if (this.object == null || this.object.get() == null) {
				Ioc.getIoc().getLogger().e("当前上下文已被回收");
				return;
			}
			// ------------------------------------------------------------------------------
			// 拿到inAll 并初始化赋值
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			// 自动注入一个对象
			Object views = field.get(this.object.get());
			if (null == views) {
				if (innerClass.getDeclaringClass() == null) {
					views = innerClass.newInstance();
				} else {
					if (innerClass.getModifiers() == Modifier.STATIC) {
						Constructor<?>[] c = innerClass.getDeclaredConstructors();
						c[0].setAccessible(true);
						views = c[0].newInstance();
					} else {
						Constructor<?>[] c = innerClass.getDeclaredConstructors();
						c[0].setAccessible(true);
						views = c[0].newInstance(this.object.get());
					}
				}
				KernelReflect.set(this.object.get(), field, views);
			}
			// ------------------------------------------------------------------------------
			// 对InAll中的View变量进行赋值
			if (viewEntities != null) {
				for (InViewEntity viewEntity : viewEntities) {
					viewEntity.setObject(views);
					viewEntity.setPobject(this.object.get());
					viewEntity.invoke(beanObject);
				}
			}
			// 对InAll中的资源变量进行赋值
			if (resourceEntities != null) {
				for (InSourceEntity resourceEntity : resourceEntities) {
					resourceEntity.setObject(views);
					resourceEntity.invoke(beanObject);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.object = null;
	}

	public boolean isActivity() {
		return isActivity;
	}

	public void setActivity(boolean isActivity) {
		this.isActivity = isActivity;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Class<?> getClazz() {
		return innerClass;
	}

	public void setInnerClass(Class<?> innerClass) {
		this.innerClass = innerClass;
	}

	public ArrayList<InViewEntity> getViewEntities() {
		return viewEntities;
	}

	public void setViewEntities(ArrayList<InViewEntity> viewEntities) {
		this.viewEntities = viewEntities;
	}

	public ArrayList<InSourceEntity> getResourceEntities() {
		return resourceEntities;
	}

	public void setResourceEntities(ArrayList<InSourceEntity> resourceEntities) {
		this.resourceEntities = resourceEntities;
	}
}
