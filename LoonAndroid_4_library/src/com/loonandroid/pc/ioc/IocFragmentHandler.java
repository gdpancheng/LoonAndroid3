package com.loonandroid.pc.ioc;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;

import com.google.dexmaker.ProxyBuilder;
import com.loonandroid.pc.annotation.InBack;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InUI;
import com.loonandroid.pc.core.AnalysisManager;
import com.loonandroid.pc.tinybus.TinyBus;
import com.loonandroid.pc.util.LoonConstant;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014年12月19日
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class IocFragmentHandler implements InvocationHandler {

	WeakReference<View> view;
	Bundle savedInstanceState;
	WeakReference<LayoutInflater> inflater;

	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {

		if (method.getName().equals("onCreateView")) {
			inflater = new WeakReference<LayoutInflater>((LayoutInflater) args[0]);
			if (inflater == null||inflater.get()==null) {
				inflater = new WeakReference<LayoutInflater>( LayoutInflater.from(((View) args[1]).getContext()));
			}
			savedInstanceState = (Bundle) args[2];
			int id = LoonConstant.Number.ID_NONE;
			Class<?> clazz = proxy.getClass().getSuperclass();
			InLayer inLayer = clazz.getAnnotation(InLayer.class);
			if (null != inLayer) {
				id = inLayer.value();
			}
			if (id == LoonConstant.Number.ID_NONE) {
				Ioc.getIoc().getLogger().e("当前适配器的item布局没设定");
				return null;
			}
			view = new WeakReference<View>(inflater.get().inflate(id, null));
			AnalysisManager.analysisOrther(view.get(), proxy);
			if (null == view) {
				Ioc.getIoc().getLogger().e(proxy.getClass().getSuperclass().getName() + " 布局为空 ");
			}
			return view.get();
		}
		if (method.getName().equals("getLayoutInflater")) {
			return inflater;
		}
		
		if (method.getName().equals("onStart")) {
			if (Ioc.hasBus(proxy)) {
				TinyBus bus = TinyBus.from(Ioc.getIoc().getApplication());
				bus.register(proxy);
            }
			return ProxyBuilder.callSuper(proxy, method, args);
		}
		
		if (method.getName().equals("onStop")) {
			if (Ioc.hasBus(proxy)) {
				TinyBus bus = TinyBus.from(Ioc.getIoc().getApplication());
				bus.unregister(proxy);
            }
		
			return ProxyBuilder.callSuper(proxy, method, args);
		}
		
		if (method.getName().equals("getSavedInstanceState")) {
			return savedInstanceState;
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

		if (method.getAnnotation(InUI.class) != null) {
			if (Looper.myLooper() != Looper.getMainLooper()) {
				Message msg = new Message();
				MethodEntity entity = new MethodEntity();
				entity.proxy = proxy;
				entity.args = args;
				entity.method = method;
				msg.obj = entity;
				handler.sendMessage(msg);
			}
			return 0;
		}
		return ProxyBuilder.callSuper(proxy, method, args);
	}

	public static  Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			MethodEntity entity = (MethodEntity) msg.obj;
			try {
				ProxyBuilder.callSuper(entity.proxy, entity.method, entity.args);
			} catch (Throwable e) {
				e.printStackTrace();
			}
		};
	};

	public static class MethodEntity {
		public Object proxy;
		public Method method;
		public Object[] args;
	}
}
