package com.loonandroid.pc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.loonandroid.pc.listener.OnClick;
/**
 * 内部类的组件注解 由于静态导致整个框架的稳定性 在此不建议使用
 * @author gdpancheng@gmail.com 2013-10-22 下午1:34:43
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InAll {
	public InBinder value() default @InBinder(method ="",listener = OnClick.class);
}