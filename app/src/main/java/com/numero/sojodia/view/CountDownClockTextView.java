package com.numero.sojodia.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.TextView;

import com.numero.sojodia.R;

public class CountDownClockTextView extends TextView {

    private boolean isAttached;
    private int hour;
    private int min;
    private int sec;

    private OnTimeChangedListener onTimeChangedListener;

    public CountDownClockTextView(Context context) {
        super(context);
    }

    public CountDownClockTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CountDownClockTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setTime(int hour, int min, int sec) {
        this.hour = hour;
        this.min = min;
        this.sec = sec;
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

//            Fixme 時間のずれ
            long now = SystemClock.uptimeMillis();
            long next = now + (1000 - now % 1000);

            getHandler().postAtTime(mTicker, next);
        }
    };

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (!isAttached) {
            isAttached = true;
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
        sec -= 1;
        if (sec == -1) {
            sec = 59;
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
        setTextColor(ContextCompat.getColor(getContext(), getCountTimeTextResColor(hour, min, sec)));
        setText(String.format("%02d:%02d:%02d", hour, min, sec));
    }

    //    Fixme int attr
    private int getCountTimeTextResColor(int hour, int min, int sec) {
        if (sec % 2 == 0) {
            return R.color.text_secondary;
        }
        if (hour == 0 && min <= 4) {
            return R.color.text_count_attention;
        } else {
            return R.color.text_count_normal;
        }
    }

    public interface OnTimeChangedListener {
        void onTimeChanged();

        void onTimeLimit();
    }
}
