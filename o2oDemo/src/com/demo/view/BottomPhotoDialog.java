package com.demo.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.demo.xiamenhome.R;

public class BottomPhotoDialog extends Dialog {

	public BottomPhotoDialog(Context context, int content, View.OnClickListener l) {
		super(context, R.style.CustomDialogStyle);
		setContentView(content);
		Window dialogWindow = getWindow();
		WindowManager m = dialogWindow.getWindowManager();
		Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用
		WindowManager.LayoutParams p = getWindow().getAttributes(); // 获取对话框当前的参数值
		double rote = d.getWidth() / 480.0;
		p.width = (int) (480 * rote);
		dialogWindow.setAttributes(p);
		setContentView(content);
		findViewById(R.id.photo).setOnClickListener(l);
		findViewById(R.id.camera).setOnClickListener(l);
		findViewById(R.id.cancle).setOnClickListener(l);
	}

}
