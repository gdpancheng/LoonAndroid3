package com.loonandroid.pc.core;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import android.app.Activity;
import android.app.Service;
import android.os.Bundle;
import android.view.View;

import com.loonandroid.pc.annotation.InBack;
import com.loonandroid.pc.annotation.InUI;
import com.loonandroid.pc.entity.ActivityEntity;
import com.loonandroid.pc.entity.CommonEntity;
import com.loonandroid.pc.entity.OrtherEntity;
import com.loonandroid.pc.interfaces.BeanFactory;
import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.ioc.config.LoonConfig;
import com.loonandroid.pc.ioc.entity.InAfterEntity;
import com.loonandroid.pc.ioc.entity.InAllEntity;
import com.loonandroid.pc.ioc.entity.InBeanEntity;
import com.loonandroid.pc.ioc.entity.InBeforeEntity;
import com.loonandroid.pc.ioc.entity.InLayerEntity;
import com.loonandroid.pc.ioc.entity.InMethodEntity;
import com.loonandroid.pc.ioc.entity.InPLayerEntity;
import com.loonandroid.pc.ioc.entity.InPlugInitEntity;
import com.loonandroid.pc.ioc.entity.InSourceEntity;
import com.loonandroid.pc.ioc.entity.InVaEntity;
import com.loonandroid.pc.ioc.entity.InViewEntity;
import com.loonandroid.pc.ioc.entity.InitEntity;
import com.loonandroid.pc.ioc.entity.Invoker;
import com.loonandroid.pc.ioc.entity.ModuleEntity;
import com.loonandroid.pc.plug.PluginComponent;
import com.loonandroid.pc.util.LoonConstant;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-12
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */

/**
 * <h1>对解析按照类型进行分发</h1><br>
 * 其中类型分为以下几种<br>
 * 1 activity<br>
 * 2 FragmentActivity<br>
 * 3 Fragment<br>
 * 4 FragmentV4<br>
 * 5 普通类<br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月20日 下午11:54:28
 */
public class AnalysisManager {

	/**
	 * 已经加载过的class集合
	 */
	public final static HashMap<String, Class<?>> CLAZZS = new HashMap<String, Class<?>>();

	/**
	 * 解析分发
	 * 
	 * @author gdpancheng@gmail.com 2014-11-12 下午5:16:09
	 * @param <T>
	 * @param className
	 * @return void
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Runnable> T distribution(String className) {
		if (null == className) {
			return null;
		}
		AnalysisCore<?> core = null;
		try {
			Class<?> clazz = getClass(className);
			if (clazz == null) {
				return null;
			}
			// -----------------------------------------------------------------------------------
			// 解析activity
			if (Activity.class.isAssignableFrom(clazz)) {
				core = new AnalysisAcitity();
			}
			// 解析FragmentActivity
			Class<?> fragmentActivity = getClass(LoonConstant.ClassName.F_ACTIVITY);
			if (core == null && fragmentActivity != null && fragmentActivity.isAssignableFrom(clazz)) {
				core = new AnalysisFragmentV4();
			}
			// 解析Fragment
			Class<?> fragmentV4 = getClass(LoonConstant.ClassName.V4_FRAGMENT);
			if (core == null && fragmentV4 != null && fragmentV4.isAssignableFrom(clazz)) {
				core = new AnalysisFragmentV4();
			}
			// 解析Fragment
			Class<?> fragment = getClass(LoonConstant.ClassName.FRAGMENT);
			if (core == null && fragment != null && fragment.isAssignableFrom(clazz)) {
				core = new AnalysisFragment();
			}
			// 解析服务
			if (Service.class.isAssignableFrom(clazz)) {
				core = new AnalysisService();
			}
			// 解析普通类
			if (core == null) {
				core = new AnalysisOrther();
			}
			// 需要解析的类
			core.setClass(clazz);
			// -----------------------------------------------------------------------------------
			// 需要子父继承关系
			extendClazz(clazz);
			// 判断是否是 网络请求
			// -----------------------------------------------------------------------------------
			return (T) core;
		} catch (Exception e) {
			Ioc.getIoc().getLogger().e(e);
		}
		return null;
	}

	/**
	 * 根据类名获取Class的对象
	 * 
	 * @author gdpancheng@gmail.com 2014年12月21日 上午12:00:01
	 * @param className
	 * @return
	 * @return Class<?>
	 */
	public static Class<?> getClass(String className) {
		if (CLAZZS.containsKey(className)) {
			return CLAZZS.get(className);
		}
		Class<?> clazz = null;
		try {
			clazz = AnalysisManager.class.getClassLoader().loadClass(className);
			CLAZZS.put(className, clazz);
		} catch (Exception e) {
			return null;
		}
		return clazz;
	}

