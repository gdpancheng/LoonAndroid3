package com.loonandroid.pc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 注解类 当所有组件即view都绑定以后会调用此注解
 * @author gdpancheng@gmail.com 2013-10-22 下午1:28:11
 */
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface InPullRefresh {
	
	/**
	 * 上拉加载更多
	 * @author gdpancheng@gmail.com 2014-3-11 下午10:14:24
	 * @return
	 * @return boolean
	 */
	public boolean pull() default true;
	
	/**
	 * 下拉刷新
	 * @author gdpancheng@gmail.com 2014-3-11 下午10:14:43
	 * @return boolean
	 */
	public boolean down() default true;
}
