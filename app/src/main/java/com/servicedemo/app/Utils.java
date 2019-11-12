package com.servicedemo.app;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Utils {

    public static boolean isMyServiceRunning(Context context, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void startService(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            ContextCompat.startForegroundService(context, new Intent(context, MyService.class));
        } else {
            context.startService(new Intent(context, MyService.class));
        }
    }

    public static void callAPi(final Context context) {
        RetrofitClient.getRetrofit().create(ApiInterface.class)
                .getPostTest(new RequestModel("" + System.currentTimeMillis()))
                .enqueue(new Callback<SuccessModel>() {
                    @Override
                    public void onResponse(Call<SuccessModel> call, Response<SuccessModel> response) {
                        SuccessModel model = response.body();
                        if (model.getStatus().equals("400")) {
                            Log.e("API","success");
                            updateInt(context, Preferences.SUCCESS);
                        }
                    }

                    @Override
                    public void onFailure(Call<SuccessModel> call, Throwable t) {
                        Log.e("API","Failed : "+t.toString());
                        updateInt(context, Preferences.FAILED);
                    }
                });
    }

    private static void updateInt(Context context, String key) {
        int value = Preferences.getInt(context, key);
        value++;
        Preferences.putInt(context, key, value);
    }
}
