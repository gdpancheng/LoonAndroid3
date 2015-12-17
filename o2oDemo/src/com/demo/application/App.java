package com.demo.application;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Application;

import com.bluemobi.config.SysConfiguration;
import com.demo.util.Constant;
import com.demo.util.Go;
import com.loonandroid.pc.handler.Handler_SharedPreferences;
import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.net.IocHttpListener;
import com.loonandroid.pc.net.IocListener;
import com.loonandroid.pc.net.NetConfig;
import com.loonandroid.pc.net.NetConfig.UploadFile;
import com.loonandroid.pc.plug.PlugConstants;
import com.loonandroid.pc.plug.login.LoginParameter;
import com.loonandroid.pc.plug.net.FastHttp;
import com.loonandroid.pc.plug.net.InternetConfig;
import com.loonandroid.pc.plug.net.ResponseEntity;
import com.loonandroid.pc.plug.photo.PhotoParameter;
import com.loonandroid.pc.util.Http;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * 
 * <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2015年12月10日 上午11:59:54
 */
public class App extends Application {

	public static App app;
	public static Http<Go> http;

	@Override
	public void onCreate() {
		Ioc.getIoc().init(this);
		super.onCreate();
		app = this;
		plugLoad();
		SysConfiguration.getInstance().init(this);
		// 初始化数据 因为数据过大 所以放到子线程处理
		new Thread(new Runnable() {
			@Override
			public void run() {
				Constant.init();
			}
		}).start();
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
		http = new Http<Go>(Go.class);
		IocListener.newInstance().setHttpListener(listener);
	}

	/**
	 * 数据存储到本地数据库
	 * 
	 * @author gdpancheng@gmail.com 2013-7-29 下午4:03:28
	 * @param key
	 * @param value
	 * @return void
	 */
	public void setData(String key, String value) {
		Handler_SharedPreferences.WriteSharedPreferences("Cache", key, value);
	}

	public void clearData() {
		Handler_SharedPreferences.ClearSharedPreferences("Cache");
	}

	/**
	 * 取出本地数据
	 * 
	 * @author gdpancheng@gmail.com 2013-7-29 下午4:03:41
	 * @param key
	 * @return
	 * @return String
	 */
	public String getData(String key) {
		return Handler_SharedPreferences.getValueByName("Cache", key, Handler_SharedPreferences.STRING).toString();
	}

	public static class Result {
		@Override
		public String toString() {
			return "Result [params=" + params + ", status=" + status + ", key=" + key + ", object=" + object + "]";
		}

		public HashMap<String, Object> params;

		public final static int OK = 0;
		public final static int ERROR = 1;
		public int status = 0;
		public int key = 0;
		public String object;

		public boolean isOk() {
			return status == OK;
		}
	}

	public IocHttpListener<Result> listener = new IocHttpListener<Result>() {

		final OkHttpClient client = new OkHttpClient();

		@Override
		public Result netCore(NetConfig config) {
			System.out.println("拦截请求：" + config);
			Result result = new Result();
			result.key = config.getCode();
			result.params = config.getParams();
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
				InternetConfig netConfig = InternetConfig.defaultConfig();
				ResponseEntity results = FastHttp.post(config.getUrl(), config.getParams(), netConfig);
				result.status = results.getStatus();
				result.object = results.getContentAsString();
			}
				break;
			case FORM: {
				ArrayList<UploadFile> files = config.getFiles();
				HashMap<String, File> uploads = new HashMap<String, File>();
				for (UploadFile uploadFile : files) {
					uploads.put(uploadFile.fileKey, uploadFile.file);
                }
				ResponseEntity results = FastHttp.form(config.getUrl(), config.getParams(), uploads);
				result.status = results.getStatus();
				result.object = results.getContentAsString();
			}
				break;
			case WEB:
				// 这里是soap协议
				break;
			}
			System.out.println("拦截结果：" + result);
			return result;
		}
	};
}
