package com.loonandroid.pc.ioc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.google.dexmaker.ProxyBuilder;
import com.loonandroid.pc.annotation.InBack;
import com.loonandroid.pc.annotation.InUI;
import com.loonandroid.pc.plug.PlugConstants;
import com.loonandroid.pc.plug.PlugInCallBack;

/**
 * 拦截器 对所有需要自动化注解的类 进行拦截 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月21日 上午12:17:33
 */
public class LoonComponentHandler implements InvocationHandler {

	static final int NONE = 0;
	
	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		
		PlugInCallBack back =PlugConstants.isIntercept(proxy,method);
		if (back!=null) {
			Object result = back.callback(proxy,method,args);
			if (result==null) {
				return NONE;
            }
			if (result.toString().equals(PlugConstants.INTERCEPT_NO)) {
				return ProxyBuilder.callSuper(proxy, method, args);
            }else {
				return result;
			}
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
			if (Activity.class.isAssignableFrom(proxy.getClass())&&((Activity)proxy).isFinishing()) {
	            Ioc.getIoc().getLogger().d("当前上下文被销毁，无法更新UI");
				return 0;
            }
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

	public  Handler handler = new Handler() {
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
