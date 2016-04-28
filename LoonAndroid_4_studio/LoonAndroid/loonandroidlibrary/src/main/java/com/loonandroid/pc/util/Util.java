package com.loonandroid.pc.util;

import android.app.Activity;

import com.loonandroid.pc.ioc.Ioc;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipFile;

/*
 * Author: pancheng Email:gdpancheng@gmail.com
 * Created Date:2015年11月9日
 * Copyright @ 2015 BU
 * Description: 类描述
 *
 * History:
 */
public class Util {

	public static String packageName;

	public static boolean isDestroyed(Object from) {
		if (Activity.class.isAssignableFrom(from.getClass()) && ((Activity) from).isFinishing()) {

		}
		return false;
	}

	/**
	 * android studio中的applicationId 会覆盖getPackageName()没办法
	 * @return
     */
	public static String getPackageName(){
		if(packageName!=null){
			return packageName;
		}
		try {
			ZipFile zipFile = new ZipFile(Ioc.getIoc().getApplication().getPackageResourcePath());
			InputStream in = zipFile.getInputStream(zipFile.getEntry("AndroidManifest.xml"));
			new BinaryXmlParser(in).parse(new BinaryXmlParser.EventHandler() {
				@Override
				public void onStartTag(String tag, Map<String, String> attrs) {
					if (tag.equals("manifest")) {
						packageName = attrs.get("package");
					}
				}

				@Override
				public void onEndTag(String tag) {

				}
			});
		} catch (IOException e) {
		}
		if (packageName == null){
			return Ioc.getIoc().getApplication().getPackageName();
		}
		return packageName;
	}

	private static final ArrayList EMPTY_LIST = new ArrayList();
	private static final HashMap EMPTY_MAP = new HashMap();

	public static final <T> ArrayList<T> emptyList() {
		return (ArrayList<T>) EMPTY_LIST;
	}
	
	public static final <D,T> HashMap<D,T> emptyMap() {
		return (HashMap<D,T>) EMPTY_MAP;
	}
}
