package com.loonandroid.pc.plug.photo;

import com.loonandroid.pc.plug.PlugEntity;
import com.loonandroid.pc.plug.PlugParameter;
import com.loonandroid.pc.plug.login.PluginLogin;

/*
* Author: pancheng Email:gdpancheng@gmail.com
* Created Date:2015年11月6日
* Copyright @ 2015 BU
* Description: 类描述
*
* History:
*/
public class PhotoParameter implements PlugParameter {

	@Override
    public PlugEntity getEntity() {
		PlugEntity plug = new PlugEntity();
		plug.setClazz(PluginPhoto.class);
		plug.addMethodName("camera");
		plug.addMethodName("photo");
		plug.addMethodName("onActivityResult");
		plug.setCallBack(new PhotoCallBack());
	    return plug;
    }
}
