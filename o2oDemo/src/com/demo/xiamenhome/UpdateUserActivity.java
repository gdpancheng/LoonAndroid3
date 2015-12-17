package com.demo.xiamenhome;

import java.util.HashMap;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.demo.application.App;
import com.demo.application.App.Result;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InHttp;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InVa;
import com.loonandroid.pc.annotation.InVaER;
import com.loonandroid.pc.annotation.InVaOK;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.handler.Handler_Json;
import com.loonandroid.pc.listener.OnClick;
import com.loonandroid.pc.validator.Validator;
import com.loonandroid.pc.validator.ValidatorCore;

/**
 * 设置 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2015年12月11日 下午6:34:22
 */
@InLayer(value = R.layout.update_user, parent = R.id.center)
public class UpdateUserActivity extends BaseActivity {
	@InAll
	Views views;
	
	static class Views{
		@InVa(empty = false, msg = "昵称不能为空")
		EditText realname;
		EditText domicile, new_active_area, remark;
		RadioButton man, woman;
		@InBinder(listener = OnClick.class, method = "click")
		ImageButton submit;
	}
	String realnames, domiciles, new_active_areas, remarks;
	
	@Init
	private void init() {
		setTitle("编辑资料");
		setBottom(R.id.user);

		views.realname.setText(App.app.getData("realname"));
		views.domicile.setText(App.app.getData("domicile"));
		views.new_active_area.setText(App.app.getData("new_active_area"));
		views.remark.setText(App.app.getData("remark"));
		views.man.setChecked(true);
		if (App.app.getData("new_sex").equals("0")) {
			views.woman.setChecked(true);
		}
	};
	
	public void click(View view) {
		Validator.verify(this);
	}
	
	@InVaOK
	private void onValidationSucceeded() {
		realnames = views.realname.getText().toString().trim();
		domiciles = views.domicile.getText().toString().trim();
		new_active_areas = views.new_active_area.getText().toString().trim();
		remarks = views.remark.getText().toString().trim();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", App.app.getData("user_id"));
		params.put("realname", realnames);
		params.put("new_sex", views.man.isChecked() ? "1" : "0");
		params.put("domicile", domiciles);
		params.put("new_active_area", new_active_areas);
		params.put("remark", remarks);
		showProgress();
		App.http.u(this).edit_user(params);
	}

	// ---------------------------------------网络请求回调-----------------------------------------------------
	/**
	 * 网络请求回调
	 * 
	 * @author gdpancheng@gmail.com 2015年12月9日 下午4:43:46
	 * @param result
	 * @return void
	 */
	@InHttp
	public void result(Result data) {
		progressDimss();
		// 判断请求是否成功
		if (!data.isOk()) {
			Toast.makeText(this, "请求失败，请重试", Toast.LENGTH_SHORT).show();
			return;
		}
		HashMap<String, Object> http_data = Handler_Json.JsonToCollection(data.object);
		if (http_data.get("status").toString().equals("1")) {
			showToast("更改成功");
			// ----------------------------------------------------------------------------------------
			// 存储更新的信息
			App.app.setData("realname", realnames);
			App.app.setData("domicile", domiciles);
			App.app.setData("new_sex", views.man.isChecked() ? "1" : "0");
			App.app.setData("new_active_area", new_active_areas);
			App.app.setData("remark", remarks);
		}
	}
	
	@InVaER
	public void onValidationFailed(ValidatorCore core) {
		EditText message = core.getView();
		message.requestFocus();
		showToast(core.getMsg());
	}
}
