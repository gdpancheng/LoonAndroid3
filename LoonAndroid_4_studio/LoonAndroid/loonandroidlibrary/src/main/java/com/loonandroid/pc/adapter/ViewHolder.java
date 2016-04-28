package com.loonandroid.pc.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 感谢http://blog.csdn.net/lmj623565791/article/details/38902805这位兄弟的思想 <br>
 * ----------------------------------------------- <br>
 * oooO............... <br>
 * (....) ... Oooo... <br>
 * .\..(.....(.....)....... <br>
 * ..\_)..... )../........ <br>
 * .......... (_/.......... <br>
 * 微信 gdpancheng <br>
 * -----------------------------------------------
 * 
 * @author gdpancheng@gmail.com 2015年1月3日 下午11:36:48
 */
public class ViewHolder {

	private final SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;

	private ViewHolder(LayoutInflater layoutInflater, int layoutId, int position) {
		this.mPosition = position;
		this.mViews = new SparseArray<View>();
		mConvertView = layoutInflater.inflate(layoutId, null);
		// setTag
		mConvertView.setTag(this);
	}

	/**
	 * 拿到一个ViewHolder对象
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static ViewHolder get(LayoutInflater layoutInflater, View convertView, int layoutId, int position) {
		if (convertView == null) {
			return new ViewHolder(layoutInflater, layoutId, position);
		}
		ViewHolder holder = (ViewHolder) convertView.getTag();
		holder.setPosition(position);
		return holder;
	}

	/**
	 * 获得根view
	 * 
	 * @author gdpancheng@gmail.com 2015年1月3日 下午11:38:05
	 * @return View
	 */
	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * 通过控件的Id获取对于的控件，如果没有则加入views
	 * 
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	public int getPosition() {
		return mPosition;
	}

	public <T extends View> T setData(int viewId, Object data) {
		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		if (data == null) {
			return (T) view;
		}
		if (TextView.class.isAssignableFrom(view.getClass())) {
			((TextView) view).setText(data.toString());
		}
		if (ImageView.class.isAssignableFrom(view.getClass())) {
			if (Integer.class.isAssignableFrom(data.getClass()) || int.class.isAssignableFrom(data.getClass())) {
				((ImageView) view).setImageResource(Integer.valueOf(data.toString()));
			} else {
				// -----------------------------------------------------------------
				// 图片下载
			}
		}
		return (T) view;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 */
	public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}

	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageResource(int viewId, int drawableId) {
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);
		return this;
	}

	public void setPosition(int mPosition) {
		this.mPosition = mPosition;
	}
}
