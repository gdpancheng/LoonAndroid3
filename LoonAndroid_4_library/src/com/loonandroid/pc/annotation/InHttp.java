package com.loonandroid.pc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.loonandroid.pc.util.LoonConstant;

/**
 * 网络请求注解类 用在网络请求的回调方法上
 * @author gdpancheng@gmail.com 2013-9-21 下午1:43:35
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InHttp {
	int[] value() default {LoonConstant.Number.ID_NONE};
}
