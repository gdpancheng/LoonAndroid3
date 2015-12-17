package com.demo.view;

/*
 * Author: pan Email:gdpancheng@gmail.com
 * Created Date:2013-8-9
 * Copyright @ 2013 BU
 * Description: 类描述
 *
 * History:
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.util.AttributeSet;
import android.widget.TextView;

public class ReflectTextView extends TextView {

	private Matrix mMatrix;
	private Paint mPaint;

	public ReflectTextView(Context context) {
		super(context);
		init();
	}

	public ReflectTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ReflectTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init() {
		// make the shadow reverse of Y
		mMatrix = new Matrix();
		mMatrix.preScale(1, -1);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight() * 2);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// draw the text from layout()
		super.onDraw(canvas);

		int height = getHeight();
		int width = getWidth();

		// make sure you can use the cache
		setDrawingCacheEnabled(true);

		// create bitmap from cache,this is the most important of this
		Bitmap originalImage = Bitmap.createBitmap(getDrawingCache());

		// create the shadow
		Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, 0, width, height / 2, mMatrix, false);

		// draw the shadow
		canvas.drawBitmap(reflectionImage, 0, height / 2, null);

		if (mPaint == null) {
			// process shadow bitmap to make it shadow like
			mPaint = new Paint();
			LinearGradient shader = new LinearGradient(0, height / 2, 0, height, 0x7fffffff, 0x0fffffff, TileMode.CLAMP);
			mPaint.setShader(shader);
			mPaint.setXfermode(new PorterDuffXfermode(Mode.DST_IN));
		}

		canvas.drawRect(0, height / 2, width, height, mPaint);
	}

}
