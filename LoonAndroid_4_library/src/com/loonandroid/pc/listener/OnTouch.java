/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-5-2 �??2:02:48
 */
package com.loonandroid.pc.listener;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.ioc.kernel.KernelDyna;

/**
 * @author absir
 * 
 */
public class OnTouch extends OnListener implements OnTouchListener {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
    private static final long serialVersionUID = -8355845975661951819L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent)
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
	
		long time = System.currentTimeMillis();
		Ioc.getIoc().getLogger().d("点击");
		boolean result = KernelDyna.to(invoke(v, event), boolean.class);
		Ioc.getIoc().getLogger().d("点击耗时：" + (System.currentTimeMillis() - time));
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
		view.setOnTouchListener(this);
	}

	@Override
    public void getParameterTypes() {
		parameterTypes = new Class[2];
		parameterTypes[0] = View.class;
		parameterTypes[1] = MotionEvent.class;
    }
}
