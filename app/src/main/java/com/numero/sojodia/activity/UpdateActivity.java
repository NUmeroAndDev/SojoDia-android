package com.numero.sojodia.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.numero.sojodia.model.BusDataFile;
import com.numero.sojodia.R;
import com.numero.sojodia.util.PreferenceUtil;
import com.numero.sojodia.network.BusDataDownloader;
import com.numero.sojodia.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class UpdateActivity extends Activity {

    private long versionCode;
    private List<BusDataFile> fileList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        versionCode = getIntent().getLongExtra(Constants.VERSION_CODE, 0L);

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Downloading");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        initBusDataFile();

        BusDataDownloader.init(this).setBusDataFileList(fileList).setCallback(new BusDataDownloader.Callback() {
            @Override
            public void onSuccess() {
                PreferenceUtil.setVersionCode(UpdateActivity.this, versionCode);
                dialog.dismiss();
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onFailure() {
                dialog.dismiss();
                Toast.makeText(UpdateActivity.this, getString(R.string.message_error), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onLoading() {

            }
        }).execute();
    }

    private void initBusDataFile() {
        fileList.add(new BusDataFile("https://raw.githubusercontent.com/NUmeroAndDev/SojoDia-BusDate/master/TkToKutc.csv", "TkToKutc.csv"));
        fileList.add(new BusDataFile("https://raw.githubusercontent.com/NUmeroAndDev/SojoDia-BusDate/master/TndToKutc.csv", "TndToKutc.csv"));
        fileList.add(new BusDataFile("https://raw.githubusercontent.com/NUmeroAndDev/SojoDia-BusDate/master/KutcToTk.csv", "KutcToTk.csv"));
        fileList.add(new BusDataFile("https://raw.githubusercontent.com/NUmeroAndDev/SojoDia-BusDate/master/KutcToTnd.csv", "KutcToTnd.csv"));
    }
}
