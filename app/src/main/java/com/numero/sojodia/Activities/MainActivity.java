package com.numero.sojodia.Activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.numero.sojodia.Adapters.BusScheduleFragmentPagerAdapter;
import com.numero.sojodia.Dialogs.UpdateNotificationDialog;
import com.numero.sojodia.Network.UpdateChecker;
import com.numero.sojodia.R;
import com.numero.sojodia.Utils.ApplicationPreferences;
import com.numero.sojodia.Utils.Constants;
import com.numero.sojodia.Utils.DateUtil;
import com.numero.sojodia.Utils.NetworkUtil;

public class MainActivity extends AppCompatActivity {

    private long versionCode = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Configuration configuration = getResources().getConfiguration();
        switch (configuration.orientation){
            case Configuration.ORIENTATION_PORTRAIT:
                toolbar.setVisibility(View.VISIBLE);
                break;
            case Configuration.ORIENTATION_LANDSCAPE:
                toolbar.setVisibility(View.GONE);

                break;
        }

        BusScheduleFragmentPagerAdapter fragmentPagerAdapter = new BusScheduleFragmentPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(fragmentPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        if (NetworkUtil.canNetworkConnect(this)) {
            if (isTodayCheckUpdate()) {
                return;
            }
            UpdateChecker checker = new UpdateChecker() {
                @Override
                public void callbackOnPostExecute(int resultCode, long versionCode) {
                    if (resultCode == UpdateChecker.RESULT_OK) {
                        if (canUpdate(versionCode)) {
                            MainActivity.this.versionCode = versionCode;
                            showUpdateNotificationDialog();
                        }
                    }
                }
            };
            checker.execute();
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
        if (ApplicationPreferences.getPreviousUpdatedDate(this).equals("")) {
            return false;
        } else if (!ApplicationPreferences.getPreviousUpdatedDate(this).equals(DateUtil.getDateString())) {
            return false;
        }
        return true;
    }

    private boolean canUpdate(long versionCode) {
        return ApplicationPreferences.getVersionCode(this) < versionCode;
    }
}
