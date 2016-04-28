package com.loonandroid.pc.ioc.entity;

import android.app.Activity;
import android.view.View;

import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.listener.OnListener;
/*
 * Author: Administrator Email:gdpancheng@gmail.com
 * Created Date:2014-11-10
 * Copyright @ 2014 BU
 * Description: 类描述
 *
 * History:
 */

/**
 * 初始化方法
 * @author gdpancheng@gmail.com 2014-11-13 下午6:26:33
 */
public class InMethodEntity extends Invoker implements InjectInvoker{

	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
    private static final long serialVersionUID = -5586039487822267824L;
    
	private OnListener[] onListener;
	private int[] ids;

	public OnListener[] getOnListener() {
		return onListener;
	}

	public void setOnListener(OnListener[] onListener) {
		this.onListener = onListener;
	}

	public int[] getIds() {
		return ids;
	}

	public void setIds(int[] ids) {
		this.ids = ids;
	}
	
	@Override
	public <T> void invoke(T beanObject, Object... args) {
		if (ids == null||beanObject==null) {
	        return;
        }
		if (this.object == null||this.object.get()==null) {
            Ioc.getIoc().getLogger().e("当前上下文已被回收");
            return;
        }
		 //得先判断当前上下文是activity 还是view
        	for (int id : ids) {
        		View view;
        		if (Activity.class.isAssignableFrom(beanObject.getClass())) {
        			view = ((Activity) beanObject).findViewById(id);
        		} else {
        			view = ((View) beanObject).findViewById(id);
        		}
        		if (view !=null) {
	                for (OnListener listener : onListener) {
	                	listener.listener(view, object.get());
                    }
                }
            }
        	this.object = null;
	}
}
