package com.loonandroid.pc.ioc.config;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;

import com.loonandroid.pc.annotation.InBack;
import com.loonandroid.pc.annotation.InUI;
import com.loonandroid.pc.core.IocAnalysis;
import com.loonandroid.pc.handler.Handler_File;
import com.loonandroid.pc.handler.Handler_Properties;
import com.loonandroid.pc.handler.Handler_System;
import com.loonandroid.pc.interfaces.Bean;
import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.util.LoonConstant;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-10
 * Copyright @ 2014 BU
 * Description: 框架的相关配置
 *
 * History:
 */
public class LoonConfig implements Serializable {

	private static final long serialVersionUID = 1816174167207041419L;
	/**
	 * 默认高度和宽度,所有的缩放比根据这个常数来获得
	 */
	private int w = 1080, h = 1920;
	private static LoonConfig config;
	private String[] permit;
	private String[] limit;
	/** 判断是否引入了代理依赖包 **/
	private boolean isDepend = false;
	/**
	 * 单例模式
	 * 
	 * @author gdpancheng@gmail.com 2014-11-10 上午11:39:45
	 * @return LoonConfig
	 */
	public static LoonConfig instance() {
		if (config == null) {
			config = Handler_File.getObject(LoonConstant.DB.CONFIG_KEY);
		}
		if (config == null) {
			config = new LoonConfig();
		}
		return config;
	}

	@SuppressWarnings("unchecked")
    public void initBean(){
		if (isDepend) {
			//动态代理监控的方法
			Bean.initData();
			//动态代理监控的类 只有有如下注解 才使用
			Bean.setFilter(InBack.class,InUI.class);
		}
	}
	
    public void init() {
		// ----------------------------------------------------------------------------------
		// 先判断是否存在依赖包 如果不存在代理依赖包 很多重要功能**将无法使用
		try {
			if (!isDepend && null != Class.forName("com.google.dexmaker.ProxyBuilder")) {
				isDepend = true;
			}
		} catch (Exception e) {
		}
		initBean();
		String packageName = Ioc.getIoc().getApplication().getPackageName();
		// ///////////////////////////////////////////////////////////////////////////////////
		// 读取配置文件
		Properties properties = Handler_Properties.loadConfigAssets("mvc.properties");
		if (properties != null && properties.containsKey("standard_w")) {
			this.w = Integer.valueOf(properties.get("standard_w").toString());
		}
		if (properties != null && properties.containsKey("standard_h")) {
			this.h = Integer.valueOf(properties.get("standard_h").toString());
		}
		Ioc.getIoc().getLogger().d("屏幕分辨率为:" + Handler_System.getDisplayMetrics() + " 配置文件设置的分辨率为 standard_w:" + getW() + " standard_h:" + getH());
		// 读取允许解析和不允许解析的包名
		HashSet<String> permitSet = new HashSet<String>();
		permitSet.add(packageName);
		if (properties != null && properties.containsKey("permit")) {
			String[] permit = properties.get("permit").toString().trim().split(",");
			permitSet.addAll(new HashSet<String>(Arrays.asList(permit)));
		}
		this.permit = permitSet.toArray(new String[permitSet.size()]);
		
		if (properties != null && properties.containsKey("limit")) {
			HashSet<String> limitSet = new HashSet<String>();
			String[] limit = properties.get("limit").toString().trim().split(",");
			limitSet.addAll(new HashSet<String>(Arrays.asList(limit)));
			this.limit = limitSet.toArray(new String[limitSet.size()]);
		}
		properties = null;
		IocAnalysis.mAnalysisActivityPool.execute(new Runnable() {
			@Override
			public void run() {
				save();
			}
		});
	}

	/**
	 * 获取配置中的宽
	 * 
	 * @author gdpancheng@gmail.com 2014-11-10 上午11:39:55
	 * @return int
	 */
	public int getW() {
		return w;
	}

	/**
	 * 设置配置中的屏幕宽度<br>
	 * 用来计算屏幕缩放宽度比
	 * 
	 * @author gdpancheng@gmail.com 2014-11-10 上午11:40:24
	 * @param w
	 * @return void
	 */
	public void setW(int w) {
		this.w = w;
	}

	/**
	 * 获取配置中的高
	 * 
	 * @author gdpancheng@gmail.com 2014-11-10 上午11:39:55
	 * @return int
	 */
	public int getH() {
		return h;
	}

	/**
	 * 设置配置中的屏幕高度<br>
	 * 用来计算屏幕缩放高度比
	 * 
	 * @author gdpancheng@gmail.com 2014-11-10 上午11:40:24
	 * @param h
	 * @return void
	 */
	public void setH(int h) {
		this.h = h;
	}

	/**
	 * 存储到文件中
	 * 
	 * @author gdpancheng@gmail.com 2014-11-10 上午11:41:47
	 * @return void
	 */
	public void save() {
		Handler_File.setObject(LoonConstant.DB.CONFIG_KEY, this);
	}

	/**
	 * 获取只允许遍历的包名
	 * 
	 * @author gdpancheng@gmail.com 2014-11-12 下午9:34:44
	 * @return String[]
	 */
	public String[] getPermit() {
		return permit;
	}

	/**
	 * 设置只允许遍历的包名
	 * 
	 * @author gdpancheng@gmail.com 2014-11-12 下午9:35:04
	 * @param permit
	 * @return void
	 */
	public void setPermit(String[] permit) {
		this.permit = permit;
	}

	/**
	 * 获得不允许的类名
	 * 
	 * @author gdpancheng@gmail.com 2014年12月7日 下午9:28:39
	 * @return
	 * @return String[]
	 */
	public String[] getLimit() {
		return limit;
	}

	/**
	 * 设置不允许的类名
	 * 
	 * @author gdpancheng@gmail.com 2014年12月7日 下午9:28:39
	 * @return
	 * @return String[]
	 */
	public void setLimit(String[] limit) {
		this.limit = limit;
	}

	/**
	 * 是否存在依赖包
	 * 
	 * @author gdpancheng@gmail.com 2014年12月7日 下午9:27:26
	 * @return boolean
	 */
	public boolean isDepend() {
		return isDepend;
	}

	/**
	 * 是否存在依赖包
	 * 
	 * @author gdpancheng@gmail.com 2014年12月7日 下午9:27:26
	 * @return boolean
	 */
	public void setDepend(boolean isDepend) {
		this.isDepend = isDepend;
	}
	
	@Override
    public String toString() {
	    return "LoonConfig [w=" + w + ", h=" + h + ", permit=" + Arrays.toString(permit) + ", limit=" + Arrays.toString(limit) + ", isDepend=" + isDepend + "]";
    }
}
