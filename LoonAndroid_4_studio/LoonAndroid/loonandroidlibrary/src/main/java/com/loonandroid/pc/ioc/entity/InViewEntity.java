package com.loonandroid.pc.ioc.entity;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.renderscript.Type;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;

import com.loonandroid.pc.entity.CommonEntity;
import com.loonandroid.pc.inject.InjectExcutor;
import com.loonandroid.pc.interfaces.BeanFactory;
import com.loonandroid.pc.interfaces.LoonAdapter;
import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.ioc.IocAdapterHandler;
import com.loonandroid.pc.ioc.config.LoonConfig;
import com.loonandroid.pc.listener.OnClick;
import com.loonandroid.pc.listener.OnListener;
import com.loonandroid.pc.refresh.Pull;
import com.loonandroid.pc.refresh.PullToRefreshBase;
import com.loonandroid.pc.refresh.PullToRefreshExpandableListView;
import com.loonandroid.pc.refresh.PullToRefreshGridView;
import com.loonandroid.pc.refresh.PullToRefreshHorizontalScrollView;
import com.loonandroid.pc.refresh.PullToRefreshListView;
import com.loonandroid.pc.refresh.PullToRefreshScrollView;
import com.loonandroid.pc.refresh.PullToRefreshVerticalViewPager;
import com.loonandroid.pc.refresh.PullToRefreshViewPager;
import com.loonandroid.pc.refresh.PullToRefreshWebView;
import com.loonandroid.pc.refresh.VerticalViewPager;
import com.loonandroid.pc.refresh.PullToRefreshBase.Mode;
import com.loonandroid.pc.refresh.PullToRefreshBase.OnRefreshListener2;
import com.loonandroid.pc.util.LoonConstant;

/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-10
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */

/**
 * view注册
 * 
 * @author gdpancheng@gmail.com 2014-11-13 下午6:32:22
 */
public class InViewEntity extends Invoker implements InjectInvoker {

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 8135542867714978741L;

	private int id;
	private boolean pull;
	private boolean down;
	private String fieldName;
	private boolean isActivity;
	private int index = 0;
	private OnListener onListener;
	/** 当属于inall里面的对象的时候 那么pObject为点击事件所在的类的对象 **/
	private WeakReference<Object> pObject;
	/** adapter是否自动创建 **/
	private int item;
	/** 下拉刷新，上拉加载更多 **/
	transient private WeakReference<PullToRefreshBase> mPullRefreshView;
	private InVaEntity inVaEntity;
	/** 列表中是否自动填充数据 **/
	private boolean fill = false;

	// private HashMap<, InMethodEntity> PullRefresh;

