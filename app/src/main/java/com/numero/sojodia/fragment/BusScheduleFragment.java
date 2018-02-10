package com.numero.sojodia.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.numero.sojodia.contract.BusScheduleContract;
import com.numero.sojodia.model.Reciprocate;
import com.numero.sojodia.model.Route;
import com.numero.sojodia.util.BroadCastUtil;
import com.numero.sojodia.view.adapter.BusTimePagerAdapter;
import com.numero.sojodia.model.BusTime;
import com.numero.sojodia.model.Time;
import com.numero.sojodia.util.DateUtil;
import com.numero.sojodia.util.TimeUtil;
import com.numero.sojodia.view.CountDownClockTextView;
import com.numero.sojodia.view.NotSwipeViewPager;
import com.numero.sojodia.R;

import java.util.List;

public class BusScheduleFragment extends Fragment implements BusScheduleContract.View {

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

    private BusScheduleFragmentListener listener;

    private BusScheduleContract.Presenter presenter;

    private String currentDateString;

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
        if (context instanceof BusScheduleFragmentListener) {
            listener = (BusScheduleFragmentListener) context;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Reciprocate reciprocate = (Reciprocate) getArguments().getSerializable(Reciprocate.class.getSimpleName());
        listener.onActivityCreated(this, reciprocate);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bus_schedule_fragment, container, false);

        currentDateString = DateUtil.getTodayStringOnlyFigure();

        initCountDownClockTextView(view);
        initTimeTableButton(view);
        initNextPreviewButton(view);
        tkBusTimePager = view.findViewById(R.id.tk_bus_time_pager);
        tndBusTimePager = view.findViewById(R.id.tnd_bus_time_pager);
        initNoBusLayout(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter == null) {
            return;
        }
        presenter.subscribe();
        presenter.onTimeChanged(DateUtil.getWeek());
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unSubscribe();
    }

    @Override
    public void setPresenter(BusScheduleContract.Presenter presenter) {
        this.presenter = presenter;
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
                presenter.nextTkBus();
            }
        });

        tndCountDownClockTextView = view.findViewById(R.id.tnd_count_down_text);
        tndCountDownClockTextView.setOnTimeChangedListener(new CountDownClockTextView.OnTimeChangedListener() {
            @Override
            public void onTimeChanged() {
            }

            @Override
            public void onTimeLimit() {
                presenter.nextTndBus();
            }
        });
    }

    private void initTimeTableButton(View view) {
        view.findViewById(R.id.tk_time_table_button).setOnClickListener(v -> presenter.showTimeTableDialog(Route.TK));
        view.findViewById(R.id.tnd_time_table_button).setOnClickListener(v -> presenter.showTimeTableDialog(Route.TND));
    }

    private void initNextPreviewButton(View view) {
        tkNextBusTimeButton = view.findViewById(R.id.tk_next_bus_time_button);
        tkPreviewBusTimeButton = view.findViewById(R.id.tk_before_bus_time_button);
        tndNextBusTimeButton = view.findViewById(R.id.tnd_next_bus_time_button);
        tndPreviewBusTimeButton = view.findViewById(R.id.tnd_before_bus_time_button);

        tkNextBusTimeButton.setOnClickListener(v -> presenter.nextTkBus());
        tkPreviewBusTimeButton.setOnClickListener(v -> presenter.previewTkBus());
        tndNextBusTimeButton.setOnClickListener(v -> presenter.nextTndBus());
        tndPreviewBusTimeButton.setOnClickListener(v -> presenter.previewTndBus());
    }

    private void initNoBusLayout(View view) {
        tkNoBusLayout = view.findViewById(R.id.tk_no_bus_layout);
        tndNoBusLayout = view.findViewById(R.id.tnd_no_bus_layout);
    }

    private void checkDateChange() {
        if (isDateChanged()) {
            currentDateString = DateUtil.getTodayStringOnlyFigure();
            BroadCastUtil.sendBroadCast(getActivity(), BroadCastUtil.ACTION_CHANGED_DATE);
            presenter.onTimeChanged(DateUtil.getWeek());
        }
    }

    private boolean isDateChanged() {
        return !DateUtil.getTodayStringOnlyFigure().equals(currentDateString);
    }

    @Override
    public void showTkBusTimeList(List<BusTime> busTimeList) {
        BusTimePagerAdapter tkPagerAdapter = new BusTimePagerAdapter();
        tkPagerAdapter.setBusTimeList(busTimeList);
        tkBusTimePager.setAdapter(tkPagerAdapter);
    }

    @Override
    public void showTndBusTimeList(List<BusTime> busTimeList) {
        BusTimePagerAdapter tndPagerAdapter = new BusTimePagerAdapter();
        tndPagerAdapter.setBusTimeList(busTimeList);
        tndBusTimePager.setAdapter(tndPagerAdapter);
    }

    @Override
    public void selectTkCurrentBusPosition(int position) {
        tkBusTimePager.setCurrentItem(position, true);
    }

    @Override
    public void startTkCountDown(BusTime busTime) {
        Time time = new Time(busTime.hour, busTime.min, 0);
        Time countTime = TimeUtil.getCountTime(time);
        tkCountDownClockTextView.setTime(countTime.getHour(), countTime.getMin());
    }

    @Override
    public void selectTndCurrentBusPosition(int position) {
        tndBusTimePager.setCurrentItem(position, true);
    }

    @Override
    public void startTndCountDown(BusTime busTime) {
        Time time = new Time(busTime.hour, busTime.min, 0);
        Time countTime = TimeUtil.getCountTime(time);
        tndCountDownClockTextView.setTime(countTime.getHour(), countTime.getMin());
    }

    @Override
    public void showTkNextButton() {
        tkNextBusTimeButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTkPreviewButton() {
        tkPreviewBusTimeButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTkNoBusLayout() {
        tkNoBusLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTndNextButton() {
        tndNextBusTimeButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTndPreviewButton() {
        tndPreviewBusTimeButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTndNoBusLayout() {
        tndNoBusLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideTkNextButton() {
        tkNextBusTimeButton.setVisibility(View.GONE);
    }

    @Override
    public void hideTkPreviewButton() {
        tkPreviewBusTimeButton.setVisibility(View.GONE);
    }

    @Override
    public void hideTkNoBusLayout() {
        tkNoBusLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideTndNextButton() {
        tndNextBusTimeButton.setVisibility(View.GONE);
    }

    @Override
    public void hideTndPreviewButton() {
        tndPreviewBusTimeButton.setVisibility(View.GONE);
    }

    @Override
    public void hideTndNoBusLayout() {
        tndNoBusLayout.setVisibility(View.GONE);
    }

    @Override
    public void showTimeTableDialog(Route route, Reciprocate reciprocate) {
        listener.showTimeTableDialog(route, reciprocate);
    }

    public interface BusScheduleFragmentListener {
        void onActivityCreated(BusScheduleFragment fragment, Reciprocate reciprocate);

        void showTimeTableDialog(Route route, Reciprocate reciprocate);
    }
}
