package com.numero.sojodia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.numero.sojodia.adapter.BusScheduleFragmentPagerAdapter;
import com.numero.sojodia.helper.ParseHelper;
import com.numero.sojodia.model.BusTime;
import com.numero.sojodia.view.UpdateNotificationDialog;
import com.numero.sojodia.task.UpdateCheckTask;
import com.numero.sojodia.R;
import com.numero.sojodia.util.PreferenceUtil;
import com.numero.sojodia.util.DateUtil;
import com.numero.sojodia.util.NetworkUtil;

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

    private long versionCode = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initBusData();
        initViews();
        checkUpdate();
    }

    private void initBusData() {
        ParseHelper.parse(this, "TkToKutc.csv", tkBusTimeListOnWeekdayGoing, tkBusTimeListOnSaturdayGoing, tkBusTimeListOnSundayGoing);
        ParseHelper.parse(this, "KutcToTk.csv", tkBusTimeListOnWeekdayReturn, tkBusTimeListOnSaturdayReturn, tkBusTimeListOnSundayReturn);
        ParseHelper.parse(this, "TndToKutc.csv", tndBusTimeListOnWeekdayGoing, tndBusTimeListOnSaturdayGoing, tndBusTimeListOnSundayGoing);
        ParseHelper.parse(this, "KutcToTnd.csv", tndBusTimeListOnWeekdayReturn, tndBusTimeListOnSaturdayReturn, tndBusTimeListOnSundayReturn);
    }

    private void initViews() {
        BusScheduleFragmentPagerAdapter fragmentPagerAdapter = new BusScheduleFragmentPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(fragmentPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        String shortcutIntent = getIntent().getStringExtra("shortcut");
        if (shortcutIntent != null) {
            viewPager.setCurrentItem(shortcutIntent.equals("coming_home") ? 1 : 0);
        }
    }

    private void checkUpdate() {
        if (NetworkUtil.canNetworkConnect(this)) {
            if (isTodayCheckUpdate()) {
                return;
            }

            UpdateCheckTask.init().execute(new UpdateCheckTask.Callback() {
                @Override
                public void onSuccess(long versionCode) {
                    PreferenceUtil.setUpdatedDate(MainActivity.this, DateUtil.getTodayStringOnlyFigure());
                    if (canUpdate(versionCode)) {
                        MainActivity.this.versionCode = versionCode;
                        showUpdateNotificationDialog();
                    }
                }

                @Override
                public void onFailure() {
                }
            });
        }
    }

    private void showUpdateNotificationDialog() {
        UpdateNotificationDialog dialog = new UpdateNotificationDialog(this) {
            @Override
            public void onClickPositiveButton() {
                updateExecute();
            }
        };
        dialog.show();
    }

    private void updateExecute() {
//        Fixme
        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra(PreferenceUtil.VERSION_CODE, versionCode);
        startActivity(intent);
        finish();
    }

    private boolean isTodayCheckUpdate() {
        if (PreferenceUtil.getPreviousUpdatedDate(this).equals("")) {
            return false;
        } else if (!PreferenceUtil.getPreviousUpdatedDate(this).equals(DateUtil.getTodayStringOnlyFigure())) {
            return false;
        }
        return true;
    }

    private boolean canUpdate(long versionCode) {
        return PreferenceUtil.getVersionCode(this) < versionCode;
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
