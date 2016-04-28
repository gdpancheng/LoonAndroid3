/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-5-2 下午1:57:44
 */
package com.loonandroid.pc.listener;

import android.view.View;
import android.view.View.OnLongClickListener;

import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.ioc.kernel.KernelDyna;

/**
 * @author absir
 * 
 */
public class OnLongClick extends OnListener implements OnLongClickListener {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
    private static final long serialVersionUID = 7557336840573628157L;

	@Override
	public boolean onLongClick(View v) {

		Ioc.getIoc().getLogger().d("点击");
		long time = System.currentTimeMillis();
		boolean result = KernelDyna.to(invoke(v), boolean.class);
		Ioc.getIoc().getLogger().d("点击耗时：" + (System.currentTimeMillis() - time));
		return result;
	}

	@Override
	protected void listener(View view) {
		view.setOnLongClickListener(this);
	}

	@Override
    public void getParameterTypes() {
		parameterTypes = new Class[1];
		parameterTypes[0] = View.class;
    }
}
