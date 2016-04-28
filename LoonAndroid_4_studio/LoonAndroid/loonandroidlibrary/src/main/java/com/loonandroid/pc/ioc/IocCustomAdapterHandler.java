package com.loonandroid.pc.ioc;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.BaseAdapter;

import com.google.dexmaker.ProxyBuilder;
import com.loonandroid.pc.adapter.ViewHolder;
import com.loonandroid.pc.annotation.InBack;
import com.loonandroid.pc.inject.InjectViewUtils;
import com.loonandroid.pc.interfaces.LoonAdapter;
import com.loonandroid.pc.interfaces.LoonViewDeal;

/**
 * 拦截器 对所有需要自动化注解的类 进行拦截
 * 
 * @author gdpancheng@gmail.com 2014年12月7日 下午4:04:34
 */
public class IocCustomAdapterHandler implements InvocationHandler {

	private ArrayList<?> data = new ArrayList();
	private WeakReference<BaseAdapter> adapter;
	private WeakReference<LayoutInflater> inflater;
	private WeakReference<Object> viewHolder;
	private Class<?> classHolder;
	private LoonViewDeal<?> deal;
	private int id;
	private boolean isAbstract = true;
	private boolean hasDeal = true;
	private Class<?> clazz;
	private Field[] fields;

	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		// ---------------------------------------------------------------------------------------
		// 当是BaseAdapter的时候
		if (method.getName().equals("getView")) {
			return analysisView(proxy, args[1], Integer.valueOf(args[0].toString()));
		}
		if (method.getName().equals("getItemId")) {
			return Long.valueOf(args[0].toString());
		}
		if (method.getName().equals("getItem")) {
			return args[0];
		}
		if (method.getName().equals("getCount")) {
			Method superMethod = ProxyBuilder.getSuper(proxy, method, args);
			isAbstract = Modifier.isAbstract(superMethod.getModifiers());
			if (!isAbstract && !Modifier.isAbstract(method.getModifiers())) {
				try {
					return ProxyBuilder.callSuper(proxy, method, args);
				} catch (Exception e) {
					return data.size();
				}
			}
			return data.size();
		}
		if (method.getName().equals("dealView")) {
			Class[] clazz = new Class[args.length];
			for (int i = 0; i < clazz.length; i++) {
				clazz[i] = args[i].getClass();
			}
			Method[] methods = proxy.getClass().getSuperclass().getMethods();
			Method dealMethod = null;
			for (Method one : methods) {
				if (one.getName().equals("dealView")) {
					dealMethod = one;
					break;
				}
			}
			Class<?>[] classes = dealMethod.getParameterTypes();
			if (!Arrays.equals(classes, clazz)) {
				if (!Modifier.isAbstract(method.getModifiers())) {
					dealMethod = method;
                }
            }
			
			return ProxyBuilder.callSuper(proxy, dealMethod, args);
		}
		if (method.getName().equals("getData")) {
			return data;
		}
		if (method.getName().equals("setDeal")) {
			return 0;
		}
		if (method.getName().equals("setData")) {
			data = (ArrayList) args[0];
			return 0;
		}
		// 拦截适配器
		if (BaseAdapter.class.isAssignableFrom(proxy.getClass())) {
		}

		if (method.getAnnotation(InBack.class) != null) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						ProxyBuilder.callSuper(proxy, method, args);
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			}).start();
			return 0;
		}

		return ProxyBuilder.callSuper(proxy, method, args);
	}

	/**
	 * 这里处理getview中的逻辑
	 * 
	 * @author gdpancheng@gmail.com 2014年12月8日 下午2:50:00
	 * @param <T>
	 * @param view
	 * @return View
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	@SuppressWarnings("unchecked")
	public View analysisView(Object adapter, Object convertView, int postion) throws InstantiationException, IllegalAccessException {
		long time = System.currentTimeMillis();
		ViewHolder viewHolder = ViewHolder.get(inflater.get(), (View) convertView, id, postion);
		// 注入inview
		boolean result = false;
		Object object = new Object();
		if (postion < data.size()) {
			object = data.get(postion);
		}
		if (!isAbstract && adapter instanceof LoonAdapter) {
			result = ((LoonAdapter) adapter).dealView(object, viewHolder);
		}

		if (!result) {
			if (Map.class.isAssignableFrom(object.getClass())) {
				for (Entry<String, Object> entry : ((HashMap<String, Object>) object).entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					viewHolder.setData(InjectViewUtils.getResouceId("id", key), value);
				}
			} else {
				if (null == clazz) {
					clazz = object.getClass();
				}
				if (String.class.isAssignableFrom(clazz)) {
					Ioc.getIoc().getLogger().d("当前绑定的数据类型为字符串，无法自动绑定");
				} else {
					if (null == fields) {
						fields = clazz.getDeclaredFields();
					}
					for (int i = 0; i < fields.length; i++) {
						try {
							Field field = fields[i];
							field.setAccessible(true);
							Method getMethod = clazz.getDeclaredMethod("get" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1));
							viewHolder.setData(InjectViewUtils.getResouceId("id", field.getName()), getMethod.invoke(object).toString());
						} catch (Exception e) {
							Ioc.getIoc().getLogger().e("当前" + clazz + "实体类不存在get方法或者其字段和对于View ID无法对应");
						}
					}
				}
			}
		}
		Ioc.getIoc().getLogger().d("解析getView 耗时:"+(System.currentTimeMillis()-time));
		return viewHolder.getConvertView();
	}

	public BaseAdapter getAdapter() {
		return adapter == null ? null : adapter.get();
	}

	public void setAdapter(BaseAdapter adapter) {
		this.adapter = new WeakReference<BaseAdapter>(adapter);
	}

	public LayoutInflater getInflater() {
		return inflater == null ? null : inflater.get();
	}

	public void setInflater(LayoutInflater inflater) {
		this.inflater = new WeakReference<LayoutInflater>(inflater);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
