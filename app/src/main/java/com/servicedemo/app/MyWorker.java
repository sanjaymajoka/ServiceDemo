package com.servicedemo.app;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class MyWorker extends Worker {
    public MyWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.e("MyWorker", "do work ...");
        if (!Utils.isMyServiceRunning(getApplicationContext(), MyService.class)) {
            Utils.startService(getApplicationContext());
        }
        return Result.success();
    }


}
