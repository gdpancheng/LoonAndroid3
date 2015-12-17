package com.loonandroid.pc.net;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Handler;

import com.loonandroid.pc.core.AnalysisManager;
import com.loonandroid.pc.entity.CommonEntity;
import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.ioc.entity.InHttpEntity;
import com.loonandroid.pc.util.LoonConstant;

/**
 * 上一个版本的网络请求只保留文件上传和webservice 其他的使用Volley <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月26日 上午12:12:01
 */
public abstract class IocHttpListener<T> {

	/**
	 * 使用挂载的网络请求库
	 * 
	 * @author gdpancheng@gmail.com 2015年11月12日 下午12:34:53
	 * @param config
	 * @return T
	 */
	public abstract T netCore(NetConfig config);

	@SuppressWarnings("unchecked")
	public Class<?>[] getClazz() {
		Type t = getClass().getGenericSuperclass();
		Class<?>[] classes = null;
		if (t instanceof ParameterizedType) {
			Type[] types = ((ParameterizedType) t).getActualTypeArguments();
			classes = new Class[types.length];
			for (int i = 0; i < types.length; i++) {
				if (types[i] instanceof Class) {
					classes[i] = (Class<?>) types[i];
					continue;
				}
				classes[i] = (Class<T>) ((ParameterizedType) types[i]).getRawType();
			}
		}
		return classes;
	}

	/**
	 * 这里将对回调进行分发
	 * 
	 * @author gdpancheng@gmail.com 2014年12月26日 上午12:10:52
	 * @param url
	 * @param param
	 * @param type
	 * @return void
	 */
	final public void callback(NetConfig config, T result) {
		Object object = config.getObject();
		if (isDestory(object)) {
			Ioc.getIoc().getLogger().d(object + "已经回收，无法回掉");
			config.setObject(null);
		} else {
			reBackPreview(config, object, result);
		}
	}

	private static boolean isDestory(Object object) {
		if (object == null) {
			return true;
		}
		if (Activity.class.isAssignableFrom(object.getClass())) {
			return ((Activity) object).isFinishing();
		}
		try {
			Class<?> clazz = Class.forName("android.support.v4.app.Fragment");
			Class<?> clazz2 = Class.forName("android.app.Fragment");
			if (clazz.isAssignableFrom(object.getClass()) || clazz2.isAssignableFrom(object.getClass())) {
				Method isDetached = object.getClass().getMethod("isDetached", null);
				Method isRemoving = object.getClass().getMethod("isRemoving", null);
				return Boolean.valueOf(isDetached.invoke(object).toString()) && Boolean.valueOf(isRemoving.invoke(object).toString());
			}
		} catch (Exception e) {
		}
		return false;
	}

	private void reBackPreview(final NetConfig config, final Object object, final T result) {
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
		// 开始遍历这些类，从最上级父类开始
		// 判断是否存在缓存的解析数据
		handler.post(new Runnable() {
			@Override
			public void run() {
				ArrayList<Class<?>> classes = Ioc.getIoc().getClasses(object.getClass());
				if (null == classes || classes.size() == 0) {
					classes = AnalysisManager.extendClazz(object.getClass());
				}
				int count = classes.size();
				boolean isBack = false;
				for (int i = 0; i < count; i++) {
					final Class<?> clazz = classes.get(i);
					if (clazz.getName().endsWith("_Proxy")) {
						continue;
					}
					CommonEntity entity = Ioc.getIoc().getAnalysisEntity(clazz.getName());
					if (entity == null) {
						continue;
					}
					HashMap<Integer, ArrayList<InHttpEntity>> httpEntities = entity.getHttps();
					ArrayList<InHttpEntity> httpEntity = httpEntities.get(config.getCode());
					if (httpEntity == null || httpEntity.size() == 0) {
						httpEntity = httpEntities.get(LoonConstant.Number.ID_NONE);
						if (httpEntity == null || httpEntity.size() == 0) {
							continue;
						}
					}
					isBack = true;
					for (InHttpEntity inHttpEntity : httpEntity) {
						inHttpEntity.invoke(object, result);
					}
				}
				if (!isBack) {
					Ioc.getIoc().getLogger().e(config.getObject().getClass() + "请求" + config.getUrl() + "的回调" + config.getCode() + "没有对应的回调方法");
				}
			}
		});
	}

	/**
	 * 
	 * @author gdpancheng@gmail.com 2014年12月26日 上午12:11:37
	 * @param url
	 * @param param
	 * @param type
	 * @return void
	 */
	final public void dealsuccess(NetConfig config, T result) {
	}

	/**
	 * 
	 * @author gdpancheng@gmail.com 2014年12月26日 上午12:11:37
	 * @param url
	 * @param param
	 * @param type
	 * @return void
	 */
	final public void dealError(NetConfig config, T result) {
	}

	/**
	 * 其他的解析方式
	 * 
	 * @author gdpancheng@gmail.com 2014年12月30日 上午12:50:16
	 * @param result
	 * @return void
	 */
	public void analysisResult(String result) {
	}

	public String getCookie() {
		return "";
	}

	final public void saveCookie(String cookie) {
	}

	public HashMap<String, Object> getHeader() {
		return null;
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
		};
	};
}
