package com.android.demo.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InAll;
import com.loonandroid.pc.annotation.InBinder;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.listener.OnClick;
import com.loonandroid.pc.plug.photo.PhotoConfig;
import com.loonandroid.pc.plug.photo.PluginPhoto;

/**
 * 获取照片 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * @author gdpancheng@gmail.com 2014年12月14日 下午11:26:47
 */

@InLayer(R.layout.activity_getphoto)
public abstract class GetPhotoActivity extends Activity implements PluginPhoto {

	@InAll
	Views test;

	class Views {
		ImageView iv_photo;
		@InBinder(listener = OnClick.class, method = "click")
		Button bt_photo, bt_camera;
	}

    private void click(View v) {
		switch (v.getId()) {
		case R.id.bt_photo:
			//从相册获取图片
			PhotoConfig config = new PhotoConfig();
			config.aspectX = 1;
			config.aspectY = 2;
			config.outputX = 200;
			config.outputY = 400;
			photo(config);
			break;
		case R.id.bt_camera:
			//从相机获取图片
			camera();
			break;
		}
	}

	@Override
	public void callBack(Object... args) {
		Toast.makeText(this, "图片路径："+args[1], Toast.LENGTH_SHORT).show();
		System.out.println("-----------------------------");
		test.iv_photo.setImageBitmap((Bitmap)args[0]);
	}
}
