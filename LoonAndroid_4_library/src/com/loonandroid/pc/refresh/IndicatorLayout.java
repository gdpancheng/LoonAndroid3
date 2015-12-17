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

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.loonandroid.pc.handler.Handler_Animation;
import com.loonandroid.pc.handler.Handler_Bitmap;
import com.loonandroid.pc.handler.Handler_System;
import com.loonandroid.pc.refresh.PullToRefreshBase;
import com.loonandroid.pc.util.LoonConstant;

@SuppressLint("ViewConstructor")
public class IndicatorLayout extends FrameLayout implements AnimationListener {

	static final int DEFAULT_ROTATION_ANIMATION_DURATION = 150;

	private Animation mInAnim, mOutAnim;
	private ImageView mArrowImageView;

	private final Animation mRotateAnimation, mResetRotateAnimation;
	private Bitmap upBitmap;
	
	public IndicatorLayout(Context context, PullToRefreshBase.Mode mode) {
		super(context);
		mArrowImageView = new ImageView(context);
		if (upBitmap==null) {
			try {
				InputStream update = getResources().getAssets().open("update.png");
				upBitmap = BitmapFactory.decodeStream(update);
				float rote = Handler_System.getPadRoate();
				upBitmap = Handler_Bitmap.scaleImg(upBitmap, (int) (upBitmap.getWidth() * rote), (int) (upBitmap.getHeight() * rote));
			} catch (IOException e) {
				InputStream is=this.getClass().getResourceAsStream("/resource/update.png"); 
				upBitmap = BitmapFactory.decodeStream(is);
				float rote = Handler_System.getPadRoate();
				upBitmap = Handler_Bitmap.scaleImg(upBitmap, (int) (upBitmap.getWidth() * rote), (int) (upBitmap.getHeight() * rote));
			}
        }

		mArrowImageView.setImageBitmap(upBitmap);

		final int padding = Handler_System.dip2px(LoonConstant.pullToRefresh.indicator_internal_padding);
		mArrowImageView.setPadding(padding, padding, padding, padding);
		addView(mArrowImageView);

		switch (mode) {
		case PULL_FROM_END:
			mInAnim = Handler_Animation.getTranslateAnimation(LoonConstant.Animations.inFromBottom);
			mOutAnim = Handler_Animation.getTranslateAnimation(LoonConstant.Animations.outToBottom);

			// Rotate Arrow so it's pointing the correct way
			mArrowImageView.setScaleType(ScaleType.MATRIX);
			Matrix matrix = new Matrix();
			matrix.setRotate(180f, upBitmap.getWidth() / 2f, upBitmap.getHeight() / 2f);
			mArrowImageView.setImageMatrix(matrix);
			break;
		default:
		case PULL_FROM_START:
			mInAnim = Handler_Animation.getTranslateAnimation(LoonConstant.Animations.inFromTop);
			mOutAnim = Handler_Animation.getTranslateAnimation(LoonConstant.Animations.outToTop);
			break;
		}

		mInAnim.setAnimationListener(this);
		mOutAnim.setAnimationListener(this);

		final Interpolator interpolator = new LinearInterpolator();
		mRotateAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mRotateAnimation.setInterpolator(interpolator);
		mRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		mRotateAnimation.setFillAfter(true);

		mResetRotateAnimation = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		mResetRotateAnimation.setInterpolator(interpolator);
		mResetRotateAnimation.setDuration(DEFAULT_ROTATION_ANIMATION_DURATION);
		mResetRotateAnimation.setFillAfter(true);

	}

	public final boolean isVisible() {
		Animation currentAnim = getAnimation();
		if (null != currentAnim) {
			return mInAnim == currentAnim;
		}

		return getVisibility() == View.VISIBLE;
	}

	public void hide() {
		startAnimation(mOutAnim);
	}

	public void show() {
		mArrowImageView.clearAnimation();
		startAnimation(mInAnim);
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (animation == mOutAnim) {
			mArrowImageView.clearAnimation();
			setVisibility(View.GONE);
		} else if (animation == mInAnim) {
			setVisibility(View.VISIBLE);
		}

		clearAnimation();
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// NO-OP
	}

	@Override
	public void onAnimationStart(Animation animation) {
		setVisibility(View.VISIBLE);
	}

	public void releaseToRefresh() {
		mArrowImageView.startAnimation(mRotateAnimation);
	}

	public void pullToRefresh() {
		mArrowImageView.startAnimation(mResetRotateAnimation);
	}

	@Override
	protected void onDetachedFromWindow() {
	    super.onDetachedFromWindow();
	    if (upBitmap!=null&&!upBitmap.isRecycled()) {
	    	upBitmap.recycle();
	    	upBitmap = null;
        }
	}
}
