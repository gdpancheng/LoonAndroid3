package com.loonandroid.pc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.loonandroid.pc.util.LoonConstant;

@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface InGet {
	
	public String value() default "";

	public int type() default LoonConstant.Number.ID_NONE;
	
}