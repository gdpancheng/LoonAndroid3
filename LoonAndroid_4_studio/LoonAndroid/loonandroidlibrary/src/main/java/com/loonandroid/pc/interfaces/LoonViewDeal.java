package com.loonandroid.pc.interfaces;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.loonandroid.pc.adapter.ViewHolder;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014年12月8日
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public abstract class LoonViewDeal<T> {

	public  int getCount(){
		return 0;
	};
	
	public abstract boolean dealView(T item, ViewHolder viewHold);

	@SuppressWarnings("unchecked")
    public Class<?>[] getClazz() {
		Type t = getClass().getGenericSuperclass();
		Class<?>[] classes = null;
		if (t instanceof ParameterizedType) {
			Type[] types = ((ParameterizedType) t).getActualTypeArguments();
			classes = new Class[types.length];
			for (int i = 0; i < types.length; i++) {
				if (types[i] instanceof Class) {
					classes[i] = (Class<?>) types[i];
					continue;
				}
				classes[i] = (Class<T>) ((ParameterizedType) types[i]).getRawType();
			}
		}
		return classes;
	}
}
