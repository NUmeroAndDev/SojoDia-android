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

public class UpdateActivity extends Activity{

    private long versionCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        versionCode = getIntent().getLongExtra(Constants.VERSION_CODE, 0L);

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Downloading");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        BusDataFile files[] = BusDataFile.init();

        BusDataDownloader downloader = BusDataDownloader.init(this).setCallback(new BusDataDownloader.Callback() {
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
        });
        downloader.execute(files[0], files[1], files[2], files[3]);
    }
}
