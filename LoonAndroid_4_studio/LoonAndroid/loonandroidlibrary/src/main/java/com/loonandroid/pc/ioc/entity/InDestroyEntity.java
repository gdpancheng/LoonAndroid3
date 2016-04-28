package com.loonandroid.pc.ioc.entity;
/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-10
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */


/**
 * 销毁
 * @author gdpancheng@gmail.com 2014-11-13 下午6:25:32
 */
public class InDestroyEntity extends Invoker implements InjectInvoker {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
    private static final long serialVersionUID = 3131747706267934997L;

	@Override
	public void invoke(Object beanObject, Object... args) {
		super.invoke(beanObject, args);
	}
}
