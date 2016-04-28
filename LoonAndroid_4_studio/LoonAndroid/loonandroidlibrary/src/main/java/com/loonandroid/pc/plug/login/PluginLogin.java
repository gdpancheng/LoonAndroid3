package com.loonandroid.pc.plug.login;


import android.view.View;

import com.loonandroid.pc.annotation.InParam;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.plug.PluginComponent;

/**
 * 	<br>-----------------------------------------------
 * <br>oooO...............
 * <br>(....) ... Oooo...
 * <br>.\..(.....(.....).......
 * <br>..\_)..... )../........
 * <br>.......... (_/..........
 * <br>微信 gdpancheng
 * <br>-----------------------------------------------
 * @author gdpancheng@gmail.com 2015年1月2日 下午8:22:42
 */
public interface PluginLogin extends PluginComponent{
	/**
	 * 获取存储的账户密码
	 * @author gdpancheng@gmail.com 2015年11月11日 下午3:06:06
	 * @return
	 * @return AccountEntity
	 */
	public AccountEntity getSave();
	public void save();
	public void clear(String... name);
	public final LoginConfig config = new LoginConfig();
	/**
	 * 加上init注解其会在当前页面上下文初始化的时候调用
	 * @author gdpancheng@gmail.com 2015年11月10日 下午4:06:03
	 * @param config
	 * @return void
	 */
	@Init
	public void i(@InParam("config") LoginConfig config);
	public void onValiResult(View view);
}
