package com.loonandroid.pc.plug.photo;



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
public interface PluginPhoto extends PluginComponent{
	/**从相机获取图片**/
	public void camera(PhotoConfig... configs);
	public void photo(PhotoConfig... configs);
	/**从相机获取图片**/
}
