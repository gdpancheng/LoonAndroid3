package com.loonandroid.pc.util;

/*
 * Author: pancheng Email:gdpancheng@gmail.com
 * Created Date:2015年11月13日
 * Copyright @ 2015 BU
 * Description: 类描述
 *
 * History:
 */
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import java.util.Locale;

public class LanguageSettingUtil {
	// 单例模式-
	private static LanguageSettingUtil instance;
	private Context context;
	// 存储当前系统的language设置-
	private String defaultLanguage;
	// 存储当前系统Locale-
	private Locale defaultLocale;
	public final static String ENGLISH = "en";
	public final static String CHINESE = "zh";

	private LanguageSettingUtil(Context paramContext) {
		// 得到系统语言-
		Locale localLocale = Locale.getDefault();
		this.defaultLocale = localLocale;

		// 保存系统语言到defaultLanguage
		String str = this.defaultLocale.getLanguage();
		this.defaultLanguage = str;

		this.context = paramContext;
	}

	// 检验自身是否被创建-
	public static LanguageSettingUtil get() {
		if (instance == null)
			throw new IllegalStateException("language setting not initialized yet");
		return instance;
	}

	// 初始化-
	public static void init(Context paramContext) {
		if (instance == null) {
			instance = new LanguageSettingUtil(paramContext);
		}
	}

	// 创建Configuration-
	private Configuration getUpdatedLocaleConfig(String paramString) {

		Configuration localConfiguration = context.getResources().getConfiguration();
		Locale localLocale = getLocale(paramString);
		localConfiguration.locale = localLocale;
		return localConfiguration;
	}

	// 得到APP配置文件目前的语言设置-
	public String getLanguage() {
		SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.context);
		// 如果当前程序没有设置language属性就返回系统语言，如果有，就返回以前的-
		return localSharedPreferences.getString("language", this.defaultLanguage);
	}

	// 如果配置文件中没有语言设置-
	public Locale getLocale() {
		String str = getLanguage();
		return getLocale(str);
	}

	// 创建新Locale并覆盖原Locale-
	public Locale getLocale(String paramString) {
		Locale localLocale = new Locale(paramString);
		Locale.setDefault(localLocale);
		return localLocale;
	}

	// 刷新显示配置-
	public void refreshLanguage() {
		String str = getLanguage();
		Resources localResources = this.context.getResources();
		if (!localResources.getConfiguration().locale.getLanguage().equals(str)) {
			Configuration localConfiguration = getUpdatedLocaleConfig(str);
			// A structure describing general information about a display, such
			// as its size, density, and font scaling.
			DisplayMetrics localDisplayMetrics = localResources.getDisplayMetrics();
			localResources.updateConfiguration(localConfiguration, localDisplayMetrics);
		}
	}

	// 设置系统语言-
	public void saveLanguage(String paramString) {
		PreferenceManager.getDefaultSharedPreferences(this.context).edit().putString("language", paramString).commit();
	}

	// 保存系统的语言设置到SharedPreferences-
	public void saveSystemLanguage() {
		PreferenceManager.getDefaultSharedPreferences(this.context).edit().putString("PreSysLanguage", this.defaultLanguage).commit();
	}

	public void checkSysChanged(String cuerSysLanguage) {
		// 如果系统语言设置发生变化-
		if (!cuerSysLanguage.equals(PreferenceManager.getDefaultSharedPreferences(this.context).getString("PreSysLanguage", "zh"))) {
			// 如果系统保存了this对象，就在这里修改defaultLanguage的值为当前系统语言cuerSysLanguage
			this.defaultLanguage = cuerSysLanguage;
			saveLanguage(cuerSysLanguage);
			saveSystemLanguage();
		}
	}
}
