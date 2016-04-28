package com.android.demo.util;

import java.util.ArrayList;


import com.android.demo.activity.AutoFragmentActivity;
import com.android.demo.activity.BaseAdapterActivity;
import com.android.demo.activity.BusFragmentActivity;
import com.android.demo.activity.CountdownActivity;
import com.android.demo.activity.CustomAdapterActivity;
import com.android.demo.activity.CustomAdapterActivity2;
import com.android.demo.activity.GetPhotoActivity;
import com.android.demo.activity.HttpActivity;
import com.android.demo.activity.InAllActivity;
import com.android.demo.activity.InBackActivity;
import com.android.demo.activity.InBeanActivity;
import com.android.demo.activity.InBusActivity;
import com.android.demo.activity.InHashMapActivity;
import com.android.demo.activity.InLayerActivity;
import com.android.demo.activity.InPLayerActivity;
import com.android.demo.activity.InSourceActivity;
import com.android.demo.activity.InViewActivity;
import com.android.demo.activity.LifeCyCleActivity;
import com.android.demo.activity.LoginActivity;
import com.android.demo.activity.Module2Activity;
import com.android.demo.activity.ModuleActivity;
import com.android.demo.activity.NoAdapterActivity;
import com.android.demo.activity.ParamFragmentActivity;
import com.android.demo.activity.PullGridActivity;
import com.android.demo.activity.PullHorizontalScrollViewActivity;
import com.android.demo.activity.PullListViewActivity;
import com.android.demo.activity.PullScrollViewActivity;
import com.android.demo.activity.PullVerticalViewPagerActivity;
import com.android.demo.activity.PullViewPagerActivity;
import com.android.demo.activity.PullWebViewActivity;
import com.android.demo.activity.SimpleAdapterActivity;
import com.android.demo.activity.SimpleAdapterActivity2;
import com.android.demo.activity.SimpleAdapterActivity3;
import com.android.demo.activity.ValidatorActivity;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014年12月9日
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class Constant {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public final static ArrayList<MenuEntity> entities = new ArrayList<MenuEntity>() {
        
		private static final long serialVersionUID = 1L;
		{
			MenuEntity basic = new MenuEntity();
			basic.title = "基础功能";
			ArrayList<MyHashMap<String, Class>> menus = new ArrayList<MyHashMap<String, Class>>();
			menus.add(new MyHashMap<String, Class>().put("Inlayer注解", InLayerActivity.class));
			menus.add(new MyHashMap<String, Class>().put("InPlayer注解", InPLayerActivity.class));
			menus.add(new MyHashMap<String, Class>().put("Activity生命周期", LifeCyCleActivity.class));
			menus.add(new MyHashMap<String, Class>().put("InView注解", InViewActivity.class));
			menus.add(new MyHashMap<String, Class>().put("InSource注解", InSourceActivity.class));
			menus.add(new MyHashMap<String, Class>().put("InAll注解", InAllActivity.class));
			menus.add(new MyHashMap<String, Class>().put("后台进程注解", InBackActivity.class));
			menus.add(new MyHashMap<String, Class>().put("方法事件注解", InPLayerActivity.class));
			menus.add(new MyHashMap<String, Class>().put("基类注解范例", InPLayerActivity.class));
			menus.add(new MyHashMap<String, Class>().put("自动Fragment", AutoFragmentActivity.class));
			menus.add(new MyHashMap<String, Class>().put("手动Fragment", ParamFragmentActivity.class));
			basic.menus = menus;
			add(basic);
			// =======================================================
			// 适配器功能
			basic = new MenuEntity();
			basic.title = "适配器功能";
			menus = new ArrayList<MyHashMap<String, Class>>();
			menus.add(new MyHashMap<String, Class>().put("无适配器", NoAdapterActivity.class));
			menus.add(new MyHashMap<String, Class>().put("无参baseAdapter", BaseAdapterActivity.class));
			menus.add(new MyHashMap<String, Class>().put("自定义adapter", CustomAdapterActivity.class));
			menus.add(new MyHashMap<String, Class>().put("自定义adapter", CustomAdapterActivity2.class));
			menus.add(new MyHashMap<String, Class>().put("无需自己处理", SimpleAdapterActivity.class));
			menus.add(new MyHashMap<String, Class>().put("无需自己处理2", SimpleAdapterActivity2.class));
			menus.add(new MyHashMap<String, Class>().put("通用适配器", SimpleAdapterActivity3.class));

			basic.menus = menus;
			add(basic);
			basic = new MenuEntity();
			basic.title = "功能类";
			menus = new ArrayList<MyHashMap<String, Class>>();
			menus.add(new MyHashMap<String, Class>().put("网络请求", HttpActivity.class));
			menus.add(new MyHashMap<String, Class>().put("版本更新", null));
			menus.add(new MyHashMap<String, Class>().put("断点下载", null));
			menus.add(new MyHashMap<String, Class>().put("跨进程Activity", InBusActivity.class));
			menus.add(new MyHashMap<String, Class>().put("跨进程Fragment", BusFragmentActivity.class));
			menus.add(new MyHashMap<String, Class>().put("josn转bean", InBeanActivity.class));
			menus.add(new MyHashMap<String, Class>().put("josn转map", InHashMapActivity.class));
			menus.add(new MyHashMap<String, Class>().put("倒计时", CountdownActivity.class));
			basic.menus = menus;
			add(basic);
			basic = new MenuEntity();
			basic.title = "下拉刷新上拉加载更多";
			menus = new ArrayList<MyHashMap<String, Class>>();
			menus.add(new MyHashMap<String, Class>().put("listview", PullListViewActivity.class));
			menus.add(new MyHashMap<String, Class>().put("Grid", PullGridActivity.class));
			menus.add(new MyHashMap<String, Class>().put("横向ScrollView", PullHorizontalScrollViewActivity.class));
			menus.add(new MyHashMap<String, Class>().put("纵向ScrollView", PullScrollViewActivity.class));
			menus.add(new MyHashMap<String, Class>().put("横向viewpage", PullViewPagerActivity.class));
			menus.add(new MyHashMap<String, Class>().put("纵向viewpage", PullVerticalViewPagerActivity.class));
			menus.add(new MyHashMap<String, Class>().put("WebView", PullWebViewActivity.class));
			basic.menus = menus;
			add(basic);
			basic = new MenuEntity();
			basic.title = "View模块类";
			menus = new ArrayList<MyHashMap<String, Class>>();
			menus.add(new MyHashMap<String, Class>().put("xml使用模块", ModuleActivity.class));
			menus.add(new MyHashMap<String, Class>().put("变量使用模块", Module2Activity.class));
			menus.add(new MyHashMap<String, Class>().put("模块综合使用", Module2Activity.class));
			menus.add(new MyHashMap<String, Class>().put("输入框验证", ValidatorActivity.class));
			menus.add(new MyHashMap<String, Class>().put("tabs模块", Module2Activity.class));
			menus.add(new MyHashMap<String, Class>().put("banner模块", null));
			menus.add(new MyHashMap<String, Class>().put("底部弹出模块", null));
			basic.menus = menus;
			add(basic);
			basic = new MenuEntity();
			basic.title = "组件类";
			menus = new ArrayList<MyHashMap<String, Class>>();
			menus.add(new MyHashMap<String, Class>().put("获取图片组件", GetPhotoActivity.class));
			menus.add(new MyHashMap<String, Class>().put("登陆组件", LoginActivity.class));
			menus.add(new MyHashMap<String, Class>().put("引导页组件", null));
			menus.add(new MyHashMap<String, Class>().put("数据库组件", null));
			menus.add(new MyHashMap<String, Class>().put("tab组件", null));
			menus.add(new MyHashMap<String, Class>().put("二维码组件", null));
			menus.add(new MyHashMap<String, Class>().put("多国语言组件", null));
			menus.add(new MyHashMap<String, Class>().put("表情组件", null));
			menus.add(new MyHashMap<String, Class>().put("滤镜组件", null));
			// menus.add(new MyHashMap<String, Class>().put("图片下载组件", null));
			basic.menus = menus;
			add(basic);
		}
	};

	@SuppressWarnings({ "unchecked" })
	public final static ArrayList<HttpEntity> https = new ArrayList<HttpEntity>() {
		{
			HttpEntity basic = new HttpEntity();
			basic.title = "同步请求";
			ArrayList<MyHashMap<String, Integer>> menus = new ArrayList<MyHashMap<String, Integer>>();
			menus.add(new MyHashMap<String, Integer>().put("get请求", 0));
			menus.add(new MyHashMap<String, Integer>().put("post键值对", 0));
			menus.add(new MyHashMap<String, Integer>().put("post字符串", 0));
			menus.add(new MyHashMap<String, Integer>().put("Web Service", 0));
			menus.add(new MyHashMap<String, Integer>().put("form请求", 0));
			basic.menus = menus;
			add(basic);
			// =======================================================
			// 适配器功能
			basic = new HttpEntity();
			basic.title = "异步请求";
			menus = new ArrayList<MyHashMap<String, Integer>>();
			menus.add(new MyHashMap<String, Integer>().put("get请求", 0));
			menus.add(new MyHashMap<String, Integer>().put("post键值对", 0));
			menus.add(new MyHashMap<String, Integer>().put("post字符串", 0));
			menus.add(new MyHashMap<String, Integer>().put("Web Service", 0));
			menus.add(new MyHashMap<String, Integer>().put("form请求", 0));
			menus.add(new MyHashMap<String, Integer>().put("正确回调", 0));
			menus.add(new MyHashMap<String, Integer>().put("错误回调", 0));
			menus.add(new MyHashMap<String, Integer>().put("多个请求", 0));
			basic.menus = menus;
			add(basic);
			basic = new HttpEntity();
			basic.title = "外挂第三方网络请求模块";
			menus = new ArrayList<MyHashMap<String, Integer>>();
			menus.add(new MyHashMap<String, Integer>().put("切换请求核心", 0));
			basic.menus = menus;
			add(basic);
		}
	};

	public static class HttpUrl {
		/** 系统配置的数据库名 **/
		public static final String BASIC = "http://cs2.137home.com/index.php?m=api&a=";
		public static final String LOGIN = BASIC + "login";
		public static final String LONGIN_POST = BASIC + "login";
		public static final String ASYNCLOGINPOST = BASIC + "login";
		public static final String ASYNCWEB = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";
		public static final String ASYNCLOGINWEB = "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";
		public static final String LOGINFORM = BASIC + "login";
		public static final String ASYNCLOGINFORM = BASIC + "login";
		public static final String ASYNCLOGINDISTRIBUTE = BASIC + "login";
		public static final String FILES = "http://cs2.137home.com/index.php?m=api&a=edit_avatar";
		public static final int LOGIN_KEY = 0;
	}
}
