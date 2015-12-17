package com.loonandroid.pc.core;

/**
 * 解析接口类
 * 	<br>-----------------------------------------------
 * <br>oooO...............
 * <br>(....) ... Oooo...
 * <br>.\..(.....(.....).......
 * <br>..\_)..... )../........
 * <br>.......... (_/..........
 * <br>微信 gdpancheng
 * <br>-----------------------------------------------
 * @author gdpancheng@gmail.com 2014年12月21日 上午12:50:59
 */
public interface Analysis<T> {
	T process();
	void setClass(Class<?> clazz);
}
