package com.loonandroid.pc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.loonandroid.pc.util.LoonConstant;
/**
 * activity生命周期注解类
 * 
 * @author gdpancheng@gmail.com 2013-10-22 下午1:34:43
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InSource {

	/**
	 * @return
	 */
	int value() default LoonConstant.Number.ID_NONE;
}
