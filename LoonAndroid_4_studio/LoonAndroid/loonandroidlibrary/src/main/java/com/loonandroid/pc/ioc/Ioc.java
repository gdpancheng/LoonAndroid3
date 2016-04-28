package com.loonandroid.pc.ioc;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.loonandroid.pc.core.AnalysisCore;
import com.loonandroid.pc.core.AnalysisManager;
import com.loonandroid.pc.core.IocAnalysis;
import com.loonandroid.pc.entity.CommonEntity;
import com.loonandroid.pc.handler.Handler_File;
import com.loonandroid.pc.handler.Handler_SharedPreferences;
import com.loonandroid.pc.handler.Handler_System;
import com.loonandroid.pc.inject.InjectViewUtils;
import com.loonandroid.pc.ioc.entity.InSubscribeEntity;
import com.loonandroid.pc.ioc.kernel.KernelObject;
import com.loonandroid.pc.ioc.kernel.KernelReflect;
import com.loonandroid.pc.tinybus.TinyBus;
import com.loonandroid.pc.util.Logger;
import com.loonandroid.pc.util.LoonConstant;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <h1>框架类</h1> 进行了如下操作<br>
 * 1 读取了配置文件<br>
 * 2 aop注入<br>
 * 3 异步遍历授权包名下的类 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 *
 * @author gdpancheng@gmail.com 2014年12月30日 上午9:55:32
 */
public class Ioc {

	/** 全局的上下文 **/
	private Application mApplication;
	/** 框架对象 **/
	private static Ioc ioc;
	/** 日志对象 **/
	private Logger logger = null;
	/** aop切入点 **/
	private InstrumentationIoc instrumentationIoc;
	/** 解析的缓存 包括了各种解析对象，如果没有则从本地缓存取得 **/
	private HashMap<String, CommonEntity> analysis = new HashMap<String, CommonEntity>();
	/** 类的从属关系 **/
	private HashMap<Class<?>, ArrayList<Class<?>>> classes = new HashMap<Class<?>, ArrayList<Class<?>>>();
	/** 启动的activity **/
	private String main = null;
	/** 鉴别版本是否最新 **/
	private long signature = 0;

	/**
	 * 获取框架的唯一对象
	 *
	 * @author gdpancheng@gmail.com 2014-11-10 上午11:42:35
	 * @return Ioc
	 */
	public static Ioc getIoc() {
		if (ioc == null) {
			ioc = new Ioc();
		}
		return ioc;
	}

	/**
	 * 获取日志
	 *
	 * @author gdpancheng@gmail.com 2014-11-10 上午10:41:07
	 * @return Logger
	 */
	public Logger getLogger() {
		return logger;
	}

	/**
	 * 获取全局的上下文
	 *
	 * @author gdpancheng@gmail.com 2014-11-10 上午10:40:46
	 * @return Application
	 */
	public Application getApplication() {
		return mApplication;
	}

	/**
	 * 框架初始化
	 *
	 * @author gdpancheng@gmail.com 2014-11-10 上午10:42:40
	 * @param application
	 * @return void
	 */
	public void init(Application application) {
		this.mApplication = application;
		LayoutInflater.from(this.mApplication).setFactory(new XmlFactory(this.mApplication));
		// ----------------------------------------------------------------------------------
		// 先判断sdk版本，低于4.0 无法使用registerActivityLifecycleCallbacks
		if (Handler_System.hasICS()) {
			application.registerActivityLifecycleCallbacks(callbacks);
		}
		// ----------------------------------------------------------------------------------
		// 先判断当前的apk安装包的时间
		long time = System.currentTimeMillis();
		logger = Logger.getLogger("debug");
		InjectViewUtils.setApplication(application);
		// ----------------------------------------------------------------------------------
		// 先判断当前的apk安装包的时间
		String path = Ioc.getIoc().getApplication().getPackageResourcePath();
		String oldSignature = Handler_SharedPreferences.getValueByName(LoonConstant.DB.HISTORY_KEY, LoonConstant.Key.SIGNATURE_KEY, Handler_SharedPreferences.STRING);
		signature = oldSignature.length() == 0 ? 0 : Long.valueOf(oldSignature);
		long newSignature = (new File(path).lastModified());
		if (signature != 0 && signature != newSignature) {
			Handler_System.cleanFiles(application);
			Handler_System.cleanCustomCache(application.getDir("dex", 0).getAbsolutePath());
		}
		Handler_SharedPreferences.WriteSharedPreferences(LoonConstant.DB.HISTORY_KEY, LoonConstant.Key.SIGNATURE_KEY, newSignature + "");
		// -------------------------------------------------------------------------------------------------
		// 设置程序的入口，保证第一个activity最新被解析，这样可以加快启动速度
		String packageName = ioc.getApplication().getPackageName();
		main = Handler_System.getMainActivity(mApplication, packageName);
		// --------------------------------------------------------------------------------------------------
		// 开启线程来提前遍历需要注入的activity 此处也是框架异步解析的核心入口
		IocAnalysis.asyncAnalysis();
		// --------------------------------------------------------------------------------------------------
		// 整个框架的核心
		// 反射获取mMainThread
		// getBaseContext()返回的是ContextImpl对象 ContextImpl中包含ActivityThread mMainThread这个对象
		Context object = application.getBaseContext();
		Object mainThread = KernelObject.declaredGet(object, "mMainThread");
		// 反射获取mInstrumentation的对象
		Field instrumentationField = KernelReflect.declaredField(mainThread.getClass(), "mInstrumentation");
		instrumentationIoc = new InstrumentationIoc();
		// 自定义一个Instrumentation的子类 并把所有的值给copy进去
		// --------------------------------------------------------------------------------------------------
		// 是否打开兼容模式
		// KernelObject.copy(KernelReflect.get(mainThread, instrumentationField), instrumentationIoc);
		// 再把替换过的Instrumentation重新放进去
		KernelReflect.set(mainThread, instrumentationField, instrumentationIoc);
		// --------------------------------------------------------------------------------------------------
		logger.d("appliaction 加载时间为:" + (System.currentTimeMillis() - time));
	}

