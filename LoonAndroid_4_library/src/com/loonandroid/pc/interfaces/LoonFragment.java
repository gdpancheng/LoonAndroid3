package com.loonandroid.pc.interfaces;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 	<br>-----------------------------------------------
 * <br>oooO...............
 * <br>(....) ... Oooo...
 * <br>.\..(.....(.....).......
 * <br>..\_)..... )../........
 * <br>.......... (_/..........
 * <br>微信 gdpancheng
 * <br>-----------------------------------------------
 * @author gdpancheng@gmail.com 2014年12月19日 上午10:57:05
 */
public interface LoonFragment{
	public Bundle getSavedInstanceState();
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
}
