package app.com.thetechnocafe.cyberoamclient.Account;

import android.content.Context;

/**
 * Created by gurleensethi on 15/11/16.
 */

public interface IAccountView {
    Context getContext();

    void onViewReady(boolean isLoggedIn);

    void setInitialData(String username, double dataUsed, String loggedInTime, int wifiStrength);

    void onLogout();

    void setStatsData(double dataConsumed, int timesLoggedIn, String durationLoggedIn, String wifiNetworkName);
}
