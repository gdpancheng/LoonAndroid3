package com.loonandroid.pc.ioc;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.google.dexmaker.ProxyBuilder;
import com.loonandroid.pc.annotation.InBack;
import com.loonandroid.pc.annotation.InUI;
import com.loonandroid.pc.interfaces.LoonCallback;

/**
 * 拦截器 对所有需要自动化注解的类 进行拦截
 * 	<br>-----------------------------------------------
 * <br>oooO...............
 * <br>(....) ... Oooo...
 * <br>.\..(.....(.....).......
 * <br>..\_)..... )../........
 * <br>.......... (_/..........
 * <br>微信 gdpancheng
 * <br>-----------------------------------------------
 * @author gdpancheng@gmail.com 2014年12月21日 上午12:17:33
 */
public class IocModuleHandler implements InvocationHandler {

	private  WeakReference<View> view;
	private Object data;
	private WeakReference<LoonCallback> callback;

	@Override
	public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
		//---------------------------------------------------------------------
		//开始拦截我们想要拦截的方法 然后返回我们想要返回的内容
		
		//当我们使用一个组件的时候，其实拿到的是这个组件的子类的对象
		//这个子类就是我们的代理类，并且其实现了LoonModule接口
		//LoonModule有以下方法
		//作为一个组件对象，我们肯定要传参进去，也需要获得这个组件的view
		//或者我们需要 回调里面的相关事件
		if (method.getName().equals("getView")) {
			if (null == view) {
				Ioc.getIoc().getLogger().e(proxy.getClass().getSuperclass().getName() + " 布局为空 ");
			}
			return view.get();
		}
		if (method.getName().equals("setData")) {
			this.data = args[0];
			return 0;
		}
		if (method.getName().equals("getData")) {
			if (null == data) {
				Ioc.getIoc().getLogger().e(proxy.getClass().getSuperclass().getName() + " 数据为空 ");
			}
			return data;
		}
		if (method.getName().equals("setCallBack")) {
			this.callback = new WeakReference<LoonCallback>( (LoonCallback) args[0]);
			return 0;
		}
		if (method.getName().equals("getCallBack")) {
			if (null == callback) {
				Ioc.getIoc().getLogger().e(proxy.getClass().getSuperclass().getName() + " 回调为空 ");
			}
			return callback.get();
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
		//对于不监听的方法，还给我们的组件类
		return ProxyBuilder.callSuper(proxy, method, args);
	}

	public View getView() {
		if (view==null) {
	        return null;
        }
		return view.get();
	}

	public void setView(View view) {
		this.view = new WeakReference<View>(view);
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public LoonCallback getCallback() {
		return callback==null?null:callback.get();
	}

	public void setCallback(LoonCallback callback) {
		this.callback = new WeakReference<LoonCallback>(callback);
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
