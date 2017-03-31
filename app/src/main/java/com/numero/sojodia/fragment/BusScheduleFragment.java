package com.numero.sojodia.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.numero.sojodia.activity.MainActivity;
import com.numero.sojodia.adapter.BusTimePagerAdapter;
import com.numero.sojodia.model.BusTime;
import com.numero.sojodia.model.Time;
import com.numero.sojodia.util.DateUtil;
import com.numero.sojodia.util.TimeUtil;
import com.numero.sojodia.view.CountDownClockTextView;
import com.numero.sojodia.view.CustomViewPager;
import com.numero.sojodia.view.TimeTableDialog;
import com.numero.sojodia.R;

import java.util.List;

public class BusScheduleFragment extends Fragment {

    private static final String RECIPROCATE = "RECIPROCATE";

    private List<BusTime> tkTimeList;
    private List<BusTime> tndTimeList;
    private BusTimePagerAdapter tkPagerAdapter;
    private BusTimePagerAdapter tndPagerAdapter;
    private View view;

    private int tkCurrentNextBusPosition = -1;
    private int tndCurrentNextBusPosition = -1;
    private int reciprocate;
    private String currentDateString;

    public BusScheduleFragment() {
    }

    public static BusScheduleFragment newInstance(int reciprocate) {
        BusScheduleFragment fragment = new BusScheduleFragment();
        Bundle args = new Bundle();
        args.putInt(RECIPROCATE, reciprocate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bus_schedule_fragment, null);

        reciprocate = getArguments().getInt(RECIPROCATE);

        initCountDownClockTextView(view);
        initTimeTableButton(view);
        initNextBeforeButton(view);
        initTkBusTimePagerView(view);
        initTndBusTimePagerView(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        Fixme 曜日セット
        initBusTimeList(DateUtil.WEEKDAY);
        initTkBusPagerView();
        initTndBusPagerView();
        setCurrentDateText(DateUtil.getTodayString(getActivity()));
        findCurrentTkBusTime();
        findCurrentTndBusTime();
    }

    private void initCountDownClockTextView(View view) {
        CountDownClockTextView countDownClockTextViewTk = (CountDownClockTextView) view.findViewById(R.id.count_down_text_tk);
        countDownClockTextViewTk.setOnTimeChangedListener(new CountDownClockTextView.OnTimeChangedListener() {
            @Override
            public void onTimeChanged() {
                checkDateChange();
                checkTkCurrentNextBus();
            }

            @Override
            public void onTimeLimit() {
//                Fixme 次の時刻をセット
                setCurrentTkBusTime(getCurrentTkBusTimePosition() + 1, true);
            }
        });
        CountDownClockTextView countDownClockTextViewTnd = (CountDownClockTextView) view.findViewById(R.id.count_down_text_tnd);
        countDownClockTextViewTnd.setOnTimeChangedListener(new CountDownClockTextView.OnTimeChangedListener() {
            @Override
            public void onTimeChanged() {
                checkTndCurrentNextBus();
            }

            @Override
            public void onTimeLimit() {
//                Fixme 次の時刻をセット
                setCurrentTndBusTime(getCurrentTndBusTimePosition() + 1, true);
            }
        });
    }

    private void initBusTimeList(int week) {
//        Fixme 日付変更時のリスト
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            tkTimeList = activity.getTkBusTimeList(reciprocate, week);
            tndTimeList = activity.getTndBusTimeList(reciprocate, week);
        }
    }

    private void findCurrentTkBusTime() {
        int position;
        Time tkTime = new Time();
        Time currentTime = TimeUtil.initCurrentTime();
        for (position = 0; position < tkTimeList.size(); position++) {
            tkTime.setTime(tkTimeList.get(position).hour, tkTimeList.get(position).min, 0);
            if (TimeUtil.isOverTime(currentTime, tkTime)) {
                tkCurrentNextBusPosition = position;
                setCurrentTkBusTime(position);
                Time countTime = TimeUtil.getCountTime(tkTime);
                setTkCountDownTime(countTime.hour, countTime.min);
                return;
            }
        }
//        Fixme その日のバスが無いとき
        setVisibleTkNoBusLayout(true);
    }

    private void findCurrentTndBusTime() {
        int position;
        Time tndTime = new Time();
        Time currentTime = TimeUtil.initCurrentTime();
        for (position = 0; position < tndTimeList.size(); position++) {
            tndTime.setTime(tndTimeList.get(position).hour, tndTimeList.get(position).min, 0);
            if (TimeUtil.isOverTime(currentTime, tndTime)) {
                tndCurrentNextBusPosition = position;
                setCurrentTndBusTime(position);
                Time countTime = TimeUtil.getCountTime(tndTime);
                setTndCountDownTime(countTime.hour, countTime.min);
                return;
            }
        }
//        Fixme その日のバスが無いとき
        setVisibleTndNoBusLayout(true);
    }

