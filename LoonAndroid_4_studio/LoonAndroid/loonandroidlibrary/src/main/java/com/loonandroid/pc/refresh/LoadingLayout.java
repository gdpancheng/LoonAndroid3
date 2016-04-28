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

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loonandroid.pc.handler.Handler_System;
import com.loonandroid.pc.refresh.PullToRefreshBase.Mode;
import com.loonandroid.pc.refresh.PullToRefreshBase.Orientation;
import com.loonandroid.pc.util.LoonConstant;

@SuppressLint("ViewConstructor")
public abstract class LoadingLayout extends FrameLayout implements ILoadingLayout {

	static final String LOG_TAG = "PullToRefresh-LoadingLayout";

	static final Interpolator ANIMATION_INTERPOLATOR = new LinearInterpolator();

	private FrameLayout mInnerLayout;

	protected ImageView mHeaderImage;
	protected ProgressBar mHeaderProgress;

	private boolean mUseIntrinsicAnimation;

	private TextView mHeaderText;
	private TextView mSubHeaderText;

	protected Mode mMode;
	protected Orientation mScrollDirection;

	private CharSequence mPullLabel;
	private CharSequence mRefreshingLabel;
	private CharSequence mReleaseLabel;
	
	private boolean isDraw = false;

	public LoadingLayout(Context context, final Mode mode, final Orientation scrollDirection) {
		super(context);
		mMode = mode;
		mScrollDirection = scrollDirection;
		isDraw = false;
		
		float rote = Handler_System.getPadRoate();
		
		switch (scrollDirection) {
		case HORIZONTAL: {
			mInnerLayout = new FrameLayout(context);
			FrameLayout.LayoutParams mInnerParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.MATCH_PARENT);
			mInnerLayout.setPadding((int)(40*rote), 0, (int)(40*rote), 0);
			mInnerLayout.setLayoutParams(mInnerParams);

			mHeaderImage = new ImageView(context);
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
			params.gravity = Gravity.CENTER;
			mHeaderImage.setLayoutParams(params);
			mInnerLayout.addView(mHeaderImage);

			mHeaderProgress = new ProgressBar(context,null,android.R.attr.progressBarStyleSmall);
			FrameLayout.LayoutParams progress_params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
			progress_params.gravity = Gravity.CENTER;
			mHeaderProgress.setLayoutParams(progress_params);
			mHeaderProgress.setIndeterminate(true);
			
			mHeaderProgress.setVisibility(View.GONE);
			mInnerLayout.addView(mHeaderProgress);

			addView(mInnerLayout);
		}
			break;
		case VERTICAL:
		default:

		{
			mInnerLayout = new FrameLayout(context);
			FrameLayout.LayoutParams fl = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
			fl.gravity = Gravity.CENTER;
			mInnerLayout.setLayoutParams(fl);
			mInnerLayout.setPadding(0, (int)(40*rote), 0, (int)(40*rote));

			FrameLayout frameLayout = new FrameLayout(context);
			FrameLayout.LayoutParams frameParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
			frameParams.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
			frameParams.leftMargin = (int)(50*rote);
			frameLayout.setLayoutParams(frameParams);

			mHeaderImage = new ImageView(context);
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
			params.gravity = Gravity.CENTER;
			mHeaderImage.setLayoutParams(params);
			frameLayout.addView(mHeaderImage);

			mHeaderProgress = new ProgressBar(context,null,android.R.attr.progressBarStyleSmall);
			FrameLayout.LayoutParams progress_params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
			progress_params.gravity = Gravity.CENTER;
			mHeaderProgress.setLayoutParams(progress_params);
			mHeaderProgress.setIndeterminate(true);
			mHeaderProgress.setVisibility(View.GONE);
			frameLayout.addView(mHeaderProgress);

			LinearLayout layout = new LinearLayout(context);
			FrameLayout.LayoutParams layout_params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
			layout_params.gravity = Gravity.CENTER;
			layout.setLayoutParams(layout_params);
			layout.setGravity(Gravity.CENTER_HORIZONTAL);
			layout.setOrientation(LinearLayout.VERTICAL);

			// mInnerLayout.addView(layout);

			mHeaderText = new TextView(context);
			LinearLayout.LayoutParams text_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			mHeaderText.setLayoutParams(text_params);
			mHeaderText.setSingleLine(true);
			mHeaderText.setTextAppearance(context, android.R.attr.textAppearance);
			TextPaint tp = mHeaderText.getPaint();
			tp.setFakeBoldText(true);
			layout.addView(mHeaderText);

			mSubHeaderText = new TextView(context);
			LinearLayout.LayoutParams sub_text_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
			mSubHeaderText.setLayoutParams(sub_text_params);
			mSubHeaderText.setSingleLine(true);
			mSubHeaderText.setTextAppearance(context, android.R.attr.textAppearanceSmall);
			mSubHeaderText.setVisibility(View.GONE);
			layout.addView(mSubHeaderText);

			mInnerLayout.addView(frameLayout);
			mInnerLayout.addView(layout);

			addView(mInnerLayout);
		}
			break;
		}

		FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mInnerLayout.getLayoutParams();

		switch (mode) {
		case PULL_FROM_END:
			lp.gravity = scrollDirection == Orientation.VERTICAL ? Gravity.TOP : Gravity.LEFT;
			// Load in labels
			break;

		case PULL_FROM_START:
		default:
			lp.gravity = scrollDirection == Orientation.VERTICAL ? Gravity.BOTTOM : Gravity.RIGHT;
			// Load in labels
			break;
		}
		mPullLabel = LoonConstant.pullToRefresh.mPullLabel;
		mRefreshingLabel = LoonConstant.pullToRefresh.mRefreshingLabel;
		mReleaseLabel = LoonConstant.pullToRefresh.mReleaseLabel;

		if (null != LoonConstant.pullToRefresh.ptrHeaderBackground) {
			ViewCompat.setBackground(this, LoonConstant.pullToRefresh.ptrHeaderBackground);
		}

		setTextAppearance(LoonConstant.pullToRefresh.ptrHeaderTextAppearance);

		setSubTextAppearance(LoonConstant.pullToRefresh.ptrSubHeaderTextAppearance);

		if (LoonConstant.pullToRefresh.ptrHeaderTextColor != 0) {
			setTextColor(getResources().getColor(LoonConstant.pullToRefresh.ptrHeaderTextColor));
		}
		if (LoonConstant.pullToRefresh.ptrHeaderSubTextColor != 0) {
			setSubTextColor(getResources().getColor(LoonConstant.pullToRefresh.ptrHeaderSubTextColor));
		}

		// Try and get defined drawable from Attrs
		Drawable imageDrawable = LoonConstant.pullToRefresh.ptrDrawable;

		// Check Specific Drawable from Attrs, these overrite the generic
		// drawable attr above
		switch (mode) {
		case PULL_FROM_START:
		default:
			if (null != LoonConstant.pullToRefresh.ptrDrawableStart) {
				imageDrawable = LoonConstant.pullToRefresh.ptrDrawableStart;
			} else if (null != LoonConstant.pullToRefresh.ptrDrawableTop) {
				imageDrawable = LoonConstant.pullToRefresh.ptrDrawableTop;
			}
			break;

		case PULL_FROM_END:

			if (null != LoonConstant.pullToRefresh.ptrDrawableEnd) {
				imageDrawable = LoonConstant.pullToRefresh.ptrDrawableEnd;
			} else if (null != LoonConstant.pullToRefresh.ptrDrawableBottom) {
				imageDrawable = LoonConstant.pullToRefresh.ptrDrawableBottom;
			}
			break;
		}

		// If we don't have a user defined drawable, load the default
		if (null == imageDrawable) {
			imageDrawable = new BitmapDrawable(getResources(),getDefaultDrawable());
		}

