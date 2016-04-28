package com.loonandroid.pc.inject;

import android.view.View;

import com.loonandroid.pc.ioc.kernel.KernelClass;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-13
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class Inject<T> {

	/** type */
	protected Class<?> type;

	protected Object object = null;

	/**
	 * 
	 */
	public Inject() {
		type = KernelClass.componentClass(getClass().getGenericSuperclass());
	}

	public Inject<T> setObject(Object object) {
		this.object = object;
		return this;
	}

	public Object getObject() {
		return object;
	}
	
	public void setContentView(View object, int id) {
	}

}
