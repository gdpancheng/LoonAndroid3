package com.loonandroid.pc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 内部类的组件注解 由于静态导致整个框架的稳定性 在此不建议使用
 * @author gdpancheng@gmail.com 2013-10-22 下午1:34:43
 */
//@Deprecated
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface InBack {
}