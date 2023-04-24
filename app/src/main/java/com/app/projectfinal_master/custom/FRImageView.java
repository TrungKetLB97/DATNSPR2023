package com.app.projectfinal_master.custom;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewParent;

public class FRImageView extends androidx.appcompat.widget.AppCompatImageView {

    private boolean mAdjustViewBounds;

    public FRImageView(Context context) {
        super(context);
    }

    public FRImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FRImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdjustViewBounds(boolean adjustViewBounds) {
        mAdjustViewBounds = adjustViewBounds;
        super.setAdjustViewBounds(adjustViewBounds);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (Build.VERSION.SDK_INT <= 17) {
            Drawable drawable = getDrawable();
            if (drawable == null) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                return;
            }

            if (mAdjustViewBounds) {
                int mDrawableWidth = drawable.getIntrinsicWidth();
                int mDrawableHeight = drawable.getIntrinsicHeight();
                int heightSize = MeasureSpec.getSize(heightMeasureSpec);
                int widthSize = MeasureSpec.getSize(widthMeasureSpec);
                int heightMode = MeasureSpec.getMode(heightMeasureSpec);
                int widthMode = MeasureSpec.getMode(widthMeasureSpec);

                if (heightMode == MeasureSpec.EXACTLY && widthMode != MeasureSpec.EXACTLY) {
                    int height = heightSize;
                    int width = height * mDrawableWidth / mDrawableHeight;
                    if (isInScrollingContainer()) {
                        setMeasuredDimension(width, height);
                    } else {
                        setMeasuredDimension(Math.min(width, widthSize), Math.min(height, heightSize));
                    }
                } else if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
                    int width = widthSize;
                    int height = width * mDrawableHeight / mDrawableWidth;
                    if (isInScrollingContainer()) {
                        setMeasuredDimension(width, height);
                    } else {
                        setMeasuredDimension(Math.min(width, widthSize), Math.min(height, heightSize));
                    }
                    {
                        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                    }
                }
            } else {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            }
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    private boolean isInScrollingContainer() {
        ViewParent vp = getParent();
        while (vp != null && vp instanceof ViewGroup) {
            if (((ViewGroup) vp).shouldDelayChildPressedState()) {
                return true;
            }
            vp = vp.getParent();
        }

        return false;
    }

}