	/**
	 * 把所有类的子父关系保存起来,为了保证在注入的时候子类能够覆盖父类 <br>
	 * 子类和父类同名的方法，比如init都可以被调用，要么都是私有的，要么参数不同
	 * 
	 * @author gdpancheng@gmail.com 2014-11-19 上午12:02:42
	 * @param clazz
	 * @return void
	 */
	public static ArrayList<Class<?>> extendClazz(Class<?> clazz) {
		ArrayList<Class<?>> classEntities = Ioc.getIoc().getClasses(clazz);
		while (!classEntities.contains(clazz) && clazz != null && clazz != Object.class && clazz != Activity.class && !clazz.getName().equals("android.support.v4.app.FragmentActivity")) {
			classEntities.add(0, clazz);
			clazz = clazz.getSuperclass();
		}
		return classEntities;
	}

	/**
	 * 在activity的OnCreate之前调用 <br>
	 * 做了如下事情 <br>
	 * 1 在setcontentview之前处理相关事件 <br>
	 * 2 设置setcontentview <br>
	 * 3 加载父布局 <br>
	 * 4 加载布局
	 * 
	 * @author gdpancheng@gmail.com 2014-11-18 下午1:07:32
	 * @param activity
	 * @param bundle
	 * @return void
	 */
	public static void analysisCreateBefore(Activity activity, Bundle bundle) {
		long time = System.currentTimeMillis();
		// ---------------------------------------------------------------------------------------
		// 框架中，子类继承父类，先调用子类中的方法，在调用父类中的方法
		// 顺序为以下几步
		// 1 判断是否有类的关系数据，如果没有说明这些类没有被解析过（这种情况是存在的）
		// 因为解析是后台异步进行的，如果activity启动的快，往往第一个activity启动的时候
		// 第一个activity还没解析完
		// 2 如果没有类的子父关系数据，那么我们先获得该类和该类的父类关系数据，这些数据
		// 是以倒序排列。
		// 3 然后根据这个子父关系，从最上级的父类一级一级的往下解析
		ArrayList<Class<?>> classes = Ioc.getIoc().getClasses(activity.getClass());
		if (null == classes || classes.size() == 0) {
			classes = AnalysisManager.extendClazz(activity.getClass());
		}
		// 这里获得的是这个类和其所有的父类 这些数据第一个为最上级的父类，依次到当前类
		int count = classes.size();
		// 开始遍历这些类，从最上级父类开始
		for (int i = 0; i < count; i++) {
			Class<?> clazz = classes.get(i);
			if (clazz.getName().endsWith("_Proxy")) {
				continue;
			}
			// 判断是否存在缓存的解析数据
			ActivityEntity entity = Ioc.getIoc().getAnalysisEntity(clazz.getName());
			// 如果数据为空，则给出警告
			if (entity == null) {
				Ioc.getIoc().getLogger().e(clazz.getName() + "解析文件不存在，跳过该类，耗时为：" + (System.currentTimeMillis() - time));
				continue;
			}
			// 在加载setcontentview之前调用before方法
			InBeforeEntity before = entity.getBefore();
			if (before != null) {
				before.invoke(activity, bundle);
			}
			// 加载父布局
			InPLayerEntity pLayer = entity.getPLayer();
			if (pLayer != null) {
				pLayer.invoke(activity, bundle);
			}
			// 为了防止当一个页面Activity子类和Activity父类中有多个ID重复时候
			// 事件绑定会出现问题，所以优先处理
			// 调用listener
			ArrayList<InMethodEntity> method = entity.getInMethod();
			if (method != null) {
				int number = method.size();
				for (int j = 0; j < number; j++) {
					if (method.get(j).getClazz().getName().equals(clazz.getName())) {
						continue;
					}
					method.get(j).setObject(activity);
					method.get(j).invoke(activity);
				}
			}
			// 加载布局
			InLayerEntity layer = entity.getLayer();
			if (layer != null) {
				layer.invoke(activity, bundle);
			}
			// 为了防止当一个页面Activity子类和Activity父类中有多个ID重复时候
			// 事件绑定会出现问题，所以优先处理
			if (method != null) {
				int number = method.size();
				for (int j = 0; j < number; j++) {
					if (method.get(j).getClazz().equals(clazz.getName())) {
						continue;
					}
					method.get(j).setObject(activity);
					method.get(j).invoke(activity);
				}
			}
		}
		Ioc.getIoc().getLogger().i("解析完毕，耗时为：" + (System.currentTimeMillis() - time));
	}

