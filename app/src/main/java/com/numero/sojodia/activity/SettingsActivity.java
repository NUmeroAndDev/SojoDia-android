package com.numero.sojodia.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.numero.sojodia.R;
import com.numero.sojodia.SojoDiaApplication;
import com.numero.sojodia.di.ApplicationComponent;
import com.numero.sojodia.fragment.SettingsFragment;
import com.numero.sojodia.presenter.SettingsPresenter;
import com.numero.sojodia.repository.ConfigRepository;

import javax.inject.Inject;

public class SettingsActivity extends AppCompatActivity {

    @Inject
    ConfigRepository configRepository;

    public static Intent createIntent(@NonNull Context context) {
        Intent intent = new Intent(context, SettingsActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        setSupportActionBar(findViewById(R.id.toolbar));
        getComponent().inject(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        SettingsFragment fragment = (SettingsFragment) getFragmentManager().findFragmentById(R.id.container);
        if (fragment == null) {
            fragment = SettingsFragment.Companion.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        }
        new SettingsPresenter(fragment, configRepository);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private ApplicationComponent getComponent() {
        return ((SojoDiaApplication) getApplication()).getComponent();
    }
}
