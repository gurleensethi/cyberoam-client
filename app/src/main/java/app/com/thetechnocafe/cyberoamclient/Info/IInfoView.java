package app.com.thetechnocafe.cyberoamclient.Info;

import android.content.Context;

/**
 * Created by gurleensethi on 19/11/16.
 */

public interface IInfoView {
    void onViewReady();

    Context getContext();

    void setInitialData(int timesLoggedIn, String durationLoggedIn, double dataConsumed);
}
