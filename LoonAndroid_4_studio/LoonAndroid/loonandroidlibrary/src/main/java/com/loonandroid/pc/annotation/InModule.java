package com.loonandroid.pc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.loonandroid.pc.util.LoonConstant;

/**
 * 用在组件上 标记了 当前类是组件 参数为组件布局 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月21日 上午12:41:27
 */
@Target({ ElementType.TYPE, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface InModule {

	/**
	 * layout
	 * 
	 * @author gdpancheng@gmail.com 2014-1-21 下午1:03:13
	 * @return int
	 */
	public int value() default LoonConstant.Number.ID_NONE;

	/**
	 * 组件在父布局中的位置
	 * 
	 * @author gdpancheng@gmail.com 2014年12月17日 下午3:09:09
	 * @return int
	 */
	public int parent() default LoonConstant.Number.ID_NONE;
}