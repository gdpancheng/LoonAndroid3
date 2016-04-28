package com.android.demo.util;

import java.util.ArrayList;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014年12月9日
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class MenuEntity {

	public String title;
	public ArrayList<MyHashMap<String, Class>> menus;
	
	@Override
    public String toString() {
	    return "MenuEntity [title=" + title + ", menus=" + menus + "]";
    }
}
