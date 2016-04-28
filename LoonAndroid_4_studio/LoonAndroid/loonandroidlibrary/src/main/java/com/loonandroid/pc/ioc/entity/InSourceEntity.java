package com.loonandroid.pc.ioc.entity;

import java.lang.reflect.Field;

import com.loonandroid.pc.inject.InjectDrawable;
import com.loonandroid.pc.inject.InjectResouceType;
import com.loonandroid.pc.inject.InjectString;
import com.loonandroid.pc.inject.InjectStrings;
import com.loonandroid.pc.ioc.Ioc;
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
 * 资源解析 TODO(这里用一句话描述这个类的作用)
 * 
 * @author gdpancheng@gmail.com 2014-11-13 下午6:29:57
 */
public class InSourceEntity extends Invoker implements InjectInvoker {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 3155779967169475419L;

	private int id;
	private String fieldName;
	private int resourceType = -1;

	public InSourceEntity(int resourceType) {
		this.resourceType = resourceType;
	}

	@Override
	public void invoke(Object beanObject, Object... args) {
		InjectResouceType<?> resouce = null;
		switch (resourceType) {
		case LoonConstant.Number.RESOURCE_DRAWABLE:
			resouce = new InjectDrawable();
			break;
		case LoonConstant.Number.RESOURCE_STRING:
			resouce = new InjectString();
			break;
		case LoonConstant.Number.RESOURCE_STRINGS:
			resouce = new InjectStrings();
			break;
		}
		if (resouce == null) {
			Ioc.getIoc().getLogger().e(clazz.getSimpleName() + " 对象 " + fieldName + " 变量类型不对\n");
			this.object = null;
			return;
		}
		if (this.object == null||this.object.get()==null) {
            Ioc.getIoc().getLogger().e("当前上下文已被回收");
            return;
        }
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			Object value = resouce.getResouce(id, fieldName);
			if (value == null || !field.getType().isAssignableFrom(value.getClass())) {
				Ioc.getIoc().getLogger().e(clazz.getSimpleName() + " 对象 " + field.getName() + " 赋值不对 请检查\n");
				this.object = null;
				return;
			}
			field.setAccessible(true);
			field.set(object.get(), value);
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.object = null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
}
