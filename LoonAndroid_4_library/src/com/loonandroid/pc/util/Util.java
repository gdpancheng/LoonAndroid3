package com.loonandroid.pc.util;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;

/*
 * Author: pancheng Email:gdpancheng@gmail.com
 * Created Date:2015年11月9日
 * Copyright @ 2015 BU
 * Description: 类描述
 *
 * History:
 */
public class Util {

	public static boolean isDestroyed(Object from) {
		if (Activity.class.isAssignableFrom(from.getClass()) && ((Activity) from).isFinishing()) {

		}
		return false;
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
