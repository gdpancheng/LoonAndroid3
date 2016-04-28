package com.loonandroid.pc.plug.photo;
/**
 * 照片的参数
 * 	<br>-----------------------------------------------
 * <br>oooO...............
 * <br>(....) ... Oooo...
 * <br>.\..(.....(.....).......
 * <br>..\_)..... )../........
 * <br>.......... (_/..........
 * <br>微信 gdpancheng
 * <br>-----------------------------------------------
 * @author gdpancheng@gmail.com 2015年11月7日 上午12:18:24
 */
public class PhotoConfig {

	public int aspectX = 1;
	public int aspectY = 1;
	public int outputX = 400;
	public int outputY = 400;
	public boolean scale = true;
	public String path;
	
	@Override
    public String toString() {
	    return "PhotoConfig [aspectX=" + aspectX + ", aspectY=" + aspectY + ", outputX=" + outputX + ", outputY=" + outputY + ", scale=" + scale + ", path=" + path + "]";
    }
}
