package com.loonandroid.pc.core;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;

import com.loonandroid.pc.annotation.Ignore;
import com.loonandroid.pc.annotation.InAfter;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBean;
import com.loonandroid.pc.annotation.InBefore;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InDestroy;
import com.loonandroid.pc.annotation.InHttp;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InListener;
import com.loonandroid.pc.annotation.InModule;
import com.loonandroid.pc.annotation.InNewIntent;
import com.loonandroid.pc.annotation.InPLayer;
import com.loonandroid.pc.annotation.InParam;
import com.loonandroid.pc.annotation.InPause;
import com.loonandroid.pc.annotation.InPullRefresh;
import com.loonandroid.pc.annotation.InRestart;
import com.loonandroid.pc.annotation.InResume;
import com.loonandroid.pc.annotation.InSource;
import com.loonandroid.pc.annotation.InStart;
import com.loonandroid.pc.annotation.InStop;
import com.loonandroid.pc.annotation.InVa;
import com.loonandroid.pc.annotation.InVaER;
import com.loonandroid.pc.annotation.InVaOK;
import com.loonandroid.pc.annotation.InView;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.entity.ActivityEntity;
import com.loonandroid.pc.entity.CommonEntity;
import com.loonandroid.pc.entity.OrtherEntity;
import com.loonandroid.pc.handler.Handler_File;
import com.loonandroid.pc.inject.InjectViewUtils;
import com.loonandroid.pc.interfaces.BeanFactory;
import com.loonandroid.pc.interfaces.LoonAdapter;
import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.ioc.config.LoonConfig;
import com.loonandroid.pc.ioc.entity.InAfterEntity;
import com.loonandroid.pc.ioc.entity.InAllEntity;
import com.loonandroid.pc.ioc.entity.InBeanEntity;
import com.loonandroid.pc.ioc.entity.InBeforeEntity;
import com.loonandroid.pc.ioc.entity.InDestroyEntity;
import com.loonandroid.pc.ioc.entity.InHttpEntity;
import com.loonandroid.pc.ioc.entity.InLayerEntity;
import com.loonandroid.pc.ioc.entity.InMethodEntity;
import com.loonandroid.pc.ioc.entity.InOnNewEntity;
import com.loonandroid.pc.ioc.entity.InPLayerEntity;
import com.loonandroid.pc.ioc.entity.InPauseEntity;
import com.loonandroid.pc.ioc.entity.InPlugInitEntity;
import com.loonandroid.pc.ioc.entity.InPullRefreshEntity;
import com.loonandroid.pc.ioc.entity.InRestartEntity;
import com.loonandroid.pc.ioc.entity.InResumeEntity;
import com.loonandroid.pc.ioc.entity.InSourceEntity;
import com.loonandroid.pc.ioc.entity.InStartEntity;
import com.loonandroid.pc.ioc.entity.InStopEntity;
import com.loonandroid.pc.ioc.entity.InSubscribeEntity;
import com.loonandroid.pc.ioc.entity.InVaEntity;
import com.loonandroid.pc.ioc.entity.InVaErEntity;
import com.loonandroid.pc.ioc.entity.InVaOkEntity;
import com.loonandroid.pc.ioc.entity.InViewEntity;
import com.loonandroid.pc.ioc.entity.InitEntity;
import com.loonandroid.pc.ioc.entity.ModuleEntity;
import com.loonandroid.pc.ioc.kernel.KernelString;
import com.loonandroid.pc.listener.OnListener;
import com.loonandroid.pc.plug.PluginComponent;
import com.loonandroid.pc.tinybus.Subscribe;
import com.loonandroid.pc.util.LoonConstant;

/**
 * 解析公用类 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月21日 上午12:52:07
 */
@SuppressWarnings("unchecked")
public class AnalysisCore<T extends CommonEntity> implements Runnable {

	// 判断哪些是已经解析了的
	public static final Set<Class<?>> hasAnalysis = new HashSet<Class<?>>();
	public Class<?> clazz;
	public T object;
	private boolean isFinish = false;

