package com.android.demo.util;

import java.io.Serializable;
import java.util.ArrayList;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014年12月9日
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class HttpEntity implements Serializable{

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<MyHashMap<String, Integer>> getMenus() {
		return menus;
	}

	public void setMenus(ArrayList<MyHashMap<String, Integer>> menus) {
		this.menus = menus;
	}

	public String title;
	public ArrayList<MyHashMap<String, Integer>> menus;
	
	@Override
    public String toString() {
	    return "MenuEntity [title=" + title + ", menus=" + menus + "]";
    }
}
