package com.android.demo.util;

import java.util.HashMap;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014年12月9日
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class MyHashMap<T,D> extends HashMap {

	@Override
	public MyHashMap put(Object key, Object value) {
	    super.put(key, value);
	    return this;
	}
}
