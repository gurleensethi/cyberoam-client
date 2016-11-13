package app.com.thetechnocafe.cyberoamclient.Settings;

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

        //Provide IP Address and Port
        mView.setUpSettingsState(
                SharedPreferenceUtils.getBaseIPAddress(mView.getContext()),
                SharedPreferenceUtils.getBasePort(mView.getContext())
        );
    }
}
