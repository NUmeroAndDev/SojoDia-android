package com.numero.sojodia.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.numero.sojodia.adapter.BusTimePagerAdapter;
import com.numero.sojodia.manager.BusDataManager;
import com.numero.sojodia.model.BusTime;
import com.numero.sojodia.model.Reciprocate;
import com.numero.sojodia.model.Time;
import com.numero.sojodia.util.DateUtil;
import com.numero.sojodia.util.TimeUtil;
import com.numero.sojodia.view.CountDownClockTextView;
import com.numero.sojodia.view.NotSwipeViewPager;
import com.numero.sojodia.view.TimeTableDialog;
import com.numero.sojodia.R;

import java.util.ArrayList;
import java.util.List;

public class BusScheduleFragment extends Fragment {

    private CountDownClockTextView tkCountDownClockTextView;
    private CountDownClockTextView tndCountDownClockTextView;
    private View tkNextBusTimeButton;
    private View tkPreviewBusTimeButton;
    private View tndNextBusTimeButton;
    private View tndPreviewBusTimeButton;
    private NotSwipeViewPager tkBusTimePager;
    private NotSwipeViewPager tndBusTimePager;
    private View tkNoBusLayout;
    private View tndNoBusLayout;

    private List<BusTime> tkTimeList = new ArrayList<>();
    private List<BusTime> tndTimeList = new ArrayList<>();
    private BusDataManager busDataManager;
    private View view;

    private int tkCurrentNextBusPosition;
    private int tndCurrentNextBusPosition;
    private Reciprocate reciprocate;
    private String currentDateString;

    public BusScheduleFragment() {
    }

