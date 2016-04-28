/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-5-2 下午2:02:48
 */
package com.loonandroid.pc.listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.refresh.PullToRefreshAdapterViewBase;
import com.loonandroid.pc.refresh.PullToRefreshBase;

public class OnItemClick extends OnListener implements OnItemClickListener {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
    private static final long serialVersionUID = 8476955827791099385L;

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Ioc.getIoc().getLogger().d("点击");
		long time = System.currentTimeMillis();
		invoke(arg0, arg1, arg2, arg3);
		Ioc.getIoc().getLogger().d("点击耗时："+(System.currentTimeMillis()-time));
	}

	@Override
	protected void listener(View view) {
		if (AdapterView.class.isAssignableFrom(view.getClass())) {
			((AdapterView) view).setOnItemClickListener(this);
		}else if(PullToRefreshBase.class.isAssignableFrom(view.getClass())){
			((PullToRefreshAdapterViewBase) view).setOnItemClickListener(this);
		}else{
			Ioc.getIoc().getLogger().e(view.getClass() +" 无法设置OnItemClick 请检查InjectMethod的参数\n");
		}
	}

	@Override
    public void getParameterTypes() {
		parameterTypes = new Class[4];
		parameterTypes[0] = AdapterView.class;
		parameterTypes[1] = View.class;
		parameterTypes[2] = int.class;
		parameterTypes[3] = long.class;
    }
}
