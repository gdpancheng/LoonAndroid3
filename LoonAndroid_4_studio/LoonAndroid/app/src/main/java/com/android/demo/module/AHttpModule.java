package com.android.demo.module;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.demo.app.App;
import com.android.demo.app.App.Result;
import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InHttp;
import com.loonandroid.pc.annotation.InModule;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.interfaces.LoonModule;
import com.loonandroid.pc.listener.OnClick;
import com.loonandroid.pc.net.IocListener;
import com.loonandroid.pc.net.NetConfig;
import com.loonandroid.pc.plug.net.ResponseEntity;

/**
 * 同步请求模块 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2015年11月15日 下午11:46:11
 */
@InModule(R.layout.module_http)
public abstract class AHttpModule implements LoonModule {

	int type = 0;
	int index = 0;

	@InAll
	Views v;

	class Views {
		@InBinder(listener = OnClick.class, method = "click")
		Button bt_send;
		TextView tv_result;
	}

	private void click(View view) {
		switch (type) {
		//同步请求
		case 0:
			new Thread(new Runnable() {
				@Override
				public void run() {
					Message message = new Message();
					switch (index) {
					case 0:
						// 方法如果有返回值 则是同步 这里是有返回值则是同步
						// 返回值的类型是根据你在listener泛型的类型
						LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
						params.put("name", "哈哈");
						Object object = App.app.http.u(AHttpModule.this).login(params);
						if (ResponseEntity.class.isAssignableFrom(object.getClass())) {
							message.obj = ((ResponseEntity) object).getContentAsString();
						} else {
							message.obj = ((Result) object).object;
						}

						handler.sendMessage(message);
						break;
					case 1:
						Object object1 = App.app.http.u(AHttpModule.this).asyncLoginPost("aa", "bb");
						if (ResponseEntity.class.isAssignableFrom(object1.getClass())) {
							message.obj = ((ResponseEntity) object1).getContentAsString();
						} else {
							message.obj = ((Result) object1).object;
						}
						handler.sendMessage(message);
						break;
					case 2:
						Object object2 = App.app.http.u(AHttpModule.this).postStr("aabb");
						if (ResponseEntity.class.isAssignableFrom(object2.getClass())) {
							message.obj = ((ResponseEntity) object2).getContentAsString();
						} else {
							message.obj = ((Result) object2).object;
						}
						handler.sendMessage(message);
						break;
					case 3:
						Object object3 = App.app.http.u(AHttpModule.this).asyncWeb();
						if (ResponseEntity.class.isAssignableFrom(object3.getClass())) {
							message.obj = ((ResponseEntity) object3).getContentAsString();
						} else {
							message.obj = ((Result) object3).object;
						}
						handler.sendMessage(message);
						// 或者
						NetConfig config = new NetConfig();
						config.setMethod("getRegionProvince");
						config.setName_space("http://WebXml.com.cn/");
						object3 = App.app.http.u(AHttpModule.this).asyncLoginWeb("3522", config);
						message = new Message();
						if (ResponseEntity.class.isAssignableFrom(object3.getClass())) {
							message.obj = ((ResponseEntity) object3).getContentAsString();
						} else {
							message.obj = ((Result) object3).object;
						}
						handler.sendMessage(message);
						break;
					case 4:

						File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "crash");
						if (!dir.exists())
							dir.mkdir();
						try {
							FileOutputStream fos = new FileOutputStream(new File(dir, "test"));
							fos.write("afdasdfasdfasdfasdfs".getBytes());
							fos.close();
						} catch (Exception e) {
							e.printStackTrace();
						}

						LinkedHashMap<String, File> files = new LinkedHashMap<String, File>();
						files.put("logo", new File(dir, "demo_quan.jpg"));
						ResponseEntity login = App.app.http.u(AHttpModule.this).login("5", files);
						System.out.println(login);
						message.obj = login.getContentAsString();
						handler.sendMessage(message);
						break;
					}
				}
			}).start();
			break;
		case 1:
			// --------------------------------------------------------------------------------
			// 异步请求 请求的方法如果没有返回值，则是异步，异步需要在请求的当前类中
			// 参数为listener泛型的类型的方法添加@InHttp进行注解，这个方法表示异步请求回调
			// 这个方法会运行在主线程
			switch (index) {
			case 0:
				App.app.http.u(AHttpModule.this).login("aaa", "bbbb");
				break;
			case 1:
				LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
				params.put("name", "哈哈");
				App.app.http.u(AHttpModule.this).loginPost(params);
				break;
			case 2:
				break;
			case 3:
				break;
			case 4:
				break;
			}
			break;
		case 2:
			// 切换成请求核心库
			if (IocListener.newInstance().getHttpListener() == App.app.listener) {
				//OKhttp
				IocListener.newInstance().setHttpListener(App.app.listener2);
            }else {
            	//自写的网络请求包
            	IocListener.newInstance().setHttpListener(App.app.listener);
			}
			break;
		}
		Toast.makeText(view.getContext(), "开始进行网络请求", Toast.LENGTH_SHORT).show();
	}

	/**
	 * 异步走这里 只要带有@InHttp 并且参数类型为@App中所创建{@IocHttpListener}的泛型一致
	 * 
	 * @author gdpancheng@gmail.com 2015年11月17日 上午10:16:06
	 * @param entity
	 * @return void
	 */
	@InHttp
	public void result(ResponseEntity entity) {
		v.tv_result.setText(entity.getContentAsString());
	}

	@InHttp
	public void result2(Result result) {
		v.tv_result.setText(result.object);
	}

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			v.tv_result.setText(msg.obj.toString());
		};
	};

	@Init
	public void init() {
		HashMap<String, Integer> test = getData();
		type = test.get("item");
		index = test.get("index");
		switch (type) {
		case 0:
			setABtTile();
			break;
		case 1:
			setSBtTile();
			break;
		case 2:
			v.bt_send.setText("点击切换网络连接框架");
			break;
		default:
			break;
		}
		System.out.println("得到的参数:" + test);
	}

	private void setABtTile() {
		switch (index) {
		case 0:
			v.bt_send.setText("同步GET请求");
			break;
		case 1:
			v.bt_send.setText("同步POST键值对");
			break;
		case 2:
			v.bt_send.setText("同步POST字符串");
			break;
		case 3:
			v.bt_send.setText("同步Web Service");
			break;
		case 4:
			v.bt_send.setText("同步form 表单");
			break;
		}
	}

	private void setSBtTile() {
		switch (index) {
		case 0:
			v.bt_send.setText("异步GET请求");
			break;
		case 1:
			v.bt_send.setText("异步POST键值对");
			break;
		case 2:
			v.bt_send.setText("异步POST字符串");
			break;
		case 3:
			v.bt_send.setText("异步Web Service");
			break;
		case 4:
			v.bt_send.setText("异步form 表单");
			break;
		case 5:
			v.bt_send.setText("异步正确回调");
			break;
		case 6:
			v.bt_send.setText("异步错误回调");
			break;
		case 7:
			v.bt_send.setText("多个请求");
			break;
		}
	}
}