    private void initTkBusTimePagerView(View view) {
        CustomViewPager viewPager = (CustomViewPager) view.findViewById(R.id.tk_bus_time_pager);
        viewPager.setSwipeEnable(false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Time tkTime = new Time(tkTimeList.get(position).hour, tkTimeList.get(position).min, 0);
                Time countTime = TimeUtil.getCountTime(tkTime);
                setTkCountDownTime(countTime.hour, countTime.min);
                setVisibleTkBeforeButton(!isTkFirstBus());
                setVisibleTkNextButton(!isTkLastBus());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initTndBusTimePagerView(View view) {
        CustomViewPager viewPager = (CustomViewPager) view.findViewById(R.id.tnd_bus_time_pager);
        viewPager.setSwipeEnable(false);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Time tndTime = new Time(tndTimeList.get(position).hour, tndTimeList.get(position).min, 0);
                Time countTime = TimeUtil.getCountTime(tndTime);
                setTndCountDownTime(countTime.hour, countTime.min);
                setVisibleTndBeforeButton(!isTndFirstBus());
                setVisibleTndNextButton(!isTndLastBus());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initTimeTableButton(View view) {
        view.findViewById(R.id.time_table_tk_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeTableDialog.init(getActivity())
                        .setReciprocate(reciprocate)
                        .setRouteTk()
                        .setBusTimeListOnWeekday(getTkBusTimeList(DateUtil.WEEKDAY))
                        .setBusTimeListOnSaturday(getTkBusTimeList(DateUtil.SATURDAY))
                        .setBusTimeListOnSunday(getTkBusTimeList(DateUtil.SUNDAY))
                        .show();
            }
        });
        view.findViewById(R.id.time_table_tnd_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeTableDialog.init(getActivity())
                        .setReciprocate(reciprocate)
                        .setRouteTnd()
                        .setBusTimeListOnWeekday(getTndBusTimeList(DateUtil.WEEKDAY))
                        .setBusTimeListOnSaturday(getTndBusTimeList(DateUtil.SATURDAY))
                        .setBusTimeListOnSunday(getTndBusTimeList(DateUtil.SUNDAY))
                        .show();
            }
        });
    }

    private void initNextBeforeButton(View view) {
        view.findViewById(R.id.tk_next_bus_time_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentTkBusTime(getCurrentTkBusTimePosition() + 1, true);
            }
        });
        view.findViewById(R.id.tk_before_bus_time_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentTkBusTime(getCurrentTkBusTimePosition() - 1, true);
            }
        });
        view.findViewById(R.id.tnd_next_bus_time_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentTndBusTime(getCurrentTndBusTimePosition() + 1, true);
            }
        });
        view.findViewById(R.id.tnd_before_bus_time_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentTndBusTime(getCurrentTndBusTimePosition() - 1, true);
            }
        });
    }

    private void initTkBusPagerView() {
        tkPagerAdapter = new BusTimePagerAdapter();
        tkPagerAdapter.setBusTimeList(tkTimeList);
        CustomViewPager viewPager = (CustomViewPager) view.findViewById(R.id.tk_bus_time_pager);
        viewPager.setAdapter(tkPagerAdapter);
    }

    private void initTndBusPagerView() {
        tndPagerAdapter = new BusTimePagerAdapter();
        tndPagerAdapter.setBusTimeList(tndTimeList);
        CustomViewPager viewPager = (CustomViewPager) view.findViewById(R.id.tnd_bus_time_pager);
        viewPager.setAdapter(tndPagerAdapter);
    }

    private void setCurrentTkBusTime(int position) {
        setCurrentTkBusTime(position, false);
    }

    private void setCurrentTkBusTime(int position, boolean isAnimation) {
        CustomViewPager viewPager = (CustomViewPager) view.findViewById(R.id.tk_bus_time_pager);
        viewPager.setCurrentItem(position, isAnimation);
    }

    private int getCurrentTkBusTimePosition() {
        CustomViewPager viewPager = (CustomViewPager) view.findViewById(R.id.tk_bus_time_pager);
        return viewPager.getCurrentItem();
    }

    private void setCurrentTndBusTime(int position) {
        setCurrentTndBusTime(position, false);
    }

    private void setCurrentTndBusTime(int position, boolean isAnimation) {
        CustomViewPager viewPager = (CustomViewPager) view.findViewById(R.id.tnd_bus_time_pager);
        viewPager.setCurrentItem(position, isAnimation);
    }

    private int getCurrentTndBusTimePosition() {
        CustomViewPager viewPager = (CustomViewPager) view.findViewById(R.id.tnd_bus_time_pager);
        return viewPager.getCurrentItem();
    }

    private void setTkCountDownTime(int hour, int min) {
        CountDownClockTextView countDownClockTextView = (CountDownClockTextView) view.findViewById(R.id.count_down_text_tk);
        countDownClockTextView.setTime(hour, min);
    }

    private void setTndCountDownTime(int hour, int min) {
        CountDownClockTextView countDownClockTextViewTnd = (CountDownClockTextView) view.findViewById(R.id.count_down_text_tnd);
        countDownClockTextViewTnd.setTime(hour, min);
    }

    private void setCurrentDateText(String date) {
        TextView textView = (TextView) view.findViewById(R.id.date_text);
        textView.setText(date);
        this.currentDateString = date;
    }

    private void setVisibleTkNextButton(boolean isVisible) {
        view.findViewById(R.id.tk_next_bus_time_button).setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void setVisibleTkBeforeButton(boolean isVisible) {
        view.findViewById(R.id.tk_before_bus_time_button).setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void setVisibleTkNoBusLayout(boolean isVisible) {
        view.findViewById(R.id.tk_no_bus_layout).setVisibility(isVisible ? View.VISIBLE : View.GONE);
        setVisibleTkNextButton(!isVisible);
        setVisibleTkBeforeButton(!isVisible);
    }

    private void setVisibleTndNextButton(boolean isVisible) {
        view.findViewById(R.id.tnd_next_bus_time_button).setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void setVisibleTndBeforeButton(boolean isVisible) {
        view.findViewById(R.id.tnd_before_bus_time_button).setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void setVisibleTndNoBusLayout(boolean isVisible) {
        view.findViewById(R.id.tnd_no_bus_layout).setVisibility(isVisible ? View.VISIBLE : View.GONE);
        setVisibleTndNextButton(!isVisible);
        setVisibleTndBeforeButton(!isVisible);
    }

    private void checkTkCurrentNextBus() {
        if (tkCurrentNextBusPosition == -1) {
            return;
        }
        Time tkTime = new Time();
        Time currentTime = TimeUtil.initCurrentTime();
        tkTime.setTime(tkTimeList.get(tkCurrentNextBusPosition).hour, tkTimeList.get(tkCurrentNextBusPosition).min, 0);
        if (TimeUtil.isOverTime(tkTime, currentTime)) {
            if (isTkLastBus(tkCurrentNextBusPosition)) {
//        Fixme その日のバスが無いとき
                tkCurrentNextBusPosition = -1;
                setVisibleTkNoBusLayout(true);
                return;
            }
            tkCurrentNextBusPosition++;
            setVisibleTkBeforeButton(!isTkFirstBus());
        }
    }

    private void checkTndCurrentNextBus() {
        if (tndCurrentNextBusPosition == -1) {
            return;
        }
        Time tndTime = new Time();
        Time currentTime = TimeUtil.initCurrentTime();
        tndTime.setTime(tndTimeList.get(tndCurrentNextBusPosition).hour, tndTimeList.get(tndCurrentNextBusPosition).min, 0);
        if (TimeUtil.isOverTime(tndTime, currentTime)) {
            if (isTndLastBus(tndCurrentNextBusPosition)) {
//        Fixme その日のバスが無いとき
                tndCurrentNextBusPosition = -1;
                setVisibleTndNoBusLayout(true);
                return;
            }
            tndCurrentNextBusPosition++;
            setVisibleTndBeforeButton(!isTndFirstBus());
        }
    }

    private void checkDateChange() {
        if (isDateChanged()) {
//            Fixme 初期化or更新処理
        }
    }

    private boolean isDateChanged() {
        return !DateUtil.getTodayString(getActivity()).equals(currentDateString);
    }

    private boolean isTkFirstBus() {
        return getCurrentTkBusTimePosition() == tkCurrentNextBusPosition;
    }

    private boolean isTndFirstBus() {
        return getCurrentTndBusTimePosition() == tndCurrentNextBusPosition;
    }

    private boolean isTkLastBus() {
        return isTkLastBus(getCurrentTkBusTimePosition());
    }

    private boolean isTkLastBus(int position) {
        return (tkTimeList.size() - 1) == position;
    }

    private boolean isTndLastBus() {
        return isTndLastBus(getCurrentTndBusTimePosition());
    }

    private boolean isTndLastBus(int position) {
        return (tndTimeList.size() - 1) == position;
    }

    private List<BusTime> getTkBusTimeList(int week) {
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            return activity.getTkBusTimeList(reciprocate, week);
        }
        return null;
    }

    private List<BusTime> getTndBusTimeList(int week) {
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            return activity.getTndBusTimeList(reciprocate, week);
        }
        return null;
    }
}
