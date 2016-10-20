package app.com.thetechnocafe.cyberoamclient.Login;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import app.com.thetechnocafe.cyberoamclient.Utils.NetworkUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.SharedPreferenceUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.ValueUtils;

import static app.com.thetechnocafe.cyberoamclient.Login.LoginPresenter.BROADCAST_REQUEST_CODE;

/**
 * Created by gurleensethi on 19/10/16.
 */

public class LoginBroadcastReceiver extends BroadcastReceiver {

    private static final String TAG = "BroadcastReceiver";
    private String savedUsername;
    private String savedPassword;

    @Override
    public void onReceive(final Context context, Intent intent) {

        Log.d("BroadcastLogin", "Request received");

        //Get username and password from shared preferences
        savedPassword = SharedPreferenceUtils.getPassword(context);
        savedUsername = SharedPreferenceUtils.getUsername(context);

        //Request to check if already logged in
        new NetworkUtils() {
            @Override
            public void onResultReceived(boolean success, int errorCode) {
                if (success) {
                    //Check if user has not logged out, if not then set another alarm
                    if (SharedPreferenceUtils.getLoginState(context).equals(ValueUtils.STATE_LOGGED_IN)) {
                        Log.d(TAG, "Already logged in");
                        setUpAlarm(context);
                    }
                } else {
                    //Check if login state is logged in and check returns to login again
                    if (errorCode == ValueUtils.ERROR_LOGIN_AGAIN && SharedPreferenceUtils.getLoginState(context).equals(ValueUtils.STATE_LOGGED_IN)) {
                        loginAgain(context, savedUsername, savedPassword);
                        Log.d(TAG, "Logging in again");
                    }
                }
            }
        }.checkLoginStatus(context, savedUsername, savedPassword);
    }

    /**
     * If logged out by cyberoam then login again
     * Notify the current state through notification is login not successful
     */
    private void loginAgain(final Context context, String username, String password) {
        new NetworkUtils() {
            @Override
            public void onResultReceived(boolean success, int errorCode) {
                if (success) {
                    Toast.makeText(context, "Logged In again", Toast.LENGTH_SHORT).show();
                } else {
                    //TODO:Handle all the errors by sending notification
                    Toast.makeText(context, "Error Logging in", Toast.LENGTH_SHORT).show();
                }
            }
        }.login(context, username, password);
    }

    /**
     * Set up another alarm for check
     */
    private void setUpAlarm(Context context) {
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
}
