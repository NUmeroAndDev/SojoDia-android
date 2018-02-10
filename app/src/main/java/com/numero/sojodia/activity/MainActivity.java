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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.numero.sojodia.SojoDiaApplication;
import com.numero.sojodia.di.ApplicationComponent;
import com.numero.sojodia.fragment.BusScheduleFragment;
import com.numero.sojodia.model.Reciprocate;
import com.numero.sojodia.model.Route;
import com.numero.sojodia.presenter.BusSchedulePresenter;
import com.numero.sojodia.repository.BusDataRepository;
import com.numero.sojodia.util.DateUtil;
import com.numero.sojodia.view.TimeTableDialog;
import com.numero.sojodia.view.adapter.BusScheduleFragmentPagerAdapter;
import com.numero.sojodia.service.UpdateBusDataService;
import com.numero.sojodia.util.BroadCastUtil;
import com.numero.sojodia.view.NeedRestartDialog;
import com.numero.sojodia.R;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements BusScheduleFragment.BusScheduleFragmentListener {

    private final BroadcastReceiver finishDownloadReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            showNeedRestartDialog();
        }
    };

    private final BroadcastReceiver changedDateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            toolbar.setSubtitle(DateUtil.getTodayString(MainActivity.this));
        }
    };

    private Toolbar toolbar;
    private ViewPager viewPager;

    @Inject
    BusDataRepository busDataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getComponent().inject(this);

        initViews();

        String shortcutIntent = getIntent().getStringExtra("shortcut");
        if (shortcutIntent != null) {
            viewPager.setCurrentItem(shortcutIntent.equals("coming_home") ? 1 : 0);
        }
        startCheckUpdateService();
    }

    @Override
    protected void onResume() {
        super.onResume();
        toolbar.setSubtitle(DateUtil.getTodayString(MainActivity.this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(this).registerReceiver(finishDownloadReceiver, new IntentFilter(BroadCastUtil.ACTION_FINISH_DOWNLOAD));
        LocalBroadcastManager.getInstance(this).registerReceiver(changedDateReceiver, new IntentFilter(BroadCastUtil.ACTION_CHANGED_DATE));
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(finishDownloadReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(changedDateReceiver);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                startActivity(SettingsActivity.createIntent(this));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(BusScheduleFragment fragment, Reciprocate reciprocate) {
        // FIXME AdapterではActivity再生成時にPresenterを生成できないからこの実装している
        new BusSchedulePresenter(fragment, busDataRepository, reciprocate);
    }

    @Override
    public void showTimeTableDialog(Route route, Reciprocate reciprocate) {
        TimeTableDialog dialog = new TimeTableDialog(this, busDataRepository);
        dialog.setRoute(route);
        dialog.setReciprocate(reciprocate);
        dialog.show();
    }

    private ApplicationComponent getComponent() {
        return ((SojoDiaApplication) getApplication()).getComponent();
    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        BusScheduleFragmentPagerAdapter fragmentPagerAdapter = new BusScheduleFragmentPagerAdapter(this, getSupportFragmentManager());
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
                .setOnPositiveButtonClickListener(this::recreate).show();
    }
}
