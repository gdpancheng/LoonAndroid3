/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-5-2 下午2:08:39
 */
package com.loonandroid.pc.listener;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;

import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.ioc.kernel.KernelDyna;

/**
 * @author absir
 * 
 */
public class OnItemLongClick extends OnListener implements OnItemLongClickListener {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
    private static final long serialVersionUID = 548627419477328623L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.AdapterView.OnItemLongClickListener#onItemLongClick(android
	 * .widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		Ioc.getIoc().getLogger().d("点击");
		long time = System.currentTimeMillis();
		boolean result = KernelDyna.to(invoke(arg0, arg1, arg2, arg3), boolean.class);
		Ioc.getIoc().getLogger().d("点击耗时："+(System.currentTimeMillis()-time));
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.absir.android.view.Listener.Listener#listener(android.view.View)
	 */
	@Override
	protected void listener(View view) {
		// TODO Auto-generated method stub
		if (view instanceof ListView) {
			((ListView) view).setOnItemLongClickListener(this);
		}else {
			Ioc.getIoc().getLogger().e(view.getClass() +" 无法设置onItemLongClick 请检查InjectMethod的参数\n");
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
