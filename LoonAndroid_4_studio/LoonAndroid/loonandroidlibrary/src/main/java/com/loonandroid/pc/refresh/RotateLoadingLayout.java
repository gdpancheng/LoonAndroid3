/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.loonandroid.pc.refresh;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView.ScaleType;

import com.loonandroid.pc.handler.Handler_Bitmap;
import com.loonandroid.pc.handler.Handler_System;
import com.loonandroid.pc.refresh.PullToRefreshBase.Mode;
import com.loonandroid.pc.refresh.PullToRefreshBase.Orientation;
import com.loonandroid.pc.util.LoonConstant;

public class RotateLoadingLayout extends LoadingLayout {

	static final int ROTATION_ANIMATION_DURATION = 1200;

	private final Animation mRotateAnimation;
	private final Matrix mHeaderImageMatrix;

	private float mRotationPivotX, mRotationPivotY;

	private final boolean mRotateDrawableWhilePulling;

	private Bitmap ptr_rotate;

	public RotateLoadingLayout(Context context, Mode mode, Orientation scrollDirection) {
		super(context, mode, scrollDirection);

		mRotateDrawableWhilePulling = LoonConstant.pullToRefresh.ptrRotateDrawableWhilePulling;

		mHeaderImage.setScaleType(ScaleType.MATRIX);
		mHeaderImageMatrix = new Matrix();
		mHeaderImage.setImageMatrix(mHeaderImageMatrix);

		mRotateAnimation = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateAnimation.setInterpolator(ANIMATION_INTERPOLATOR);
		mRotateAnimation.setDuration(ROTATION_ANIMATION_DURATION);
		mRotateAnimation.setRepeatCount(Animation.INFINITE);
		mRotateAnimation.setRepeatMode(Animation.RESTART);
	}

	public void onLoadingDrawableSet(Drawable imageDrawable) {
		if (null != imageDrawable) {
			mRotationPivotX = Math.round(imageDrawable.getIntrinsicWidth() / 2f);
			mRotationPivotY = Math.round(imageDrawable.getIntrinsicHeight() / 2f);
		}
	}

	protected void onPullImpl(float scaleOfLayout) {
		float angle;
		if (mRotateDrawableWhilePulling) {
			angle = scaleOfLayout * 90f;
		} else {
			angle = Math.max(0f, Math.min(180f, scaleOfLayout * 360f - 180f));
		}

		mHeaderImageMatrix.setRotate(angle, mRotationPivotX, mRotationPivotY);
		mHeaderImage.setImageMatrix(mHeaderImageMatrix);
	}

	@Override
	protected void refreshingImpl() {
		mHeaderImage.startAnimation(mRotateAnimation);
	}

	@Override
	protected void resetImpl() {
		mHeaderImage.clearAnimation();
		resetImageRotation();
	}

	private void resetImageRotation() {
		if (null != mHeaderImageMatrix) {
			mHeaderImageMatrix.reset();
			mHeaderImage.setImageMatrix(mHeaderImageMatrix);
		}
	}

	@Override
	protected void pullToRefreshImpl() {
		// NO-OP
	}

	@Override
	protected void releaseToRefreshImpl() {
		// NO-OP
	}

	@Override
	protected Bitmap getDefaultDrawable() {
		if (ptr_rotate == null) {
			try {
				InputStream down = getResources().getAssets().open("rotate.png");
				ptr_rotate = BitmapFactory.decodeStream(down);
				float rote = Handler_System.getPadRoate();
				ptr_rotate = Handler_Bitmap.scaleImg(ptr_rotate, (int) (ptr_rotate.getWidth() * rote), (int) (ptr_rotate.getHeight() * rote));
			} catch (IOException e) {
				InputStream is=this.getClass().getResourceAsStream("/resource/rotate.png"); 
				ptr_rotate = BitmapFactory.decodeStream(is);
				float rote = Handler_System.getPadRoate();
				ptr_rotate = Handler_Bitmap.scaleImg(ptr_rotate, (int) (ptr_rotate.getWidth() * rote), (int) (ptr_rotate.getHeight() * rote));
			}
		}
		return ptr_rotate;
	}

	@Override
	protected void onDetachedFromWindow() {
	    super.onDetachedFromWindow();
	    if (ptr_rotate!=null&&!ptr_rotate.isRecycled()) {
	    	ptr_rotate.recycle();
	    	ptr_rotate = null;
        }
	}
}
