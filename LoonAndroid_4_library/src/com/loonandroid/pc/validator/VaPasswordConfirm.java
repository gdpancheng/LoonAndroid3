package com.loonandroid.pc.validator;

import android.widget.TextView;

/*
 * Author: pancheng Email:gdpancheng@gmail.com
 * Created Date:2015年12月2日
 * Copyright @ 2015 BU
 * Description: 类描述
 *
 * History:
 */
public class VaPasswordConfirm extends ValidatorCore implements VaRule {

	public void init() {
		setMsg("确认密码错误");
	}

	public boolean check() {
		String str = ((TextView)(getView())).getText().toString().trim();
		return super.check()&&!Regex.StrisNull(str)&&str.equals(getPreStr());
	}
}
