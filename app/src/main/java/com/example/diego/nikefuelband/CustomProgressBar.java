package com.example.diego.nikefuelband;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Looper;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/**
 * Created by diego on 05/10/15.
 */
public class CustomProgressBar extends ProgressBar {

	private Bitmap mBitmapBackground;
	private Bitmap mBitmapForeground;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mProgressBounds = new RectF();
    private Matrix mMatrix = new Matrix();

	public CustomProgressBar(Context context) {
		super(context);
	}

	public CustomProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public CustomProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


    @Override
	protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
		super.onSizeChanged(width, height, oldWidth, oldHeight);

		mBitmapBackground = BitmapFactory.decodeResource(getResources(), R.drawable.ring_blur);
		mBitmapForeground = BitmapFactory.decodeResource(getResources(), R.drawable.color_meter);

        mProgressBounds = new RectF(0, 0, getWidth(), getHeight());
        RectF src = new RectF(0, 0, mBitmapForeground.getWidth(), mBitmapForeground.getHeight());
        mMatrix.setRectToRect(src, mProgressBounds, Matrix.ScaleToFit.CENTER);
        Shader shader = new BitmapShader(mBitmapForeground, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
		shader.setLocalMatrix(mMatrix);
		mPaint.setShader(shader);
		mMatrix.mapRect(mProgressBounds, src);
	}

	@Override
	protected void onDraw(Canvas canvas) {

		float progressAngle = ((float) getProgress()/getMax()) * 360f;
        canvas.drawBitmap(mBitmapBackground, mMatrix, mPaint);
		canvas.drawArc(mProgressBounds, 90f, progressAngle, true, mPaint);

	}

	@Override
	public synchronized void setProgress(int progress) {

		// check if this is the ui thread and trigger redraw
		if(Looper.myLooper() == Looper.getMainLooper()) {
			this.invalidate();
		} else {
			this.postInvalidate();
		}
		super.setProgress(progress);
	}
}