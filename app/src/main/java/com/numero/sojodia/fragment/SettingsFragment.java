package com.numero.sojodia.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;

import com.numero.sojodia.BuildConfig;
import com.numero.sojodia.R;
import com.numero.sojodia.activity.LicensesActivity;
import com.numero.sojodia.contract.SettingsContract;

public class SettingsFragment extends PreferenceFragment implements SettingsContract.View {

    private SettingsContract.Presenter presenter;
    private PreferenceScreen dataVersionScreen;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.settings);

        dataVersionScreen = (PreferenceScreen) findPreference("data_version");

        PreferenceScreen appVersionScreen = (PreferenceScreen) findPreference("app_version");
        appVersionScreen.setSummary(BuildConfig.VERSION_NAME);

        PreferenceScreen licensesScreen = (PreferenceScreen) findPreference("licenses");
        licensesScreen.setOnPreferenceClickListener(preference -> {
            startActivity(LicensesActivity.createIntent(getActivity()));
            return false;
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unSubscribe();
    }

    @Override
    public void setPresenter(SettingsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showBusDataVersion(String version) {
        dataVersionScreen.setSummary(version);
    }
}
