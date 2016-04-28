package com.loonandroid.pc.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import com.loonandroid.pc.entity.ActivityEntity;
import com.loonandroid.pc.ioc.config.LoonConfig;

/**
 * 解析activity
 * 	<br>-----------------------------------------------
 * <br>oooO...............
 * <br>(....) ... Oooo...
 * <br>.\..(.....(.....).......
 * <br>..\_)..... )../........
 * <br>.......... (_/..........
 * <br>微信 gdpancheng
 * <br>-----------------------------------------------
 * @author gdpancheng@gmail.com 2014年12月21日 上午12:51:15
 */
public class AnalysisAcitity extends AnalysisCore<ActivityEntity> implements Analysis<ActivityEntity> {

	@Override
	public ActivityEntity process() {
		Class<?> clazz = this.clazz;
		Method[] methods = clazz.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			if (methods[i].getAnnotations().length == 0) {
	            continue;
            }
			// -----------------------------------------------------------
			// 解析setcontentView以前和以后
			activityAnalysis(methods[i]);
			// 解析init
			initAnalysis(methods[i]);
			// 解析上下文
			progressAnalysis(methods[i]);
			// 解析方法的注解（网络请求/以及点击事件的方法）
			methodAnalysis(methods[i]);
		}
		//变量以及点击事件等解析
		variableAnalysis(clazz);
		//--------------------------------------------------------------------------------
		//判断是否属于组建，如果是属于组件 那么自动对齐创建动态代理
		if (LoonConfig.instance().isDepend()) {
			AnalysisManager.creatActivityProxy(clazz,true);
        }
		return object;
	}
}
