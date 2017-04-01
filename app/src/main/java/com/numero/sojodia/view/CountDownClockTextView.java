package com.numero.sojodia.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.numero.sojodia.R;

import java.util.Calendar;

public class CountDownClockTextView extends TextView {

    private boolean isAttached;
    private int hour;
    private int min;
    private boolean flg = false;

    private int defaultTextColor;
    private int attentionTextColor;
    private int safeTextColor;

    private OnTimeChangedListener onTimeChangedListener;

    public CountDownClockTextView(Context context) {
        super(context);
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

    private final Runnable mTicker = new Runnable() {
        public void run() {
            onTimeChanged();

            if (onTimeChangedListener != null) {
                onTimeChangedListener.onTimeChanged();
            }

            getHandler().postDelayed(mTicker, 200);
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (!isAttached) {
            isAttached = true;
            flg = false;
            mTicker.run();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (isAttached) {

            getHandler().removeCallbacks(mTicker);

            isAttached = false;
        }
    }

    @SuppressLint("DefaultLocale")
    private void onTimeChanged() {
        int sec = 59 - getCurrentSec();
        if (sec == 0) {
            flg = true;
        }
        if (flg && sec == 59) {
            flg = false;
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
