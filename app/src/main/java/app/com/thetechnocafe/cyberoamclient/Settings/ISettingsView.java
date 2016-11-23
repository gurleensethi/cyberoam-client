package app.com.thetechnocafe.cyberoamclient.Settings;

import android.content.Context;

/**
 * Created by gurleensethi on 14/11/16.
 */

interface ISettingsView {
    void setUpView();

    void setUpSettingsState(String ipAddress, String port, boolean notificationsEnabled, boolean isActivityLogEnabled, boolean continuousLogin);

    void setUpGeneralSettingsState(boolean isAutoLoginEnabled);

    Context getContext();
}
