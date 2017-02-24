package com.numero.sojodia.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.numero.sojodia.adapter.BusScheduleFragmentPagerAdapter;
import com.numero.sojodia.view.UpdateNotificationDialog;
import com.numero.sojodia.network.UpdateChecker;
import com.numero.sojodia.R;
import com.numero.sojodia.util.PreferenceUtil;
import com.numero.sojodia.util.Constants;
import com.numero.sojodia.util.DateUtil;
import com.numero.sojodia.util.NetworkUtil;

public class MainActivity extends AppCompatActivity {

    private long versionCode = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        checkUpdate();
    }

    private void initViews() {
        BusScheduleFragmentPagerAdapter fragmentPagerAdapter = new BusScheduleFragmentPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(fragmentPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void checkUpdate() {
        if (NetworkUtil.canNetworkConnect(this)) {
            if (isTodayCheckUpdate()) {
                return;
            }

            UpdateChecker.init().setCallback(new UpdateChecker.Callback() {
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
            }).execute();
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
        Intent intent = new Intent(this, UpdateActivity.class);
        intent.putExtra(Constants.VERSION_CODE, versionCode);
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
}
