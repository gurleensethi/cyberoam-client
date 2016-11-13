package app.com.thetechnocafe.cyberoamclient.Login;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Common.AccountsModel;
import app.com.thetechnocafe.cyberoamclient.Common.RealmDatabase;
import app.com.thetechnocafe.cyberoamclient.Utils.NetworkUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.SharedPreferenceUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.ValueUtils;

/**
 * Created by gurleensethi on 18/10/16.
 */

public class LoginPresenter implements ILoginPresenter {
    private static final String TAG = "LoginPresenter";
    public static final int BROADCAST_REQUEST_CODE = 1;

    private ILoginView mainView;
    private NetworkUtils mNetworkUtils = new NetworkUtils() {
        @Override
        public void onResultReceived(boolean success, int errorCode) {
            Log.d("LoginPresenter", success + " " + errorCode);
            mainView.isLoginSuccessful(success, errorCode);
        }
    };

    /**
     * Constructor
     * Requires a class that implements ILoginView
     */
    public LoginPresenter(ILoginView view) {
        mainView = view;
        mainView.setUpOnClickListeners();
        mainView.setUpSavedState(
                SharedPreferenceUtils.getUsername(mainView.getContext()),
                SharedPreferenceUtils.getPassword(mainView.getContext())
        );

        //If app is running for first time
        if (SharedPreferenceUtils.isFirstRun(mainView.getContext())) {
            //Add the default ip address and port
            SharedPreferenceUtils.setBaseIPAddress(mainView.getContext(), ValueUtils.BASE_IP_ADDRESS);
            SharedPreferenceUtils.setBasePort(mainView.getContext(), ValueUtils.BASE_PORT);

            mainView.completeFirstRunSetup();
        }
    }


    /**
     * View calls this function to login
     */
    @Override
    public void login(String username, String password) {
        //Check if fields are not empty
        if (username.equals("")) {
            mainView.isLoginSuccessful(false, ValueUtils.ERROR_USERNAME_EMPTY);
        } else if (password.equals("")) {
            mainView.isLoginSuccessful(false, ValueUtils.ERROR_PASSWORD_EMPTY);
        } else {
            //Add username and password to shared preferences
            SharedPreferenceUtils.setUsernameAndPassword(mainView.getContext(), username, password);

            //Send login request
            mNetworkUtils.login(mainView.getContext(), username, password);
        }
    }

    /**
     * Set up the alarm manager that,
     * wakes up Login Broadcast Receiver after fixed interval.
     */
    @Override
    public void setUpAlarmManager() {
        //Get alarm manager service
        AlarmManager alarmManager = (AlarmManager) mainView.getContext().getSystemService(Context.ALARM_SERVICE);

        //Create a pending intent for broadcast receiver
        Intent intent = new Intent(mainView.getContext(), LoginBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mainView.getContext(), BROADCAST_REQUEST_CODE, intent, 0);

        //Create interval
        long interval = SystemClock.elapsedRealtime() + ValueUtils.FIXED_INTERVAL;
        Log.d(TAG, String.valueOf(interval));

        //Set alarm
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, interval, pendingIntent);
    }

    /**
     * Change the login state in shared preferences
     */
    @Override
    public void setLoginState(boolean isLoggedIn) {
        if (isLoggedIn) {
            SharedPreferenceUtils.changeLoginState(mainView.getContext(), ValueUtils.STATE_LOGGED_IN);
        } else {
            SharedPreferenceUtils.changeLoginState(mainView.getContext(), ValueUtils.STATE_LOGGED_OUT);
        }
    }

    /**
     * Get Accounts from Realm Database
     */
    @Override
    public List<AccountsModel> getSavedAccounts() {
        return RealmDatabase.getInstance(mainView.getContext()).getAllAccounts();
    }

    @Override
    public void changeSharedUsernameAndPassword(String username) {
        //Get corresponding password from Realm
        String password = RealmDatabase.getInstance(mainView.getContext()).getPassword(username);

        SharedPreferenceUtils.setUsernameAndPassword(mainView.getContext(), username, password);

        mainView.setUpSavedState(
                SharedPreferenceUtils.getUsername(mainView.getContext()),
                SharedPreferenceUtils.getPassword(mainView.getContext())
        );
    }
}
