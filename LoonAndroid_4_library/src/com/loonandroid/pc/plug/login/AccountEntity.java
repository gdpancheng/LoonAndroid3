package com.loonandroid.pc.plug.login;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
/**
 * 存储的密码信息
 * 	<br>-----------------------------------------------
 * <br>oooO...............
 * <br>(....) ... Oooo...
 * <br>.\..(.....(.....).......
 * <br>..\_)..... )../........
 * <br>.......... (_/..........
 * <br>微信 gdpancheng
 * <br>-----------------------------------------------
 * @author gdpancheng@gmail.com 2015年11月11日 下午2:46:45
 */
public class AccountEntity implements Serializable {

	/**
	 * 用户名
	 */
	public static final String NAME = "n";
	/**
	 * 密码
	 */
	public static final String PASSWORD = "p";
	
	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
    private static final long serialVersionUID = -7381874769412731991L;

    private ArrayList<HashMap<String, String>> userInfos = new ArrayList<HashMap<String,String>>();
	/**
	 * 是否保存密码
	 */
    private boolean isSave = false;
	
    /**
     * 获取账户列表
     * @author gdpancheng@gmail.com 2015年11月11日 下午3:07:57
     * @return
     * @return ArrayList<HashMap<String,String>>
     */
	public ArrayList<HashMap<String, String>> getAccountLists(){
		return userInfos;
	}
	
	/**
	 * 获取一个最新存储的账户密码
	 * TODO(这里用一句话描述这个方法的作用)
	 * @author gdpancheng@gmail.com 2015年11月11日 下午3:08:04
	 * @return HashMap<String,String>
	 */
	public HashMap<String, String> getLastAccount(){
		if (userInfos.size() == 0) {
	        return null;
        }
		return userInfos.get(userInfos.size()-1);
	}
	
	/**
	 * 存储一个账户
	 * @author gdpancheng@gmail.com 2015年11月11日 下午3:07:43
	 * @param name
	 * @param password
	 * @return void
	 */
	public void add(String name,String password){
		Iterator<HashMap<String, String>> iter = userInfos.iterator();  
		while(iter.hasNext()){  
			HashMap<String, String> one= iter.next();  
		    if(one.get(NAME).equals(name)){  
		        iter.remove();  
		    }  
		}  
		HashMap<String, String> user = new HashMap<String, String>();
		user.put(NAME, name);
		user.put(PASSWORD, password);
		userInfos.add(user);
	};
	
	/**
	 * 清空所有存储的账号
	 * @author gdpancheng@gmail.com 2015年11月11日 下午3:07:25
	 * @return void
	 */
	public void removeAll(){
		userInfos = new ArrayList<HashMap<String,String>>();
		isSave = false;
	};
	/**
	 * 删除已经存储的某一个账号
	 * @author gdpancheng@gmail.com 2015年11月11日 下午3:07:15
	 * @param name
	 * @return void
	 */
	public void removeByAccount(String name){
		Iterator<HashMap<String, String>> iter = userInfos.iterator();  
		while(iter.hasNext()){  
			HashMap<String, String> one= iter.next();  
		    if(one.get(NAME).equals(name)){  
		        iter.remove();  
		    }  
		}  
	};

	public boolean isSave() {
		return isSave;
	}

	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}
	@Override
    public String toString() {
	    return "AccountEntity [userInfos=" + userInfos + ", isSave=" + isSave + "]";
    }
}
