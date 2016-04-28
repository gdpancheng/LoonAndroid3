package com.google.dexmaker;

import java.io.Serializable;
import java.lang.reflect.Method;


/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014年12月7日
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class MethodEntity implements Serializable {
	
	public String name;
	public Class clazz;
	public Class[] params;
	transient public Method method;
}
