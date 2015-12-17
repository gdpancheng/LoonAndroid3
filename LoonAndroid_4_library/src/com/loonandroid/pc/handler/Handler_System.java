package com.loonandroid.pc.handler;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Vibrator;
import android.provider.Settings.Secure;
import android.telephony.NeighboringCellInfo;
import android.telephony.TelephonyManager;
import android.view.Display;
import android.view.WindowManager;

import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.ioc.config.LoonConfig;

/**
 * 获取相关系统信息
 * 
 * @author gdpancheng@gmail.com 2013-10-12 下午2:26:34
 */
public class Handler_System {

	public static String UA = Build.MODEL;
	private static String mIMEI;
	private static String mSIM;
	private static String mMobileVersion;
	private static String mNetwrokIso;
	private static String mNetType;
	private static String mDeviceID;
	private static List<NeighboringCellInfo> mCellinfos;

	public static final String systemWidth = "width";
	public static final String systemHeight = "height";
	private static HashMap<String, Integer> map;

	static {
		init();
	}

	/**
	 * 获取应用程序名称
	 * 
	 * @author gdpancheng@gmail.com 2013-10-12 下午2:30:56
	 * @return
	 * @return String
	 */
	public static String getAppName() {
		return getAppName(null);
	}

	/**
	 * 获取应用程序名称
	 * 
	 * @author gdpancheng@gmail.com 2013-10-12 下午2:30:43
	 * @param packageName
	 * @return
	 * @return String
	 */
	public static String getAppName(String packageName) {
		String applicationName;

		if (packageName == null) {
			packageName = Ioc.getIoc().getApplication().getPackageName();
		}

		try {
			PackageManager packageManager = Ioc.getIoc().getApplication().getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
			applicationName = Ioc.getIoc().getApplication().getString(packageInfo.applicationInfo.labelRes);
		} catch (Exception e) {
			Ioc.getIoc().getLogger().e(e);
			applicationName = "";
		}

		return applicationName;
	}

	/**
	 * 获取版本名称
	 * 
	 * @author gdpancheng@gmail.com 2013-10-12 下午2:30:37
	 * @return
	 * @return String
	 */
	public static String getAppVersionNumber() {
		return getAppVersionNumber(null);
	}

	/**
	 * 获取版本名称
	 * 
	 * @author gdpancheng@gmail.com 2013-10-12 下午2:30:27
	 * @param packageName
	 * @return
	 * @return String
	 */
	public static String getAppVersionNumber(String packageName) {
		String versionName;

		if (packageName == null) {
			packageName = Ioc.getIoc().getApplication().getPackageName();
		}

		try {
			PackageManager packageManager = Ioc.getIoc().getApplication().getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
			versionName = packageInfo.versionName;
		} catch (Exception e) {
			Ioc.getIoc().getLogger().e(e);
			versionName = "";
		}

		return versionName;
	}

	/**
	 * 获取应用程序的版本号
	 * 
	 * @author gdpancheng@gmail.com 2013-10-12 下午2:30:12
	 * @return
	 * @return String
	 */
	public static String getAppVersionCode() {
		return getAppVersionCode(null);
	}

	/**
	 * 获取指定应用程序的版本号
	 * 
	 * @author gdpancheng@gmail.com 2013-10-12 下午2:29:51
	 * @param packageName
	 * @return
	 * @return String
	 */
	public static String getAppVersionCode(String packageName) {
		String versionCode;

		if (packageName == null) {
			packageName = Ioc.getIoc().getApplication().getPackageName();
		}

		try {
			PackageManager packageManager = Ioc.getIoc().getApplication().getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 0);
			versionCode = Integer.toString(packageInfo.versionCode);
		} catch (Exception e) {
			Ioc.getIoc().getLogger().e(e);
			versionCode = "";
		}