	/**
	 * 在activity的OnCreate之后调用
	 * 
	 * @author gdpancheng@gmail.com 2014-11-18 下午1:08:02
	 * @param activity
	 * @param bundle
	 * @return void
	 */
	public static void analysisCreateAfter(Activity activity) {
		long time = System.currentTimeMillis();
		ArrayList<Class<?>> classes = Ioc.getIoc().getClasses(activity.getClass());
		if (null == classes || classes.size() == 0) {
			classes = AnalysisManager.extendClazz(activity.getClass());
		}
		int count = classes.size();
		for (int i = 0; i < count; i++) {
			Class<?> clazz = classes.get(i);
			if (clazz.getName().endsWith("_Proxy")) {
				continue;
			}
			ActivityEntity entity = Ioc.getIoc().getAnalysisEntity(clazz.getName());
			if (entity == null) {
				Ioc.getIoc().getLogger().e(clazz.getName() + "解析文件不存在，跳过该类，耗时为：" + (System.currentTimeMillis() - time));
				continue;
			}
			// 注入inall
			ArrayList<InAllEntity> all = entity.getAll();
			int number = all.size();
			for (int j = 0; j < number; j++) {
				all.get(j).setObject(activity);
				all.get(j).invoke(activity);
			}
			// 注入inview
			ArrayList<InViewEntity> view = entity.getInView();
			number = view.size();
			for (int j = 0; j < number; j++) {
				view.get(j).setObject(activity);
				view.get(j).invoke(activity);
			}
			// 注入inSource
			ArrayList<InSourceEntity> inSources = entity.getResource();
			number = inSources.size();
			for (int j = 0; j < number; j++) {
				inSources.get(j).setObject(activity);
				inSources.get(j).invoke(activity);
			}
			ArrayList<ModuleEntity> modules = entity.getModuleEntity();
			number = modules.size();
			for (int j = 0; j < number; j++) {
				modules.get(j).setInit(false);
				modules.get(j).setObject(activity);
				modules.get(j).invoke(activity);
			}
			// 加载适配器
			ArrayList<InBeanEntity> bean = entity.getInBeanEntity();
			number = bean.size();
			for (int j = 0; j < number; j++) {
				bean.get(j).setObject(activity);
				bean.get(j).invoke(activity);
			}
			// 调用after
			InAfterEntity after = entity.getAfter();
			if (after != null) {
				after.invoke(activity);
			}

			// 如果当前页面有组件 则调用组件的初始化方法
			Class<?>[] clazzs = clazz.getInterfaces();
			if (clazzs != null) {
				for (int j = 0; j < clazzs.length; j++) {
					Class<?> interfaces = clazzs[j];
					if (PluginComponent.class.isAssignableFrom(interfaces)) {
						OrtherEntity orther = Ioc.getIoc().getAnalysisEntity(interfaces.getName());
						if (orther == null) {
							continue;
						}
						ArrayList<InPlugInitEntity> inits = orther.getInitEntity();
						if (inits == null) {
							continue;
						}
						int n = inits.size();
						for (int k = 0; k < n; k++) {
							InPlugInitEntity init = inits.get(k);
							String[] fields = init.getFields();
							Object[] objects = new Object[fields.length];
							for (int l = 0; l < fields.length; l++) {
								try {
									Field field = interfaces.getDeclaredField(fields[l]);
									field.setAccessible(true);
									objects[l] = field.get(activity);
								} catch (Exception e) {
									Ioc.getIoc().getLogger().e("组件" + clazz + "接口中的方法参数注解" + fields[l] + "不存在");
								}
							}
							init.invoke(activity, objects);
						}
					}
				}
			}
			InitEntity init = entity.getInit();
			if (init != null) {
				init.invoke(activity);
			}
			number = modules.size();
			for (int j = 0; j < number; j++) {
				modules.get(j).setInit(true);
				modules.get(j).setObject(activity);
				modules.get(j).invoke(activity);
			}
		}
		Ioc.getIoc().getLogger().i(activity.getClass() + "解析完毕，耗时为：" + (System.currentTimeMillis() - time));
	}

