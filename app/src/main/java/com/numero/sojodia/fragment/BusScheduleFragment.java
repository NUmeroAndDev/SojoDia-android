package com.numero.sojodia.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.numero.sojodia.view.adapter.BusTimePagerAdapter;
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
            }

            @Override
            public void onTimeLimit() {
                if (busDataManager.isNoTkBus()) {
                    return;
                }
                busDataManager.nextTkBusTime();
                tkBusTimePager.setCurrentItem(busDataManager.getTkBusPosition(), true);
            }
        });

        tndCountDownClockTextView = view.findViewById(R.id.tnd_count_down_text);
        tndCountDownClockTextView.setOnTimeChangedListener(new CountDownClockTextView.OnTimeChangedListener() {
            @Override
            public void onTimeChanged() {
            }

            @Override
            public void onTimeLimit() {
                if (busDataManager.isNoTndBus()) {
                    return;
                }
                busDataManager.nextTndBusTime();
                tndBusTimePager.setCurrentItem(busDataManager.getTndBusPosition(), true);
            }
        });
    }

    private void initBusTimeList(int week) {
        busDataManager.setWeekAndReciprocate(week, reciprocate);
        tkTimeList = busDataManager.getTkTimeList();
        tndTimeList = busDataManager.getTndTimeList();
    }

    private void initTkBusTimePagerView(View view) {
        tkBusTimePager = view.findViewById(R.id.tk_bus_time_pager);
        tkBusTimePager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setupTkCountDown(position);
                setVisibleTkBeforeButton(busDataManager.canPreviewTkTime());
                setVisibleTkNextButton(busDataManager.canNextTkTime());
                setVisibleTkNoBusLayout(busDataManager.isNoTkBus());
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
                setupTndCountDown(position);
                setVisibleTndBeforeButton(busDataManager.canPreviewTndTime());
                setVisibleTndNextButton(busDataManager.canNextTndTime());
                setVisibleTndNoBusLayout(busDataManager.isNoTndBus());
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
                TimeTableDialog.init(getActivity(), busDataManager)
                        .setReciprocate(reciprocate)
                        .setRouteTk()
                        .show();
            }
        });
        view.findViewById(R.id.tnd_time_table_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeTableDialog.init(getActivity(), busDataManager)
                        .setReciprocate(reciprocate)
                        .setRouteTnd()
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
                busDataManager.nextTkBusTime();
                tkBusTimePager.setCurrentItem(busDataManager.getTkBusPosition(), true);
            }
        });
        tkPreviewBusTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busDataManager.previewTkBusTime();
                tkBusTimePager.setCurrentItem(busDataManager.getTkBusPosition(), true);
            }
        });
        tndNextBusTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busDataManager.nextTndBusTime();
                tndBusTimePager.setCurrentItem(busDataManager.getTndBusPosition(), true);
            }
        });
        tndPreviewBusTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                busDataManager.previewTndBusTime();
                tndBusTimePager.setCurrentItem(busDataManager.getTndBusPosition(), true);
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
        tkBusTimePager.setCurrentItem(busDataManager.getTkBusPosition(), false);
    }

    private void initTndBusPagerView() {
        BusTimePagerAdapter tndPagerAdapter = new BusTimePagerAdapter(getContext());
        tndPagerAdapter.setBusTimeList(tndTimeList);
        tndBusTimePager.setAdapter(tndPagerAdapter);
        tndBusTimePager.setCurrentItem(busDataManager.getTndBusPosition(), false);
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
    }

    private void setVisibleTndNextButton(boolean isVisible) {
        tndNextBusTimeButton.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void setVisibleTndBeforeButton(boolean isVisible) {
        tndPreviewBusTimeButton.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void setVisibleTndNoBusLayout(boolean isVisible) {
        tndNoBusLayout.setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void checkDateChange() {
        if (isDateChanged()) {
            currentDateString = DateUtil.getTodayStringOnlyFigure();
            reset();
        }
    }

    private void reset() {
        setCurrentDateText(DateUtil.getTodayString(getActivity()));
        initBusTimeList(DateUtil.getWeek());
        initTkBusPagerView();
        initTndBusPagerView();
        setupTkCountDown(busDataManager.getTkBusPosition());
        setupTndCountDown(busDataManager.getTndBusPosition());
        setVisibleTkNoBusLayout(busDataManager.isNoTkBus());
        setVisibleTkBeforeButton(busDataManager.canPreviewTkTime());
        setVisibleTkNextButton(busDataManager.canNextTkTime());
        setVisibleTndNoBusLayout(busDataManager.isNoTndBus());
        setVisibleTndBeforeButton(busDataManager.canPreviewTndTime());
        setVisibleTndNextButton(busDataManager.canNextTndTime());
    }

    private void setupTkCountDown(int position) {
        if (busDataManager.isNoTkBus()) {
            setVisibleTkNoBusLayout(true);
            return;
        }
        setVisibleTkNoBusLayout(false);
        BusTime busTime = tkTimeList.get(position);
        Time time = new Time(busTime.hour, busTime.min, 0);
        Time countTime = TimeUtil.getCountTime(time);
        tkCountDownClockTextView.setTime(countTime.hour, countTime.min);
    }

    private void setupTndCountDown(int position) {
        if (busDataManager.isNoTndBus()) {
            setVisibleTndNoBusLayout(true);
            return;
        }
        setVisibleTndNoBusLayout(false);
        BusTime busTime = tndTimeList.get(position);
        Time time = new Time(busTime.hour, busTime.min, 0);
        Time countTime = TimeUtil.getCountTime(time);
        tndCountDownClockTextView.setTime(countTime.hour, countTime.min);
    }

    private boolean isDateChanged() {
        return !DateUtil.getTodayStringOnlyFigure().equals(currentDateString);
    }
}
