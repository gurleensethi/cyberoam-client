package app.com.thetechnocafe.cyberoamclient.Settings;

/**
 * Created by gurleensethi on 14/11/16.
 */

public interface ISettingsPresenter {
    void onViewReady();

    void changeNotificationsState(boolean state);

    void changeActivityLogState(boolean state);

    void cleanActivityLogs();

    void changeContinuousLogin(boolean isEnabled);

    void changeWifiAutoLogin(boolean isEnabled);

    void changeIPAddress(String ipAddress);

    void changePort(String port);

    void changeThemeColor(int colorCode);
}
