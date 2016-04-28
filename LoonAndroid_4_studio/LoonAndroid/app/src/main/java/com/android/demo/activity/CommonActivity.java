package com.android.demo.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InListener;
import com.loonandroid.pc.annotation.InPLayer;
import com.loonandroid.pc.listener.OnClick;
import com.loonandroid.pc.util.LanguageSettingUtil;

/**
 * 这里是通用的父布局 主要展示{@link InPLayer}的用法<br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * ----------------------------------------------- <br>
 * 
 * @author gdpancheng@gmail.com 2014年12月10日 上午10:37:24
 */
@InPLayer(R.layout.activity_com)
public class CommonActivity extends FragmentActivity {

	public void startFragmentAdd(Fragment fragment) {
		// ------------------------------------------------------------------------
		FragmentManager fragmentManager = this.getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.fl_test, fragment);
		fragmentTransaction.commit();
		// ------------------------------------------------------------------------
	}

	/**
	 * 对基类中通用模块的view进行事件绑定，如果需要在子类中单独处理 则在子类中覆盖即可 注意其必须为private 其中ids是需要绑定事件的View的ID，后面为事件类型 TODO(这里用一句话描述这个方法的作用)
	 * 
	 * @author gdpancheng@gmail.com 2015年11月6日 下午6:00:52
	 * @param view
	 * @return void
	 */
	@InListener(ids = { R.id.top, R.id.bottom }, listeners = { OnClick.class })
	private void click(View view) {
		LanguageSettingUtil.get().saveLanguage("en");
		LanguageSettingUtil.get().refreshLanguage();
		Toast.makeText(this, "父类中点击了", Toast.LENGTH_SHORT).show();
	}
}
