package app.com.thetechnocafe.cyberoamclient.Utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import app.com.thetechnocafe.cyberoamclient.BroadcastReceivers.LoginBroadcastReceiver;

import static app.com.thetechnocafe.cyberoamclient.Login.LoginPresenter.BROADCAST_REQUEST_CODE;

/**
 * Created by gurleensethi on 23/11/16.
 */

public class AlarmUtils {
    private static final String TAG = "AlarmUtils";

    /**
     * Set up another alarm for check
     */
    public static void setUpAlarm(Context context) {
        //Get alarm manager service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //Create a pending intent for broadcast receiver
        Intent intent = new Intent(context, LoginBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, BROADCAST_REQUEST_CODE, intent, 0);

        //Create interval
        long interval = SystemClock.elapsedRealtime() + ValueUtils.FIXED_INTERVAL;
        Log.d(TAG, String.valueOf(interval));

        //Set alarm
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, interval, pendingIntent);
    }

    /**
     * Stop alarm
     */
    public static void cancelAlarm(Context context) {
        //Get alarm manager service
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //Create a pending intent for broadcast receiver
        Intent intent = new Intent(context, LoginBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, BROADCAST_REQUEST_CODE, intent, 0);

        //Cancel alarms
        alarmManager.cancel(pendingIntent);
    }
}
