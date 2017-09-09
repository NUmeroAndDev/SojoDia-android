package com.numero.sojodia.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.numero.sojodia.adapter.BusScheduleFragmentPagerAdapter;
import com.numero.sojodia.helper.ParseHelper;
import com.numero.sojodia.model.BusTime;
import com.numero.sojodia.service.UpdateBusDataService;
import com.numero.sojodia.util.BroadCastUtil;
import com.numero.sojodia.view.NeedRestartDialog;
import com.numero.sojodia.R;
import com.numero.sojodia.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<BusTime> tkBusTimeListOnWeekdayGoing = new ArrayList<>();
    private List<BusTime> tkBusTimeListOnSaturdayGoing = new ArrayList<>();
    private List<BusTime> tkBusTimeListOnSundayGoing = new ArrayList<>();
    private List<BusTime> tkBusTimeListOnWeekdayReturn = new ArrayList<>();
    private List<BusTime> tkBusTimeListOnSaturdayReturn = new ArrayList<>();
    private List<BusTime> tkBusTimeListOnSundayReturn = new ArrayList<>();
    private List<BusTime> tndBusTimeListOnWeekdayGoing = new ArrayList<>();
    private List<BusTime> tndBusTimeListOnSaturdayGoing = new ArrayList<>();
    private List<BusTime> tndBusTimeListOnSundayGoing = new ArrayList<>();
    private List<BusTime> tndBusTimeListOnWeekdayReturn = new ArrayList<>();
    private List<BusTime> tndBusTimeListOnSaturdayReturn = new ArrayList<>();
    private List<BusTime> tndBusTimeListOnSundayReturn = new ArrayList<>();

    private final BroadcastReceiver finishDownloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            showNeedRestartDialog();
        }
    };

    private ViewPager viewPager;
    private BusScheduleFragmentPagerAdapter fragmentPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBusData();
        initViews();

        String shortcutIntent = getIntent().getStringExtra("shortcut");
        if (shortcutIntent != null) {
            viewPager.setCurrentItem(shortcutIntent.equals("coming_home") ? 1 : 0);
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(finishDownloadReceiver, new IntentFilter(BroadCastUtil.ACTION_FINISH_DOWNLOAD));
        startCheckUpdateService();
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(finishDownloadReceiver);
        super.onDestroy();
    }

    private void initBusData() {
        ParseHelper.parse(this, "TkToKutc.csv", tkBusTimeListOnWeekdayGoing, tkBusTimeListOnSaturdayGoing, tkBusTimeListOnSundayGoing);
        ParseHelper.parse(this, "KutcToTk.csv", tkBusTimeListOnWeekdayReturn, tkBusTimeListOnSaturdayReturn, tkBusTimeListOnSundayReturn);
        ParseHelper.parse(this, "TndToKutc.csv", tndBusTimeListOnWeekdayGoing, tndBusTimeListOnSaturdayGoing, tndBusTimeListOnSundayGoing);
        ParseHelper.parse(this, "KutcToTnd.csv", tndBusTimeListOnWeekdayReturn, tndBusTimeListOnSaturdayReturn, tndBusTimeListOnSundayReturn);
    }

    private void initViews() {
        fragmentPagerAdapter = new BusScheduleFragmentPagerAdapter(this, getSupportFragmentManager());
        viewPager = findViewById(R.id.container);
        viewPager.setAdapter(fragmentPagerAdapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void startCheckUpdateService() {
        Intent intent = new Intent(MainActivity.this, UpdateBusDataService.class);
        startService(intent);
    }

    private void showNeedRestartDialog() {
        NeedRestartDialog.init(this)
                .setOnPositiveButtonClickListener(new NeedRestartDialog.OnPositiveButtonClickListener() {
                    @Override
                    public void onClick() {
                        recreate();
                    }
                }).show();
    }

    public List<BusTime> getTkBusTimeList(int reciprocate, int week) {
        if (reciprocate == BusScheduleFragmentPagerAdapter.RECIPROCATE_GOING) {
            return getTkBusTimeListGoing(week);
        }
        return getTkBusTimeListReturn(week);
    }

    private List<BusTime> getTkBusTimeListGoing(int week) {
        switch (week) {
            case DateUtil.SATURDAY:
                return tkBusTimeListOnSaturdayGoing;
            case DateUtil.SUNDAY:
                return tkBusTimeListOnSundayGoing;
        }
        return tkBusTimeListOnWeekdayGoing;
    }

    private List<BusTime> getTkBusTimeListReturn(int week) {
        switch (week) {
            case DateUtil.SATURDAY:
                return tkBusTimeListOnSaturdayReturn;
            case DateUtil.SUNDAY:
                return tkBusTimeListOnSundayReturn;
        }
        return tkBusTimeListOnWeekdayReturn;
    }

    public List<BusTime> getTndBusTimeList(int reciprocate, int week) {
        if (reciprocate == BusScheduleFragmentPagerAdapter.RECIPROCATE_GOING) {
            return getTndBusTimeListGoing(week);
        }
        return getTndBusTimeListReturn(week);
    }

    public List<BusTime> getTndBusTimeListGoing(int week) {
        switch (week) {
            case DateUtil.SATURDAY:
                return tndBusTimeListOnSaturdayGoing;
            case DateUtil.SUNDAY:
                return tndBusTimeListOnSundayGoing;
        }
        return tndBusTimeListOnWeekdayGoing;
    }

    public List<BusTime> getTndBusTimeListReturn(int week) {
        switch (week) {
            case DateUtil.SATURDAY:
                return tndBusTimeListOnSaturdayReturn;
            case DateUtil.SUNDAY:
                return tndBusTimeListOnSundayReturn;
        }
        return tndBusTimeListOnWeekdayReturn;
    }
}
