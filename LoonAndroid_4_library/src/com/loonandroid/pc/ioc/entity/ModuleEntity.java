package com.loonandroid.pc.ioc.entity;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.loonandroid.pc.core.AnalysisManager;
import com.loonandroid.pc.interfaces.BeanFactory;
import com.loonandroid.pc.interfaces.LoonModule;
import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.ioc.IocModuleHandler;

/**
 * 组件对象类，实现了组件注入过程
 * 	<br>-----------------------------------------------
 * <br>oooO...............
 * <br>(....) ... Oooo...
 * <br>.\..(.....(.....).......
 * <br>..\_)..... )../........
 * <br>.......... (_/..........
 * <br>微信 gdpancheng
 * <br>-----------------------------------------------
 * @author <a href="mailto:gdpancheng@gmail.com">gdpancheng@gmail.com</a> 2014年12月21日 上午12:24:37
 */
public class ModuleEntity extends Invoker implements InjectInvoker {


	/**
	 * @Fields serialVersionUID : TODO(用一句话描述这个变量表示什么)
	 */
	private static final long serialVersionUID = 5164082430241717128L;
	private int id;
	private int parentId;
	private Class<?> fieldClass;
	private String filedName;
	private boolean isInit = false;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public Class getFieldClass() {
		return fieldClass;
	}

	public void setFieldClass(Class fieldClass) {
		this.fieldClass = fieldClass;
	}

	public String getFiledName() {
		return filedName;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	public boolean isInit() {
		return isInit;
	}

	public void setInit(boolean isInit) {
		this.isInit = isInit;
	}
	@Override
	public <T> void invoke(T beanObject, Object... args) {
		if (clazz == null) {
			return;
		}
		if (this.object == null||this.object.get()==null) {
            Ioc.getIoc().getLogger().e("当前上下文已被回收");
            return;
        }
		try {
	        Field field = object.get().getClass().getDeclaredField(this.filedName);
	        field.setAccessible(true);
	        if (field.get(object.get())!=null) {
	        	if (isInit) {
	        		LoonModule loonModule = (LoonModule) field.get(object.get());
	        		AnalysisManager.analysisModule(loonModule.getView(), loonModule,true);
                }
	            return;
	        }
	        
	        //得先判断当前上下文是activity 还是view
	        boolean isAcitivity = true;
	        Context context = null;
	        if (Activity.class.isAssignableFrom(beanObject.getClass())) {
	        	context = ((Context) beanObject);
	        	isAcitivity = true;
	        } else {
	        	context = ((View) beanObject).getContext();
	        	isAcitivity = false;
	        }

	        //----------------------------------------------------------------------------
	        //开始创建代理进行拦截了
	        IocModuleHandler moduleHandler = new IocModuleHandler();
	        LayoutInflater inflater = LayoutInflater.from(context);
	        LinearLayout frame = new LinearLayout(context);
	        View view = inflater.inflate(id, null);
	        moduleHandler.setView(frame);
            Object module = BeanFactory.instance(fieldClass, new Class[]{LoonModule.class}, moduleHandler);
	        LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	        frame.addView(view, params);
	        AnalysisManager.analysisModule(view, module,false);
	        field.set(object.get(), module);
	        ViewGroup group = null;
	        if (parentId != 0 && isAcitivity) {
	        	group = (ViewGroup) ((Activity) beanObject).findViewById(parentId);
	        } else if (parentId != 0 && !isAcitivity) {
	        	group = (ViewGroup) ((View) beanObject).findViewById(parentId);
	        }
	        //----------------------------------------------------------------------------
	        //把组件添加到父布局中
	        if (null != group) {
	        	if (LinearLayout.class.isAssignableFrom(group.getClass())) {
					LinearLayout.LayoutParams layout =new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
					group.addView(frame,layout);
	            }
				if (RelativeLayout.class.isAssignableFrom(group.getClass())) {
					RelativeLayout.LayoutParams layout =new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
					group.addView(frame,layout);
				}
				if (AbsoluteLayout.class.isAssignableFrom(group.getClass())) {
					AbsoluteLayout.LayoutParams layout =new AbsoluteLayout.LayoutParams(AbsoluteLayout.LayoutParams.FILL_PARENT, AbsoluteLayout.LayoutParams.FILL_PARENT, 0, 0);
					group.addView(frame,layout);
	            }
				if (FrameLayout.class.isAssignableFrom(group.getClass())) {
					FrameLayout.LayoutParams layout =new FrameLayout.LayoutParams(FrameLayout.LayoutParams.FILL_PARENT, FrameLayout.LayoutParams.FILL_PARENT);
					group.addView(frame,layout);
	            }
	        }
        } catch (Exception e) {
	        e.printStackTrace();
        } 
		this.object = null;
	}
}
