package com.mindiii.lasross.helper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

public class RoundRectCornerImageViewNew extends androidx.appcompat.widget.AppCompatImageView {

    private float radius = 16.0f;
    private Path path;
    private RectF rect;

    public RoundRectCornerImageViewNew(Context context) {
        super(context);
        init();
    }

    public RoundRectCornerImageViewNew(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RoundRectCornerImageViewNew(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        path = new Path();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        //force a 16:9 aspect ratio
        int height = Math.round(width * 1.25f);
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        path.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }
}