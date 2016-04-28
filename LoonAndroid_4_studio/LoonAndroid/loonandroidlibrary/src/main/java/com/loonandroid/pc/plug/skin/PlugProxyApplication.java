package com.loonandroid.pc.plug.skin;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.google.dexmaker.Constants;
import com.google.dexmaker.ProxyBuilder;
import com.loonandroid.pc.ioc.kernel.KernelObject;
import com.loonandroid.pc.ioc.kernel.KernelReflect;
import com.loonandroid.pc.util.Util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/*
 * Author: pancheng Email:gdpancheng@gmail.com
 * Created Date:2015年11月13日
 * Copyright @ 2015 BU
 * Description: 类描述
 *
 * History:
 */
public class PlugProxyApplication extends ProxyApplication {

	String className = "android.app.Application";
	String key = "my_application";

	@Override
	protected void initProxyApplication() {
		long time = System.currentTimeMillis();
		try {
			ApplicationInfo appInfo = getPackageManager().getApplicationInfo(super.getPackageName(), PackageManager.GET_META_DATA);
			Bundle bundle = appInfo.metaData;
			if (bundle != null && bundle.containsKey(key)) {
				className = bundle.getString(key);
				if (className.startsWith("."))
					className = Util.getPackageName() + className;
			}
			Class delegateClass = Class.forName(className, true, getClassLoader());
			Constants.method.add("onCreate");
			Constants.method.add("getResources");
			Constants.method.add("startService");
			Constants.method.add("startActivity");
			Constants.method.add("getTheme");
			
			Application delegate = (Application) ProxyBuilder.forClass(delegateClass).handler(new LoonApplicationHandler()).dexCache(getDir("dex", 0)).build(this);

			Field mOuterContext = KernelReflect.declaredField(getBaseContext().getClass(), "mOuterContext");
			KernelReflect.set(getBaseContext(), mOuterContext, delegate);

			Object mPackageInfo = KernelObject.declaredGet(getBaseContext(), "mPackageInfo");
			Field mApplication = KernelReflect.declaredField(mPackageInfo.getClass(), "mApplication");
			KernelReflect.set(mPackageInfo, mApplication, delegate);

			Object mActivityThread = KernelObject.declaredGet(mPackageInfo, "mActivityThread");
			Field mInitialApplication = KernelReflect.declaredField(mActivityThread.getClass(), "mInitialApplication");
			KernelReflect.set(mActivityThread, mInitialApplication, delegate);

			Field mAllApplications = KernelReflect.declaredField(mActivityThread.getClass(), "mAllApplications");
			mAllApplications.setAccessible(true);
			List<Application> allApplications = (List<Application>) mAllApplications.get(mActivityThread);
			allApplications.clear();
			allApplications.add(delegate);
			KernelReflect.set(mActivityThread, mAllApplications, allApplications);

			Method attach = Application.class.getDeclaredMethod("attach", Context.class);
			attach.setAccessible(true);
			attach.invoke(delegate, getBaseContext());
			delegate.onCreate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(System.currentTimeMillis() - time);
	}
	
	
	
//	String className = "android.app.Application";
//	String key = "my_application";
//
//	@Override
//	protected void initProxyApplication() {
//		long time = System.currentTimeMillis();
//		try {
//			ApplicationInfo appInfo = getPackageManager().getApplicationInfo("com.example.loonandroid2", PackageManager.GET_META_DATA);
//			Bundle bundle = appInfo.metaData;
//			if (bundle != null && bundle.containsKey(key)) {
//				className = bundle.getString(key);
//				if (className.startsWith("."))
//					className = super.getPackageName() + className;
//			}
//			Class delegateClass = Class.forName(className, true, getClassLoader());
//			Constants.method.add("onCreate");
//			Constants.method.add("getResources");
//			Constants.method.add("startService");
//			Constants.method.add("startActivity");
//
//			Application delegate = (Application) ProxyBuilder.forClass(delegateClass).handler(new LoonApplicationHandler()).dexCache(getDir("dex", 0)).build(this);
//
//			Field mOuterContext = KernelReflect.declaredField(getBaseContext().getClass(), "mOuterContext");
//			KernelReflect.set(getBaseContext(), mOuterContext, delegate);
//
//			Object mPackageInfo = KernelObject.declaredGet(getBaseContext(), "mPackageInfo");
//			Field mApplication = KernelReflect.declaredField(mPackageInfo.getClass(), "mApplication");
//			KernelReflect.set(mPackageInfo, mApplication, delegate);
//
//			Object mActivityThread = KernelObject.declaredGet(mPackageInfo, "mActivityThread");
//			Field mInitialApplication = KernelReflect.declaredField(mActivityThread.getClass(), "mInitialApplication");
//			KernelReflect.set(mActivityThread, mInitialApplication, delegate);
//
//			Object currentActivityThread = RefInvoke.invokeStaticMethod("android.app.ActivityThread", "currentActivityThread", new Class[] {}, new Object[] {});
//			Object mBoundApplication = RefInvoke.getFieldOjbect("android.app.ActivityThread", currentActivityThread, "mBoundApplication");
//			Object loadedApkInfo = RefInvoke.getFieldOjbect("android.app.ActivityThread$AppBindData", mBoundApplication, "info");
//			RefInvoke.setFieldOjbect("android.app.LoadedApk", "mApplication", loadedApkInfo, null);
//			Object oldApplication = RefInvoke.getFieldOjbect("android.app.ActivityThread", currentActivityThread, "mInitialApplication");
//			ArrayList<Application> mAllApplications = (ArrayList<Application>) RefInvoke.getFieldOjbect("android.app.ActivityThread", currentActivityThread, "mAllApplications");
//			mAllApplications.remove(oldApplication);
//			mAllApplications.add(delegate);
//			ApplicationInfo appinfo_In_LoadedApk = (ApplicationInfo) RefInvoke.getFieldOjbect("android.app.LoadedApk", loadedApkInfo, "mApplicationInfo");
//			ApplicationInfo appinfo_In_AppBindData = (ApplicationInfo) RefInvoke.getFieldOjbect("android.app.ActivityThread$AppBindData", mBoundApplication, "appInfo");
//			appinfo_In_LoadedApk.className = className;
//			appinfo_In_AppBindData.className = className;
//			Map mProviderMap = (Map) RefInvoke.getFieldOjbect("android.app.ActivityThread", currentActivityThread, "mProviderMap");
//			Iterator it = mProviderMap.values().iterator();
//			while (it.hasNext()) {
//				Object providerClientRecord = it.next();
//				Object localProvider = RefInvoke.getFieldOjbect("android.app.ActivityThread$ProviderClientRecord", providerClientRecord, "mLocalProvider");
//				RefInvoke.setFieldOjbect("android.content.ContentProvider", "mContext", localProvider, delegate);
//			}
//
//			// Field mAllApplications = KernelReflect.declaredField(mActivityThread.getClass(), "mAllApplications");
//			// mAllApplications.setAccessible(true);
//			// List<Application> allApplications = (List<Application>) mAllApplications.get(mActivityThread);
//			// int n = allApplications.size();
//			// allApplications.clear();
//			// for (int i = 0; i < n; i++) {
//			// allApplications.add(delegate);
//			// }
//			// KernelReflect.set(mActivityThread, mAllApplications, allApplications);
//
//			Method attach = Application.class.getDeclaredMethod("attach", Context.class);
//			attach.setAccessible(true);
//			attach.invoke(delegate, getBaseContext());
//			delegate.onCreate();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		// // 如果源应用配置有Appliction对象，则替换为源应用Applicaiton，以便不影响源程序逻辑。
//		// String appClassName = null;
//		// try {
//		// ApplicationInfo ai = this.getPackageManager().getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
//		// Bundle bundle = ai.metaData;
//		// if (bundle != null && bundle.containsKey("my_application")) {
//		// appClassName = bundle.getString("my_application");
//		// } else {
//		// return;
//		// }
//		// } catch (NameNotFoundException e) {
//		// // TODO Auto-generated catch block
//		// e.printStackTrace();
//		// }
//		//
//		// Object currentActivityThread = RefInvoke.invokeStaticMethod("android.app.ActivityThread", "currentActivityThread", new Class[] {}, new Object[] {});
//		// Object mBoundApplication = RefInvoke.getFieldOjbect("android.app.ActivityThread", currentActivityThread, "mBoundApplication");
//		// Object loadedApkInfo = RefInvoke.getFieldOjbect("android.app.ActivityThread$AppBindData", mBoundApplication, "info");
//		// RefInvoke.setFieldOjbect("android.app.LoadedApk", "mApplication", loadedApkInfo, null);
//		// Object oldApplication = RefInvoke.getFieldOjbect("android.app.ActivityThread", currentActivityThread, "mInitialApplication");
//		// ArrayList<Application> mAllApplications = (ArrayList<Application>) RefInvoke.getFieldOjbect("android.app.ActivityThread", currentActivityThread, "mAllApplications");
//		// mAllApplications.remove(oldApplication);
//		// ApplicationInfo appinfo_In_LoadedApk = (ApplicationInfo) RefInvoke.getFieldOjbect("android.app.LoadedApk", loadedApkInfo, "mApplicationInfo");
//		// ApplicationInfo appinfo_In_AppBindData = (ApplicationInfo) RefInvoke.getFieldOjbect("android.app.ActivityThread$AppBindData", mBoundApplication, "appInfo");
//		// appinfo_In_LoadedApk.className = appClassName;
//		// appinfo_In_AppBindData.className = appClassName;
//		// Application app = (Application) RefInvoke.invokeMethod("android.app.LoadedApk", "makeApplication", loadedApkInfo, new Class[] { boolean.class, Instrumentation.class }, new Object[] { false, null });
//		// RefInvoke.setFieldOjbect("android.app.ActivityThread", "mInitialApplication", currentActivityThread, app);
//		//
//		// Map mProviderMap = (Map) RefInvoke.getFieldOjbect("android.app.ActivityThread", currentActivityThread, "mProviderMap");
//		// Iterator it = mProviderMap.values().iterator();
//		// while (it.hasNext()) {
//		// Object providerClientRecord = it.next();
//		// Object localProvider = RefInvoke.getFieldOjbect("android.app.ActivityThread$ProviderClientRecord", providerClientRecord, "mLocalProvider");
//		// RefInvoke.setFieldOjbect("android.content.ContentProvider", "mContext", localProvider, app);
//		// }
//		// mAllApplications.add(app);
//		// app.onCreate();
//		System.out.println(System.currentTimeMillis() - time);
//	}
}
