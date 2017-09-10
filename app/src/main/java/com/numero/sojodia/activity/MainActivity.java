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

import com.numero.sojodia.view.adapter.BusScheduleFragmentPagerAdapter;
import com.numero.sojodia.service.UpdateBusDataService;
import com.numero.sojodia.util.BroadCastUtil;
import com.numero.sojodia.view.NeedRestartDialog;
import com.numero.sojodia.R;

public class MainActivity extends AppCompatActivity {

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
}
