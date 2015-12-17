package com.loonandroid.pc.plug.photo;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Method;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.loonandroid.pc.handler.Handler_File;
import com.loonandroid.pc.handler.Handler_System;
import com.loonandroid.pc.plug.PlugConstants;
import com.loonandroid.pc.plug.PlugInCallBack;

/*
 * Author: pancheng Email:gdpancheng@gmail.com
 * Created Date:2015年11月6日
 * Copyright @ 2015 BU
 * Description: 类描述
 *
 * History:
 */
public class PhotoCallBack implements PlugInCallBack {

	private Uri uri;
	private String fileName;
	private String path;
	private PhotoConfig config;
	/** 相册请求参数 */
	private static final int PHOTO_PICKED_REQUEST_DATA = 1011;
	/** 相机请求参数 */
	private static final int CAMERA_REQUEST_DATA = 1012;
	/** 图片编辑请求参数 */
	private static final int PIC_EDIT_REQUEST_DATA = 1013;

	@Override
	public Object callback(Object object, Method method, Object[] args) {
		if (method.getName().equals("camera")) {
			if (!Handler_System.checkStorageStatus()) {
				Toast.makeText((Context) object, "SD卡不存在", Toast.LENGTH_SHORT).show();
				return PlugConstants.INTERCEPT_YES;
			}

			if (args!=null&&args[0]!=null) {
				PhotoConfig[] configs = (PhotoConfig[]) args[0];
				if (configs.length>0) {
					config = configs[0];
                }
            }
			// 下面这句指定调用相机拍照后的照片存储的路径
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			fileName = System.currentTimeMillis() + ".jpg";
			// 判断存储照片的文件夹目录是否存在，如果不存在就创建该目录
			File pathdir = new File(new File(Environment.getExternalStorageDirectory(), "DCIM"), "Camera");
			if (!pathdir.exists()) {
				pathdir.mkdirs();
			}
			File photoFile = new File(pathdir, fileName);
			fileName = photoFile.getAbsolutePath();
			// 指定调用相机拍照后的照片存储的路径
			uri = Uri.fromFile(photoFile);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
			((Activity) object).startActivityForResult(intent, CAMERA_REQUEST_DATA);
			return PlugConstants.INTERCEPT_YES;
		}
		if (method.getName().equals("photo")) {
			if (args!=null&&args[0]!=null) {
				PhotoConfig[] configs = (PhotoConfig[]) args[0];
				if (configs.length>0) {
					config = configs[0];
                }
            }
			Intent intentx = new Intent(Intent.ACTION_PICK, null);
			intentx.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
			((Activity) object).startActivityForResult(intentx, PHOTO_PICKED_REQUEST_DATA);
			return PlugConstants.INTERCEPT_YES;
		}
		if (method.getName().equals("onActivityResult")) {
			if (null != args && args.length == 3) {
				int requestCode = Integer.valueOf(args[0].toString());
				Intent data = (Intent) args[2];
				switch (requestCode) {
				// 相机拍照
				case CAMERA_REQUEST_DATA:
					startPhotoZoom(((Activity) object), uri);
					return PlugConstants.INTERCEPT_YES;
					// 相册获取
				case PHOTO_PICKED_REQUEST_DATA:
					if (null == data) {
						Toast.makeText((Context) object, "图片获取失败", Toast.LENGTH_SHORT).show();
						return PlugConstants.INTERCEPT_YES;
					}
					fileName = Handler_System.getFilePath(((Activity) object), data.getData());
					startPhotoZoom(((Activity) object), data.getData());
					return PlugConstants.INTERCEPT_YES;
					// 裁剪图片
				case PIC_EDIT_REQUEST_DATA:
					if (null == data) {
						Toast.makeText((Context) object, "图片裁剪失败", Toast.LENGTH_SHORT).show();
						return PlugConstants.INTERCEPT_YES;
					}
					((PluginPhoto) object).callBack(decodeUriAsBitmap((Context) object, Uri.fromFile(new File(path))), path);
					return PlugConstants.INTERCEPT_YES;
				default:
					return PlugConstants.INTERCEPT_NO;
				}
			}
			return PlugConstants.INTERCEPT_YES;
		}
		return PlugConstants.INTERCEPT_YES;
	}

	public void startPhotoZoom(Activity activity, Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 200);
		intent.putExtra("outputY", 200);
		intent.putExtra("scale", true);
		intent.putExtra("return-data", false);
		File file = new File(Handler_File.getExternalCacheDir(activity, "crop"), System.currentTimeMillis() + ".jpg");
		path = file.getPath();
		Uri fileUri = Uri.fromFile(file);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
		if (config!=null) {
			intent.putExtra("aspectX", config.aspectX);
			intent.putExtra("aspectY", config.aspectY);
			// outputX outputY 是裁剪图片宽高
			intent.putExtra("outputX", config.outputX);
			intent.putExtra("outputY", config.outputY);
			intent.putExtra("scale", config.scale);
			if (config.path!=null) {
				if (new File(config.path).getParentFile().exists()) {
					file = new File(config.path);
                }
				path = file.getPath();
				fileUri = Uri.fromFile(file);
				intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
            }
        }
		intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		activity.startActivityForResult(intent, PIC_EDIT_REQUEST_DATA);
	}

	private Bitmap decodeUriAsBitmap(Context context, Uri uri) {
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return bitmap;
	}
}
