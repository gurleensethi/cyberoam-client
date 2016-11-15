package app.com.thetechnocafe.cyberoamclient.Account;

import android.content.Context;

/**
 * Created by gurleensethi on 15/11/16.
 */

public interface IAccountView {
    Context getContext();

    void onViewReady();

    void setInitialData(String username, double dataUsed);

    void onLogout();
}