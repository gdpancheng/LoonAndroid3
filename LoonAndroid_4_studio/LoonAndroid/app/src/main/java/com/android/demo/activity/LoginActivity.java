package com.android.demo.activity;

import java.util.HashMap;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.android.demo.app.App;
import com.android.demo.util.Constant.HttpUrl;
import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InHttp;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.handler.Handler_Json;
import com.loonandroid.pc.plug.login.AccountEntity;
import com.loonandroid.pc.plug.login.LoginConfig;
import com.loonandroid.pc.plug.login.PluginLogin;
import com.loonandroid.pc.plug.net.FastHttp;
import com.loonandroid.pc.plug.net.ResponseEntity;

/**
 * 欢迎页面 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月14日 下午11:26:47
 */
@InLayer(R.layout.activity_login)
public abstract class LoginActivity extends Activity implements PluginLogin{

	@Override
	public void i(LoginConfig config) {
		config.init(R.id.ed_number, R.id.ed_password, R.id.ed_submit, R.id.ed_remember);
	}
	
	@Init
	public void init(){
		AccountEntity datas = getSave();
		//是否保存用户名密码
		System.out.println("是否已经保存了用户名密码："+datas.isSave());
		//所有存储的用户名密码
		System.out.println("所有存储的用户名密码："+datas.getAccountLists());
		//存储的最新的一条用户名密码
		if (datas.getLastAccount()!=null) {
			System.out.println("存储的最新的一条账户是 "+datas.getLastAccount()+" 用户名是 "+datas.getLastAccount().get(AccountEntity.NAME));
        }
	}
	/**
	 * 当点击登陆按钮，会自动获取输入框内的用户名和密码，对其进行验证
	 */
	@Override
	public void onValiResult(View view) {
		if (view == null) {
	        //验证通过
			App.app.http.u(this).login("aaa", "bbb");
        }else{
        	//验证失败给出提示语
        	Toast.makeText(this, "账号密码不能为空", Toast.LENGTH_SHORT).show();
        }
	}
	
	@InHttp(HttpUrl.LOGIN_KEY)
	public void result(ResponseEntity entity){
		if (entity.getStatus() == FastHttp.result_net_err) {
			Toast.makeText(this, "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
			return;
		}
		if (entity.getContentAsString()==null||entity.getContentAsString().length()==0) {
			Toast.makeText(this, "网络请求失败，请检查网络", Toast.LENGTH_SHORT).show();
			return;
        }
		//解析返回的数据
		HashMap<String, Object> data = Handler_Json.JsonToCollection(entity.getContentAsString());
		int status = Integer.valueOf(data.get("status").toString());
		if (status == 0) {
			Toast.makeText(this, data.get("data").toString(), Toast.LENGTH_SHORT).show();
			return;
        }
		save();
		//------------------------------------------------------------
		//清除保存的数据
//		clear("bbb");清除账号bbb的缓存
//		clear();清除所有缓存
	}
}
