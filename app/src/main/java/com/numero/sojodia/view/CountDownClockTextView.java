package com.numero.sojodia.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.numero.sojodia.R;

import java.util.Calendar;

public class CountDownClockTextView extends AppCompatTextView {

    private boolean isAttached;
    private int hour;
    private int min;
    private boolean isBecomeZeroSecond = false;

    private int defaultTextColor;
    private int attentionTextColor;
    private int safeTextColor;

    private OnTimeChangedListener onTimeChangedListener;

    private final Runnable runnable = new Runnable() {
        public void run() {
            onTimeChanged();

            if (onTimeChangedListener != null) {
                onTimeChangedListener.onTimeChanged();
            }

            getHandler().postDelayed(runnable, 200);
        }
    };

    public CountDownClockTextView(Context context) {
        this(context, null);
    }

    public CountDownClockTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CountDownClockTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CountDownClockTextView);
        defaultTextColor = array.getColor(R.styleable.CountDownClockTextView_defaultTextColor, 0);
        attentionTextColor = array.getColor(R.styleable.CountDownClockTextView_attentionTextColor, 0);
        safeTextColor = array.getColor(R.styleable.CountDownClockTextView_safeTextColor, 0);
        array.recycle();
    }

    public void setTime(int hour, int min) {
        this.hour = hour;
        this.min = min;
    }

    public void setOnTimeChangedListener(OnTimeChangedListener onTimeChangedListener) {
        this.onTimeChangedListener = onTimeChangedListener;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (!isAttached) {
            isAttached = true;
            isBecomeZeroSecond = false;
            runnable.run();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isAttached) {

            getHandler().removeCallbacks(runnable);

            isAttached = false;
        }
    }

    @SuppressLint("DefaultLocale")
    private void onTimeChanged() {
        int sec = 59 - getCurrentSec();
        if (sec == 0) {
            isBecomeZeroSecond = true;
        }
        if (isBecomeZeroSecond && sec == 59) {
            isBecomeZeroSecond = false;
            min -= 1;
        }
        if (min == -1) {
            min = 59;
            hour -= 1;
        }

        if (hour == -1) {
            hour = 0;
            min = 0;
            sec = 0;
            if (onTimeChangedListener != null) {
                onTimeChangedListener.onTimeLimit();
            }
        }
        setTextColor(getCountTimeTextColor(hour, min, sec));
        setText(String.format("%02d:%02d:%02d", hour, min, sec));
    }

    private int getCurrentSec() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.SECOND);
    }

    private int getCountTimeTextColor(int hour, int min, int sec) {
        if (sec % 2 == 0) {
            return defaultTextColor;
        }
        if (hour == 0 && min <= 4) {
            return attentionTextColor;
        } else {
            return safeTextColor;
        }
    }

    public interface OnTimeChangedListener {
        void onTimeChanged();

        void onTimeLimit();
    }
}
