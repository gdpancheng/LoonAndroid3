package com.loonandroid.pc.ioc;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
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
import com.loonandroid.pc.interfaces.LoonViewDeal;
import com.loonandroid.pc.util.LoonConstant;

/**
 * 拦截器 adapter进行拦截
 * 
 * @author gdpancheng@gmail.com 2014年12月7日 下午4:04:34
 */
public class IocAdapterHandler implements InvocationHandler {

	private ArrayList<Object> data = new ArrayList<Object>();
	private WeakReference<LayoutInflater> inflater;
	private LoonViewDeal<Object> deal;
	private int id = LoonConstant.Number.ID_NONE;
	private Class<? extends Object> clazz;
	private Field[] fields;

	@Override
	public Object invoke(final Object proxy,final Method method,final Object[] args) throws Throwable {
		
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
		
		// ---------------------------------------------------------------------------------------
		// 当是BaseAdapter的时候
		if (proxy.getClass().getSuperclass() == BaseAdapter.class) {
			if (method.getName().equals("getView")) {
				return analysisView(args[1], Integer.valueOf(args[0].toString()));
			}
			if (method.getName().equals("getItemId")) {
				return Long.valueOf(args[0].toString());
			}
			if (method.getName().equals("getItem")) {
				return args[0];
			}
			if (method.getName().equals("getCount")) {
				if (deal!=null&&deal.getCount()!=0) {
					return deal.getCount();
				}
				inflater = new WeakReference<LayoutInflater>(LayoutInflater.from(Ioc.getIoc().getApplication()));
				return data.size();
			}
			if (method.getName().equals("getData")) {
				return data;
			}
			if (method.getName().equals("setDeal")) {
				deal = ((LoonViewDeal) args[0]);
				return 0;
			}
			if (method.getName().equals("setData")) {
				data = (ArrayList) args[0];
				return 0;
			}
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
	public View analysisView(Object convertView, int postion) throws InstantiationException, IllegalAccessException {

		ViewHolder viewHolder = ViewHolder.get(inflater.get(), (View) convertView, id, postion);
		Object object = data.get(postion);
		// 注入inview
		if (deal==null||!deal.dealView(object, viewHolder)) {
			if (Map.class.isAssignableFrom(object.getClass())) {
				for (Entry<String, Object> entry : ((HashMap<String, Object>) object).entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					viewHolder.setData(InjectViewUtils.getResouceId("id", key), value);
				}
			} else {
				if (null==clazz) {
					clazz = object.getClass();
                }
				if (null==fields) {
					fields = clazz.getDeclaredFields();
                }
				for (int i = 0; i < fields.length; i++) {
	                try {
	                    Field field = fields[i];
	                    field.setAccessible(true);
	                    Method getMethod = clazz.getDeclaredMethod("get" + Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1));
	                    viewHolder.setData(InjectViewUtils.getResouceId("id", field.getName()), getMethod.invoke(object).toString());
                    } catch (Exception e) {
	                    e.printStackTrace();
                    } 
				}
			}

		}
		return viewHolder.getConvertView();
	}

	public LayoutInflater getInflater() {
		return inflater == null?null:inflater.get();
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
