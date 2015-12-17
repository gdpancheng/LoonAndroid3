package com.demo.xiamenhome;

import java.io.File;
import java.util.HashMap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bluemobi.imageload.AsynImageView;
import com.bluemobi.imageload.ImageDownloader;
import com.demo.application.App;
import com.demo.application.App.Result;
import com.demo.util.Constant.HttpUrl;
import com.demo.view.BottomPhotoDialog;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InHttp;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.handler.Handler_Json;
import com.loonandroid.pc.listener.OnClick;
import com.loonandroid.pc.plug.photo.PhotoConfig;
import com.loonandroid.pc.plug.photo.PluginPhoto;

@InLayer(value = R.layout.user, parent = R.id.center)
public abstract class UserActivity extends BaseActivity implements PluginPhoto, View.OnClickListener {

	@InAll
	Views view;

	BottomPhotoDialog bottomPhotoDialog;
	Bitmap bitmap;
	File picture;

	// ----------------------------------------------------------------------------
	// view类
	static class Views {
		@InBinder(listener = OnClick.class, method = "onClick")
		TextView update_info;
		@InBinder(listener = OnClick.class, method = "onClick")
		AsynImageView user_photo;
		ImageView user_head;
		TextView username, realname, domicile, new_sex, new_active_area, remark, level, gold_coin, sorce, fans, favor, flower;
	}

	@Init
	private void init() {
		setBottom(R.id.user);
		setTitle("个人中心");
		getInfo();
	}

	private void getInfo() {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", App.app.getData("user_id"));
		showProgress();
		App.http.u(this).user_info(params);
	}

	private void upload_head(String url) {
		HashMap<String, File> files = new HashMap<String, File>();
		files.put("logo", new File(url));
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("id", App.app.getData("user_id"));
		App.http.u(this).edit_avatar(params, files);
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
	public void result(Result result) {
		progressDimss();
		// 判断请求是否成功
		if (!result.isOk()) {
			Toast.makeText(this, "请求失败，请重试", Toast.LENGTH_SHORT).show();
			return;
		}
		HashMap<String, Object> http_data = Handler_Json.JsonToCollection(result.object);
		switch (result.key) {
		case HttpUrl.USER_INFO_KEY:
			if (http_data.containsKey("status") && http_data.get("status").equals("1")) {
				http_data = (HashMap<String, Object>) http_data.get("data");
				if (http_data.containsKey("username")) {
					App.app.setData("username", http_data.get("username").toString());
					view.username.setText(http_data.get("username").toString());
				}
				if (http_data.containsKey("realname")) {
					App.app.setData("realname", http_data.get("realname").toString());
					view.realname.setText(http_data.get("realname").toString());
				}
				if (http_data.containsKey("domicile")) {
					String domi_str = http_data.get("domicile").toString();
					if (domi_str.length() > 0) {
						App.app.setData("domicile", domi_str.replace("null", ""));
						view.domicile.setText(App.app.getData("domicile"));
					}
				}
				if (http_data.containsKey("new_sex")) {
					App.app.setData("new_sex", http_data.get("new_sex").toString());
					view.new_sex.setText(http_data.get("new_sex").toString().equals("1") ? "男" : "女");
				}
				if (http_data.containsKey("new_active_area")) {
					String area = http_data.get("new_active_area").toString();
					if (area.length() > 0) {
						App.app.setData("new_active_area", area.replace("null", ""));
						view.new_active_area.setText(App.app.getData("new_active_area"));
					}
				}
				if (http_data.containsKey("remark")) {
					App.app.setData("remark", http_data.get("remark").toString());
					view.remark.setText(http_data.get("remark").toString());
				}
				if (http_data.containsKey("level")) {
					App.app.setData("level", http_data.get("level").toString());
					view.level.setText(http_data.get("level").toString());
				}
				if (http_data.containsKey("gold_coin")) {
					App.app.setData("gold_coin", http_data.get("gold_coin").toString());
					view.gold_coin.setText(http_data.get("gold_coin").toString());
				}
				if (http_data.containsKey("sorce")) {
					App.app.setData("sorce", http_data.get("sorce").toString());
					view.sorce.setText(http_data.get("sorce").toString());
				}
				if (http_data.containsKey("fans")) {
					App.app.setData("fans", http_data.get("fans").toString());
					view.fans.setText(http_data.get("fans").toString());
				}
				if (http_data.containsKey("favor")) {
					App.app.setData("favor", http_data.get("favor").toString());
					view.favor.setText(http_data.get("favor").toString());
				}
				if (http_data.containsKey("flower")) {
					App.app.setData("flower", http_data.get("flower").toString());
					view.flower.setText(http_data.get("flower").toString());
				}
				if (http_data.containsKey("avatar") && http_data.get("avatar").toString().trim().length() != 0) {
					ImageDownloader.getInstance().download(http_data.get("avatar").toString(), view.user_photo);
				}
			}
			break;
		case HttpUrl.EDIT_AVATAR_KEY:
			if (bottomPhotoDialog.isShowing()) {
				bottomPhotoDialog.dismiss();
			}
			if (!http_data.containsKey("status")) {
				return;
			}
			if (http_data.get("status").toString().equals("1")) {
				showToast("头像上传成功");
			} else {
				showToast(http_data.get("error").toString());
			}
			if (picture != null) {
				picture.delete();
				picture.deleteOnExit();
			}
			break;
		default:
			break;
		}
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.update_info:
			startActivity(new Intent(this, UpdateUserActivity.class));
			break;
		case R.id.user_photo:
			if (bottomPhotoDialog == null) {
				bottomPhotoDialog = new BottomPhotoDialog(UserActivity.this, R.layout.bottom_dialog_content, this);
			}
			if (!bottomPhotoDialog.isShowing()) {
				bottomPhotoDialog.show();
			}
			break;
		case R.id.camera:
			camera(getPhotoConfig());
			break;
		case R.id.photo:
			photo(getPhotoConfig());
			break;
		case R.id.cancle:
			bottomPhotoDialog.dismiss();
			break;
		default:
			break;
		}
	}

	private PhotoConfig getPhotoConfig() {
		PhotoConfig config = new PhotoConfig();
		config.aspectX = 1;
		config.aspectY = 1;
		config.outputX = 300;
		config.outputY = 300;
		return config;
	}

	@Override
	public void callBack(Object... args) {
		Toast.makeText(this, "图片路径：" + args[1], Toast.LENGTH_SHORT).show();
		view.user_photo.setImageBitmap((Bitmap) args[0]);
		upload_head(args[1].toString());
	}
}
