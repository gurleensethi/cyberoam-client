package app.com.thetechnocafe.cyberoamclient.Settings;

import app.com.thetechnocafe.cyberoamclient.Common.RealmDatabase;
import app.com.thetechnocafe.cyberoamclient.Utils.SharedPreferenceUtils;

/**
 * Created by gurleensethi on 14/11/16.
 */

public class SettingsPresenter implements ISettingsPresenter {
    private ISettingsView mView;

    public SettingsPresenter(ISettingsView view) {
        mView = view;
    }

    @Override
    public void onViewReady() {
        mView.setUpView();

        updateSettingsInView();
    }

    @Override
    public void changeNotificationsState(boolean state) {
        //Change state in shared preferences
        SharedPreferenceUtils.setNotifications(mView.getContext(), state);

        //Update settings
        updateSettingsInView();
    }

    @Override
    public void changeActivityLogState(boolean state) {
        //Change state in shared preferences
        SharedPreferenceUtils.setActivityLog(mView.getContext(), state);

        //Update settings
        updateSettingsInView();
    }

    @Override
    public void cleanActivityLogs() {
        //Clear all the logs
        RealmDatabase.getInstance(mView.getContext()).clearActivityLogs();
    }

    @Override
    public void changeContinuousLogin(boolean isEnabled) {
        //Change continuous login feature
        SharedPreferenceUtils.setContinousLogin(mView.getContext(), isEnabled);
    }

    @Override
    public void changeWifiAutoLogin(boolean isEnabled) {
        //Change auto login wifi state
        SharedPreferenceUtils.changeAutoLoginOnWifi(mView.getContext(), isEnabled);
    }

    @Override
    public void changeIPAddress(String ipAddress) {
        //Change ip address
        SharedPreferenceUtils.setBaseIPAddress(mView.getContext(), ipAddress);
    }

    @Override
    public void changePort(String port) {
        //Change port number
        SharedPreferenceUtils.setBasePort(mView.getContext(), port);
    }

    //Provide View with updated settings
    private void updateSettingsInView() {
        mView.setUpSettingsState(
                SharedPreferenceUtils.getBaseIPAddress(mView.getContext()),
                SharedPreferenceUtils.getBasePort(mView.getContext()),
                SharedPreferenceUtils.getNotifications(mView.getContext()),
                SharedPreferenceUtils.getActivityLog(mView.getContext()),
                SharedPreferenceUtils.getContinuousLogin(mView.getContext())
        );

        mView.setUpGeneralSettingsState(
                SharedPreferenceUtils.getAutoLoginOnWifi(mView.getContext())
        );
    }
}
