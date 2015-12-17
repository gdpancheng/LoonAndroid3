package com.android.demo.activity;

import android.view.View;
import android.widget.Toast;

import com.example.loonandroid2.R;
import com.loonandroid.pc.annotation.InLayer;
import com.loonandroid.pc.annotation.InListener;
import com.loonandroid.pc.listener.OnClick;

/**
 * InPLayer基类注解范例<br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * ----------------------------------------------- 
 * 
 * @author gdpancheng@gmail.com 2014年12月9日 下午5:34:30
 */
@InLayer(value = R.layout.activity_inplayer, parent = R.id.common)
public class InPLayerActivity extends CommonActivity {

	// --------------------------------------------------------------------------------------------
	// 1 InPLayer主要是用在基（父）类上
	// 2 InLayer中的当参数1个以上的时候，就需要些value，否则可以直接@InLayer(R.layout.activity_inlayer)
	// 3 InLayer中的parent所表示的id必须在当前父类InPLayer的布局中存在
	// 4 InPLayer主要是和InLayer配套使用
	// 5 InPLayer多用于通用上下导航或者模块
	
	// --------------------------------------------------------------------------------------------
	//当前activity加载的时候，会先检查当前activity的父类是否含有InPLayer
	//如果存在InPLayer，那么会把InPLayer中的值setContentView
	//子类读取@InLayer的parent，如果子类设置的ID在父类布局中存在，把InLayer中的
	//value添加（addview）到父布局中去
	
	
	/**
	 * 对基类中通用模块的view进行事件绑定，如果需要在子类中单独处理
	 * 则在子类中覆盖即可 注意其必须为private
	 * 其中ids是需要绑定事件的View的ID，后面为事件类型
	 * TODO(这里用一句话描述这个方法的作用)
	 * @author gdpancheng@gmail.com 2015年11月6日 下午6:00:52
	 * @param view
	 * @return void
	 */
	@InListener(ids={R.id.top},listeners={OnClick.class})
	private void click(View view){
		Toast.makeText(this, "子类中点击了", Toast.LENGTH_SHORT).show();
	}
}
