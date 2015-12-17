package com.demo.adapter;

import java.util.HashMap;

import android.graphics.Paint;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bluemobi.config.Configuration;
import com.bluemobi.imageload.AsynImageView;
import com.bluemobi.imageload.ImageDownloader;
import com.demo.xiamenhome.R;
import com.loonandroid.pc.adapter.ViewHolder;
import com.loonandroid.pc.interfaces.LoonAdapter;

/**
 * 
 * 当前为自动注入的适配器<br>
 * <h1>以下为需要注意的</h1> 写法分为两种<br>
 * 1 CustomAdapter 实现LoonAdapter接口，并声明LoonAdapter接口的泛型 该泛型为你绑定到该Item的来源数据的类型<br>
 * 2 如果你不实现LoonAdapter接口，当这个适配器注入的时候 也会自动实现LoonAdapter接口， 那么你在使用的时候，就必须强转<br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2014年12月15日 上午10:30:12
 */
public abstract class GoodsDetailAdapter extends BaseAdapter implements LoonAdapter<HashMap<String, Object>> {

	View.OnClickListener l;
	
	@Override
	public boolean dealView(HashMap<String, Object> arg0, ViewHolder arg1) {
		AsynImageView photo = arg1.getView(R.id.photo);
		TextView suppliername =  arg1.getView(R.id.suppliername);
		TextView saleprice =  arg1.getView(R.id.saleprice);
		TextView prices = arg1.getView(R.id.prices);
		prices.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);
		TextView name = (TextView)  arg1.getView(R.id.name);
		if (arg0.containsKey("name")) {
			name.setText(arg0.get("name").toString());
		}
		if (arg0.containsKey("suppliername")) {
			suppliername.setText(arg0.get("suppliername").toString());
		}
		if (arg0.containsKey("price")) {
			prices.setText(arg0.get("price").toString());
		}
		if (arg0.containsKey("saleprice")) {
			saleprice.setText(arg0.get("saleprice").toString());
		}
		if (arg0.get("logo").toString().length()!=0) {
			Configuration configuration = new Configuration();
			configuration.setListView(true);
			configuration.setPostion(arg1.getPosition());
			configuration.setMax_width(207);
			configuration.setMax_height(150);
			ImageDownloader.getInstance().download(arg0.get("logo").toString(), photo, configuration);
        }
		return true;
	}

	public void setL(View.OnClickListener l) {
		this.l = l;
	}

}
