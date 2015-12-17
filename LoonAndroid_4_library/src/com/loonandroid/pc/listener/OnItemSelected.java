/**
 * Copyright 2013 ABSir's Studio
 * 
 * All right reserved
 *
 * Create on 2013-5-2 下午2:17:25
 */
package com.loonandroid.pc.listener;

import java.lang.reflect.Method;
import java.util.Arrays;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.ioc.kernel.KernelReflect;
import com.loonandroid.pc.ioc.kernel.KernelString;

/**
 * @author absir
 * 
 */
public class OnItemSelected extends OnListener implements OnItemSelectedListener {

	/** noneMethod */
	private String noneMethod;

	/** noneTargetMethod */
	private Method noneTargetMethod;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(android .widget.AdapterView, android.view.View, int, long)
	 */
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		if (arg0 == null || arg1 == null) {
			Ioc.getIoc().getLogger().d(" 无法调用OnItemSelected(如果没有对程序造成影响请忽略，这是个未找出问题的错误)\n");
			return;
		}
		invoke(arg0, arg1, arg2, arg3);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.AdapterView.OnItemSelectedListener#onNothingSelected(android .widget.AdapterView)
	 */
	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		if (noneTargetMethod != null) {
			KernelReflect.invoke(target, noneTargetMethod, arg0);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.absir.android.view.listener.Listener#listener(android.view.View)
	 */
	@Override
	protected void listener(View view) {

		Class[] noneParameterTypes = new Class[1];
		noneParameterTypes[0] = AdapterView.class;
		if (noneMethod != null) {
			noneTargetMethod = KernelReflect.assignableMethod(target.getClass(), noneMethod, 0, true, noneParameterTypes);
			noneMethod = null;
		}
		if (AdapterView.class.isAssignableFrom(view.getClass())) {
			((AdapterView) view).setOnItemSelectedListener(this);
		} else {
			Ioc.getIoc().getLogger().e(view.getClass() + " 无法设置OnItemSelected 请检查InjectMethod的参数\n");
		}
	}

	@Override
	public void getParameterTypes() {

		String[] methods = method.split(",");
		if (methods.length == 2) {
			method = methods[0];
			noneMethod = methods[1];
		} else {
			noneMethod = "none" + KernelString.uncapitalize(method);
		}
		parameterTypes = new Class[4];
		parameterTypes[0] = AdapterView.class;
		parameterTypes[1] = View.class;
		parameterTypes[2] = int.class;
		parameterTypes[3] = long.class;
	}

}
