package app.com.thetechnocafe.cyberoamclient.Login;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

import java.util.Date;
import java.util.List;

import app.com.thetechnocafe.cyberoamclient.BroadcastReceivers.LoginBroadcastReceiver;
import app.com.thetechnocafe.cyberoamclient.Common.RealmDatabase;
import app.com.thetechnocafe.cyberoamclient.Models.AccountsModel;
import app.com.thetechnocafe.cyberoamclient.R;
import app.com.thetechnocafe.cyberoamclient.Services.AutoWifiLoginService;
import app.com.thetechnocafe.cyberoamclient.Utils.AlarmUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.ContinuousLoginUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.NetworkUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.SharedPreferenceUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.TrafficUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.ValueUtils;

/**
 * Created by gurleensethi on 18/10/16.
 */

public class LoginPresenter implements ILoginPresenter {
    private static final String TAG = "LoginPresenter";
    public static final int BROADCAST_REQUEST_CODE = 1;

    private ILoginView mainView;
    private NetworkUtils mNetworkUtils = new NetworkUtils(null) {
        @Override
        public void onResultReceived(boolean success, String message) {
            Log.d("LoginPresenter", success + " " + message);
            mainView.isLoginSuccessful(success, message);
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

        //Configure the settings when app runs for the first time
        if (SharedPreferenceUtils.isFirstRun(mainView.getContext())) {
            //Add the default ip address and port
            SharedPreferenceUtils.setBaseIPAddress(mainView.getContext(), ValueUtils.BASE_IP_ADDRESS);
            SharedPreferenceUtils.setBasePort(mainView.getContext(), ValueUtils.BASE_PORT);
            SharedPreferenceUtils.setNotifications(mainView.getContext(), true);
            SharedPreferenceUtils.setActivityLog(mainView.getContext(), true);
            SharedPreferenceUtils.setContinousLogin(mainView.getContext(), false);
            SharedPreferenceUtils.changeAutoLoginOnWifi(mainView.getContext(), false);

            mainView.completeFirstRunSetup();
        }

        //Start the AutoWifiLogin Service
        Intent intent = new Intent(mainView.getContext(), AutoWifiLoginService.class);
        mainView.getContext().startService(intent);
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

            //Save the initial bytes
            TrafficUtils.saveInitialBytes(mainView.getContext());

            //Save initial time
            SharedPreferenceUtils.setLoggedInTime(mainView.getContext(), new Date().getTime());

            //Cancel any previous alarms
            AlarmUtils.cancelAlarm(mainView.getContext());

            if (SharedPreferenceUtils.getContinuousLogin(mainView.getContext())) {
                continuousLogin(0);
            } else {
                //Send login request
                mNetworkUtils.login(mainView.getContext(), username, password);
            }
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

    @Override
    public void refreshState() {
        mainView.onRefreshState(SharedPreferenceUtils.getLoginState(mainView.getContext()).equals(ValueUtils.STATE_LOGGED_IN));
        mainView.isContinuousLoginEnabled(SharedPreferenceUtils.getContinuousLogin(mainView.getContext()));
    }

    /**
     * User the continuous login utils to login with all accounts
     */
    public void continuousLogin(int position) {
        //Get all accounts
        new ContinuousLoginUtils() {
            @Override
            public void onLoginResult(boolean success, String message, boolean isLast, int position, String username, String password) {
                //If login successful then notify the user
                if (success) {
                    //Notify the view for change
                    mainView.setUpSavedState(username, password);
                    //Change the current shared preferences login and password
                    SharedPreferenceUtils.setUsernameAndPassword(mainView.getContext(), username, password);
                    //Notify the view
                    mainView.isLoginSuccessful(success, message);
                } else if (!isLast) {
                    //Login is not successful then try next
                    LoginPresenter.this.continuousLogin(position + 1);
                } else {
                    mainView.isLoginSuccessful(success, mainView.getContext().getString(R.string.unable_to_login_saved_accounts));
                }
            }
        }.continuousLogin(mainView.getContext(), position);
    }
}
