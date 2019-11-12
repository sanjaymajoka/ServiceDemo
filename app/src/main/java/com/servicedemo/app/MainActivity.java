package com.servicedemo.app;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.os.Bundle;
import android.view.View;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btnStartService).setOnClickListener(this);


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