	/**
	 * 解析其他类
	 * 
	 * @author gdpancheng@gmail.com 2014年12月21日 上午12:04:55
	 * @param view
	 * @param object
	 * @return void
	 */
	public static void analysisOrther(View view, Object object) {
		// -------------------------------------------------------------------------
		// 判断是否为代理类
		// 例如 自动注入的adapter 自动注入的fragment 自动注入的组件
		String name = object.getClass().getName();
		if (name.endsWith("_Proxy")) {
			name = object.getClass().getSuperclass().getName();
		}
		long time = System.currentTimeMillis();
		CommonEntity entity = Ioc.getIoc().getAnalysisEntity(name);
		// 如果数据还是为空，则警告
		if (entity == null) {
			Ioc.getIoc().getLogger().e(name + "解析文件不存在，跳过该类，耗时为：" + (System.currentTimeMillis() - time));
			return;
		}
		// 注入inall
		ArrayList<InAllEntity> all = entity.getAll();
		int number = all.size();
		for (int j = 0; j < number; j++) {
			all.get(j).setObject(object);
			all.get(j).invoke(view);
		}
		// 注入inview
		ArrayList<InViewEntity> inview = entity.getInView();
		number = inview.size();
		for (int j = 0; j < number; j++) {
			inview.get(j).setObject(object);
			inview.get(j).invoke(view);
		}

		// 注入inSource
		ArrayList<InSourceEntity> inSources = entity.getResource();
		number = inSources.size();
		for (int j = 0; j < number; j++) {
			inSources.get(j).setObject(object);
			inSources.get(j).invoke(view);
		}

		// 自动注入组件
		ArrayList<ModuleEntity> modules = entity.getModuleEntity();
		number = modules.size();
		for (int j = 0; j < number; j++) {
			modules.get(j).setObject(object);
			modules.get(j).invoke(view);
		}

		// 加载Bean
		ArrayList<InBeanEntity> bean = entity.getInBeanEntity();
		number = bean.size();
		for (int j = 0; j < number; j++) {
			bean.get(j).setObject(object);
			bean.get(j).invoke(view);
		}
		// 调用listener
		ArrayList<InMethodEntity> method = entity.getInMethod();
		if (method != null) {
			number = method.size();
			for (int j = 0; j < number; j++) {
				method.get(j).setObject(object);
				method.get(j).invoke(view);
			}
		}

		// 调用init
		InitEntity init = entity.getInit();
		if (init != null) {
			init.invoke(object);
		}
	}

