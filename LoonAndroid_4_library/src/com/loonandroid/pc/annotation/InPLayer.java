package com.loonandroid.pc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.loonandroid.pc.util.LoonConstant;

/**
 * 父布局中的设置
 * @author gdpancheng@gmail.com 2013-10-22 下午1:33:10
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface InPLayer {

	/**
	 * layout
	 * @author gdpancheng@gmail.com 2014-1-21 下午1:03:13
	 * @return
	 * @return int
	 */
	public int value() default LoonConstant.Number.ID_NONE;
	
	/**
	 * 是否全屏 默认不是全屏
	 * @author gdpancheng@gmail.com 2014-1-21 下午1:03:01
	 * @return
	 * @return boolean
	 */
	public boolean isFull() default false;
	/**
	 * 是否有标题栏 默认有标题栏
	 * @author gdpancheng@gmail.com 2014-1-21 下午1:14:48
	 * @return boolean
	 */
	public boolean isTitle() default false;
}