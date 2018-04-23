package com.numero.sojodia.view

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import androidx.core.content.withStyledAttributes

import com.numero.sojodia.R
import java.util.*

class CountDownClockTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var isAttached: Boolean = false
    private var hour: Int = 0
    private var min: Int = 0
    private var isBecomeZeroSecond = false

    private var defaultTextColor: Int = 0
    private var attentionTextColor: Int = 0
    private var safeTextColor: Int = 0

    private var onTimeChangedListener: OnTimeChangedListener? = null

    private val runnable = object : Runnable {
        override fun run() {
            onTimeChanged()

            onTimeChangedListener?.onTimeChanged()

            handler.postDelayed(this, 200)
        }
    }

    private val currentSec: Int
        get() {
            val calendar = Calendar.getInstance()
            return calendar.get(Calendar.SECOND)
        }

    init {
        context.withStyledAttributes(attrs, R.styleable.CountDownClockTextView) {
            defaultTextColor = getColor(R.styleable.CountDownClockTextView_defaultTextColor, 0)
            attentionTextColor = getColor(R.styleable.CountDownClockTextView_attentionTextColor, 0)
            safeTextColor = getColor(R.styleable.CountDownClockTextView_safeTextColor, 0)
        }
    }

    /**
     * @param hour
     * @param min
     *
     * hh:(mm - 1):59 からスタートするので min - 1 する
     */
    fun setTime(hour: Int, min: Int) {
        this.hour = hour
        this.min = min - 1
    }

    fun setOnTimeChangedListener(onTimeChangedListener: OnTimeChangedListener) {
        this.onTimeChangedListener = onTimeChangedListener
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (isAttached.not()) {
            isAttached = true
            isBecomeZeroSecond = false
            runnable.run()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        if (isAttached) {

            handler.removeCallbacks(runnable)

            isAttached = false
        }
    }

    private fun onTimeChanged() {
        var sec = 59 - currentSec
        if (sec == 0) {
            isBecomeZeroSecond = true
        }
        if (isBecomeZeroSecond && sec == 59) {
            isBecomeZeroSecond = false
            min -= 1
        }
        if (min == -1) {
            min = 59
            hour -= 1
        }

        if (hour == -1) {
            hour = 0
            min = 0
            sec = 0
            onTimeChangedListener?.onTimeLimit()
        }
        setTextColor(getCountTimeTextColor(hour, min, sec))
        text = String.format(Locale.ENGLISH, "%02d:%02d:%02d", hour, min, sec)
    }

    private fun getCountTimeTextColor(hour: Int, min: Int, sec: Int): Int {
        if (sec % 2 == 0) {
            return defaultTextColor
        }
        return if (hour == 0 && min <= 4) {
            attentionTextColor
        } else {
            safeTextColor
        }
    }

    interface OnTimeChangedListener {
        fun onTimeChanged()

        fun onTimeLimit()
    }
}