		ViewTreeObserver vto = mInnerLayout.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
            	if (!isDraw&&drawFinsh!=null&&mInnerLayout.getHeight()>0&&mInnerLayout.getWidth()>0) {
            		isDraw = true;
					drawFinsh.finsh();
                }
            }
        });
		
		// Set Drawable, and save width/height
		setLoadingDrawable(imageDrawable);

		reset();
	}

	public final void setHeight(int height) {
		ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) getLayoutParams();
		lp.height = height;
		requestLayout();
	}

	public final void setWidth(int width) {
		ViewGroup.LayoutParams lp = (ViewGroup.LayoutParams) getLayoutParams();
		lp.width = width;
		requestLayout();
	}

	public final int getContentSize() {
		switch (mScrollDirection) {
		case HORIZONTAL:
			return mInnerLayout.getWidth();
		case VERTICAL:
		default:
			return mInnerLayout.getHeight();
		}
	}

	public final void hideAllViews() {
		if (View.VISIBLE == mHeaderText.getVisibility()) {
			mHeaderText.setVisibility(View.INVISIBLE);
		}
		if (View.VISIBLE == mHeaderProgress.getVisibility()) {
			mHeaderProgress.setVisibility(View.INVISIBLE);
		}
		if (View.VISIBLE == mHeaderImage.getVisibility()) {
			mHeaderImage.setVisibility(View.INVISIBLE);
		}
		if (View.VISIBLE == mSubHeaderText.getVisibility()) {
			mSubHeaderText.setVisibility(View.INVISIBLE);
		}
	}

	public final void onPull(float scaleOfLayout) {
		if (!mUseIntrinsicAnimation) {
			onPullImpl(scaleOfLayout);
		}
	}

	public final void pullToRefresh() {
		if (null != mHeaderText) {
			mHeaderText.setText(mPullLabel);
		}

		// Now call the callback
		pullToRefreshImpl();
	}

	public final void refreshing() {
		if (null != mHeaderText) {
			mHeaderText.setText(mRefreshingLabel);
		}

		if (mUseIntrinsicAnimation) {
			((AnimationDrawable) mHeaderImage.getDrawable()).start();
		} else {
			// Now call the callback
			refreshingImpl();
		}

		if (null != mSubHeaderText) {
//			mSubHeaderText.setVisibility(View.GONE);
		}
	}

	public final void releaseToRefresh() {
		if (null != mHeaderText) {
			mHeaderText.setText(mReleaseLabel);
		}

		// Now call the callback
		releaseToRefreshImpl();
	}

	public final void reset() {
		if (null != mHeaderText) {
			mHeaderText.setText(mPullLabel);
		}
		mHeaderImage.setVisibility(View.VISIBLE);

		if (mUseIntrinsicAnimation) {
			((AnimationDrawable) mHeaderImage.getDrawable()).stop();
		} else {
			// Now call the callback
			resetImpl();
		}

		if (null != mSubHeaderText) {
			if (TextUtils.isEmpty(mSubHeaderText.getText())) {
				mSubHeaderText.setVisibility(View.GONE);
			} else {
				mSubHeaderText.setVisibility(View.VISIBLE);
			}
		}
	}

	@Override
	public void setLastUpdatedLabel(CharSequence label) {
		setSubHeaderText(label);
	}

	public final void setLoadingDrawable(Drawable imageDrawable) {
		// Set Drawable
		mHeaderImage.setImageDrawable(imageDrawable);
		mUseIntrinsicAnimation = (imageDrawable instanceof AnimationDrawable);

		// Now call the callback
		onLoadingDrawableSet(imageDrawable);
	}

	public void setPullLabel(CharSequence pullLabel) {
		mPullLabel = pullLabel;
	}

	public void setRefreshingLabel(CharSequence refreshingLabel) {
		mRefreshingLabel = refreshingLabel;
	}

	public void setReleaseLabel(CharSequence releaseLabel) {
		mReleaseLabel = releaseLabel;
	}

	@Override
	public void setTextTypeface(Typeface tf) {
		mHeaderText.setTypeface(tf);
	}

	public final void showInvisibleViews() {
		if (View.INVISIBLE == mHeaderText.getVisibility()) {
			mHeaderText.setVisibility(View.VISIBLE);
		}
		if (View.INVISIBLE == mHeaderProgress.getVisibility()) {
			mHeaderProgress.setVisibility(View.VISIBLE);
		}
		if (View.INVISIBLE == mHeaderImage.getVisibility()) {
			mHeaderImage.setVisibility(View.VISIBLE);
		}
		if (View.INVISIBLE == mSubHeaderText.getVisibility()) {
			mSubHeaderText.setVisibility(View.VISIBLE);
		}
	}

	/**
	 * Callbacks for derivative Layouts
	 */

	protected abstract Bitmap getDefaultDrawable();

	protected abstract void onLoadingDrawableSet(Drawable imageDrawable);

	protected abstract void onPullImpl(float scaleOfLayout);

	protected abstract void pullToRefreshImpl();

	protected abstract void refreshingImpl();

	protected abstract void releaseToRefreshImpl();

	protected abstract void resetImpl();

	private void setSubHeaderText(CharSequence label) {
		if (null != mSubHeaderText) {
			if (TextUtils.isEmpty(label)) {
				mSubHeaderText.setVisibility(View.GONE);
			} else {
				mSubHeaderText.setText(label);

				// Only set it to Visible if we're GONE, otherwise VISIBLE will
				// be set soon
				if (View.GONE == mSubHeaderText.getVisibility()) {
					mSubHeaderText.setVisibility(View.VISIBLE);
				}
			}
		}
	}

	private void setSubTextAppearance(int value) {
		if (null != mSubHeaderText) {
			mSubHeaderText.setTextAppearance(getContext(), value);
		}
	}

	private void setSubTextColor(int color) {
		if (null != mSubHeaderText) {
			mSubHeaderText.setTextColor(color);
		}
	}

	private void setTextAppearance(int value) {
		if (null != mHeaderText) {
			mHeaderText.setTextAppearance(getContext(), value);
		}
		if (null != mSubHeaderText) {
			mSubHeaderText.setTextAppearance(getContext(), value);
		}
	}

	private void setTextColor(int color) {
		if (null != mHeaderText) {
			mHeaderText.setTextColor(color);
		}
		if (null != mSubHeaderText) {
			mSubHeaderText.setTextColor(color);
		}
	}

	public interface DrawFinsh{
		public void finsh();
	}
	
	private DrawFinsh drawFinsh;
	
	public void setDrawFinsh(DrawFinsh drawFinsh){
		this.drawFinsh = drawFinsh;
	}
}
