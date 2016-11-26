package app.com.thetechnocafe.cyberoamclient.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Date;

import app.com.thetechnocafe.cyberoamclient.R;
import app.com.thetechnocafe.cyberoamclient.Utils.AlarmUtils;
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
                    //Get username and password from shared preferences
                    final String username = SharedPreferenceUtils.getUsername(context);
                    String password = SharedPreferenceUtils.getPassword(context);

                    if (!username.equals("") || !password.equals("")) {
                        //Login into Wifi
                        new NetworkUtils(null) {
                            @Override
                            public void onResultReceived(boolean success, String message) {
                                //Check for success
                                if (success) {

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
