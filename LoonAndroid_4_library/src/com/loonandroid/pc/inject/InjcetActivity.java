package com.loonandroid.pc.inject;

import android.app.Activity;
import android.view.View;

import com.loonandroid.pc.ioc.Ioc;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-13
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class InjcetActivity extends Inject<Activity> implements InjectExcutor<Activity> {

	@Override
	public void setContentView(Activity object, int id) {
		try {
			object.setContentView(id);
		} catch (Exception e) {
			Ioc.getIoc().getLogger().e(object.getClass().getSimpleName() + " setContentView() 出错 请检查InjectLayer的布局\n");
			e.printStackTrace();
		}
	}

	@Override
	public View loadView(Activity object, int id) {
		return object.getLayoutInflater().inflate(id, null);
	}

	@Override
	public View findViewById(Activity object, int id) {
		try {
			return object.findViewById(id);
		} catch (Exception e) {
			Ioc.getIoc().getLogger().e(object.getClass().getSimpleName() + " findViewById() 出错 请检查InjectView的参数\n");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public View findViewById(int id) {
		return object != null ? ((View) object).findViewById(id) : null;
	}

}
