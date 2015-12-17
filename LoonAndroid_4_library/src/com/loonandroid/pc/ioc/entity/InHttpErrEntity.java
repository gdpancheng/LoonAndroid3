package com.loonandroid.pc.ioc.entity;

import java.util.Set;
/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-10
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */

/**
 * 网络请求失败
 * @author gdpancheng@gmail.com 2014-11-13 下午6:25:41
 */
public class InHttpErrEntity extends Invoker implements InjectInvoker{

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
    private static final long serialVersionUID = -1717073967879927637L;
	private Set<Integer> key;
	
	@Override
	public void invoke(Object beanObject, Object... args) {

	}


	public Set<Integer> getKey() {
		return key;
	}

	public void setKey(Set<Integer> key) {
		this.key = key;
	}
}
