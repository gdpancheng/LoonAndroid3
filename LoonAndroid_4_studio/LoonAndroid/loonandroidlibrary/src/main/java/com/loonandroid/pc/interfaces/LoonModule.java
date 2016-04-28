package com.loonandroid.pc.interfaces;

import android.view.View;

/**
 * 组件接口 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月16日 下午1:43:57
 */
public interface LoonModule {
	
	public <T extends View>T getView();

	public <T> void setData(T t);

	public <T> T getData();

	public void setCallBack(LoonCallback callback);
	
	public LoonCallback getCallBack();
}
