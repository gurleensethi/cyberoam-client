package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;

import java.util.List;

import app.com.thetechnocafe.cyberoamclient.Common.RealmDatabase;
import app.com.thetechnocafe.cyberoamclient.Models.SessionLogModel;

/**
 * Created by gurleensethi on 20/11/16.
 */

public class StatsUtils {

    /**
     * Return the number of realm entries
     */
    public static int getTotalTimesLoggedIn(Context context) {
        //Calculate the number of entries from realm database
        return RealmDatabase.getInstance(context).getSessionLogs().size();
    }

    /**
     * Return the total amount of data consumed
     */
    public static double getTotalDataConsumed(Context context) {
        //Get list form database
        List<SessionLogModel> list = RealmDatabase.getInstance(context).getSessionLogs();

        double totalDataConsumed = 0.0;

        //Iterate the list and store the data
        for (SessionLogModel model : list) {
            totalDataConsumed += model.getDataConsumed();
        }

        //Convert to 2 decimal places
        return ((int) (totalDataConsumed * 100)) / 100.0;
    }

    /**
     * Return the total duration logged in
     */
    public static long getTotalDurationLoggedIn(Context context) {
        //Get list form database
        List<SessionLogModel> list = RealmDatabase.getInstance(context).getSessionLogs();

        long totalLoggedInTime = 0;

        //Iterate the list and store the data
        for (SessionLogModel model : list) {
            totalLoggedInTime += model.getLoggedInDuration();
        }

        return totalLoggedInTime;
    }
}
