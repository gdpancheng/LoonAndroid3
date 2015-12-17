package com.loonandroid.pc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.loonandroid.pc.util.LoonConstant;
import com.loonandroid.pc.validator.Regex;
import com.loonandroid.pc.validator.VaRule;

/*
 * Author: pancheng Email:gdpancheng@gmail.com
 * Created Date:2015年12月2日
 * Copyright @ 2015 BU
 * Description: 类描述
 * History:
 */

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface InVa {
	/**
	 * 类型 其中VaEntity包含了正则表达式，等等
	 * 
	 * @author gdpancheng@gmail.com 20152年12月2日 下午11:28:22
	 * @return
	 * @return int
	 */
	public Class<? extends VaRule> value() default VaRule.class;

	/**
	 * 验证错误的提示
	 * 
	 * @author gdpancheng@gmail.com 2015年12月2日 下午11:37:33
	 * @return
	 * @return String
	 */
	public String msg() default Regex.EMPTY_STRING;

	/**
	 * 正则表达式
	 * 
	 * @author gdpancheng@gmail.com 2015年12月2日 下午11:38:18
	 * @return String
	 */
	public String reg() default Regex.EMPTY_STRING;

	/**
	 * 索引 
	 * 
	 * @author gdpancheng@gmail.com 2015年12月2日 下午11:39:05
	 * @return int
	 */
	public int index() default LoonConstant.Number.ID_NONE;

	/**
	 * 是否可以为空
	 * 
	 * @author gdpancheng@gmail.com 2015年12月3日 上午12:27:14
	 * @return boolean
	 */
	public boolean empty() default true;

	/**
	 * 最小长度
	 * @author gdpancheng@gmail.com 2015年12月3日 上午11:10:11
	 * @return
	 * @return int
	 */
	public int minLength() default 0;

	/**
	 * 最大长度
	 * @author gdpancheng@gmail.com 2015年12月3日 上午11:10:22
	 * @return
	 * @return int
	 */
	public int maxLength() default Integer.MAX_VALUE;

	/**
	 * 数据类型
	 * 
	 * @author gdpancheng@gmail.com 2014-1-22 下午11:03:22
	 * @return
	 * @return NumberType
	 */
	public StringType type() default StringType.STRING;

	/**
	 * 最小
	 * 
	 * @author gdpancheng@gmail.com 2014-1-22 下午10:58:44
	 * @return
	 * @return double
	 */
	public double gt() default Double.MAX_VALUE;

	/**
	 * 最大
	 * 
	 * @author gdpancheng@gmail.com 2014-1-22 下午10:59:02
	 * @return
	 * @return double
	 */
	public double lt() default Double.MIN_VALUE;

	/**
	 * 等于
	 * 
	 * @author gdpancheng@gmail.com 2014-1-22 下午11:03:43
	 * @return
	 * @return double
	 */
	public double eq() default Double.MAX_VALUE;

	/**
	 * 是否选中
	 * @author gdpancheng@gmail.com 2015年12月3日 上午11:10:59
	 * @return
	 * @return boolean
	 */
	public boolean checked() default true;

	public static enum StringType {
		INTEGER, LONG, DOUBLE,STRING
	}
}
