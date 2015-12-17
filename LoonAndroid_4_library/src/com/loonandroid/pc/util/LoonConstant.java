package com.loonandroid.pc.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.StringTokenizer;

import android.graphics.drawable.Drawable;

import com.loonandroid.pc.animation.entity.AnimationEntity;
import com.loonandroid.pc.inject.InjcetActivity;
import com.loonandroid.pc.inject.InjectExcutor;
import com.loonandroid.pc.inject.InjectView;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-10
 * Copyright @ 2014 BU
 * Description: 常量
 * History:
 */
public class LoonConstant {

	public static class DB {
		/** 系统配置的数据库名 **/
		public static final String CONFIG_KEY = "config";
		public static final String HISTORY_KEY = "history";
	}

	/**
	 * 下拉刷新配置对象
	 */
	public static PullToRefresh pullToRefresh = new PullToRefresh();

	/**
	 * 下拉刷新 参数类
	 * 
	 * @author gdpancheng@gmail.com 2014-11-14 下午4:29:11
	 */
	public static class PullToRefresh {

		/** 字符串 **/
		public String mPullLabel = "下拉刷新......";
		public String mRefreshingLabel = "加载中.....";
		public String mReleaseLabel = "松开刷新......";

		/** 顶部背景 **/
		public Drawable ptrHeaderBackground = null;
		/** 顶部文字样式 **/
		public int ptrHeaderTextAppearance = android.R.attr.textAppearance;
		/** 描述基本文本颜色、字体、大小和风格加载页眉和页脚视图的子标题 **/
		public int ptrSubHeaderTextAppearance = android.R.attr.textAppearanceSmall;
		/** 描述顶部文字颜色 **/
		public int ptrHeaderTextColor = 0;
		/** 描述顶部基本文字颜色 **/
		public int ptrHeaderSubTextColor = 0;
		/** 下拉图片 **/
		public Drawable ptrDrawable = null;
		public Drawable ptrDrawableStart = null;
		public Drawable ptrDrawableTop = null;
		public Drawable ptrDrawableEnd = null;
		public Drawable ptrDrawableBottom = null;

		public boolean ptrShowIndicator = true;
		public boolean ptrOverScroll = true;
		public boolean ptrListViewExtrasEnabled = true;
		public boolean ptrRotateDrawableWhilePulling = true;
		public boolean ptrScrollingWhileRefreshingEnabled = false;
		public int ptrMode = 1;
		public int ptrAnimationStyle = 1;

		public Drawable ptrRefreshableViewBackground = null;
		public Drawable ptrAdapterViewBackground = null;

		public int gridview_id = 0;
		public int scrollview_id = 1;
		public int viewpager_id = 2;
		public int webview_id = 3;

		/****/
		public int indicator_internal_padding = 4;
		public int LoadingLayout_top = 12;
		public int LoadingLayout_left = 24;
		public int LoadingLayout_bottom = 12;
		public int LoadingLayout_right = 24;
		public int right_padding = 10;
	}

	public static class Number {
		/** 线程池中的线程数 **/
		public static final int ANALYSIS_POOL = Runtime.getRuntime().availableProcessors()+1;

		public static final int ID_NONE = -1;

		public static final int RESOURCE_DRAWABLE = 0;
		public static final int RESOURCE_STRING = 1;
		public static final int RESOURCE_STRINGS = 2;

		public static final int ACTIVITY_ONNEW = 1;
		public static final int ACTIVITY_PAUSE = 2;
		public static final int ACTIVITY_RESUME = 3;
		public static final int ACTIVITY_RESTART = 4;
		public static final int ACTIVITY_START = 5;
		public static final int ACTIVITY_STOP = 6;
		public static final int ACTIVITY_DESTROY = 7;
	}

	public static class Animations {
		public static AnimationEntity inFromBottom = new AnimationEntity() {
			public float getTy() {
				return 1.0f;
			};

			public float getFy() {
				return 0.0f;
			};

			public int getDuration() {
				return 500;
			};
		};
		public static AnimationEntity outToBottom = new AnimationEntity() {
			public float getTy() {
				return 0.0f;
			};

			public float getFy() {
				return 1.0f;
			};

			public int getDuration() {
				return 500;
			};
		};
		public static AnimationEntity inFromTop = new AnimationEntity() {
			public float getTy() {
				return -1.0f;
			};

			public float getFy() {
				return 0.0f;
			};

			public int getDuration() {
				return 500;
			};
		};
		public static AnimationEntity outToTop = new AnimationEntity() {
			public float getTy() {
				return 0.0f;
			};

			public float getFy() {
				return -1.0f;
			};

			public int getDuration() {
				return 500;
			};
		};
	}

	public static class Key {
		/** 系统配置的数据库名 **/
		public static final String ACTIVITY_KEY = "activity";
		public static final String VIEW_KEY = "view";

		public static final String SIGNATURE_KEY = "signature";
		
		public static final String NAMESPACE = "http://tempuri.org/";
	}

	public static class ClassName {
		public static final String ACTIVITY = "android.app.Activity";
		public static final String F_ACTIVITY = "android.support.v4.app.FragmentActivity";
		public static final String V4_FRAGMENT = "android.support.v4.app.Fragment";
		public static final String FRAGMENT = "android.app.Fragment";
		public static final String SERVICE = "android.app.Service";

	}

	/**
	 * 初始化布局findbyId解析器
	 */
	public static HashMap<String, InjectExcutor> INJECT = new HashMap<String, InjectExcutor>() {
		/**
		 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
		 */
		private static final long serialVersionUID = 1063247726078024161L;
		{
			put(Key.ACTIVITY_KEY, new InjcetActivity());
			put(Key.VIEW_KEY, new InjectView());
		}
	};

	public static String getCurrentMethodName() {
		return getMethodName(0);
	}

	public static String getCallingMethodName() {
		return getMethodName(1);
	}

	private static String getMethodName(int stackLevel) {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		PrintWriter printWriter = new PrintWriter(byteArrayOutputStream);
		(new Throwable()).printStackTrace(printWriter);
		printWriter.flush();
		String stackTrace = byteArrayOutputStream.toString();
		printWriter.close();

		StringTokenizer stringTokenizer = new StringTokenizer(stackTrace, "\n");
		String methodName = "";
		for (int i = 0; i < stackLevel + 4; i++) {
			methodName = stringTokenizer.nextToken();
		}

		stringTokenizer = new StringTokenizer(methodName.trim(), " (");
		stringTokenizer.nextToken();
		methodName = stringTokenizer.nextToken();
		return methodName;
	}
}