	public AnalysisCore() {
		// -----------------------------------------------------------------------------------
		// 解析泛型的对象
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		Class<T> t = ((Class<T>) pt.getActualTypeArguments()[0]);
		try {
			object = t.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 解析完毕
		// -----------------------------------------------------------------------------------
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setClass(Class<?> clazz) {
		this.clazz = clazz;
	}

	public T process() {
		Method[] methods = clazz.getDeclaredMethods();
		for (int i = 0; i < methods.length; i++) {
			// init注解
			initAnalysis(methods[i]);
			// 解析方法的注解（网络请求/以及点击事件的方法）
			methodAnalysis(methods[i]);
		}
		variableAnalysis(clazz);
		return object;
	}

	@Override
	public void run() {
		Ioc.getIoc().getLogger().d("类" + clazz + "开始解析");
		long time = System.currentTimeMillis();
		analysis(false);
		isFinish = true;
		Ioc.getIoc().getLogger().d("类" + clazz + "完毕 耗时：" + (System.currentTimeMillis() - time));
	}

	public void analysis(boolean sync) {
		if (hasAnalysis.contains(clazz)) {
			return;
		}
		T t = this.process();
		Ioc.getIoc().setAnalysisEntity(clazz.getName(), t);
		hasAnalysis.add(clazz);
		// -----------------------------------------------------------------------------------
		// 在activity中同步解析 同步不去存储，由后台异步解析的去存储，因为在2.0~2.3 低配手机
		// 对象序列化耗时太长，导致第一个页面打开速度太慢。而我们程序启动以后，已经开启了一个
		// 异步线程去处理了，同步能够走到这里来，说明异步还没处理到这个页面，这个时候的同步只为了
		// 第一个页面能够快速加载。
		if (!sync) {
			if (!t.isEmpty()) {
				Handler_File.setObject(clazz.getName(), (Serializable) t);
			}
		}
		// 解析结束
		// ----------------------------------------------------------------------------------
	}

	/**
	 * 解析acitivity中setContentView之前触发和setContentView之后触发
	 * 
	 * @author gdpancheng@gmail.com 2014-11-13 下午7:57:28
	 * @param method
	 * @return void
	 */
	public void activityAnalysis(Method method) {
		if (method.getAnnotation(InBefore.class) != null) {
			InBeforeEntity beforeEntity = new InBeforeEntity();
			beforeEntity.setClazz(clazz);
			beforeEntity.setMethodName(method.getName());
			((ActivityEntity) object).setBefore(beforeEntity);
			object.setEmpty(false);
		} else if (method.getAnnotation(InAfter.class) != null) {
			InAfterEntity afterEntity = new InAfterEntity();
			afterEntity.setClazz(clazz);
			afterEntity.setMethodName(method.getName());
			((ActivityEntity) object).setAfter(afterEntity);
			object.setEmpty(false);
		}
	}

	/**
	 * 在
	 * 
	 * @author gdpancheng@gmail.com 2014-11-13 下午7:58:11
	 * @param method
	 * @return void
	 */
	public void progressAnalysis(Method method) {
		if (method.getAnnotation(InNewIntent.class) != null) {
			InOnNewEntity newIntent = new InOnNewEntity();
			newIntent.setMethodName(method.getName());
			newIntent.setClazz(clazz);
			newIntent.setParameter(method.getParameterTypes());
			object.setNewIntent(newIntent);
			object.setEmpty(false);
		} else if (method.getAnnotation(InPause.class) != null) {
			InPauseEntity pauseEntity = new InPauseEntity();
			pauseEntity.setMethodName(method.getName());
			pauseEntity.setClazz(clazz);
			object.setPauseEntity(pauseEntity);
			object.setEmpty(false);
		} else if (method.getAnnotation(InResume.class) != null) {
			InResumeEntity resumeEntity = new InResumeEntity();
			resumeEntity.setMethodName(method.getName());
			resumeEntity.setClazz(clazz);
			object.setResumeEntity(resumeEntity);
			object.setEmpty(false);
		} else if (method.getAnnotation(InRestart.class) != null) {
			InRestartEntity restartEntity = new InRestartEntity();
			restartEntity.setMethodName(method.getName());
			restartEntity.setClazz(clazz);
			object.setRestartEntity(restartEntity);
			object.setEmpty(false);
		} else if (method.getAnnotation(InStart.class) != null) {
			InStartEntity startEntity = new InStartEntity();
			startEntity.setMethodName(method.getName());
			startEntity.setClazz(clazz);
			object.setStartEntity(startEntity);
			object.setEmpty(false);
		} else if (method.getAnnotation(InStop.class) != null) {
			InStopEntity stopEntity = new InStopEntity();
			stopEntity.setMethodName(method.getName());
			stopEntity.setClazz(clazz);
			object.setStopEntity(stopEntity);
			object.setEmpty(false);
		} else if (method.getAnnotation(InDestroy.class) != null) {
			InDestroyEntity destroyEntity = new InDestroyEntity();
			destroyEntity.setMethodName(method.getName());
			destroyEntity.setClazz(clazz);
			object.setDestroy(destroyEntity);
			object.setEmpty(false);
		}
	}

	/**
	 * 解析init
	 * 
	 * @author gdpancheng@gmail.com 2014-11-18 下午2:06:37
	 * @param method
	 * @return void
	 */
	public void initAnalysis(Method method) {
		if (method.getAnnotation(Init.class) != null) {
			InitEntity inEntity = new InitEntity();
			inEntity.setMethodName(method.getName());
			inEntity.setParameter(method.getParameterTypes());
			inEntity.setClazz(clazz);
			object.setInit(inEntity);
			object.setEmpty(false);
		}
	}

	/**
	 * 解析方法的注解（网络请求/以及点击事件的方法）
	 * 
	 * @author gdpancheng@gmail.com 2014-11-18 下午2:06:28
	 * @param method
	 * @return void
	 */
	public void methodAnalysis(Method method) {
		if (method.getAnnotation(InHttp.class) != null) {
			InHttpEntity httpEntity = new InHttpEntity();
			httpEntity.setClazz(clazz);
			httpEntity.setMethodName(method.getName());
			httpEntity.setParameter(method.getParameterTypes());
			InHttp http = method.getAnnotation(InHttp.class);
			HashSet<Integer> codes = new HashSet<Integer>();
			for (int i = 0; i < http.value().length; i++) {
				codes.add(http.value()[i]);
			}
			httpEntity.setKey(codes);
			Iterator<Integer> iterator = codes.iterator();
			while (iterator.hasNext()) {
				int key = iterator.next();
				object.setHttp(key, httpEntity);
			}
			object.setEmpty(false);
		} else if (method.getAnnotation(InListener.class) != null) {
			InListener inListener = method.getAnnotation(InListener.class);
			OnListener[] onlisteners = new OnListener[inListener.listeners().length];
			int i = 0;
			try {
				for (Class<? extends OnListener> clazz : inListener.listeners()) {
					onlisteners[i] = clazz.newInstance();
					onlisteners[i].setClazz(this.clazz);
					onlisteners[i].setListener(clazz);
					onlisteners[i].setMethod(method.getName());
					i++;
				}
			} catch (Exception e) {
				e.printStackTrace();
				Ioc.getIoc().getLogger().e("类" + this.clazz.getName() + "中" + "点击事件注解错了");
			}

			InMethodEntity inMethodEntity = new InMethodEntity();
			inMethodEntity.setMethodName(method.getName());
			inMethodEntity.setClazz(clazz);
			inMethodEntity.setIds(inListener.ids());
			inMethodEntity.setOnListener(onlisteners);
			object.setInMethod(inMethodEntity);
			object.setEmpty(false);
		} else if (method.getAnnotation(InPullRefresh.class) != null) {
			InPullRefresh inPullRefresh = method.getAnnotation(InPullRefresh.class);
			InPullRefreshEntity inPullRefreshEntity = new InPullRefreshEntity();
			inPullRefreshEntity.setClazz(clazz);
			inPullRefreshEntity.setDown(inPullRefresh.down());
			inPullRefreshEntity.setPull(inPullRefresh.pull());
			inPullRefreshEntity.setMethodName(method.getName());
			inPullRefreshEntity.setParameter(method.getParameterTypes());
			object.setPullRefresh(inPullRefreshEntity);
			object.setEmpty(false);
		} else if (method.getAnnotation(InVaOK.class) != null) {
			InVaOkEntity inVaOkEntity = new InVaOkEntity();
			inVaOkEntity.setClazz(clazz);
			inVaOkEntity.setMethodName(method.getName());
			inVaOkEntity.setParameter(method.getParameterTypes());
			object.setInVaOk(inVaOkEntity);
			object.setEmpty(false);
		} else if (method.getAnnotation(InVaER.class) != null) {
			InVaErEntity inVaErEntity = new InVaErEntity();
			inVaErEntity.setClazz(clazz);
			inVaErEntity.setMethodName(method.getName());
			inVaErEntity.setParameter(method.getParameterTypes());
			object.setInVaER(inVaErEntity);
			object.setEmpty(false);
		} else if (method.getAnnotation(Subscribe.class) != null) {
			InSubscribeEntity inSubscribeEntity = new InSubscribeEntity();
			inSubscribeEntity.setClazz(clazz);
			inSubscribeEntity.setMethodName(method.getName());
			inSubscribeEntity.setParameter(method.getParameterTypes());
			object.setInSubscribe(inSubscribeEntity);
			object.setEmpty(false);
		}
	}

	/**
	 * 变量以及点击事件等解析
	 * 
	 * @author gdpancheng@gmail.com 2014-11-18 下午2:06:14
	 * @param clazz
	 * @return void
	 */
	public void variableAnalysis(Class<?> clazz) {
		InLayer inLayer = clazz.getAnnotation(InLayer.class);
		InPLayer inPLayer = clazz.getAnnotation(InPLayer.class);
		Boolean isActivity = Activity.class.isAssignableFrom(clazz);
		if (inLayer != null && inLayer.value() != LoonConstant.Number.ID_NONE) {
			InLayerEntity layer = new InLayerEntity();
			layer.setActivity(isActivity);
			layer.setFull(inLayer.isFull());
			layer.setId(inLayer.value());
			layer.setTile(inLayer.isTitle());
			layer.setParent(inLayer.parent());
			Class<?> pClazz = clazz.getSuperclass();
			if (inLayer.parent() != LoonConstant.Number.ID_NONE && pClazz.getAnnotation(InPLayer.class) != null) {
				layer.setpClazz(pClazz);
			}
			object.setLayer(layer);
			object.setEmpty(false);
		} else if (inPLayer != null && inPLayer.value() != LoonConstant.Number.ID_NONE) {
			InPLayerEntity pLayer = new InPLayerEntity();
			pLayer.setActivity(isActivity);
			pLayer.setFull(inPLayer.isFull());
			pLayer.setId(inPLayer.value());
			pLayer.setTile(inPLayer.isTitle());
			object.setPLayer(pLayer);
			object.setEmpty(false);
		}

		// -------------------------------------------------------------------------------------------
		// 开始解析变量
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			// 获取allview注解
			InAll inAll = field.getAnnotation(InAll.class);
			Class<?> type = field.getType();
			// InLayer layer = type.getAnnotation(InLayer.class);
			// 如果InAll不为空 并且InLayer也不为空 说明这是adapter中的viewholder注入
			if (inAll != null) {
				// ------------------------------------------------------------------------------------
				// 创建了一个内部类的对象
				InAllEntity allEntity = new InAllEntity(isActivity);
				allEntity.setInnerClass(type);
				allEntity.setFieldName(field.getName());
				allEntity.setClazz(clazz);
				object.setAll(allEntity);
				object.setEmpty(false);
				// ------------------------------------------------------------------------------------
				// 获取all中的view
				Field[] allFields = type.getDeclaredFields();
				ArrayList<InViewEntity> viewEntities = new ArrayList<InViewEntity>();
				ArrayList<InSourceEntity> resourceEntities = new ArrayList<InSourceEntity>();
				int id = 0;
				for (Field inField : allFields) {
					Class<?> inclass = inField.getType();
					if (View.class.isAssignableFrom(inclass)) {
						try {
							id = InjectViewUtils.getResouceId("id", inField.getName());
						} catch (Exception e) {
							Ioc.getIoc().getLogger().e("内部类 " + type + " 变量 " + inField.getName() + " 无法获取到对应的ID 请检查InjectView的参数\n");
							continue;
						}
						InViewEntity viewEntity = new InViewEntity(isActivity);
						viewEntity.setId(id);
						viewEntity.setFieldName(inField.getName());
						viewEntity.setClazz(type);
						// 绑定的各种事件
						InBinder innerBinder = inField.getAnnotation(InBinder.class);
						Ignore ignore = inField.getAnnotation(Ignore.class);
						InView inView = inField.getAnnotation(InView.class);
						if (inView != null) {
							if (inView.pull() || inView.down()) {
								// 绑定下拉刷新 上拉加载更多
								viewEntity.setDown(inView.down());
								viewEntity.setPull(inView.pull());
							}
							if (inView.item() != LoonConstant.Number.ID_NONE) {
								viewEntity.setItem(inView.item());
								if (LoonConfig.instance().isDepend()) {
									if (AdapterView.class.isAssignableFrom(type)) {
										// 加载代理类
										BeanFactory.load(type, new Class[] { LoonAdapter.class }, null);
									}
								}
							}
							if (inView.binder().method().trim().length() > 0) {
								innerBinder = inView.binder();
							}
						}

						if (innerBinder != null && innerBinder.method().trim().length() > 0) {
							if (ignore == null) {
								if (innerBinder == null || innerBinder.method().trim().length() == 0) {
									innerBinder = inAll.value();
								}
							}
							try {
								OnListener listener = innerBinder.listener().newInstance();
								listener.setClazz(clazz);
								listener.setListener(innerBinder.listener());
								listener.setMethod(innerBinder.method());
								viewEntity.setOnListener(listener);
							} catch (Exception e) {
								e.printStackTrace();
								Ioc.getIoc().getLogger().e("类" + clazz.getName() + "中" + inField.getName() + "变量的事件注解错了");
							}
						}

						InVa inVa = inField.getAnnotation(InVa.class);
						if (inVa != null) {
							InVaEntity inVaEntity = new InVaEntity();
							inVaEntity.setChecked(inVa.checked());
							inVaEntity.setClazz(clazz);
							inVaEntity.setEmpty(inVa.empty());
							inVaEntity.setEq(inVa.eq());
							inVaEntity.setGt(inVa.gt());
							inVaEntity.setIndex(inVa.index());
							inVaEntity.setLt(inVa.lt());
							inVaEntity.setMaxLength(inVa.maxLength());
							inVaEntity.setMinLength(inVa.minLength());
							inVaEntity.setMsg(inVa.msg());
							inVaEntity.setStrType(inVa.type());
							inVaEntity.setReg(inVa.reg());
							inVaEntity.setType(inVa.value());
							viewEntity.setInVaEntity(inVaEntity);
							viewEntity.setIndex(inVa.index());
						}

						// 绑定下拉刷新 上拉加载更多
						InPullRefresh pullRefresh = inField.getAnnotation(InPullRefresh.class);
						if (pullRefresh != null) {
							viewEntity.setDown(pullRefresh.down());
							viewEntity.setPull(pullRefresh.pull());
						}
						viewEntities.add(viewEntity);
						continue;
					} else if (Drawable.class.isAssignableFrom(inclass)) {
						int key_id = InjectViewUtils.getResouceId(KernelString.capitalize(inclass.getSimpleName()), inField.getName());
						InSourceEntity resourceEntity = new InSourceEntity(LoonConstant.Number.RESOURCE_DRAWABLE);
						resourceEntity.setClazz(type);
						resourceEntity.setId(key_id);
						resourceEntity.setFieldName(inField.getName());
						resourceEntities.add(resourceEntity);
						continue;
					} else if (String.class.isAssignableFrom(inclass)) {
						int key_id = InjectViewUtils.getResouceId(KernelString.capitalize(inclass.getSimpleName()), inField.getName());
						InSourceEntity resourceEntity = new InSourceEntity(inclass.isArray() ? LoonConstant.Number.RESOURCE_STRINGS : LoonConstant.Number.RESOURCE_STRING);
						resourceEntity.setClazz(type);
						resourceEntity.setId(key_id);
						resourceEntity.setFieldName(inField.getName());
						resourceEntities.add(resourceEntity);
						continue;
					}
					// ------------------------------------------------------------------------------------
					// 获取all中的资源
				}
				// ------------------------------------------------------------------------------------
				// inall中的view解析完了
				if (viewEntities.size() > 0) {
					allEntity.setViewEntities(viewEntities);
					object.setEmpty(false);
				}
				// inall中的resource解析完了
				if (viewEntities.size() > 0) {
					allEntity.setResourceEntities(resourceEntities);
					object.setEmpty(false);
				}
				// ------------------------------------------------------------------------------------
				continue;
			}
			// 获取单个view注解
			InView inView = field.getAnnotation(InView.class);
			if (inView != null) {
				Integer id = inView.value();
				// 如果注解为空 那么我们就根据名字取获取其id
				if (id == LoonConstant.Number.ID_NONE) {
					id = InjectViewUtils.getResouceId("id", field.getName());
					if (id == null) {
						Ioc.getIoc().getLogger().e(clazz + " 变量" + field.getName() + "无法获取到对应的ID 请检查InjectView的参数\n");
						continue;
					}
				}

				InViewEntity viewEntity = new InViewEntity(isActivity);
				viewEntity.setDown(inView.down());
				viewEntity.setPull(inView.pull());
				viewEntity.setClazz(clazz);
				viewEntity.setId(id);
				viewEntity.setItem(inView.item());
				viewEntity.setFieldName(field.getName());

				InVa inVa = field.getAnnotation(InVa.class);
				if (inVa != null) {
					InVaEntity inVaEntity = new InVaEntity();
					inVaEntity.setChecked(inVa.checked());
					inVaEntity.setClazz(clazz);
					inVaEntity.setEmpty(inVa.empty());
					inVaEntity.setEq(inVa.eq());
					inVaEntity.setGt(inVa.gt());
					inVaEntity.setIndex(inVa.index());
					inVaEntity.setLt(inVa.lt());
					inVaEntity.setMaxLength(inVa.maxLength());
					inVaEntity.setMinLength(inVa.minLength());
					inVaEntity.setMsg(inVa.msg());
					inVaEntity.setStrType(inVa.type());
					inVaEntity.setReg(inVa.reg());
					inVaEntity.setType(inVa.value());
					viewEntity.setIndex(inVa.index());
					viewEntity.setInVaEntity(inVaEntity);
				}

				InBinder inBinder = inView.binder();
				if (inBinder.method().trim().length() > 0) {
					try {
						OnListener listener = inBinder.listener().newInstance();
						listener.setClazz(clazz);
						listener.setListener(inBinder.listener());
						listener.setMethod(inBinder.method());
						viewEntity.setOnListener(listener);
					} catch (Exception e) {
						e.printStackTrace();
						Ioc.getIoc().getLogger().e("类" + clazz.getName() + "中" + field.getName() + "变量的事件注解错了");
					}
				}
				object.setView(viewEntity);
				object.setEmpty(false);
				continue;
			}
			// 获取资源注解
			InSource inResource = field.getAnnotation(InSource.class);
			Class<?> inclass = field.getType();
			if (inResource != null) {
				InSourceEntity resourceEntity = null;
				int key_id = InjectViewUtils.getResouceId(KernelString.capitalize(inclass.getSimpleName()), field.getName());
				if (Drawable.class.isAssignableFrom(inclass)) {
					resourceEntity = new InSourceEntity(LoonConstant.Number.RESOURCE_DRAWABLE);
					resourceEntity.setId(key_id);
					resourceEntity.setFieldName(field.getName());
				} else if (String.class.isAssignableFrom(inclass)) {
					resourceEntity = new InSourceEntity(inclass.isArray() ? LoonConstant.Number.RESOURCE_STRINGS : LoonConstant.Number.RESOURCE_STRING);
					resourceEntity.setId(key_id);
					resourceEntity.setFieldName(field.getName());
				}
				if (resourceEntity == null) {
					continue;
				}
				resourceEntity.setClazz(clazz);
				object.setResource(resourceEntity);
				object.setEmpty(false);
				continue;
			}

			InModule module = field.getAnnotation(InModule.class);
			int parentId = -1;
			if (module != null) {
				ModuleEntity inBeanEntity = new ModuleEntity();
				parentId = module.parent();
				if (parentId == LoonConstant.Number.ID_NONE) {
					Integer ids = InjectViewUtils.getResouceId("id", field.getName());
					if (ids == null) {
						parentId = LoonConstant.Number.ID_NONE;
					} else {
						parentId = ids;
					}
				}
				if (parentId == LoonConstant.Number.ID_NONE) {
					Ioc.getIoc().getLogger().e("--------------该组件没有父布局------------");
				}
				inBeanEntity.setParentId(parentId);
				Class<?> fieldClass = field.getType();
				inBeanEntity.setFieldClass(fieldClass);
				inBeanEntity.setFiledName(field.getName());
				module = fieldClass.getAnnotation(InModule.class);
				if (module != null) {
					inBeanEntity.setId(module.value());
					inBeanEntity.setClazz(clazz);
					object.setModuleEntity(inBeanEntity);
				}
				continue;
			}

			InBean inBean = field.getAnnotation(InBean.class);
			Class<?> v4Fragment = AnalysisManager.getClass(LoonConstant.ClassName.V4_FRAGMENT);
			Class<?> fragment = AnalysisManager.getClass(LoonConstant.ClassName.FRAGMENT);
			if (inBean != null && (BaseAdapter.class.isAssignableFrom(type) || v4Fragment.isAssignableFrom(type) || fragment.isAssignableFrom(type))) {
				InBeanEntity inBeanEntity = new InBeanEntity();
				inBeanEntity.setFieldName(field.getName());
				inBeanEntity.setId(inBean.value());
				inBeanEntity.setClazz(clazz);
				object.setInBeanEntity(inBeanEntity);
				object.setEmpty(false);
				if (LoonConfig.instance().isDepend()) {
					if (BaseAdapter.class.isAssignableFrom(type)) {
						// 自定义Adapter加载代理类
						BeanFactory.load(type, new Class[] { LoonAdapter.class }, null);
						continue;
					}
					if (fragment.isAssignableFrom(type)) {
						// 加载代理类
						BeanFactory.load(type);
						continue;
					}
					if (v4Fragment.isAssignableFrom(type)) {
						// 加载代理类
						BeanFactory.load(type);
						continue;
					}
				}
				continue;
			}
		}
	}

	/**
	 * 自动装载组件
	 * 
	 * @author gdpancheng@gmail.com 2015年11月10日 上午11:17:07
	 * @param clazz
	 * @return void
	 */
	protected void loadPlug(Class<?> clazz) {
		Class<?>[] classes = clazz.getInterfaces();
		if (classes == null) {
			return;
		}
		for (int i = 0; i < classes.length; i++) {
			Class<?> interfaces = classes[i];
			if (PluginComponent.class.isAssignableFrom(interfaces)) {
				Method[] methods = clazz.getMethods();
				for (int j = 0; j < methods.length; j++) {
					Method method = methods[j];
					if (method.getAnnotation(Init.class) != null) {

						InPlugInitEntity inPlugInitEntity = new InPlugInitEntity();
						inPlugInitEntity.setClazz(clazz);
						inPlugInitEntity.setParameter(method.getParameterTypes());
						inPlugInitEntity.setMethodName(method.getName());
						Annotation[][] paramAnnotations = method.getParameterAnnotations();
						Class<?>[] types = method.getParameterTypes();
						String[] parameters = new String[types.length];
						for (int m = 0; m < paramAnnotations.length; m++) {
							InParam inParam = null;
							Annotation[] annotations = paramAnnotations[m];
							if (null != annotations && annotations.length > 0) {
								if (annotations[0].annotationType() == InParam.class) {
									inParam = (InParam) annotations[0];
								}
							}
							if (inParam != null) {
								if (inParam.value().length() > 0) {
									try {
										Field inField = clazz.getDeclaredField(inParam.value());
										inField.setAccessible(true);
										if (inField.getGenericType().toString().equals(types[m].toString())) {
											parameters[m] = inParam.value();
										}
									} catch (Exception e) {
										Ioc.getIoc().getLogger().e("组件" + clazz + "接口中的方法参数注解" + inParam.value() + "不存在");
									}
								}
							}
						}
						inPlugInitEntity.setFields(parameters);
						((OrtherEntity) object).setInitEntity(inPlugInitEntity);
						object.setEmpty(false);
						continue;
					}
				}
			}
		}
	}
}
