package com.loonandroid.pc.net;

import android.graphics.Bitmap;
import android.view.View;

/**
 * 上一个版本的网络请求只保留文件上传和webservice 其他的使用Volley <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月26日 上午12:12:01
 */
public  class ImageListener {
	public  void onLoadingStarted(String imageUri, View view){};
	public  void onLoadingFailed(String imageUri, View view){};
	public  void onLoadingComplete(String imageUri, View view, Bitmap loadedImage){};
	public  void onLoadingCancelled(String imageUri, View view){};
}
