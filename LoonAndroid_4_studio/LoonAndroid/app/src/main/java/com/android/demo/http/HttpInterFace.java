package com.android.demo.http;

import java.io.File;
import java.util.LinkedHashMap;

import com.android.demo.util.Constant.HttpUrl;
import com.loonandroid.pc.annotation.InForm;
import com.loonandroid.pc.annotation.InGet;
import com.loonandroid.pc.annotation.InNet;
import com.loonandroid.pc.annotation.InParam;
import com.loonandroid.pc.annotation.InPost;
import com.loonandroid.pc.annotation.InWeb;
import com.loonandroid.pc.net.NetConfig;

/**
 * 网络接口范例 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2015年1月7日 下午2:16:21
 * @param <T>
 */
@InNet(HttpUrl.class)
public interface HttpInterFace {

	//---------------------------------------------------------------------------------------
	//同步
	@InForm(HttpUrl.FILES)
	public <T> T login(@InParam("id") String id,LinkedHashMap<String, File> params);

	@InGet
	public <T> T login(LinkedHashMap<String, Object> params);

	@InPost
	public <T> T asyncLoginPost(String name, String password);

	@InPost(HttpUrl.LOGIN)
	public <T> T postStr(@InParam String json);
	
	@InWeb(method="getRegionCountry",space="http://WebXml.com.cn/")
	public <T> T asyncWeb();
	
	@InWeb
	public <T> T asyncLoginWeb(@InParam("theRegionCode") String theRegionCode,NetConfig config);
	
	//---------------------------------------------------------------------------------------
	//异步
	@InPost(HttpUrl.LOGIN)
	public void login(@InParam("username") String name, @InParam("password") String password);

	@InPost(HttpUrl.LOGIN)
	public void loginPost(@InParam LinkedHashMap<String, Object> params);
	
	@InPost
	public void login(@InParam String json);
	
	@InWeb
	public void LoginWeb(String json);
	
	@InForm
	public void LoginForm(String name, String password);
}
