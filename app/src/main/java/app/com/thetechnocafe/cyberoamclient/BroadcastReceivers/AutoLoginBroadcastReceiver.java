package app.com.thetechnocafe.cyberoamclient.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.Date;

import app.com.thetechnocafe.cyberoamclient.R;
import app.com.thetechnocafe.cyberoamclient.Utils.AlarmUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.ContinuousLoginUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.NetworkUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.NotificationsUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.SessionLogUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.SharedPreferenceUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.TrafficUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.ValueUtils;

/**
 * Created by gurleensethi on 23/11/16.
 */

public class AutoLoginBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {
        //Get connectivity service
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (activeNetwork != null) {

            //Check if connection type is WiFi
            boolean isWifi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;

            if (isWifi) {
                //Check if AutoLogin is enabled
                if (SharedPreferenceUtils.getAutoLoginOnWifi(context)) {
                    if (SharedPreferenceUtils.getContinuousLogin(context)) {
                        continuousLogin(context, 0);
                    } else {
                        //Get username and password from shared preferences
                        final String username = SharedPreferenceUtils.getUsername(context);
                        final String password = SharedPreferenceUtils.getPassword(context);

                        if (!username.equals("") || !password.equals("")) {
                            //Login into Wifi
                            new NetworkUtils(null) {
                                @Override
                                public void onResultReceived(boolean success, String message) {
                                    //Check for success
                                    if (success) {
                                        initializeStatesAndNotify(context, username, password);
                                    } else {
                                        if (SharedPreferenceUtils.getLoginState(context).equals(ValueUtils.STATE_LOGGED_IN)) {
                                            //Change login state
                                            SharedPreferenceUtils.changeLoginState(context, ValueUtils.STATE_LOGGED_OUT);

                                            //Record session
                                            SessionLogUtils.saveSessionLog(context);
                                        }
                                    }
                                }
                            }.login(context, username, password);
                        }
                    }
                }
            }
        }
    }

    /**
     * User the continuous login utils to login with all accounts
     */
    public void continuousLogin(final Context context, int position) {
        //Get all accounts
        new ContinuousLoginUtils() {
            @Override
            public void onLoginResult(boolean success, String message, boolean isLast, int position, String username, String password) {
                //If login successful then notify the user
                Log.d("AutoLogin", message);
                if (success) {
                    initializeStatesAndNotify(context, username, password);
                } else if (!isLast) {
                    //Login is not successful then try next
                    AutoLoginBroadcastReceiver.this.continuousLogin(context, position + 1);
                } else {
                    //Notify user
                    if (SharedPreferenceUtils.getNotifications(context)) {
                        if (!message.equals(ValueUtils.ERROR_VOLLEY_ERROR) && !message.equals(ValueUtils.ERROR_NO_SAVED_ACCOUNTS)) {
                            NotificationsUtils.sendSimpleTextNotification(
                                    context,
                                    context.getString(R.string.unable_to_login_saved_accounts),
                                    ""
                            );
                        }
                    }
                }
            }
        }.continuousLogin(context, position);
    }

    /**
     * Initialize the required states in shared preferences and
     * notify the user about login
     */
    private void initializeStatesAndNotify(Context context, String username, String password) {
        //Change the current shared preferences login and password
        SharedPreferenceUtils.setUsernameAndPassword(context, username, password);
        //If was already logged in
        if (!SharedPreferenceUtils.getLoginState(context).equals(ValueUtils.STATE_LOGGED_IN)) {
            //Change login state
            SharedPreferenceUtils.changeLoginState(context, ValueUtils.STATE_LOGGED_IN);

            //Cancel Alarm if already running
            AlarmUtils.cancelAlarm(context);

            //Start alarms
            AlarmUtils.setUpAlarm(context);

            //Start data tracking
            TrafficUtils.saveInitialBytes(context);

            //Save initial time
            SharedPreferenceUtils.setLoggedInTime(context, new Date().getTime());

            //Notify user
            if (SharedPreferenceUtils.getNotifications(context)) {
                NotificationsUtils.sendSimpleTextNotification(
                        context,
                        context.getString(R.string.logged_into_cyerbaom),
                        String.format(context.getString(R.string.auto_wifi_logged_in), username)
                );
            }
        }
    }
}
