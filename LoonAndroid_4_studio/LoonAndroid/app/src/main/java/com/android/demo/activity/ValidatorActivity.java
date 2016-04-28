package com.android.demo.activity;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InVa;
import com.loonandroid.pc.annotation.InVaER;
import com.loonandroid.pc.annotation.InVaOK;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.listener.OnClick;
import com.loonandroid.pc.validator.Regex;
import com.loonandroid.pc.validator.VaCard;
import com.loonandroid.pc.validator.VaDate;
import com.loonandroid.pc.validator.VaEmail;
import com.loonandroid.pc.validator.VaMobile;
import com.loonandroid.pc.validator.VaPassword;
import com.loonandroid.pc.validator.VaPasswordConfirm;
import com.loonandroid.pc.validator.VaWeb;
import com.loonandroid.pc.validator.Validator;
import com.loonandroid.pc.validator.ValidatorCore;

/**
 * 输入验证范例 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2015年11月26日 下午5:11:20
 */
@InLayer(R.layout.activity_vali)
public class ValidatorActivity extends Activity {

	@InAll
	Views views;

	static class Views {
		@InVa(value=VaPassword.class,index=1)
		EditText tv_password;
		@InVa(value=VaPasswordConfirm.class,index=2)
		EditText tv_passwordconfirm;
		@InVa(value=VaEmail.class,index=3)
		EditText tv_email;
		@InVa(value=VaMobile.class,index=4)
		EditText tv_mobile;
		@InVa(value=VaDate.class,index=5)
		EditText tv_data;
		@InVa(value=VaWeb.class,index=6)
		EditText tv_web;
		@InVa(value=VaCard.class,index=7)
		EditText tv_card;
		@InVa(msg = "不能为空",empty=false,index=8)
		EditText tv_notnull;
		@InVa(reg=Regex.LET_NUM_UNLINE_REG,msg="请输入字母数字或下划线",empty=false,index=9)
		EditText tv_number;
		@InBinder(listener=OnClick.class,method="click")
		Button bt_onclick;
	}

	@Init
	public void init() {
	}
	
	public void click(View view) {
		Validator.verify(this);
	}

	@InVaOK
	private void onValidationSucceeded() {
		Toast.makeText(this, "验证成功", Toast.LENGTH_SHORT).show();
	}

	@InVaER
	public void onValidationFailed(ValidatorCore core) {
		System.out.println(core.getMsg());
		if (core.getView()!=null&&TextView.class.isAssignableFrom(core.getView().getClass())) {
			EditText editText = core.getView();
			editText.requestFocus();
			editText.setFocusable(true);
			editText.setError(core.getMsg());
        } 
	}
}
