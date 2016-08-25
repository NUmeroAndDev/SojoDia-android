package com.numero.sojodia.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import com.numero.sojodia.Models.BusDataFile;
import com.numero.sojodia.Utils.ApplicationPreferences;
import com.numero.sojodia.Network.BusDataDownloader;
import com.numero.sojodia.Utils.Constants;
import com.numero.sojodia.Utils.DateUtil;

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

        BusDataDownloader downloader = new BusDataDownloader(this) {
            @Override
            public void callbackOnPostExecute(int resultCode) {
                if (resultCode == BusDataDownloader.RESULT_OK) {
                    ApplicationPreferences.setUpdatedDate(UpdateActivity.this, DateUtil.getDateString());
                    ApplicationPreferences.setVersionCode(UpdateActivity.this, versionCode);
                }
                dialog.dismiss();
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void callbackOnProgressUpdate() {

            }
        };
        downloader.execute(files[0], files[1], files[2], files[3]);
    }
}
