package com.numero.sojodia.ui.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager

class NotSwipeViewPager @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : ViewPager(context, attrs) {

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
