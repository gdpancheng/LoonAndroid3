package com.loonandroid.pc.plug.login;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 感谢http://blog.csdn.net/lmj623565791/article/details/38902805这位兄弟的思想 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2015年1月3日 下午11:36:48
 */
public  class LoginConfig {
	
	public void init(int etNId, int etPId, int etLgId, int etRmID) {
	    this.etNId = etNId;
	    this.etPId = etPId;
	    this.etLgId = etLgId;
	    this.etRmID = etRmID;
    }
	/**
	 * 账号ViewId 密码ViewId 提交按钮ViewId 忘记密码ViewID
	 */
	public transient  int etNId,etPId,etLgId,etRmID;
	
	public  ArrayList<HashMap<String, String>> info = new ArrayList<HashMap<String, String>>();
}
