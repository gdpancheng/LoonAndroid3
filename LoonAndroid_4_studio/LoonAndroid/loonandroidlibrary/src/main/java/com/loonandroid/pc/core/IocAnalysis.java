package com.loonandroid.pc.core;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.ioc.config.LoonConfig;
import com.loonandroid.pc.ioc.kernel.KernelLang.BreakException;
import com.loonandroid.pc.ioc.kernel.KernelLang.CallbackBreak;
import com.loonandroid.pc.ioc.kernel.KernelLang.FilterTemplate;
import com.loonandroid.pc.util.DalvikHelper;
import com.loonandroid.pc.util.LoonConstant;

/**
 * 整个框架的入口类<br>
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
 * @author gdpancheng@gmail.com 2014年12月20日 下午11:54:53
 */
public class IocAnalysis {

	/**
	 * 解析线程池
	 */
	public static final ExecutorService mAnalysisActivityPool = Executors.newFixedThreadPool(LoonConstant.Number.ANALYSIS_POOL); // 固定线程;
	/** 判断当前类是否已经被解析 避免重复解析 **/
	public static final HashMap<String,AnalysisCore> dealed = new HashMap<String,AnalysisCore>();

	/**
	 * activity解析
	 * 
	 * @author gdpancheng@gmail.com 2014-11-10 下午1:25:28
	 * @return void
	 */
	public static void asyncAnalysis() {
		// ----------------------------------------------------------------------------------
		// 先判断是否存在解析的数据 如果有就不需要解析
		String path = Ioc.getIoc().getApplication().getPackageResourcePath();
		if (Ioc.getIoc().getSignature() == (new File(path).lastModified())) {
			Ioc.getIoc().getLogger().i(" 读取上次解析记录 无需再次解析 ");
			LoonConfig.instance().initBean();
			return;
		}
		// -------------------------------------------------------------------------------------------------
		// 读取配置
		LoonConfig.instance().init();
		// ----------------------------------------------------------------------------------
		Ioc.getIoc().getLogger().i(" 无本地记录，开启异步解析 ");
		// ----------------------------------------------------------------------------------
		// 解析activity和其他类
		analysisClass();
	}

	/**
	 * 开启异步线程去扫描所有需要解析的类
	 * 
	 * @author gdpancheng@gmail.com 2014年12月30日 上午10:09:30
	 * @return void
	 */
	private static void analysisClass() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// ----------------------------------------------------------------------------------
					// 防止项目没有添加v4的依赖包，导致里面fragment.class报错，特在此进行验证
					staticClass();
					// ----------------------------------------------------------------------------------
					// 开始解析第一个activity 因为开启异步解析 为了避免在第一个activity启动的时候
					// 该activity还需要同步解析，特放在第一个解析。
					AnalysisCore runnable = AnalysisManager.distribution(Ioc.getIoc().getMain());
					if (null != runnable) {
						mAnalysisActivityPool.execute(runnable);
						dealed.put(Ioc.getIoc().getMain(),runnable);
					} else {
						Ioc.getIoc().getLogger().e("无法获得启动activity" + Ioc.getIoc().getMain() + "不存在");
					}
					// ----------------------------------------------------------------------------------
					// 开始解析activity 从activities 获取xml中配置的所有的activity
					PackageInfo packageInfo = Ioc.getIoc().getApplication().getPackageManager().getPackageInfo(Ioc.getIoc().getApplication().getPackageName(), PackageManager.GET_ACTIVITIES);
					ActivityInfo[] activityInfos = packageInfo.activities;
					for (int i = 0; i < activityInfos.length; i++) {
						if (dealed.containsKey(activityInfos[i].name)) {
							continue;
						}
						runnable = AnalysisManager.distribution(activityInfos[i].name);
						if (null == runnable) {
							continue;
						}
						mAnalysisActivityPool.execute(runnable);
						dealed.put(activityInfos[i].name,runnable);
					}
				} catch (Exception e) {
				}
				// ----------------------------------------------------------------------------------
				// 开始解析其他类
				DalvikHelper.doScannerFilter(Ioc.getIoc().getApplication(), new CallbackBreak<String>() {
					@Override
					public void doWith(String template) throws BreakException {
						AnalysisCore runnable = AnalysisManager.distribution(template);
						dealed.put(template,runnable);
						if (null == runnable) {
							return;
						}
						mAnalysisActivityPool.execute(runnable);
					}
				}, new FilterTemplate<String>() {
					@Override
					public boolean doWith(String template) throws BreakException {
						if (dealed.containsKey(template)) {
							return false;
						}
						for (String name : LoonConfig.instance().getLimit()) {
							if (template.indexOf(name) > -1) {
								return false;
							}
						}
						for (String name : LoonConfig.instance().getPermit()) {
							if (template.indexOf(name) > -1) {
								return true;
							}
						}
						return false;
					}
				});
			}
		}).start();
	}

	/**
	 * 用来解析所有的class
	 * 
	 * @author gdpancheng@gmail.com 2014-11-10 下午2:57:55
	 */
	public static void staticClass() {
		try {
			AnalysisManager.CLAZZS.put(LoonConstant.ClassName.F_ACTIVITY, Class.forName(LoonConstant.ClassName.F_ACTIVITY));
		} catch (Exception e) {
			AnalysisManager.CLAZZS.put(LoonConstant.ClassName.F_ACTIVITY, null);
		}
		try {
			AnalysisManager.CLAZZS.put(LoonConstant.ClassName.V4_FRAGMENT, Class.forName(LoonConstant.ClassName.V4_FRAGMENT));
		} catch (Exception e) {
			e.printStackTrace();
			AnalysisManager.CLAZZS.put(LoonConstant.ClassName.V4_FRAGMENT, null);
		}
		try {
			AnalysisManager.CLAZZS.put(LoonConstant.ClassName.FRAGMENT, Class.forName(LoonConstant.ClassName.FRAGMENT));
		} catch (Exception e) {
			AnalysisManager.CLAZZS.put(LoonConstant.ClassName.FRAGMENT, null);
		}
	}
}