    public static BusScheduleFragment newInstance(Reciprocate reciprocate) {
        BusScheduleFragment fragment = new BusScheduleFragment();
        Bundle args = new Bundle();
        args.putSerializable(Reciprocate.class.getSimpleName(), reciprocate);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        busDataManager = BusDataManager.getInstance(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reciprocate = (Reciprocate) getArguments().getSerializable(Reciprocate.class.getSimpleName());
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bus_schedule_fragment, null);

        currentDateString = DateUtil.getTodayStringOnlyFigure();

        initBusTimeList(DateUtil.getWeek());

        initCountDownClockTextView(view);
        initTimeTableButton(view);
        initNextPreviewButton(view);
        initTkBusTimePagerView(view);
        initTndBusTimePagerView(view);
        initNoBusLayout(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        reset();
    }

    private void initCountDownClockTextView(View view) {
        tkCountDownClockTextView = view.findViewById(R.id.tk_count_down_text);
        tkCountDownClockTextView.setOnTimeChangedListener(new CountDownClockTextView.OnTimeChangedListener() {
            @Override
            public void onTimeChanged() {
                checkDateChange();
                if (tkCurrentNextBusPosition == -1 || tkTimeList == null) {
                    return;
                }
                checkTkCurrentNextBus();
            }

            @Override
            public void onTimeLimit() {
                if (tkCurrentNextBusPosition == -1) {
                    return;
                }
                setCurrentTkBusTime(getCurrentTkBusTimePosition() + 1, true);
            }
        });

        tndCountDownClockTextView = view.findViewById(R.id.tnd_count_down_text);
        tndCountDownClockTextView.setOnTimeChangedListener(new CountDownClockTextView.OnTimeChangedListener() {
            @Override
            public void onTimeChanged() {
                if (tndCurrentNextBusPosition == -1 || tndTimeList == null) {
                    return;
                }
                checkTndCurrentNextBus();
            }

            @Override
            public void onTimeLimit() {
                if (tndCurrentNextBusPosition == -1) {
                    return;
                }
                setCurrentTndBusTime(getCurrentTndBusTimePosition() + 1, true);
            }
        });
    }

    private void initBusTimeList(int week) {
        busDataManager.setWeekAndReciprocate(week, reciprocate);
        tkTimeList = busDataManager.getTkTimeList();
        tndTimeList = busDataManager.getTndTimeList();
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
                tkCountDownClockTextView.setTime(countTime.hour, countTime.min);
                setVisibleTkBeforeButton(!isTkFirstBus());
                setVisibleTkNextButton(!isTkLastBus());
                return;
            }
        }
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
                tndCountDownClockTextView.setTime(countTime.hour, countTime.min);
                setVisibleTndBeforeButton(!isTndFirstBus());
                setVisibleTndNextButton(!isTndLastBus());
                return;
            }
        }
        setVisibleTndNoBusLayout(true);
    }

    private void initTkBusTimePagerView(View view) {
        tkBusTimePager = view.findViewById(R.id.tk_bus_time_pager);
        tkBusTimePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Time tkTime = new Time(tkTimeList.get(position).hour, tkTimeList.get(position).min, 0);
                Time countTime = TimeUtil.getCountTime(tkTime);
                tkCountDownClockTextView.setTime(countTime.hour, countTime.min);
                setVisibleTkBeforeButton(!isTkFirstBus());
                setVisibleTkNextButton(!isTkLastBus());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initTndBusTimePagerView(View view) {
        tndBusTimePager = view.findViewById(R.id.tnd_bus_time_pager);
        tndBusTimePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Time tndTime = new Time(tndTimeList.get(position).hour, tndTimeList.get(position).min, 0);
                Time countTime = TimeUtil.getCountTime(tndTime);
                tndCountDownClockTextView.setTime(countTime.hour, countTime.min);
                setVisibleTndBeforeButton(!isTndFirstBus());
                setVisibleTndNextButton(!isTndLastBus());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initTimeTableButton(View view) {
        view.findViewById(R.id.tk_time_table_button).setOnClickListener(new View.OnClickListener() {
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
        view.findViewById(R.id.tnd_time_table_button).setOnClickListener(new View.OnClickListener() {
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

    private void initNextPreviewButton(View view) {
        tkNextBusTimeButton = view.findViewById(R.id.tk_next_bus_time_button);
        tkPreviewBusTimeButton = view.findViewById(R.id.tk_before_bus_time_button);
        tndNextBusTimeButton = view.findViewById(R.id.tnd_next_bus_time_button);
        tndPreviewBusTimeButton = view.findViewById(R.id.tnd_before_bus_time_button);

        tkNextBusTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentTkBusTime(getCurrentTkBusTimePosition() + 1, true);
            }
        });
        tkPreviewBusTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentTkBusTime(getCurrentTkBusTimePosition() - 1, true);
            }
        });
        tndNextBusTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentTndBusTime(getCurrentTndBusTimePosition() + 1, true);
            }
        });
        tndPreviewBusTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setCurrentTndBusTime(getCurrentTndBusTimePosition() - 1, true);
            }
        });
    }

    private void initNoBusLayout(View view) {
        tkNoBusLayout = view.findViewById(R.id.tk_no_bus_layout);
        tndNoBusLayout = view.findViewById(R.id.tnd_no_bus_layout);
    }

    private void initTkBusPagerView() {
        BusTimePagerAdapter tkPagerAdapter = new BusTimePagerAdapter(getContext());
        tkPagerAdapter.setBusTimeList(tkTimeList);
        tkBusTimePager.setAdapter(tkPagerAdapter);
    }

    private void initTndBusPagerView() {
        BusTimePagerAdapter tndPagerAdapter = new BusTimePagerAdapter(getContext());
        tndPagerAdapter.setBusTimeList(tndTimeList);
        tndBusTimePager.setAdapter(tndPagerAdapter);
    }

    private void setCurrentTkBusTime(int position) {
        setCurrentTkBusTime(position, false);
    }

    private void setCurrentTkBusTime(int position, boolean isAnimation) {
        tkBusTimePager.setCurrentItem(position, isAnimation);
    }

    private int getCurrentTkBusTimePosition() {
        return tkBusTimePager.getCurrentItem();
    }

    private void setCurrentTndBusTime(int position) {
        setCurrentTndBusTime(position, false);
    }

    private void setCurrentTndBusTime(int position, boolean isAnimation) {
        tndBusTimePager.setCurrentItem(position, isAnimation);
    }

    private int getCurrentTndBusTimePosition() {
        return tndBusTimePager.getCurrentItem();
    }

    private void setCurrentDateText(String date) {
        //TODO 日付の表示機能は消す?
        TextView textView = (TextView) view.findViewById(R.id.date_text);
        textView.setText(date);
    }

    private void setVisibleTkNextButton(boolean isVisible) {
        tkNextBusTimeButton.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void setVisibleTkBeforeButton(boolean isVisible) {
        tkPreviewBusTimeButton.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void setVisibleTkNoBusLayout(boolean isVisible) {
        tkNoBusLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        setVisibleTkNextButton(!isVisible);
        setVisibleTkBeforeButton(!isVisible);
    }

    private void setVisibleTndNextButton(boolean isVisible) {
        tndNextBusTimeButton.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void setVisibleTndBeforeButton(boolean isVisible) {
        tndPreviewBusTimeButton.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void setVisibleTndNoBusLayout(boolean isVisible) {
        tndNoBusLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        setVisibleTndNextButton(!isVisible);
        setVisibleTndBeforeButton(!isVisible);
    }

    private void checkTkCurrentNextBus() {
        Time tkTime = new Time();
        Time currentTime = TimeUtil.initCurrentTime();
        tkTime.setTime(tkTimeList.get(tkCurrentNextBusPosition).hour, tkTimeList.get(tkCurrentNextBusPosition).min, 0);
        if (TimeUtil.isOverTime(tkTime, currentTime)) {
            if (busDataManager.isTkLastBus(tkCurrentNextBusPosition)) {
                tkCurrentNextBusPosition = -1;
                setVisibleTkNoBusLayout(true);
                return;
            }
            tkCurrentNextBusPosition++;
            setVisibleTkBeforeButton(!isTkFirstBus());
        }
    }

    private void checkTndCurrentNextBus() {
        Time tndTime = new Time();
        Time currentTime = TimeUtil.initCurrentTime();
        tndTime.setTime(tndTimeList.get(tndCurrentNextBusPosition).hour, tndTimeList.get(tndCurrentNextBusPosition).min, 0);
        if (TimeUtil.isOverTime(tndTime, currentTime)) {
            if (busDataManager.isTndLastBus(tndCurrentNextBusPosition)) {
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
            currentDateString = DateUtil.getTodayStringOnlyFigure();
            reset();
        }
    }

    private void reset() {
        tkCurrentNextBusPosition = -1;
        tndCurrentNextBusPosition = -1;
        setVisibleTkNoBusLayout(false);
        setVisibleTndNoBusLayout(false);
        setCurrentDateText(DateUtil.getTodayString(getActivity()));
        initBusTimeList(DateUtil.getWeek());
        initTkBusPagerView();
        initTndBusPagerView();
        findCurrentTkBusTime();
        findCurrentTndBusTime();
    }

    private boolean isDateChanged() {
        return !DateUtil.getTodayStringOnlyFigure().equals(currentDateString);
    }

    private boolean isTkFirstBus() {
        return getCurrentTkBusTimePosition() == tkCurrentNextBusPosition;
    }

    private boolean isTndFirstBus() {
        return getCurrentTndBusTimePosition() == tndCurrentNextBusPosition;
    }

    private boolean isTkLastBus() {
        return busDataManager.isTkLastBus(getCurrentTkBusTimePosition());
    }

    private boolean isTndLastBus() {
        return busDataManager.isTndLastBus(getCurrentTndBusTimePosition());
    }

    private List<BusTime> getTkBusTimeList(int week) {
        switch (reciprocate) {
            case GOING:
                return busDataManager.getTkGoingBusTimeList(week);
            case RETURN:
                return busDataManager.getTkReturnBusTimeList(week);
            default:
                return new ArrayList<>();
        }
    }

    private List<BusTime> getTndBusTimeList(int week) {
        switch (reciprocate) {
            case GOING:
                return busDataManager.getTndGoingBusTimeList(week);
            case RETURN:
                return busDataManager.getTndReturnBusTimeList(week);
            default:
                return new ArrayList<>();
        }
    }
}
