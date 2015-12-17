package com.android.demo.app;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import android.app.Application;

import com.android.demo.http.HttpInterFace;
import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.net.IocHttpListener;
import com.loonandroid.pc.net.IocListener;
import com.loonandroid.pc.net.NetConfig;
import com.loonandroid.pc.plug.PlugConstants;
import com.loonandroid.pc.plug.login.LoginParameter;
import com.loonandroid.pc.plug.net.FastHttp;
import com.loonandroid.pc.plug.net.InternetConfig;
import com.loonandroid.pc.plug.net.ResponseEntity;
import com.loonandroid.pc.plug.photo.PhotoParameter;
import com.loonandroid.pc.util.Coundown;
import com.loonandroid.pc.util.Http;
import com.loonandroid.pc.util.LanguageSettingUtil;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Request.Builder;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

public class App extends Application {

	public static App app;
	public Http<HttpInterFace> http;
	
	@Override
	public void onCreate() {
		// ------------------------------------------------------------------------------------------------------------
		app = this;
		Ioc.getIoc().init(this);
		LanguageSettingUtil.init(this);
		// ------------------------------------------------------------------------------------------------------------
		// 装载组件
		plugLoad();
		// -----------------------------------------------------------------------------------------------------------
		// 图片下载统一处理入口
		super.onCreate();
		
		// -----------------------------------------------------------------------------------------------------------
		// 开启倒计时 参数为时间间隔
		Coundown.start(1000);
	}

	// 增加一个自动获取照片的第三方库
	public void plugLoad() {
		// ------------------------------------------------------------------
		// 从本地或者摄像头获取照片模块
		PlugConstants.setPlugIn(new PhotoParameter());
		// 登录模块
		PlugConstants.setPlugIn(new LoginParameter());
		// ------------------------------------------------------------------
		// 框架只是实现了分发，具体核心请求模块还必须用自己或者第三方网络请求框架
		// 网络请求的统一拦截处 异步请求 请返回 请求的值 异步 手动分发
		http = new Http<HttpInterFace>(HttpInterFace.class);
		IocListener.newInstance().setHttpListener(listener);
	}

	public IocHttpListener<ResponseEntity> listener = new IocHttpListener<ResponseEntity>() {

		@Override
		public ResponseEntity netCore(NetConfig config) {
			System.out.println("拦截请求："+config);
			InternetConfig netConfig = InternetConfig.defaultConfig();
			ResponseEntity reslut = null;
			switch (config.getType()) {
			case GET:
				reslut = FastHttp.get(config.getUrl(), config.getParams(), netConfig);
				break;
			case POST:
				reslut = FastHttp.post(config.getUrl(), config.getParams(), netConfig);
				break;
			case FORM:
				reslut = FastHttp.form(config.getUrl(), config.getParams(), new HashMap<String, File>(), netConfig);
				break;
			case WEB:
				netConfig.setMethod(config.getMethod());
				netConfig.setName_space(config.getName_space());
				reslut = FastHttp.webServer(config.getUrl(), config.getParams(), netConfig, "post");
				break;
			}
			System.out.println("拦截结果："+reslut);
			return reslut;
		}
	};

	public static class Result {
		public final static int OK = 0;
		public final static int ERROR = 1;
		int status = 0;
		public String object;
	}

	public IocHttpListener<Result> listener2 = new IocHttpListener<Result>() {

		final OkHttpClient client = new OkHttpClient();

		@Override
		public Result netCore(NetConfig config) {
			System.out.println("拦截请求："+config);
			Result result = new Result();
			System.out.println(config);
			switch (config.getType()) {
			case GET: {
				try {
					Request request = new Request.Builder().url(config.getUrl()).build();
					Response response = client.newCall(request).execute();
					if (!response.isSuccessful()) {
						result.status = Result.ERROR;
						break;
					}
					Headers responseHeaders = response.headers();
					for (int i = 0; i < responseHeaders.size(); i++) {
						System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
					}
					result.status = Result.OK;
					result.object = response.body().string();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;
			case POST: {
				try {
					StringBuffer buffer = new StringBuffer();
					for (String key : config.getParams().keySet()) {
	                    if (buffer.length() != 0) {
	                    	buffer.append("&");
                        }
	                    buffer.append(key+"="+config.getParams().get(key));
                    }
					MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
					Builder builder = new Request.Builder().url(config.getUrl());
					builder.post(RequestBody.create(MEDIA_TYPE_MARKDOWN, buffer.toString()));
					Request request = builder.build();
					Response response = client.newCall(request).execute();
					if (!response.isSuccessful()) {
						result.status = Result.ERROR;
						break;
					}
					Headers responseHeaders = response.headers();
					for (int i = 0; i < responseHeaders.size(); i++) {
						System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
					}
					result.status = Result.OK;
					result.object = response.body().string();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;
			case FORM:{
				try {
					StringBuffer buffer = new StringBuffer();
					for (String key : config.getParams().keySet()) {
	                    if (buffer.length() != 0) {
	                    	buffer.append("&");
                        }
	                    buffer.append(key+"="+config.getParams().get(key));
                    }
					MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");
					Builder builder = new Request.Builder().url(config.getUrl());
					builder.post(RequestBody.create(MEDIA_TYPE_MARKDOWN, buffer.toString()));
					Request request = builder.build();
					Response response = client.newCall(request).execute();
					if (!response.isSuccessful()) {
						result.status = Result.ERROR;
						break;
					}
					Headers responseHeaders = response.headers();
					for (int i = 0; i < responseHeaders.size(); i++) {
						System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
					}
					result.status = Result.OK;
					result.object = response.body().string();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				break;
			case WEB:
				//这里是soap协议
				break;
			}
			System.out.println("拦截结果："+result);
			return result;
		}
	};
}