	/**
	 * 解析其他类
	 * 
	 * @author gdpancheng@gmail.com 2014年12月21日 上午12:04:55
	 * @param view
	 * @param object
	 * @return void
	 */
	public static void analysisModule(View view, Object object, boolean isInit) {
		// -------------------------------------------------------------------------
		// 判断是否为代理类
		// 例如 自动注入的adapter 自动注入的fragment 自动注入的组件
		String name = object.getClass().getName();
		if (name.endsWith("_Proxy")) {
			name = object.getClass().getSuperclass().getName();
		}
		long time = System.currentTimeMillis();
		CommonEntity entity = Ioc.getIoc().getAnalysisEntity(name);
		// 如果数据还是为空，则警告
		if (entity == null) {
			Ioc.getIoc().getLogger().e(name + "解析文件不存在，跳过该类，耗时为：" + (System.currentTimeMillis() - time));
			return;
		}
		if (isInit) {
			// 调用init
			InitEntity init = entity.getInit();
			if (init != null) {
				init.invoke(object);
			}
			return;
		}
		// 注入inall
		ArrayList<InAllEntity> all = entity.getAll();
		int number = all.size();
		for (int j = 0; j < number; j++) {
			all.get(j).setObject(object);
			all.get(j).invoke(view);
		}
		// 注入inview
		ArrayList<InViewEntity> inview = entity.getInView();
		number = inview.size();
		for (int j = 0; j < number; j++) {
			inview.get(j).setObject(object);
			inview.get(j).invoke(view);
		}

		// 注入inSource
		ArrayList<InSourceEntity> inSources = entity.getResource();
		number = inSources.size();
		for (int j = 0; j < number; j++) {
			inSources.get(j).setObject(object);
			inSources.get(j).invoke(view);
		}

		// 自动注入组件
		ArrayList<ModuleEntity> modules = entity.getModuleEntity();
		number = modules.size();
		for (int j = 0; j < number; j++) {
			modules.get(j).setObject(object);
			modules.get(j).invoke(view);
		}

		// 加载Bean
		ArrayList<InBeanEntity> bean = entity.getInBeanEntity();
		number = bean.size();
		for (int j = 0; j < number; j++) {
			bean.get(j).setObject(object);
			bean.get(j).invoke(view);
		}
		// 调用listener
		ArrayList<InMethodEntity> method = entity.getInMethod();
		if (method != null) {
			number = method.size();
			for (int j = 0; j < number; j++) {
				method.get(j).setObject(object);
				method.get(j).invoke(view);
			}
		}
	}

	/**
	 * 解析activity生命周期类
	 * 
	 * @author gdpancheng@gmail.com 2014年12月21日 上午12:06:57
	 * @param activity
	 * @param key
	 * @param object
	 * @return void
	 */
	public static void analysisProcess(Activity activity, int key, Object... object) {
		if (activity.getClass().getName().endsWith("_Proxy")) {
			return;
		}
		ActivityEntity entity = Ioc.getIoc().getAnalysisEntity(activity.getClass().getName());
		if (entity == null) {
			Ioc.getIoc().getLogger().i(activity.getClass().getName() + "解析文件不存在");
			return;
		}
		Invoker invoker = null;
		switch (key) {
		case LoonConstant.Number.ACTIVITY_ONNEW:
			invoker = entity.getNewIntent();
			break;
		case LoonConstant.Number.ACTIVITY_PAUSE:
			invoker = entity.getPauseEntity();
			break;
		case LoonConstant.Number.ACTIVITY_RESUME:
			invoker = entity.getResumeEntity();
			break;
		case LoonConstant.Number.ACTIVITY_RESTART:
			invoker = entity.getRestartEntity();
			break;
		case LoonConstant.Number.ACTIVITY_START:
			invoker = entity.getStartEntity();
			break;
		case LoonConstant.Number.ACTIVITY_STOP:
			invoker = entity.getStopEntity();
			break;
		case LoonConstant.Number.ACTIVITY_DESTROY:
			invoker = entity.getDestroy();
			break;
		}
		if (invoker != null) {
			invoker.invoke(activity, object);
		}
	}

