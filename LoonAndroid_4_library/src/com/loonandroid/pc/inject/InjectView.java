package com.loonandroid.pc.inject;

import android.view.LayoutInflater;
import android.view.View;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-13
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */
public class InjectView extends Inject<View> implements InjectExcutor<View> {

	@Override
	public View loadView(View object, int id) {
		return LayoutInflater.from(object.getContext()).inflate(id, null);
	}

	@Override
	public View findViewById(View object, int id) {
		return object.findViewById(id);
	}

	@Override
	public View findViewById(int id) {
		return object != null ? ((View) object).findViewById(id) : null;
	}
}
