package com.loonandroid.pc.net;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.loonandroid.pc.annotation.InForm;
import com.loonandroid.pc.annotation.InGet;
import com.loonandroid.pc.annotation.InNet;
import com.loonandroid.pc.annotation.InParam;
import com.loonandroid.pc.annotation.InPost;
import com.loonandroid.pc.annotation.InWeb;
import com.loonandroid.pc.core.IocAnalysis;
import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.util.LoonConstant;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2015年1月4日
 * Copyright @ 2015 BU
 * Description: 类描述
 *
 * History:
 */
public class IocHttpInvocationHandler implements InvocationHandler {
	private WeakReference<Object> object;

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) {
		IocHttp http = new IocHttp();
		return http.deal(proxy, method, args, object==null?object:object.get());
	}

	public Object getObject() {
		return object==null?object:object.get();
	}

	public void setObject(Object object) {
		this.object = new WeakReference<Object>(object);
	}

	public static class IocHttp {

		private String url;
		private int code = LoonConstant.Number.ID_NONE;
		private LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		private String param = "";
		private NetConfig config;

		public Object deal(Object proxy, Method method, Object[] args, Object object) {
			String urlField = method.getName().toUpperCase();
			Class<?>[] inters = proxy.getClass().getInterfaces();
			if (inters.length == 0) {
				Ioc.getIoc().getLogger().e(" 当前类不是网络请求接口无法使用 ");
				return null;
			}
			Class<?> clazz = inters[0];
			InNet inNet = clazz.getAnnotation(InNet.class);
			if (inNet == null) {
				Ioc.getIoc().getLogger().e(" 当前类缺少网络URL注解 ");
				return null;
			}
			clazz = inNet.value();

			config = new NetConfig();

			Net type = Net.NONE;
			InGet get = method.getAnnotation(InGet.class);
			if (get != null) {
				type = Net.GET;
				url = get.value().length() > 0 ? get.value() : url;
			}

			InPost post = method.getAnnotation(InPost.class);
			if (post != null) {
				type = Net.POST;
				url = post.value().length() > 0 ? post.value() : url;
			}

			InForm form = method.getAnnotation(InForm.class);
			if (form != null) {
				type = Net.FORM;
				url = form.value().length() > 0 ? form.value() : url;
			}

			InWeb web = method.getAnnotation(InWeb.class);
			if (web != null) {
				type = Net.WEB;
				url = web.value().length() > 0 ? web.value() : url;
				config.setMethod(web.method());;
				config.setName_space(web.space());
			}
			Field field;
			if (url == null) {
				try {
					field = clazz.getDeclaredField(urlField);
					field.setAccessible(true);
					url = (String) field.get(clazz);
				} catch (Exception e) {
					Ioc.getIoc().getLogger().e(" 当前调用的方法名和网络请求链接不匹配 ");
					return null;
				}
			}

			try {
				field = clazz.getDeclaredField(urlField + "_KEY");
				if (field != null) {
					field.setAccessible(true);
					code = field.getInt(clazz);
				}
			} catch (Exception e) {
			}

			// 获取所有注解
			Annotation[][] paramAnnotations = method.getParameterAnnotations();
			Class<?>[] types = method.getParameterTypes();
			Type[] types2 = method.getGenericParameterTypes();

			for (int i = 0; i < paramAnnotations.length; i++) {
				Annotation[] annotations = paramAnnotations[i];
				InParam inParam = null;
				if (null != annotations && annotations.length > 0) {
					if (annotations[0].annotationType() == InParam.class) {
						inParam = (InParam) annotations[0];
					}
				}
				// 说明参数无注解
				if (Map.class.isAssignableFrom(types[i])) {
					// 有注解，但是参数是map类型 则注解无效
					boolean isFile = false;
					if (types2[i] instanceof ParameterizedType) {
						ParameterizedType parameterizedType = (ParameterizedType) types2[i];
						Type[] actualType = parameterizedType.getActualTypeArguments();
						for (int j = 0; j < actualType.length; j++) {
							if (actualType[j] == File.class) {
								// 说明是文件
								isFile = true;
								break;
							}
						}
					}
					// 说明是参数
					if (!isFile) {
						params.putAll((Map) args[i]);
					} else {
						config.addFiles((Map) args[i]);
					}
				}

				if (List.class.isAssignableFrom(types[i]) || Set.class.isAssignableFrom(types[i])) {
					// 有注解，但是参数是set类型 则注解无效
					if (types2[i] instanceof ParameterizedType) {
						ParameterizedType parameterizedType = (ParameterizedType) types2[i];
						Type[] actualType = parameterizedType.getActualTypeArguments();
						for (int j = 0; j < actualType.length; j++) {
						}
					}
				}

				if (String.class.isAssignableFrom(types[i])) {
					// 有注解，参数类型是string 则注解生效
					if (inParam == null) {
						// 无注解
						param = (String) args[i];
					} else {
						if (inParam.value().trim().length() > 0) {
							params.put(inParam.value(), (String) args[i]);
						} else {
							param = (String) args[i];
							config.setParam(param);
						}
					}
				}
				if (File.class.isAssignableFrom(types[i])) {
					// 有注解，参数类型是string 则注解生效
					// 无注解
					if (inParam == null) {
						config.addFile((File) args[i]);
					} else {
						config.addFile(inParam.value(), (File) args[i]);
					}
				}
				if (NetConfig.class.isAssignableFrom(types[i])) {
					// 有注解，参数类型是string 则注解生效
					NetConfig config = (NetConfig) args[i];
					this.config.setCharset(config.getCharset());
					this.config.setHead(config.getHead());
					this.config.setMethod(config.getMethod());
				}
			}
			config.setCode(code);
			config.setUrl(url);
			config.setType(type);
			config.setObject(object);
			config.setParams(params);
			final IocHttpListener httpListener = IocListener.newInstance().getHttpListener();
			if (method.getReturnType() != void.class) {
				// 同步
				return httpListener.netCore(this.config);
			} else {
				// 异步
				IocAnalysis.mAnalysisActivityPool.execute(new Runnable() {
					@Override
					public void run() {
						httpListener.callback(config, httpListener.netCore(config));
					}
				});
				return null;
			}
		}
	}
}