		return versionCode;
	}

	/**
	 * 获取SDK版本
	 * 
	 * @author gdpancheng@gmail.com 2013-10-12 下午2:29:13
	 * @return
	 * @return int
	 */
	public static int getSdkVersion() {
		try {
			return Build.VERSION.class.getField("SDK_INT").getInt(null);
		} catch (Exception e) {
			Ioc.getIoc().getLogger().e(e);
			return 3;
		}
	}

	/*
	 * 判断是否是该签名打包
	 */
	public static boolean isRelease(String signatureString) {
		final String releaseSignatureString = signatureString;
		if (releaseSignatureString == null || releaseSignatureString.length() == 0) {
			throw new RuntimeException("Release signature string is null or missing.");
		}

		final Signature releaseSignature = new Signature(releaseSignatureString);
		try {
			PackageManager pm = Ioc.getIoc().getApplication().getPackageManager();
			PackageInfo pi = pm.getPackageInfo(Ioc.getIoc().getApplication().getPackageName(), PackageManager.GET_SIGNATURES);
			for (Signature sig : pi.signatures) {
				if (sig.equals(releaseSignature)) {
					return true;
				}
			}
		} catch (Exception e) {
			Ioc.getIoc().getLogger().e(e);
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是模拟器
	 * 
	 * @author gdpancheng@gmail.com 2013-10-12 下午2:28:40
	 * @return
	 * @return boolean
	 */
	public static boolean isEmulator() {
		return Build.MODEL.equals("sdk") || Build.MODEL.equals("google_sdk");
	}

	/**
	 * @author 潘城 gdpancheng@gmail.com 2012-5-7 下午1:21:48
	 * @Title: getMobileInfo
	 * @Description: 获取手机的硬件信息
	 * @param @return 设定文件
	 * @return String 返回类型
	 */
	public static String getMobileInfo() {
		StringBuffer sb = new StringBuffer();
		/**
		 * 通过反射获取系统的硬件信息 获取私有的信息
		 */
		try {
			Field[] fields = Build.class.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				String name = field.getName();
				String value = field.get(null).toString();
				sb.append(name + "=" + value);
				sb.append("\n");
			}
		} catch (Exception e) {
			Ioc.getIoc().getLogger().e(e);
		}
		return sb.toString();
	}

	/**
	 * @author 潘城 gdpancheng@gmail.com 2012-5-7 下午5:18:34
	 * @Title: getDisplayMetrics
	 * @Description: 获取屏幕的分辨率
	 * @param @param cx
	 * @param @return 设定文件
	 * @return HashMap<String,Integer> 返回类型
	 */
	public static HashMap<String, Integer> getDisplayMetrics() {
		if (map == null) {
			map = new HashMap<String, Integer>();
			Display display = ((WindowManager) Ioc.getIoc().getApplication().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			int screenWidth = display.getWidth();
			int screenHeight = display.getHeight();
			map.put(systemWidth, screenWidth);
			map.put(systemHeight, screenHeight);
		}
		return map;
	}

	/**
	 * 获取屏幕宽度缩放率
	 * 
	 * @author gdpancheng@gmail.com 2013-10-22 下午1:10:47
	 * @param width
	 * @return float
	 */
	public static float getWidthRoate() {
		if (map == null) {
			map = new HashMap<String, Integer>();
			Display display = ((WindowManager) Ioc.getIoc().getApplication().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			int screenWidth = display.getWidth();
			int screenHeight = display.getHeight();
			map.put(systemWidth, screenWidth);
			map.put(systemHeight, screenHeight);
		}
		return (map.get(systemWidth) * 1.00f) / LoonConfig.instance().getW();
	}

	public static float getRoate() {
		if (map == null) {
			map = new HashMap<String, Integer>();
			Display display = ((WindowManager) Ioc.getIoc().getApplication().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			int screenWidth = display.getWidth();
			int screenHeight = display.getHeight();
			map.put(systemWidth, screenWidth);
			map.put(systemHeight, screenHeight);
		}
		float w = (map.get(systemWidth) * 1.00f) / LoonConfig.instance().getW();
		float h = (map.get(systemHeight) * 1.00f) / LoonConfig.instance().getH();
		return w > h ? w : h;
	}

	public static float getPadRoate() {
		if (map == null) {
			map = new HashMap<String, Integer>();
			Display display = ((WindowManager) Ioc.getIoc().getApplication().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			int screenWidth = display.getWidth();
			int screenHeight = display.getHeight();
			map.put(systemWidth, screenWidth);
			map.put(systemHeight, screenHeight);
		}
		float w = (map.get(systemWidth) * 1.00f) / LoonConfig.instance().getW();
		float h = (map.get(systemHeight) * 1.00f) / LoonConfig.instance().getH();
		return w < h ? w : h;
	}

	/**
	 * 获取屏幕高度缩放率
	 * 
	 * @author gdpancheng@gmail.com 2013-10-22 下午1:11:08
	 * @param height
	 * @return float
	 */
	public static float getHeightRoate() {
		if (map == null) {
			map = new HashMap<String, Integer>();
			Display display = ((WindowManager) Ioc.getIoc().getApplication().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
			int screenWidth = display.getWidth();
			int screenHeight = display.getHeight();
			map.put(systemWidth, screenWidth);
			map.put(systemHeight, screenHeight);
		}
		return (map.get(systemHeight) * 1.00f) / LoonConfig.instance().getH();
	}

	/**
	 * dp转px
	 * 
	 * @author gdpancheng@gmail.com 2013-10-22 下午1:11:48
	 * @param dipValue
	 * @return int
	 */
	public static int dip2px(float dipValue) {
		final float scale = Ioc.getIoc().getApplication().getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * px转dip TODO(这里用一句话描述这个方法的作用)
	 * 
	 * @author gdpancheng@gmail.com 2013-10-22 下午1:12:09
	 * @param pxValue
	 * @return
	 * @return int
	 */
	public static int px2dip(float pxValue) {
		final float scale = Ioc.getIoc().getApplication().getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 获取通知栏高度
	 * 
	 * @author gdpancheng@gmail.com 2012-2-12 下午07:37:13
	 * @Title: getBarHeight
	 * @param @param context
	 * @param @return 设定文件
	 * @return int 返回类型
	 */
	public static int getBarHeight() {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = Ioc.getIoc().getApplication().getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			Ioc.getIoc().getLogger().e(e1);
		}
		return sbar;
	}

	public static boolean hasFroyo() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	public static boolean hasGingerbread() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
	}

	public static boolean hasGingerbreadMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD_MR1;
	}

	public static boolean hasHoneycomb() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	}

	public static boolean hasHoneycombMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
	}

	public static boolean hasICS() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	}

	public static boolean hasJellyBean() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
	}

	public static boolean hasJellyBeanMR1() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;
	}

	public static boolean isTablet(Context context) {
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
	}

	public static boolean isHoneycombTablet(Context context) {
		return hasHoneycomb() && isTablet(context);
	}

	public static boolean isGingerbread() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD && Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1;
	}

	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (activeNetwork == null || !activeNetwork.isConnected()) {
			return false;
		}
		return true;
	}

	public static final int DEFAULT_THREAD_POOL_SIZE = getDefaultThreadPoolSize();

	/**
	 * get recommend default thread pool size
	 * 
	 * @return if 2 * availableProcessors + 1 less than 8, return it, else return 8;
	 * @see {@link #getDefaultThreadPoolSize(int)} max is 8
	 */
	public static int getDefaultThreadPoolSize() {
		return getDefaultThreadPoolSize(8);
	}

	/**
	 * get recommend default thread pool size
	 * 
	 * @param max
	 * @return if 2 * availableProcessors + 1 less than max, return it, else return max;
	 */
	public static int getDefaultThreadPoolSize(int max) {
		int availableProcessors = 2 * Runtime.getRuntime().availableProcessors() + 1;
		return availableProcessors > max ? max : availableProcessors;
	}

	/**
	 * 
	 * 设置手机立刻震动
	 * */
	public static void Vibrate(long milliseconds) {
		Vibrator vib = (Vibrator) Ioc.getIoc().getApplication().getSystemService(Service.VIBRATOR_SERVICE);
		vib.vibrate(milliseconds);
	}

	static TelephonyManager mTm = null;

	/**
	 * 在获取系统信息前初始化
	 * 
	 * @author gdpancheng@gmail.com 2013-10-22 下午1:14:12
	 * @return void
	 */
	public static void init() {
		mTm = (TelephonyManager) Ioc.getIoc().getApplication().getSystemService(Context.TELEPHONY_SERVICE);
		mIMEI = mTm.getDeviceId();
		mMobileVersion = mTm.getDeviceSoftwareVersion();
		mCellinfos = mTm.getNeighboringCellInfo();
		mNetwrokIso = mTm.getNetworkCountryIso();
		mSIM = mTm.getSimSerialNumber();
		mDeviceID = getDeviceId();
		try {
			ConnectivityManager cm = (ConnectivityManager) Ioc.getIoc().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo info = cm.getActiveNetworkInfo();
			if (info != null) {
				mNetType = info.getTypeName();
			}
		} catch (Exception ex) {
		}
	}

	/**
	 * 获得android设备-唯一标识，android2.2 之前无法稳定运行.
	 * */
	public static String getDeviceId(Context mCm) {
		return Secure.getString(mCm.getContentResolver(), Secure.ANDROID_ID);
	}

	/**
	 * 获取设备号 TODO(这里用一句话描述这个方法的作用)
	 * 
	 * @author gdpancheng@gmail.com 2013-10-15 下午10:31:48
	 * @return
	 * @return String
	 */
	private static String getDeviceId() {
		return Secure.getString(Ioc.getIoc().getApplication().getContentResolver(), Secure.ANDROID_ID);
	}

	public static String getImei() {
		return mIMEI;
	}

	public static String getSIM() {
		return mSIM;
	}

	public static String getUA() {
		return UA;
	}

	/**
	 * 获取设备信息 以字符串的形式
	 * 
	 * @author gdpancheng@gmail.com 2013-10-22 下午1:14:30
	 * @return String
	 */
	public static String getDeviceInfo() {
		StringBuffer info = new StringBuffer();
		info.append("IMEI:").append(getImei());
		info.append("\n");
		info.append("SIM:").append(getSIM());
		info.append("\n");
		info.append("UA:").append(getUA());
		info.append("\n");
		info.append("MobileVersion:").append(mMobileVersion);

		info.append("\n");
		info.append("SDK: ").append(android.os.Build.VERSION.SDK);
		info.append("\n");
		info.append(getCallState());
		info.append("\n");
		info.append("SIM_STATE: ").append(getSimState());
		info.append("\n");
		info.append("SIM: ").append(getSIM());
		info.append("\n");
		info.append(getSimOpertorName());
		info.append("\n");
		info.append(getPhoneType());
		info.append("\n");
		info.append(getPhoneSettings());
		info.append("\n");
		return info.toString();
	}

	/**
	 * 检查sim的状态
	 * 
	 * @author gdpancheng@gmail.com 2013-10-22 下午1:15:25
	 * @return String
	 */
	public static String getSimState() {
		switch (mTm.getSimState()) {
		case android.telephony.TelephonyManager.SIM_STATE_UNKNOWN:
			return "未知SIM状态_" + android.telephony.TelephonyManager.SIM_STATE_UNKNOWN;
		case android.telephony.TelephonyManager.SIM_STATE_ABSENT:
			return "没插SIM卡_" + android.telephony.TelephonyManager.SIM_STATE_ABSENT;
		case android.telephony.TelephonyManager.SIM_STATE_PIN_REQUIRED:
			return "锁定SIM状态_需要用户的PIN码解锁_" + android.telephony.TelephonyManager.SIM_STATE_PIN_REQUIRED;
		case android.telephony.TelephonyManager.SIM_STATE_PUK_REQUIRED:
			return "锁定SIM状态_需要用户的PUK码解锁_" + android.telephony.TelephonyManager.SIM_STATE_PUK_REQUIRED;
		case android.telephony.TelephonyManager.SIM_STATE_NETWORK_LOCKED:
			return "锁定SIM状态_需要网络的PIN码解锁_" + android.telephony.TelephonyManager.SIM_STATE_NETWORK_LOCKED;
		case android.telephony.TelephonyManager.SIM_STATE_READY:
			return "就绪SIM状态_" + android.telephony.TelephonyManager.SIM_STATE_READY;
		default:
			return "未知SIM状态_" + android.telephony.TelephonyManager.SIM_STATE_UNKNOWN;

		}
	}

	/**
	 * 获取手机信号状态
	 * 
	 * @author gdpancheng@gmail.com 2013-10-22 下午1:15:37
	 * @return String
	 */
	public static String getPhoneType() {
		switch (mTm.getPhoneType()) {
		case android.telephony.TelephonyManager.PHONE_TYPE_NONE:
			return "PhoneType: 无信号_" + android.telephony.TelephonyManager.PHONE_TYPE_NONE;
		case android.telephony.TelephonyManager.PHONE_TYPE_GSM:
			return "PhoneType: GSM信号_" + android.telephony.TelephonyManager.PHONE_TYPE_GSM;
		case android.telephony.TelephonyManager.PHONE_TYPE_CDMA:
			return "PhoneType: CDMA信号_" + android.telephony.TelephonyManager.PHONE_TYPE_CDMA;
		default:
			return "PhoneType: 无信号_" + android.telephony.TelephonyManager.PHONE_TYPE_NONE;
		}
	}

	/**
	 * 服务商名称：例如：中国移动、联通 　　 SIM卡的状态必须是 SIM_STATE_READY(使用getSimState()判断). 　　
	 */
	public static String getSimOpertorName() {
		if (mTm.getSimState() == android.telephony.TelephonyManager.SIM_STATE_READY) {
			StringBuffer sb = new StringBuffer();
			sb.append("SimOperatorName: ").append(mTm.getSimOperatorName());
			sb.append("\n");
			sb.append("SimOperator: ").append(mTm.getSimOperator());
			sb.append("\n");
			sb.append("Phone:").append(mTm.getLine1Number());
			return sb.toString();
		} else {
			StringBuffer sb = new StringBuffer();
			sb.append("SimOperatorName: ").append("未知");
			sb.append("\n");
			sb.append("SimOperator: ").append("未知");
			return sb.toString();
		}
	}

	/**
	 * 获取手机设置状态 比如蓝牙开启与否
	 * 
	 * @author gdpancheng@gmail.com 2013-10-22 下午1:16:02
	 * @return String
	 */
	public static String getPhoneSettings() {
		StringBuffer buf = new StringBuffer();
		String str = Secure.getString(Ioc.getIoc().getApplication().getContentResolver(), Secure.BLUETOOTH_ON);
		buf.append("蓝牙:");
		if (str.equals("0")) {
			buf.append("禁用");
		} else {
			buf.append("开启");
		}
		//
		str = Secure.getString(Ioc.getIoc().getApplication().getContentResolver(), Secure.BLUETOOTH_ON);
		buf.append("WIFI:");
		buf.append(str);

		str = Secure.getString(Ioc.getIoc().getApplication().getContentResolver(), Secure.INSTALL_NON_MARKET_APPS);
		buf.append("APP位置来源:");
		buf.append(str);

		return buf.toString();
	}

	/**
	 * 获取电话状态
	 * 
	 * @author gdpancheng@gmail.com 2013-10-22 下午1:16:37
	 * @return String
	 */
	public static String getCallState() {
		switch (mTm.getCallState()) {
		case android.telephony.TelephonyManager.CALL_STATE_IDLE:
			return "电话状态[CallState]: 无活动";
		case android.telephony.TelephonyManager.CALL_STATE_OFFHOOK:
			return "电话状态[CallState]: 无活动";
		case android.telephony.TelephonyManager.CALL_STATE_RINGING:
			return "电话状态[CallState]: 无活动";
		default:
			return "电话状态[CallState]: 未知";
		}
	}

	public static String getNetwrokIso() {
		return mNetwrokIso;
	}

	/**
	 * @return the mDeviceID
	 */
	public String getmDeviceID() {
		return mDeviceID;
	}

	/**
	 * @return the mNetType
	 */
	public String getNetType() {
		return mNetType;
	}

	public static String getMainActivity(Context context, String packageName) {
		try {
			PackageInfo pi = context.getPackageManager().getPackageInfo(packageName, 0);
			Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
			resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER);
			resolveIntent.setPackage(pi.packageName);
			List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(resolveIntent, 0);
			ResolveInfo ri = apps.iterator().next();
			if (ri != null) {
				return ri.activityInfo.name;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void clearUserData(final Context context) {
		try {
			Class<?> iPackageDataObserverClass = Class.forName("android.content.pm.IPackageDataObserver");
			ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			Class[] classes = { String.class, iPackageDataObserverClass };
			Method method = ActivityManager.class.getDeclaredMethod("clearApplicationUserData", classes);
			Object iPackageDataObserverObject = Proxy.newProxyInstance(Ioc.class.getClassLoader(), new Class[] { iPackageDataObserverClass }, new InvocationHandler() {
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					return null;
				}
			});
			method.invoke(activityManager, context.getPackageName(), iPackageDataObserverObject);
			// 重启
			// Intent i = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
			// i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// context.startActivity(i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

//	public static void clearUserCache(final Context context) {
//		PackageManager pm = context.getPackageManager();
//		// 反射
//		try {
//			Method method = PackageManager.class.getMethod("getPackageSizeInfo", new Class[] { String.class, IPackageStatsObserver.class });
//			method.invoke(pm, new Object[] { context.getPackageName(), new IPackageStatsObserver.Stub() {
//				@Override
//				public void onGetStatsCompleted(PackageStats pStats, boolean succeeded) throws RemoteException {
//					long cachesize = pStats.cacheSize;
//					long codesize = pStats.codeSize;
//					long datasize = pStats.dataSize;
//					Long freeStorageSize = Long.valueOf(datasize);
//					PackageManager packageManager = context.getPackageManager();
//					try {
//						Method localMethod = packageManager.getClass().getMethod("freeStorageAndNotify", Long.TYPE, IPackageDataObserver.class);
//						localMethod.invoke(packageManager, freeStorageSize, new IPackageDataObserver.Stub() {
//							@Override
//							public void onRemoveCompleted(String packageName, boolean succeeded) throws RemoteException {
//								System.out.println(packageName + " 清除成功:" + succeeded);
//							}
//						});
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				}
//			} });
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

	/** * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache) * * @param context */
	public static void cleanInternalCache(Context context) {
		deleteFilesByDirectory(context.getCacheDir());
	}

	/** * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases) * * @param context */
	public static void cleanDatabases(Context context) {
		deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
	}

	/**
	 * * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs) * * @param context
	 */
	public static void cleanSharedPreference(Context context) {
		deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
	}

	/** * 按名字清除本应用数据库 * * @param context * @param dbName */
	public static void cleanDatabaseByName(Context context, String dbName) {
		context.deleteDatabase(dbName);
	}

	/** * 清除/data/data/com.xxx.xxx/files下的内容 * * @param context */
	public static void cleanFiles(Context context) {
		deleteFilesByDirectory(context.getFilesDir());
	}

	/**
	 * * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache) * * @param context
	 */
	public static void cleanExternalCache(Context context) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			deleteFilesByDirectory(context.getExternalCacheDir());
		}
	}

	/** * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除 * * @param filePath */
	public static void cleanCustomCache(String filePath) {
		deleteFilesByDirectory(new File(filePath));
	}

	/** * 清除本应用所有的数据 * * @param context * @param filepath */
	public static void cleanApplicationData(Context context, String... filepath) {
		Ioc.getIoc().getLogger().d("**************清除缓存 和数据库**************");
		cleanInternalCache(context);
		cleanExternalCache(context);
		cleanDatabases(context);
		cleanSharedPreference(context);
		cleanFiles(context);
		for (String filePath : filepath) {
			cleanCustomCache(filePath);
		}
		Ioc.getIoc().getLogger().d("**************清除缓存 执行完毕**************");
	}

	/** * 清除本应用所有的数据 * * @param context * @param filepath */
	public static void cleanApplicationFiles(Context context) {
		Ioc.getIoc().getLogger().d("**************清除缓存  清除/data/data/com.xxx.xxx/files下的内容**************");
		cleanFiles(context);
	}

	/**
	 * 删除文件
	 * 
	 * @author gdpancheng@gmail.com 2014年12月30日 下午3:33:51
	 * @param directory
	 * @return void
	 */
	public static void deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				item.delete();
			}
		}
