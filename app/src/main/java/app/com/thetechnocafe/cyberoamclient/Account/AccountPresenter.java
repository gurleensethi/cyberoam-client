package app.com.thetechnocafe.cyberoamclient.Account;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import app.com.thetechnocafe.cyberoamclient.Login.LoginBroadcastReceiver;
import app.com.thetechnocafe.cyberoamclient.Utils.NetworkUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.SharedPreferenceUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.TrafficUtils;
import app.com.thetechnocafe.cyberoamclient.Utils.ValueUtils;

import static app.com.thetechnocafe.cyberoamclient.Login.LoginPresenter.BROADCAST_REQUEST_CODE;

/**
 * Created by gurleensethi on 15/11/16.
 */

public class AccountPresenter implements IAccountPresenter {
    private IAccountView mView;

    public AccountPresenter(IAccountView view) {
        mView = view;
    }

    @Override
    public void onViewReady() {
        mView.onViewReady();
        mView.setInitialData(
                SharedPreferenceUtils.getUsername(mView.getContext()),
                TrafficUtils.getTotalUsage(mView.getContext())
        );
    }

    @Override
    public void logout() {
        cancelAlarm();
        SharedPreferenceUtils.changeLoginState(mView.getContext(), ValueUtils.STATE_LOGGED_OUT);
        new NetworkUtils(null) {
            @Override
            public void onResultReceived(boolean success, int errorCode) {
                if (success) {
                    Toast.makeText(mView.getContext(), "Successfully logged out", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mView.getContext(), "Error logging out", Toast.LENGTH_SHORT).show();
                }
            }
        }.logout(mView.getContext(), SharedPreferenceUtils.getUsername(mView.getContext()), SharedPreferenceUtils.getPassword(mView.getContext()));

        //Change logged in state
        SharedPreferenceUtils.changeLoginState(mView.getContext(), ValueUtils.STATE_LOGGED_OUT);

        //Notify the view for logout
        mView.onLogout();
    }

    @Override
    public boolean isLoggedIn() {
        if (SharedPreferenceUtils.getLoginState(mView.getContext()).equals(ValueUtils.STATE_LOGGED_IN)) {
            return true;
        }
        return false;
    }

    //Cancel the pending alarms
    public void cancelAlarm() {
        //Get alarm manager service
        AlarmManager alarmManager = (AlarmManager) mView.getContext().getSystemService(Context.ALARM_SERVICE);

        //Create a pending intent for broadcast receiver
        Intent intent = new Intent(mView.getContext(), LoginBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(mView.getContext(), BROADCAST_REQUEST_CODE, intent, 0);

        //Cancel alarms
        alarmManager.cancel(pendingIntent);
    }
}
