package com.joshuatheengineer.dodotodo.utilities;

import android.content.Context;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * Attempted timer to create scheduled notifications
 * will try later
 *
 * https://www.iitk.ac.in/esc101/05Aug/tutorial/essential/threads/timer.html
 * http://www.jguru.com/faq/view.jsp?EID=542438
 *
 * https://docs.oracle.com/javase/7/docs/api/java/util/TimerTask.html
 * https://docs.oracle.com/javase/7/docs/api/java/util/Timer.html#scheduleAtFixedRate(java.util.TimerTask,%20java.util.Date,%20long)
 */
public class DodoTimer {
    Timer timer;
    String msg;
    Context context;

    public DodoTimer(Context context, String msg, int seconds) {
        int secondsInDay = 30;//86400;
        this.timer = new Timer();
        this.msg = msg;
        this.timer.scheduleAtFixedRate(new DodoTask(), seconds * 1000, secondsInDay);
        this.context = context;
    }

    public void cancel(){
        if (timer != null) {
            timer.cancel();
        }
    }

    class DodoTask extends TimerTask {
        @Override
        public void run() {
            final DodoNotification nManager = new DodoNotification(context, msg);
            nManager.addNotification();
            this.cancel();
        }
    }
}
