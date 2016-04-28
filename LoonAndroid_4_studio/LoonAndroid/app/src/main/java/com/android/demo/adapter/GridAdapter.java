package com.android.demo.adapter;

import android.widget.BaseAdapter;

import com.example.loonandroid2.R;
import com.loonandroid.pc.adapter.ViewHolder;
import com.loonandroid.pc.interfaces.LoonAdapter;

/**
 * 
 * <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月15日 上午10:30:12
 */
public abstract class GridAdapter extends BaseAdapter implements LoonAdapter<String>{
	
	@Override
	public boolean dealView(String item, ViewHolder viewHolder) {
		viewHolder.setData(R.id.tv_test, item);
	    return false;
	}
}
