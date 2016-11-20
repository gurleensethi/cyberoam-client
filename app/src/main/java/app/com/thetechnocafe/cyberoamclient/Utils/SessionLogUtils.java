package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;

import java.util.Date;

import app.com.thetechnocafe.cyberoamclient.Common.RealmDatabase;
import app.com.thetechnocafe.cyberoamclient.Models.SessionLogModel;

/**
 * Created by gurleensethi on 17/11/16.
 */

public class SessionLogUtils {
    /**
     * Save the log of the user into realm
     */
    public static void saveSessionLog(Context context) {
        //Get consumed data
        double dataConsumed = TrafficUtils.getTotalUsage(context);

        //Get logged in time
        long loggedInTime = SharedPreferenceUtils.getLoggedInTime(context);

        //Logged in duration
        long loggedInDuration = new Date().getTime() - loggedInTime;

        //Get username
        String username = SharedPreferenceUtils.getUsername(context);

        //Create model
        SessionLogModel model = new SessionLogModel();
        model.setUsername(username);
        model.setDataConsumed(dataConsumed);
        model.setLoggedInDuration(loggedInDuration);
        model.setLoggedInTime(loggedInTime);
        model.setLoginStatus(ValueUtils.LOGIN_SUCCESS);

        //Save to realm
        RealmDatabase.getInstance(context).saveLogSession(model);
    }
}
