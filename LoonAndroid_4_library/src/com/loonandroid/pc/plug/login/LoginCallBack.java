package com.loonandroid.pc.plug.login;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.dexmaker.ProxyBuilder;
import com.loonandroid.pc.handler.Handler_File;
import com.loonandroid.pc.ioc.Ioc;
import com.loonandroid.pc.listener.PlugOnClick;
import com.loonandroid.pc.plug.PlugInCallBack;
import com.loonandroid.pc.validator.Regex;

/*
 * Author: pancheng Email:gdpancheng@gmail.com
 * Created Date:2015年11月6日
 * Copyright @ 2015 BU
 * Description: 类描述
 * History:
 */
public class LoginCallBack implements PlugInCallBack {

	private WeakReference<EditText> et_name;
	private WeakReference<EditText> et_password;
	private WeakReference<CheckBox> et_check;
	private WeakReference<View> et_button;

	@Override
	public Object callback(Object object, Method method, Object[] args) {

		String name = method.getName();

		if (name.equals("getSave")) {
			AccountEntity infoEntity = Handler_File.getObject("account");
			if (infoEntity == null) {
				infoEntity = new AccountEntity();
			}
			return infoEntity;
		}

		if (name.equals("i")) {
			try {
				ProxyBuilder.callSuper(object, method, args);

				// ------------------------------------------------------------------
				// 这里获取保存的用户名密码
				AccountEntity datas = ((PluginLogin) object).getSave();
				LoginConfig config = (LoginConfig) args[0];
				if (Activity.class.isAssignableFrom(object.getClass())) {
					et_name = new WeakReference<EditText>((EditText) ((Activity) object).findViewById(config.etNId));
					et_password = new WeakReference<EditText>((EditText) ((Activity) object).findViewById(config.etPId));
					et_check = new WeakReference<CheckBox>((CheckBox) ((Activity) object).findViewById(config.etRmID));
					et_button = new WeakReference<View>(((Activity) object).findViewById(config.etLgId));
					// ----------------------------------------------------------------------------
					// 判断是否存在保存用户名和密码 是否保存密码
					if (datas.isSave()) {
						ArrayList<HashMap<String, String>> entity = datas.getAccountLists();
						int count = entity.size();
						if (count != 0) {
							HashMap<String, String> info = entity.get(count - 1);
							if (et_name.get() != null) {
								et_name.get().setText(info.containsKey(AccountEntity.NAME) ? info.get(AccountEntity.NAME) : "");
							}
							if (et_password.get() != null) {
								et_password.get().setText(info.containsKey(AccountEntity.PASSWORD) ? info.get(AccountEntity.PASSWORD) : "");
							}
						}
						if (et_check.get() != null) {
							et_check.get().setChecked(true);
						}
					}

					if (et_button.get() != null) {
						PlugOnClick click = new PlugOnClick();
						click.setClazz(object.getClass());
						click.setMethod("callBack");
						click.listener(et_button.get(), object);
					}
				} else {
					Ioc.getIoc().getLogger().e("登录组件的i方法未实现");
					return null;
				}
			} catch (Throwable e) {
				
				Ioc.getIoc().getLogger().e("登录组件的i方法实现错误"+e.getMessage());
			}
		}

		if (name.equals("callBack")) {
			if (args.length == 0) {
				return null;
			}
			if (!View[].class.isAssignableFrom(args[0].getClass())) {
				return null;
			}

			View[] views = (View[]) args[0];

			if (et_button.get() != null) {
				for (int i = 0; i < views.length; i++) {
					View view = views[i];
					if (view.getId() == et_button.get().getId()) {
						// ------------------------------------------------------------------------
						// 这里开始验证用户名 密码长度是否为空 等等
						if (et_name!=null||et_name.get()!=null) {
							if (Regex.StrisNull(et_name.get().getText().toString())) {
								((PluginLogin) object).onValiResult(et_name.get());
								break;
                            }
                        }
						if (et_password!=null||et_password.get()!=null) {
							if (Regex.StrisNull(et_password.get().getText().toString())) {
								((PluginLogin) object).onValiResult(et_password.get());
								break;
                            }
                        }
						((PluginLogin) object).onValiResult(null);
						break;
					}
				}
			}
		}

		if (name.equals("save") && et_name.get() != null && et_password.get() != null) {
			AccountEntity infoEntity = Handler_File.getObject("account");
			if (infoEntity == null) {
				infoEntity = new AccountEntity();
			}
			infoEntity.add(et_name.get().getText().toString().trim(), et_password.get().getText().toString().trim());

			infoEntity.setSave(true);
			Handler_File.setObject("account", infoEntity);
		}

		if (name.equals("clear")) {
			for (Object param : args) {
				String[] params = (String[]) param;
				if (params.length == 0) {
					// 参数为0 说明清空所有数据
					Handler_File.setObject("account", null);
					return null;
				}
			}

			AccountEntity infoEntity = Handler_File.getObject("account");
			for (Object param : args) {
				String[] params = (String[]) param;
				for (String key : params) {
					infoEntity.removeByAccount(key);
				}
				Handler_File.setObject("account", infoEntity);
			}
		}

		return null;
	}
}