	/**
	 * 设置子父关系记录
	 *
	 * @author gdpancheng@gmail.com 2014年12月9日 下午1:51:17
	 * @param clazz
	 * @param superClazz
	 * @return void
	 */
	public void setClass(Class<?> clazz, Class<?> superClazz) {
		if (!classes.containsKey(clazz)) {
			classes.put(clazz, new ArrayList<Class<?>>());
		}
		ArrayList<Class<?>> classList = classes.get(clazz);
		classList.add(superClazz);
	}

	/**
	 * 获取子父关系记录
	 *
	 * @author gdpancheng@gmail.com 2014年12月9日 下午1:50:52
	 * @param clazz
	 * @return ArrayList<Class<?>>
	 */
	public ArrayList<Class<?>> getClasses(Class<?> clazz) {
		if (!classes.containsKey(clazz)) {
			classes.put(clazz, new ArrayList<Class<?>>());
		}
		return classes.get(clazz);
	}

	/**
	 * 缓存解析对象
	 *
	 * @author gdpancheng@gmail.com 2014年12月7日 下午9:27:00
	 * @param name
	 * @param entity
	 * @return void
	 */
	public void setAnalysisEntity(String name, CommonEntity entity) {
		analysis.put(name, entity);
	}

	/**
	 * 取出解析对象
	 *
	 * @author gdpancheng@gmail.com 2014年12月7日 下午9:27:13
	 * @param name
	 * @return
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public <T extends CommonEntity> T getAnalysisEntity(String name) {
		T t = (T) analysis.get(name);
		if (t == null) {
			try {
				t = Handler_File.getObject(name);
				analysis.put(name, t);
			} catch (Exception e) {
			}
		}
		//说明当前类正在解析，等待解析完以后 这个判断主要是为了
		//如下情况，比如你的第一个activity 解析耗时比较长，当你启动
		//第一个activity的时候 解析那个activity的线程还未执行完成
		//为了避免浪费再次去开启一个线程，在此判断等候加载完毕
		if (t == null && IocAnalysis.dealed.containsKey(name)) {
			while (!IocAnalysis.dealed.get(name).isFinish()) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			t = (T) analysis.get(name);
		}
		if (t == null) {
			// ===================================================
			// 同步去处理
			AnalysisCore<?> analysisAcitity = AnalysisManager.distribution(name);
			if (analysisAcitity == null) {
				return null;
			}
			long time = System.currentTimeMillis();
			analysisAcitity.analysis(true);
			Ioc.getIoc().getLogger().d(name + " 同步解析耗时为:" + (System.currentTimeMillis() - time));
			t = (T) Ioc.getIoc().getAnalysisEntity(name);
		}
		return t;
	}

	/**
	 * 判断之前的历史数据和当前版本是否一致
	 *
	 * @author gdpancheng@gmail.com 2014年12月9日 下午4:12:56
	 * @return long
	 */
	public long getSignature() {
		return signature;
	}

	/**
	 * 第一个activity
	 *
	 * @author gdpancheng@gmail.com 2014年12月14日 下午2:21:01
	 * @return Class
	 */
	public String getMain() {
		return main;
	}

	ActivityLifecycleCallbacks callbacks = new ActivityLifecycleCallbacks() {

		@Override
		public void onActivityStopped(Activity activity) {
		}

		@Override
		public void onActivityStarted(Activity activity) {
		}

		@Override
		public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
		}

		@Override
		public void onActivityResumed(Activity activity) {
		}

		@Override
		public void onActivityPaused(Activity activity) {
		}

		@Override
		public void onActivityDestroyed(Activity activity) {
			if (hasBus(activity)) {
				TinyBus bus = TinyBus.from(getApplication());
				bus.unregister(activity);
			}
		}

		@Override
		public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
			if (hasBus(activity)) {
				TinyBus bus = TinyBus.from(getApplication());
				bus.register(activity);
			}
		}

	};

	public static boolean hasBus(Object activity) {
		// -------------------------------------------------------------------------
		// 判断是否为代理类
		// 例如 自动注入的adapter 自动注入的fragment 自动注入的组件
		String name = activity.getClass().getName();
		if (name.endsWith("_Proxy")) {
			name = activity.getClass().getSuperclass().getName();
		}
		long time = System.currentTimeMillis();
		CommonEntity entity = Ioc.getIoc().getAnalysisEntity(name);
		// 如果数据还是为空，则警告
		if (entity == null) {
			Ioc.getIoc().getLogger().e(name + "解析文件不存在，跳过该类，耗时为：" + (System.currentTimeMillis() - time));
			return false;
		}
		// 注入inall
		ArrayList<InSubscribeEntity> all = entity.getInSubscribe();
		if (all.size() > 0) {
			return true;
		}
		return false;
	}
}