	@SuppressWarnings("unchecked")
	public static void analysisValidator(Object object) {
		// -------------------------------------------------------------------------
		// 判断是否为代理类
		// 例如 自动注入的adapter 自动注入的fragment 自动注入的组件
		String name = object.getClass().getName();
		if (name.endsWith("_Proxy")) {
			name = object.getClass().getSuperclass().getName();
		}
		long time = System.currentTimeMillis();
		CommonEntity entity = Ioc.getIoc().getAnalysisEntity(name);
		// 如果数据还是为空，则警告
		if (entity == null) {
			Ioc.getIoc().getLogger().e(name + "解析文件不存在，跳过该类，耗时为：" + (System.currentTimeMillis() - time));
			return;
		}
		ArrayList<InAllEntity> allEntities = entity.getAll();
		InVaEntity preEntity = null;
		int count = allEntities.size();
		for (int i = 0; i < count; i++) {
			InAllEntity allEntity = allEntities.get(i);
			ArrayList<InViewEntity> viewEntities = allEntity.getViewEntities();
			if (viewEntities == null) {
				continue;
			}
			Collections.sort(viewEntities, new Comparator() {
				@Override
				public int compare(Object o1, Object o2) {
					return Integer.valueOf(((InViewEntity) o1).getIndex()).compareTo(Integer.valueOf(((InViewEntity) o2).getIndex()));
				}
			});

			int number = viewEntities.size();
			for (int j = 0; j < number; j++) {
				InVaEntity inVaEntity = viewEntities.get(j).getInVaEntity();
				if (inVaEntity == null) {
					continue;
				}
				inVaEntity.setPreEntity(preEntity);
				inVaEntity.setInViewEntity(viewEntities.get(j));
				inVaEntity.setVaOkEntity(entity.getInVaOk());
				inVaEntity.setVaErEntity(entity.getInVaER());
				inVaEntity.invoke(object);
				if (inVaEntity.isOk()) {
					preEntity = inVaEntity;
					continue;
				} else {
					return;
				}
			}
		}

		ArrayList<InViewEntity> inViewEntities = entity.getInView();

		count = inViewEntities.size();

		for (int i = 0; i < count; i++) {
			InVaEntity inVaEntity = inViewEntities.get(i).getInVaEntity();
			if (inVaEntity == null) {
				continue;
			}
			preEntity = inVaEntity;
			inVaEntity.setPreEntity(preEntity);
			inVaEntity.setInViewEntity(inViewEntities.get(i));
			inVaEntity.setVaOkEntity(entity.getInVaOk());
			inVaEntity.setVaErEntity(entity.getInVaER());
			inVaEntity.invoke(object);
			if (inVaEntity.isOk()) {
				preEntity = inVaEntity;
				continue;
			} else {
				return;
			}
		}
		entity.getInVaOk().invoke(object);
	}

	public static boolean creatActivityProxy(Class<?> clazz, boolean isCreat) {
		// 判断是否需要创建代理 对方法进行拦截
		Class<?>[] classes = clazz.getInterfaces();
		boolean proxy = false;
		for (int i = 0; i < classes.length; i++) {
			if (PluginComponent.class.isAssignableFrom(classes[i])) {
				proxy = true;
			}
		}

		if (!proxy) {
			for (Method method : clazz.getDeclaredMethods()) {
				if (method.getAnnotation(InBack.class) != null) {
					proxy = true;
					break;
				}
				if (method.getAnnotation(InUI.class) != null) {
					proxy = true;
					break;
				}
			}
		}

		if (!LoonConfig.instance().isDepend()) {
			proxy = false;
		}
		if (proxy && isCreat) {
			BeanFactory.load(clazz, null, null);
		}
		return proxy;
	}
}
