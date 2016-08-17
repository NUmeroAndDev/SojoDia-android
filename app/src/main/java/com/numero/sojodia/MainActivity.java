package com.numero.sojodia;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.numero.sojodia.Adapter.ReciprocatingFragmentPagerAdapter;
import com.numero.sojodia.Dialogs.UpdateInfoDialog;
import com.numero.sojodia.Utils.ApplicationPreferences;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        ReciprocatingFragmentPagerAdapter fragmentPagerAdapter = new ReciprocatingFragmentPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(fragmentPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        if (ApplicationPreferences.getPreviousVersionCode(this) < getCurrentVersionCode()) {
            UpdateInfoDialog dialog = new UpdateInfoDialog(this);
            dialog.show();
            ApplicationPreferences.setVersionCode(this, getCurrentVersionCode());
        }
    }

    private int getCurrentVersionCode() {
        int versionCode = 0;
        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionCode = packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return versionCode;
    }
}
