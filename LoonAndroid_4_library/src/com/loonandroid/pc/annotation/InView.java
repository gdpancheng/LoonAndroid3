package com.loonandroid.pc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.loonandroid.pc.listener.OnClick;
import com.loonandroid.pc.util.LoonConstant;

/**
 * 组件的注解
 * 
 * @author gdpancheng@gmail.com 2013-10-22 下午1:34:43
 */
@Target({ ElementType.FIELD, ElementType.LOCAL_VARIABLE })
@Retention(RetentionPolicy.RUNTIME)
public @interface InView {

	public static final int PULL = 1;
	public static final int DOWN = 2;

	public static final int PULL_CLOSE = 3;
	public static final int DOWN_CLOSE = 4;

	public static final int PULL_OPEN = 5;
	public static final int DOWN_OPEN = 6;

	/**
	 * @return
	 */
	public int value() default LoonConstant.Number.ID_NONE;

	/**
	 * 上拉加载更多
	 * 
	 * @author gdpancheng@gmail.com 2014-3-11 下午10:14:24
	 * @return
	 * @return boolean
	 */
	public boolean pull() default false;

	/**
	 * 下拉刷新
	 * 
	 * @author gdpancheng@gmail.com 2014-3-11 下午10:14:43
	 * @return boolean
	 */
	public boolean down() default false;

	/**
	 * @return
	 */
	public InBinder binder() default @InBinder(listener = OnClick.class, method = "");

	/**
	 * adapterView适配器的布局 自动 填充adapter
	 * @author gdpancheng@gmail.com 2014年12月9日 上午12:28:23
	 * @return String
	 */
	public int item() default LoonConstant.Number.ID_NONE;
}