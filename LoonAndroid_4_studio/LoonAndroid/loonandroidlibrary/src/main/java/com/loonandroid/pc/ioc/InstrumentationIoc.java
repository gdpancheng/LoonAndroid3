package com.loonandroid.pc.ioc;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.loonandroid.pc.core.AnalysisManager;
import com.loonandroid.pc.interfaces.BeanFactory;
import com.loonandroid.pc.util.LoonConstant;

/**
 * 替换掉系统类
 * 
 * @author gdpancheng@gmail.com 2014-2-25 下午11:13:31
 */
public class InstrumentationIoc extends Instrumentation {

	@Override
	public void callActivityOnCreate(Activity activity, Bundle bundle) {
		long time = System.currentTimeMillis();
		// ----------------------------------------------------------------------------------------
		LayoutInflater.from(activity).setFactory(new XmlFactory(activity));
		// --------------------------------------------------------------
		// 清除了所有动画
		activity.overridePendingTransition(0, 0);
		// --------------------------------------------------------------
		AnalysisManager.analysisCreateBefore(activity, bundle);
		// --------------------------------------------------------------
		super.callActivityOnCreate(activity, bundle);
		// --------------------------------------------------------------
		AnalysisManager.analysisCreateAfter(activity);
		// --------------------------------------------------------------
		Ioc.getIoc().getLogger().d(activity + " 布局设置耗时 View组件，适配器，fragment解析 等耗时 " + (System.currentTimeMillis() - time));
	}

	@Override
	public void callActivityOnNewIntent(Activity activity, Intent intent) {
		AnalysisManager.analysisProcess(activity, LoonConstant.Number.ACTIVITY_ONNEW,intent);
		super.callActivityOnNewIntent(activity, intent);
	}

	@Override
	public void callActivityOnSaveInstanceState(Activity activity, Bundle outState) {
		// 保存一些数据
		super.callActivityOnSaveInstanceState(activity, outState);
	}

	@Override
	public void callActivityOnPostCreate(Activity activity, Bundle icicle) {
		super.callActivityOnPostCreate(activity, icicle);
	}

	@Override
	public void callActivityOnRestoreInstanceState(Activity activity, Bundle savedInstanceState) {
		// 恢复一些数据
		super.callActivityOnRestoreInstanceState(activity, savedInstanceState);
	}

	@Override
	public void callActivityOnUserLeaving(Activity activity) {
		// 实现了跳转出去以后 显示顶部通知
		// http://www.blogjava.net/lihao336/archive/2010/11/22/338677.html
		// 用户按下Home键将程序置于后台运行或者应用启动其他activity，比如系统浏览器，短信等，需要向系统发送通知，用户做完别的操作后，点击通知栏，回到应用。
		// 问题一
		// 在什么时机发送通知？
		// 用户按下Home的事件在应用层时捕捉不到的，因此只能从activity生命周期方法着手。
		super.callActivityOnUserLeaving(activity);
	}

	@Override
	public void callActivityOnPause(Activity activity) {
		AnalysisManager.analysisProcess(activity, LoonConstant.Number.ACTIVITY_PAUSE);
		super.callActivityOnPause(activity);
	}

	@Override
	public void callActivityOnResume(Activity activity) {
		AnalysisManager.analysisProcess(activity, LoonConstant.Number.ACTIVITY_RESUME);
		super.callActivityOnResume(activity);
	}

	@Override
	public void callActivityOnRestart(Activity activity) {
		AnalysisManager.analysisProcess(activity, LoonConstant.Number.ACTIVITY_RESTART);
		super.callActivityOnRestart(activity);
	}

	@Override
	public void callActivityOnStart(Activity activity) {
		AnalysisManager.analysisProcess(activity, LoonConstant.Number.ACTIVITY_START);
		super.callActivityOnStart(activity);
	}

	@Override
	public void callActivityOnStop(Activity activity) {
		AnalysisManager.analysisProcess(activity, LoonConstant.Number.ACTIVITY_STOP);
		super.callActivityOnStop(activity);
	}

	@Override
	public void callActivityOnDestroy(Activity activity) {
		activity.overridePendingTransition(0, 0);
		// ------------------------------------------------------------------------------------
		// 记录当前的activity
		AnalysisManager.analysisProcess(activity, LoonConstant.Number.ACTIVITY_DESTROY);
		super.callActivityOnDestroy(activity);
	}

	@Override
	public Activity newActivity(ClassLoader cl, String className, Intent intent) throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		Class<?> clazz = cl.loadClass(className);
		if (!AnalysisManager.creatActivityProxy(clazz, false)) {
			return (Activity) cl.loadClass(className).newInstance();
		}
		return (Activity) BeanFactory.instance(clazz, null, new LoonComponentHandler());
	}

}
