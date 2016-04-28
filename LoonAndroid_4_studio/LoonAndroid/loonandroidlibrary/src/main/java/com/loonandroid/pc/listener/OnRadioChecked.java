/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-5-2 下午2:33:16
 */
package com.loonandroid.pc.listener;

import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.loonandroid.pc.ioc.Ioc;

/**
 * @author absir
 * 
 */
public class OnRadioChecked extends OnListener implements OnCheckedChangeListener {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
    private static final long serialVersionUID = 4835557691764055818L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.RadioGroup.OnCheckedChangeListener#onCheckedChanged(android .widget.RadioGroup, int)
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
	
		long time = System.currentTimeMillis();
		Ioc.getIoc().getLogger().d("点击");
		invoke(group, checkedId);
		Ioc.getIoc().getLogger().d("点击耗时：" + (System.currentTimeMillis() - time));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.absir.android.view.listener.Listener#listener(android.view.View)
	 */
	@Override
	protected void listener(View view) {
		if (view instanceof RadioGroup) {
			((RadioGroup) view).setOnCheckedChangeListener(this);
		} else {
			Ioc.getIoc().getLogger().e(view.getClass() + " 无法设置OnRadioChecked 请检查InjectMethod的参数\n");
		}
	}

	@Override
    public void getParameterTypes() {
		parameterTypes = new Class[2];
		parameterTypes[0] = RadioGroup.class;
		parameterTypes[1] = int.class;
    }

}