//		String command = "rm -r " + directory.getAbsolutePath(); // the command that will be execute
//		Runtime runtime = Runtime.getRuntime();
//		Process proc = null;
//		try {
//			proc = runtime.exec(command);
//			if (proc == null) {
//				return;
//			}
//			if (proc.waitFor() != 0) {
//				Ioc.getIoc().getLogger().d(" 命令行清除" + directory.getAbsolutePath() + "缓存失败，重新遍历删除");
//				if (directory != null && directory.exists() && directory.isDirectory()) {
//					for (File item : directory.listFiles()) {
//						item.delete();
//					}
//				}
//			}
//		} catch (Exception e) {
//			Ioc.getIoc().getLogger().d(" 命令行清除" + directory.getAbsolutePath() + "缓存失败，重新遍历删除");
//			if (directory != null && directory.exists() && directory.isDirectory()) {
//				for (File item : directory.listFiles()) {
//					item.delete();
//				}
//			}
//		}
	}

	/**
	 * 检查sd卡是否存在 TODO(这里用一句话描述这个方法的作用)
	 * 
	 * @author gdpancheng@gmail.com 2015年1月2日 下午9:20:49
	 * @return boolean
	 */
	public static boolean checkStorageStatus() {
		String sdStatus = Environment.getExternalStorageState();
		// 检测sd卡是否可用
		return sdStatus.equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取文件的真实路径
	 * @author gdpancheng@gmail.com 2015年1月2日 下午11:15:03
	 * @param context
	 * @param mUri
	 * @return String
	 */
	public static String getFilePath(Context context ,Uri mUri) {
		ContentResolver mContentResolver = context.getContentResolver();
		try {
			if (mUri.getScheme().equals("file")) {
				return mUri.getPath();
			} else {
				return getFilePathByUri(mContentResolver,mUri);
			}
		} catch (FileNotFoundException ex) {
			return null;
		}
	}

	public static String getFilePathByUri(ContentResolver mContentResolver,Uri mUri) throws FileNotFoundException {
		String imgPath;
		Cursor cursor = mContentResolver.query(mUri, null, null, null, null);
		cursor.moveToFirst();
		imgPath = cursor.getString(1); // 图片文件路径
		return imgPath;
	}
	
	/**
	 * 推出应用
	 * TODO(这里用一句话描述这个方法的作用)
	 * @author gdpancheng@gmail.com 2015年10月29日 下午5:55:33
	 * @param context
	 * @return void
	 */
	public static void exit(Activity context) {
		int currentVersion = android.os.Build.VERSION.SDK_INT;
		if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
			Intent startMain = new Intent(Intent.ACTION_MAIN);
			startMain.addCategory(Intent.CATEGORY_HOME);
			startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(startMain);
			context.finish();
			android.os.Process.killProcess(android.os.Process.myPid());
			System.exit(0);
		} else {// android2.1
			ActivityManager am = (ActivityManager) Ioc.getIoc().getApplication().getSystemService(Activity.ACTIVITY_SERVICE);
			am.restartPackage(Ioc.getIoc().getApplication().getPackageName());
		}
	    
    }
}
