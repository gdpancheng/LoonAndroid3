package com.demo.xiamenhome;

import java.util.HashMap;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.application.App;
import com.demo.application.App.Result;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InHttp;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.handler.Handler_Json;
import com.loonandroid.pc.listener.OnClick;
import com.loonandroid.pc.plug.login.LoginConfig;
import com.loonandroid.pc.plug.login.PluginLogin;

/**
 * 登录页面 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2015年12月10日 下午2:58:57
 */
@InLayer(value = R.layout.login, parent = R.id.center)
public abstract class LoginActivity extends BaseActivity implements PluginLogin {

	@InAll
	Views views;

	static class Views {
		@InBinder(listener = OnClick.class, method = "click")
		View register;
		TextView user_name, user_password;
	}

	// -------------------------------------------------------------登录组件的初始化---------------------------------------------
	@Override
	public void i(LoginConfig config) {
		config.init(R.id.user_name, R.id.user_password, R.id.login_bt, R.id.ed_remember);
	}
	
	// -------------------------------------------------------------activity的初始化---------------------------------------------
	@Init
	private void init() {
		hideBottom();
		setTitle("登录");
	}

	// -------------------------------------------------------------登录组件的点击按钮以后会自动判断输入框是否为空---------------------------------------------
	@Override
	public void onValiResult(View view) {
		if (view == null) {
			// 验证通过
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("username", views.user_name.getText().toString());
			params.put("password", views.user_password.getText().toString());
			showProgress();
			App.http.u(this).login(params);
		} else {
			// 验证失败给出提示语
			Toast.makeText(this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
		}
	}

	// -------------------------------------------------------------网络请求回调---------------------------------------------
	@InHttp
	public void result(Result result) {
		progressDimss();
		// 判断请求是否成功
		if (!result.isOk()) {
			Toast.makeText(this, "请求失败，请重试", Toast.LENGTH_SHORT).show();
			return;
		}
		HashMap<String, Object> data = Handler_Json.JsonToCollection(result.object);
		if (data.get("status").toString().equals("0")) {
			showToast(data.get("data").toString());
			return;
		}
		App.app.setData("user_id", data.get("data").toString());
		finish();
	}
}
