package com.numero.sojodia.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.numero.sojodia.activity.MainActivity;
import com.numero.sojodia.adapter.BusTimePagerAdapter;
import com.numero.sojodia.manager.BusDataManager;
import com.numero.sojodia.model.BusTime;
import com.numero.sojodia.model.Time;
import com.numero.sojodia.util.DateUtil;
import com.numero.sojodia.util.TimeUtil;
import com.numero.sojodia.view.CountDownClockTextView;
import com.numero.sojodia.view.TimeTableDialog;
import com.numero.sojodia.R;
import com.numero.sojodia.util.Constants;

import java.util.List;

public class BusScheduleFragment extends Fragment {

    private List<BusTime> tkTimeList;
    private List<BusTime> tndTimeList;
    private BusTimePagerAdapter tkPagerAdapter;
    private BusTimePagerAdapter tndPagerAdapter;
    private View view;

    private int reciprocating;
    private String currentDateString;

    public BusScheduleFragment() {
    }

    public static BusScheduleFragment newInstance(int reciprocating) {
        BusScheduleFragment fragment = new BusScheduleFragment();
        Bundle args = new Bundle();
        args.putInt(Constants.RECIPROCATING, reciprocating);
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

        reciprocating = getArguments().getInt(Constants.RECIPROCATING);

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
        initBusTimeList(BusDataManager.WEEKDAY);
        setListTkBusTimePagerView();
        setListTndBusTimePagerView();
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
            }

