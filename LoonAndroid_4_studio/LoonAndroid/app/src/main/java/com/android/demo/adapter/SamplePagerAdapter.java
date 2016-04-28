package com.android.demo.adapter;

import java.io.IOException;
import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

import com.example.loonandroid2.R;

/*
* Author: Administrator Email:gdpancheng@gmail.com
* Created Date:2015年1月7日
* Copyright @ 2015 BU
* Description: 类描述
*
* History:
*/
public class SamplePagerAdapter extends PagerAdapter {

	@Override
	public int getCount() {
		return 6;
	}

	@Override
	public View instantiateItem(ViewGroup container, int position) {
		ImageView imageView = new ImageView(container.getContext());
		
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inPreferredConfig = Bitmap.Config.RGB_565;
		opt.inPurgeable = true;
		opt.inInputShareable = true;

		//获取资源图片

		InputStream is = container.getResources().openRawResource(R.drawable.wallpaper);
		Bitmap bitmap = BitmapFactory.decodeStream(is,null, opt);
		try {
	        is.close();
        } catch (IOException e) {
	        e.printStackTrace();
        }
		imageView.setImageBitmap(bitmap);
		// Now just add ImageView to ViewPager and return it
		container.addView(imageView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

		return imageView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}
}
