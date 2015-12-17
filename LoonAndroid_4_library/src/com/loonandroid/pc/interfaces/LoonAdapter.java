package com.loonandroid.pc.interfaces;

import java.util.ArrayList;
import java.util.List;

import com.loonandroid.pc.adapter.ViewHolder;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014年12月7日
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */

/**
 * 处理类
 * 
 * @author gdpancheng@gmail.com 2014年12月8日 下午3:20:45
 */
public interface LoonAdapter<T> {
	
	public ArrayList<T> getData();

	public void setData(List<T> data);

	public void setDeal(LoonViewDeal<T> deal);

	public boolean dealView(T item, ViewHolder viewHolder);
}
