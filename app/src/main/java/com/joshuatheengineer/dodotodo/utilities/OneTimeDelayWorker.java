package com.joshuatheengineer.dodotodo.utilities;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.PeriodicWorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.concurrent.TimeUnit;

/**
 * Due to limitations of
 */
public class OneTimeDelayWorker extends Worker {
    PeriodicWorkRequest dodoNoteRequest;
    Context context;

    public OneTimeDelayWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        String customMsg = getInputData().getString("msg");

        // will first send out the notification
        // before submitting a periodic request for daily notifications
        final DodoNotification nManager = new DodoNotification(context, customMsg);
        nManager.addNotification();

        Data.Builder data = new Data.Builder();
        data.putString("msg", customMsg);
        Constraints constraints = new Constraints.Builder().build();
        // runs notifications daily, or every 24 hours
        dodoNoteRequest = new PeriodicWorkRequest.Builder(DodoWorker.class, 24, TimeUnit.HOURS)
                .setInputData(data.build())
                .setConstraints(constraints)
                .build();
        return Result.success();
    }
}
