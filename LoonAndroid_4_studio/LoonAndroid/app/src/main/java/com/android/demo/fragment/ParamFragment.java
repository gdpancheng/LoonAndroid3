package com.android.demo.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InListener;
import com.loonandroid.pc.annotation.Init;
import com.loonandroid.pc.listener.OnClick;

/*
 * Author: pan Email:gdpancheng@gmail.com
 * Created Date:2013-8-10
 * Copyright @ 2013 BU
 * Description: 类描述
 *
 * History:
 */

@InLayer(R.layout.activity_com)
public class ParamFragment extends Fragment {
	
	public String aa;
	
	public ParamFragment(String aa) {
		this.aa = aa;
    }
	
	@Init
	 void init(){
		System.out.println("fragment 初始化完毕"+aa);
	}
	
	/**
	 * 对view的view进行事件绑定
	 * 其中ids是需要绑定事件的View的ID，后面为事件类型
	 * TODO(这里用一句话描述这个方法的作用)
	 * @author gdpancheng@gmail.com 2015年11月6日 下午6:00:52
	 * @param view
	 * @return void
	 */
	@InListener(ids={R.id.top,R.id.bottom},listeners={OnClick.class})
	private void l(View view){
		Toast.makeText(view.getContext(), "父类中点击了", Toast.LENGTH_SHORT).show();
	}
}
