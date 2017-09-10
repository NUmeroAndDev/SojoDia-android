package com.numero.sojodia.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NotSwipeViewPager extends ViewPager {

    private static boolean isSwipeEnable = false;

    public NotSwipeViewPager(Context context) {
        super(context);
    }

    public NotSwipeViewPager(Context context, AttributeSet attrs) {
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
}
