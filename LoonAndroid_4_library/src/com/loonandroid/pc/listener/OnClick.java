/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-5-2 上午9:37:59
 */
package com.loonandroid.pc.listener;

import com.loonandroid.pc.ioc.Ioc;

import android.view.View;
import android.view.View.OnClickListener;

/**
 * @author absir
 * 
 */
public class OnClick extends OnListener implements OnClickListener {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
    private static final long serialVersionUID = 5577720054159826691L;

	@Override
	public void onClick(View v) {
		long time = System.currentTimeMillis();
		Ioc.getIoc().getLogger().d("点击");
		invoke(v);
		Ioc.getIoc().getLogger().d("点击耗时：" + (System.currentTimeMillis() - time));
	}

	@Override
	public void listener(View view) {
		view.setOnClickListener(this);
	}

	@Override
    public void getParameterTypes() {
		parameterTypes = new Class[1];
		parameterTypes[0] = View.class;
    }
}
