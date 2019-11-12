package com.servicedemo.app;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView txtSuccess;
    private TextView txtFail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnStartService).setOnClickListener(this);
        txtSuccess = findViewById(R.id.txtSucess);
        txtFail = findViewById(R.id.txtFailed);
        updateValue();

        Utils.callAPi(this);
    }

    private void updateValue() {
        txtSuccess.setText("Success : " + Preferences.getInt(this, Preferences.SUCCESS));
        txtFail.setText("Failed : " + Preferences.getInt(this, Preferences.FAILED));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartService:
                //startService(new Intent(this, MyService.class));
                startWorkManager();
                break;

            case R.id.btnStop:
                stopService(new Intent(this, MyService.class));
                WorkManager.getInstance(this).getWorkInfosByTag(MyWorker.class.getSimpleName())
                        .cancel(true);
                break;
        }
    }

    private void startWorkManager() {
        WorkManager manager = WorkManager.getInstance(this);
        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(MyWorker.class, 15
                , TimeUnit.MINUTES).setInitialDelay(15, TimeUnit.SECONDS)
                .addTag(MyWorker.class.getSimpleName())
                .build();

        manager.enqueueUniquePeriodicWork(MyWorker.class.getSimpleName()
                , ExistingPeriodicWorkPolicy.KEEP, request);
    }
}
