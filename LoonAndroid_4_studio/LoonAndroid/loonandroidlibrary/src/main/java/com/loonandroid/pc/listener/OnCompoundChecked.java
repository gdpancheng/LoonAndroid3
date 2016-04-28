/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-5-2 下午2:29:35
 */
package com.loonandroid.pc.listener;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.loonandroid.pc.ioc.Ioc;

/**
 * @author absir
 * 
 */
public class OnCompoundChecked extends OnListener implements OnCheckedChangeListener {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
    private static final long serialVersionUID = 2870454940667577230L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.widget.CompoundButton.OnCheckedChangeListener#onCheckedChanged
	 * (android.widget.CompoundButton, boolean)
	 */
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		Ioc.getIoc().getLogger().d("点击");
		long time = System.currentTimeMillis();
		invoke(buttonView, isChecked);
		Ioc.getIoc().getLogger().d("点击耗时："+(System.currentTimeMillis()-time));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.absir.android.view.listener.Listener#listener(android.view.View)
	 */
	@Override
	protected void listener(View view) {
		// TODO Auto-generated method stub
		if (view instanceof CompoundButton) {
			((CompoundButton) view).setOnCheckedChangeListener(this);
		}else {
			Ioc.getIoc().getLogger().e(view.getClass() +" 无法设置OnCompoundChecked 请检查InjectMethod的参数\n");
		}
	}

	@Override
    public void getParameterTypes() {
		parameterTypes = new Class[2];
		parameterTypes[0] = CompoundButton.class;
		parameterTypes[1] = boolean.class;
    }
}
