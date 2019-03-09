package com.numero.sojodia.widget

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.withStyledAttributes
import com.numero.sojodia.R
import java.util.*

class CountDownClockTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(context, attrs, defStyleAttr), ISyncThreadView {

    private var hour: Int = 0
    private var min: Int = 0
    private var isBecomeZeroSecond = false

    private var defaultTextColor: Int = 0
    private var attentionTextColor: Int = 0
    private var safeTextColor: Int = 0

    override var isSynced: Boolean = false
        set(value) {
            field = value
            if (value and isRunning) {
                intervalHandler.removeCallbacks(runnable)
            }
        }
    private var syncView: ISyncThreadView? = null

    private var onTimeLimitListener: (() -> Unit)? = null
    private var onCountListener: (() -> Unit)? = null

    private val intervalHandler = Handler()
    private var isRunning: Boolean = false
    private val runnable = object : Runnable {
        override fun run() {
            doOnThread()
            syncView?.doOnThread()

            intervalHandler.postDelayed(this, 200)
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

    fun setSyncView(syncThreadView: ISyncThreadView) {
        syncView = syncThreadView
        syncThreadView.isSynced = true
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

    fun setOnTimeLimitListener(listener: () -> Unit) {
        onTimeLimitListener = listener
    }

    fun setOnCountListener(listener: () -> Unit) {
        onCountListener = listener
    }

    fun start() {
        if (isRunning.not() and isSynced.not()) {
            isRunning = true
            isBecomeZeroSecond = false
            runnable.run()
        }
    }

    fun stop() {
        if (isRunning) {
            intervalHandler.removeCallbacks(runnable)
            isRunning = false
        }
    }

    override fun doOnThread() {
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
            onTimeLimitListener?.invoke()
        }
        setTextColor(getCountTimeTextColor(hour, min, sec))
        text = String.format(Locale.ENGLISH, "%02d:%02d:%02d", hour, min, sec)

        onCountListener?.invoke()
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
}
