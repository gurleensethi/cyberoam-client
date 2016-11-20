package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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

    /**
     * Get total data used today
     * Get the time in long for 12:00AM of the particular day
     * Check which logs logged in time more than the 12:00AM in millis
     */
    public static double getTotalDataUsedToday(Context context) {
        //Create a new date for today
        Calendar calendar = GregorianCalendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.setTimeInMillis(1478620479914l);

        //Convert to long
        long timeInLong = calendar.getTimeInMillis();

        //Get list of models
        List<SessionLogModel> list = RealmDatabase.getInstance(context).getSessionLogs();

        double totalDataConsumed = 0.0;

        //Iterate and get data
        for (SessionLogModel model : list) {
            if (model.getLoggedInTime() > timeInLong) {
                totalDataConsumed += model.getDataConsumed();
            }
        }

        //Convert to 2 decimal places
        return ((int) (totalDataConsumed * 100)) / 100.0;
    }
}