	public InViewEntity(boolean isActivity) {
		this.isActivity = isActivity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> void invoke(T beanObject, Object... args) {
		InjectExcutor<T> injectExcutor;
		if (isActivity) {
			injectExcutor = LoonConstant.INJECT.get(LoonConstant.Key.ACTIVITY_KEY);
		} else {
			injectExcutor = LoonConstant.INJECT.get(LoonConstant.Key.VIEW_KEY);
		}
		if (this.object == null || this.object.get() == null) {
			Ioc.getIoc().getLogger().e("当前上下文已被回收");
			return;
		}
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			if (!View.class.isAssignableFrom(field.getType())) {
				Ioc.getIoc().getLogger().e(clazz.getSimpleName() + " 对象 " + fieldName + "不对  不是一个View对象 请检查\n");
				return;
			}
			View view = injectExcutor.findViewById(beanObject, id);
			if (view == null) {
				Ioc.getIoc().getLogger().e(clazz.getSimpleName() + " 对象 " + fieldName + " ID:" + id + "不对 无法查找到view 请检查\n");
				return;
			}
			// 上拉下拉（这里要改成适配多种 不一定只有AbsListView）
			if ((down || pull) && creatPullRefreshView(view)) {
				/** 设置下拉和上拉事件 **/
				mPullRefreshView.get().setOnRefreshListener(new OnRefreshListener2() {
					@Override
					public void onPullDownToRefresh(PullToRefreshBase refreshView) {
						Object from = (pObject != null ? pObject.get() : object.get());
						if (Activity.class.isAssignableFrom(from.getClass()) && ((Activity) from).isFinishing()) {
							pObject = null;
							object = null;
							Ioc.getIoc().getLogger().d("下拉刷新回掉失败，当前上下文已经被销毁");
							return;
						}
						String label = DateUtils.formatDateTime(refreshView.getContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
						String name = (pObject != null ? pObject.get() : object.get()).getClass().getName();
						if (name.endsWith("_Proxy")) {
							name = (pObject != null ? pObject.get() : object.get()).getClass().getSuperclass().getName();
						}

						CommonEntity entity = Ioc.getIoc().getAnalysisEntity(name);
						if (entity != null && entity.getPullRefresh() != null) {
							InPullRefreshEntity refreshEntity = entity.getPullRefresh();
							Object[] params = new Object[2];
							params[0] = refreshView;
							params[1] = Pull.DOWN;
							refreshEntity.invoke((pObject != null ? pObject.get() : object.get()), params);
						}
					}

					@Override
					public void onPullUpToRefresh(PullToRefreshBase refreshView) {
						Object from = (pObject != null ? pObject.get() : object.get());
						if (Activity.class.isAssignableFrom(from.getClass()) && ((Activity) from).isFinishing()) {
							pObject = null;
							object = null;
							Ioc.getIoc().getLogger().d("下拉刷新回掉失败，当前上下文已经被销毁");
							return;
						}
						String label = DateUtils.formatDateTime(refreshView.getContext(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
						refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
						String name = (pObject != null ? pObject.get() : object.get()).getClass().getName();
						if (name.endsWith("_Proxy")) {
							name = (pObject != null ? pObject.get() : object.get()).getClass().getSuperclass().getName();
						}

						CommonEntity entity = Ioc.getIoc().getAnalysisEntity(name);
						if (entity != null && entity.getPullRefresh() != null) {
							InPullRefreshEntity refreshEntity = entity.getPullRefresh();
							Object[] params = new Object[2];
							params[0] = refreshView;
							params[1] = Pull.UP;
							refreshEntity.invoke((pObject != null ? pObject.get() : object.get()), params);
						}
					}
				});
				// 这里需要配置
				// mPullRefreshView.setScrollingWhileRefreshingEnabled(true);
				replaceView(view, mPullRefreshView.get());
				view = mPullRefreshView.get().getRefreshableView();
				
				if (onListener != null) {
					onListener.listener(mPullRefreshView.get(), pObject != null ? pObject.get() : object.get());
				}
			}
			// --------------------------------------------------------------------------------------
			// 当时listview的时候 禁用onclick事件
			if (onListener != null && !(AdapterView.class.isAssignableFrom(view.getClass()) && OnClick.class.getName().equals(onListener.getListener().getName()))) {
				onListener.listener(view, pObject != null ? pObject.get() : object.get());
			}

			if (AdapterView.class.isAssignableFrom(view.getClass()) && LoonConfig.instance().isDepend()) {
				if (item != LoonConstant.Number.ID_NONE) {
					IocAdapterHandler invocationHandler = new IocAdapterHandler();
					invocationHandler.setInflater(LayoutInflater.from(view.getContext()));
					invocationHandler.setId(item);
					BaseAdapter adapter = BeanFactory.instance(BaseAdapter.class, new Class[] { LoonAdapter.class }, invocationHandler);
					((AdapterView) view).setAdapter(adapter);
				}
			}
			/** 注入 **/
			field.set(object.get(), view);

			if (null != mPullRefreshView && mPullRefreshView.get() != null) {
				mPullRefreshView.get().setAuto(true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Ioc.getIoc().getLogger().e(clazz.getSimpleName() + " 对象出现错误 请检查\n");
		}
		if (mPullRefreshView == null) {
			// 当没有上拉刷新和下拉加载更多的时候，释放对于context的占用
			this.object = null;
		}
	}

	@SuppressWarnings("unchecked")
	public <T> View getView(T beanObject) {
		InjectExcutor<T> injectExcutor;
		if (isActivity) {
			injectExcutor = LoonConstant.INJECT.get(LoonConstant.Key.ACTIVITY_KEY);
		} else {
			injectExcutor = LoonConstant.INJECT.get(LoonConstant.Key.VIEW_KEY);
		}
		if (this.object == null || this.object.get() == null) {
			Ioc.getIoc().getLogger().e("当前上下文已被回收");
			return null;
		}

		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			if (!View.class.isAssignableFrom(field.getType())) {
				Ioc.getIoc().getLogger().e(clazz.getSimpleName() + " 对象 " + fieldName + "不对  不是一个View对象 请检查\n");
				return null;
			}
			View view = injectExcutor.findViewById(beanObject, id);
			if (view == null) {
				Ioc.getIoc().getLogger().e(clazz.getSimpleName() + " 对象 " + fieldName + " ID:" + id + "不对 无法查找到view 请检查\n");
				return null;
			}
			return view;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private boolean creatPullRefreshView(View target) {
		Class<? extends View> clazz = target.getClass();
		if (ListView.class.isAssignableFrom(clazz)) {
			mPullRefreshView = new WeakReference<PullToRefreshBase>(new PullToRefreshListView(target.getContext()));
		} else if (ExpandableListView.class.isAssignableFrom(clazz)) {
			mPullRefreshView = new WeakReference<PullToRefreshBase>(new PullToRefreshExpandableListView(target.getContext()));
		} else if (HorizontalScrollView.class.isAssignableFrom(clazz)) {
			mPullRefreshView = new WeakReference<PullToRefreshBase>(new PullToRefreshHorizontalScrollView(target.getContext()));
		} else if (GridView.class.isAssignableFrom(clazz)) {
			mPullRefreshView = new WeakReference<PullToRefreshBase>(new PullToRefreshGridView(target.getContext()));
		} else if (VerticalViewPager.class.isAssignableFrom(clazz)) {
			mPullRefreshView = new WeakReference<PullToRefreshBase>(new PullToRefreshVerticalViewPager(target.getContext()));
		} else if (ViewPager.class.isAssignableFrom(clazz)) {
			mPullRefreshView = new WeakReference<PullToRefreshBase>(new PullToRefreshViewPager(target.getContext()));
		} else if (WebView.class.isAssignableFrom(clazz)) {
			// if (VERSION.SDK_INT >= VERSION_CODES.GINGERBREAD) {
			mPullRefreshView = new WeakReference<PullToRefreshBase>(new PullToRefreshWebView(target.getContext()));
			// } else {
			// mPullRefreshView = new PullToRefreshWebView2(target.getContext());
			// }
		} else if (ScrollView.class.isAssignableFrom(clazz)) {
			mPullRefreshView = new WeakReference<PullToRefreshBase>(new PullToRefreshScrollView(target.getContext()));
		}
		if (mPullRefreshView == null) {
			return false;
		}

		if (pull && !down) {
			mPullRefreshView.get().setMode(Mode.PULL_FROM_START);
		} else if (!pull && down) {
			mPullRefreshView.get().setMode(Mode.PULL_FROM_END);
		} else if (pull && down) {
			mPullRefreshView.get().setMode(Mode.BOTH);
		}
		return true;
	}

	private void replaceView(View target, PullToRefreshBase view) {
		LayoutParams lp = target.getLayoutParams();
		ViewGroup group = (ViewGroup) target.getParent();
		int index = group.indexOfChild(target);
		if (ScrollView.class.isAssignableFrom(target.getClass()) || HorizontalScrollView.class.isAssignableFrom(target.getClass())) {
			int count = ((FrameLayout) target).getChildCount();
			for (int i = 0; i < count; i++) {
				View children = ((FrameLayout) target).getChildAt(i);
				((FrameLayout) target).removeView(children);
				((FrameLayout) view.getRefreshableView()).addView(children, i);
			}
		}
		group.removeView(target);
		target.setLayoutParams(new LinearLayout.LayoutParams(lp.width, lp.height));
		group.addView(view, index, lp);

		target.setBackgroundColor(Color.RED);
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPull(boolean pull) {
		this.pull = pull;
	}

	public void setDown(boolean down) {
		this.down = down;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setActivity(boolean isActivity) {
		this.isActivity = isActivity;
	}

	public int getId() {
		return id;
	}

	public boolean isPull() {
		return pull;
	}

	public boolean isDown() {
		return down;
	}

	public String getFieldName() {
		return fieldName;
	}

	public boolean isActivity() {
		return isActivity;
	}

	public Object getPobject() {
		if (pObject == null) {
			return null;
		}
		return pObject.get();
	}

	public void setPobject(Object pObject) {
		this.pObject = new WeakReference<Object>(pObject);
	}

	public OnListener getOnListener() {
		return onListener;
	}

	public void setOnListener(OnListener onListener) {
		this.onListener = onListener;
	}

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}

	public InVaEntity getInVaEntity() {
		return inVaEntity;
	}

	public void setInVaEntity(InVaEntity inVaEntity) {
		this.inVaEntity = inVaEntity;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return "InViewEntity [id=" + id + ", pull=" + pull + ", down=" + down + ", fieldName=" + fieldName + ", isActivity=" + isActivity + ", onListener=" + onListener + ", pObject=" + pObject + ", item=" + item + ", inVaEntity=" + inVaEntity + ", fill=" + fill + "]";
	}

}
