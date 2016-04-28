package com.loonandroid.pc.inject;

import com.loonandroid.pc.ioc.kernel.KernelClass;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-16
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public abstract class InjectResouceType<T> {
	/** type */
	private Class<?> type;

	/**
	 * 
	 */
	public InjectResouceType() {
		type = KernelClass.componentClass(this.getClass());
	}

	/**
	 * @param id
	 * @param name
	 * @return
	 */
	public T getResouce(int id, String name) {
		id = InjectViewUtils.getResouceId(id, type.getSimpleName(), name);
		if (id == InjectViewUtils.ID_ZERO) {
			return null;
		}

		return getResouce(id);
	}

	/**
	 * @param id
	 * @return
	 */
	protected abstract T getResouce(int id);
}