            @Override
            public void onTimeLimit() {
//                Fixme 次の時刻をセット
                setCurrentTkBusTime(getCurrentTkBusTimePosition() + 1);
            }
        });
        CountDownClockTextView countDownClockTextViewTnd = (CountDownClockTextView) view.findViewById(R.id.count_down_text_tnd);
        countDownClockTextViewTnd.setOnTimeChangedListener(new CountDownClockTextView.OnTimeChangedListener() {
            @Override
            public void onTimeChanged() {
                checkDateChange();
            }

            @Override
            public void onTimeLimit() {
//                Fixme 次の時刻をセット
                setCurrentTndBusTime(getCurrentTndBusTimePosition() + 1);
            }
        });
    }

    private void initBusTimeList(int week) {
//        Fixme 日付変更時のリスト
        if (getActivity() instanceof MainActivity) {
            MainActivity activity = (MainActivity) getActivity();
            tkTimeList = activity.getTkBusTimeList(reciprocating, week);
            tndTimeList = activity.getTndBusTimeList(reciprocating, week);
        }
    }

    private void findCurrentTkBusTime() {
        int position;
        Time tkTime = new Time();
        Time currentTime = TimeUtil.initCurrentTime();
        for (position = 0; position < tkTimeList.size(); position++) {
            tkTime.setTime(tkTimeList.get(position).hour, tkTimeList.get(position).min, 0);
            if (TimeUtil.isOverTime(tkTime, currentTime)) {
                setCurrentTkBusTime(position);
                Time countTime = TimeUtil.getCountTime(tkTime);
                setTkCountDownTime(countTime.hour, countTime.min, countTime.sec);
                return;
            }
        }
//        Fixme その日のバスが無いとき
    }

    private void findCurrentTndBusTime() {
        int position;
        Time tndTime = new Time();
        Time currentTime = TimeUtil.initCurrentTime();
        for (position = 0; position < tndTimeList.size(); position++) {
            tndTime.setTime(tndTimeList.get(position).hour, tndTimeList.get(position).min, 0);
            if (TimeUtil.isOverTime(tndTime, currentTime)) {
                setCurrentTndBusTime(position);
                Time countTime = TimeUtil.getCountTime(tndTime);
                setTndCountDownTime(countTime.hour, countTime.min, countTime.sec);
                return;
            }
        }
//        Fixme その日のバスが無いとき
    }

    private void initTkBusTimePagerView(View view) {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.tk_bus_time_pager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Time tkTime = new Time(tkTimeList.get(position).hour, tkTimeList.get(position).min, 0);
                Time countTime = TimeUtil.getCountTime(tkTime);
                setTkCountDownTime(countTime.hour, countTime.min, countTime.sec);
                setVisibleTkBeforeButton(!isTkFirstBus());
                setVisibleTkNextButton(!isTkLastBus());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initTndBusTimePagerView(View view) {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.tnd_bus_time_pager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Time tndTime = new Time(tndTimeList.get(position).hour, tndTimeList.get(position).min, 0);
                Time countTime = TimeUtil.getCountTime(tndTime);
                setTndCountDownTime(countTime.hour, countTime.min, countTime.sec);
                setVisibleTndBeforeButton(!isTndFirstBus());
                setVisibleTndNextButton(!isTndLastBus());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void initTimeTableButton(View view) {
        ImageButton tkTimeTableButton = (ImageButton) view.findViewById(R.id.time_table_tk_button);
        ImageButton tndTimeTableButton = (ImageButton) view.findViewById(R.id.time_table_tnd_button);
        tkTimeTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeTableDialog.init(getActivity(), Constants.ROUTE_TK, reciprocating).show();
            }
        });
        tndTimeTableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeTableDialog.init(getActivity(), Constants.ROUTE_TND, reciprocating).show();
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

    private void setListTkBusTimePagerView() {
        tkPagerAdapter = new BusTimePagerAdapter();
        tkPagerAdapter.setBusTimeList(tkTimeList);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.tk_bus_time_pager);
        viewPager.setAdapter(tkPagerAdapter);
    }

    private void setListTndBusTimePagerView() {
        tndPagerAdapter = new BusTimePagerAdapter();
        tndPagerAdapter.setBusTimeList(tndTimeList);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.tnd_bus_time_pager);
        viewPager.setAdapter(tndPagerAdapter);
    }

    private void setCurrentTkBusTime(int position) {
        setCurrentTkBusTime(position, false);
    }

    private void setCurrentTkBusTime(int position, boolean isAnimation) {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.tk_bus_time_pager);
        viewPager.setCurrentItem(position, isAnimation);
    }

    private int getCurrentTkBusTimePosition() {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.tk_bus_time_pager);
        return viewPager.getCurrentItem();
    }

    private void setCurrentTndBusTime(int position) {
        setCurrentTndBusTime(position, false);
    }

    private void setCurrentTndBusTime(int position, boolean isAnimation) {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.tnd_bus_time_pager);
        viewPager.setCurrentItem(position, isAnimation);
    }

    private int getCurrentTndBusTimePosition() {
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.tnd_bus_time_pager);
        return viewPager.getCurrentItem();
    }

    private void setTkCountDownTime(int hour, int min, int sec) {
        CountDownClockTextView countDownClockTextView = (CountDownClockTextView) view.findViewById(R.id.count_down_text_tk);
        countDownClockTextView.setTime(hour, min, sec);
    }

    private void setTndCountDownTime(int hour, int min, int sec) {
        CountDownClockTextView countDownClockTextViewTnd = (CountDownClockTextView) view.findViewById(R.id.count_down_text_tnd);
        countDownClockTextViewTnd.setTime(hour, min, sec);
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

    private void setVisibleTndNextButton(boolean isVisible) {
        view.findViewById(R.id.tnd_next_bus_time_button).setVisibility(isVisible ? View.VISIBLE : View.GONE);
    }

    private void setVisibleTndBeforeButton(boolean isVisible) {
        view.findViewById(R.id.tnd_before_bus_time_button).setVisibility(isVisible ? View.VISIBLE : View.GONE);
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
        return getCurrentTkBusTimePosition() == 0;
    }

    private boolean isTndFirstBus() {
        return getCurrentTndBusTimePosition() == 0;
    }

    private boolean isTkLastBus() {
        return getCurrentTkBusTimePosition() == (tkTimeList.size() - 1);
    }

    private boolean isTndLastBus() {
        return getCurrentTndBusTimePosition() == (tndTimeList.size() - 1);
    }
}
