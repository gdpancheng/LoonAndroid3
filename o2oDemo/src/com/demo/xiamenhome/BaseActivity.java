package com.demo.xiamenhome;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.application.App;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InPLayer;
import com.loonandroid.pc.listener.OnClick;

/*
* Author: pancheng Email:gdpancheng@gmail.com
* Created Date:2015年12月9日
* Copyright @ 2015 BU
* Description: 类描述
*
* History:
*/
@InPLayer(R.layout.activity_base)
public class BaseActivity extends Activity {

	@InAll
	private Views v;
	ProgressDialog progressDialog;
	
	static class Views{
		@InBinder(listener=OnClick.class,method="click")
		ImageView home, list, user, buy, more;
		@InBinder(listener=OnClick.class,method="click")
		ImageButton back;
		TextView title;
		View top,bottom;
	}
	/**
	 * 设置显示的底部
	 * @author gdpancheng@gmail.com 2013-7-16 下午4:35:12
	 * @param id
	 * @return void
	 */
	public void setBottom(int id) {
		v.home.setImageResource(R.drawable.home_bt);
		v.list.setImageResource(R.drawable.list_bt);
		v.user.setImageResource(R.drawable.user_bt);
		v.buy.setImageResource(R.drawable.buy_bt);
		v.more.setImageResource(R.drawable.more_bt);

		switch (id) {
		case R.id.home:
			v.home.setImageResource(R.drawable.home_press);
			break;
		case R.id.list:
			v.list.setImageResource(R.drawable.list_press);
			break;
		case R.id.user:
			v.user.setImageResource(R.drawable.user_press);
			break;
		case R.id.buy:
			v.buy.setImageResource(R.drawable.buy_press);
			break;
		case R.id.more:
			v.more.setImageResource(R.drawable.more_press);
			break;
		default:
			break;
		}
	}
	
	/**
	 * 隐藏顶部的导航栏
	 * @author gdpancheng@gmail.com 2015年12月10日 上午11:35:18
	 * @return void
	 */
	public void hideTop() {
	    v.top.setVisibility(View.GONE);
    }
	
	public void hideBottom() {
	    v.bottom.setVisibility(View.GONE);
    }
	
	public void setTitle(String title) {
	    v.title.setText(title);
    }
	
	/**
	 * 显示进度条
	 * @author gdpancheng@gmail.com 2013-7-30 下午6:01:38
	 * @return void
	 */
	protected void showProgress() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setTitle("请等待...");
			progressDialog.setMessage("正在访问网络");
		}
		if (!progressDialog.isShowing()) {
			progressDialog.show();
		}
	}

	protected void progressDimss() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}

	public void showToast(String text) {
		Toast.makeText(this, text, Toast.LENGTH_LONG).show();
	}
	
	private void click(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.home:
			startActivity(new Intent(this, MainActivity.class));
			if (this.getClass().getName().equals(MainActivity.class.getName())) {
				finish();
            }
			break;
		case R.id.list:
			startActivity(new Intent(this, GoodsListActivity.class));
			if (this.getClass().getName().equals(MainActivity.class.getName())) {
				finish();
            }
			break;
		case R.id.user:
			if (App.app.getData("user_id").length() == 0) {
				Intent intent = new Intent(this, LoginActivity.class);
				intent.putExtra("class", SettingActivity.class.getName());
				startActivity(intent);
				return;
			}
			startActivity(new Intent(this, SettingActivity.class));
			if (this.getClass().getName().equals(MainActivity.class.getName())) {
				finish();
            }
			break;
		case R.id.buy:
//			startActivity(new Intent(this, BuyingActivity.class));
//			if (this.getClass() != MainActivity.class) {
//				finish();
//            }
			break;
		case R.id.more:
//			startActivity(new Intent(this, MoreActivity.class));
//			if (this.getClass() != MainActivity.class) {
//				finish();
//            }
			break;
		}
	}
}
