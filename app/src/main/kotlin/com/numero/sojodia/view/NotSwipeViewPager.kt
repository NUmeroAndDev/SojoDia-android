package com.numero.sojodia.view

import android.annotation.SuppressLint
import android.content.Context
import androidx.viewpager.widget.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class NotSwipeViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : androidx.viewpager.widget.ViewPager(context, attrs) {

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return SWIPE_ENABLE
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return SWIPE_ENABLE
    }

    companion object {
        private const val SWIPE_ENABLE = false
    }
}
