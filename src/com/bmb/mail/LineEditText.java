package com.bmb.mail;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

public class LineEditText extends EditText {
	private Paint mPaint;
	private Rect mRect;
	private float mult = 1.5f;
	private float add = 2.0f;

	public LineEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public LineEditText(Context context) {
		super(context);
		init();
	}

	private void init() {
		mRect = new Rect();
		mPaint = new Paint();
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(Color.GRAY);
		mPaint.setAntiAlias(true);
		this.setLineSpacing(add, mult);
	}

	@Override
	public void onDraw(Canvas canvas) {
		int count = getLineCount();
		for (int i = 0; i < count; i++) {
			getLineBounds(i, mRect);
			int baseline = (i + 1) * getLineHeight();
			canvas.drawLine(mRect.left, baseline, mRect.right, baseline, mPaint);
		}
		super.onDraw(canvas);
	}

}
