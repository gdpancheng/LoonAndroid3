/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-6-21 下午2:45:55
 */
package com.loonandroid.pc.inject;

import android.view.View;

/**
 * 反射器
 * 
 * @author gdpancheng@gmail.com 2014-11-13 上午12:24:08
 */
public interface InjectExcutor<T> {

	public void setContentView(T object, int id);

	public View loadView(T object, int id);

	public View findViewById(T object, int id);

	public View findViewById(int id);
}
