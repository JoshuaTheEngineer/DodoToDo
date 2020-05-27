package com.joshuatheengineer.dodotodo.utilities;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class DodoWorker extends Worker {
    public DodoWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    /**
     * Sets Notification with the custom message
     */
    @NonNull
    @Override
    public Result doWork() {
        String customMsg = getInputData().getString("msg");
        // should i pass a context for local, not sure if 'getApplicationContext' works
        final DodoNotification nManager = new DodoNotification(getApplicationContext(), customMsg);
        nManager.addNotification();
        return Result.success();
    }
}
