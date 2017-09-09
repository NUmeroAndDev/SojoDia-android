package com.numero.sojodia.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

//TODO リネーム
public class CustomViewPager extends ViewPager {

    private boolean isSwipeEnable = true;

    public CustomViewPager(Context context) {
        super(context);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isSwipeEnable;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return isSwipeEnable;
    }

    public void setSwipeEnable(boolean isSwipeEnable) {
        this.isSwipeEnable = isSwipeEnable;
    }
}
