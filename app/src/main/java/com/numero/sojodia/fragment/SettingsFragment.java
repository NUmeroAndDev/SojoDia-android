package com.numero.sojodia.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.numero.sojodia.BuildConfig;
import com.numero.sojodia.R;
import com.numero.sojodia.activity.LicensesActivity;
import com.numero.sojodia.util.PreferenceUtil;

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        PreferenceScreen dataVersionScreen = (PreferenceScreen) findPreference("data_version");
        dataVersionScreen.setSummary(String.valueOf(PreferenceUtil.getVersionCode(getActivity())));

        PreferenceScreen appVersionScreen = (PreferenceScreen) findPreference("app_version");
        appVersionScreen.setSummary(BuildConfig.VERSION_NAME);

        PreferenceScreen licensesScreen = (PreferenceScreen) findPreference("licenses");
        licensesScreen.setOnPreferenceClickListener(preference -> {
            startActivity(LicensesActivity.createIntent(getActivity()));
            return false;
        });
    }
}
