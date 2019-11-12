package com.servicedemo.app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;

import androidx.core.app.NotificationCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyService extends Service {

    private String CHANNEL_ID = MyService.class.getSimpleName();
    private int notificationId = 1011;
    private Timer timer;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        createNotificationChannel();
        startForeground(notificationId, getNotification());
        startTimer();
        return START_STICKY;
    }

    private void startTimer() {
        stopTimer();
        timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                workToDo();
            }
        };

        timer.schedule(timerTask, 1000L, 1000L * 60L * 2);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopTimer();
        Log.e("MyService", "Stopped");
        Utils.callAPi(getApplicationContext());
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
        }
        timer = null;
    }

    private void workToDo() {
        Log.e("MyService", "Running.....");

        Utils.callAPi(getApplicationContext());
    }


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Example Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private Notification getNotification() {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);
        return new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentText("Running...")
                .setContentTitle("MyService")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentIntent(pendingIntent)
                .build();
    }

}
