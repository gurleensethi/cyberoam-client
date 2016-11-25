package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;
import android.util.Log;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        //Get time in millis
        long timeInLong = TimeUtils.getTodayTimeInMillis();

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

    /**
     * Get total enrollment id used
     */
    public static int getTotalEnrollmentIdUsed(Context context) {
        //Get list of models
        List<SessionLogModel> list = RealmDatabase.getInstance(context).getSessionLogs();

        Set<String> set = new HashSet<>();

        //Iterate and get id's
        for (SessionLogModel model : list) {
            set.add(model.getUsername());
        }

        return set.size();
    }

    /**
     * Get data for a same day for a particular id
     */
    public static double getDataForSingleDay(Context context, String username) {
        //Get list of sessions
        List<SessionLogModel> list = RealmDatabase.getInstance(context).getSessionLogs();

        double totalDataConsumed = 0.0;

        //Get today time in millis
        long todayTimeInMillis = TimeUtils.getTodayTimeInMillis();

        //Iterate and find the values that have greater time
        for (SessionLogModel model : list) {
            if (model.getLoggedInTime() >= todayTimeInMillis && model.getUsername().equals(username)) {
                totalDataConsumed += model.getDataConsumed();
            }
        }

        Log.d("STATSLOG", "" + TrafficUtils.getTwoDecimalPlaces(totalDataConsumed));

        return TrafficUtils.getTwoDecimalPlaces(totalDataConsumed);
    }

    /**
     * Get times logged in for single user in a single day
     */
    public static int getTimesLoggedInForSingleDay(Context context, String username) {
        //Get list of sessions
        List<SessionLogModel> list = RealmDatabase.getInstance(context).getSessionLogs();

        int totalTimesLoggedIn = 0;

        //Get today time in millis
        long todayTimeInMillis = TimeUtils.getTodayTimeInMillis();

        //Iterate and find the values that have greater time
        for (SessionLogModel model : list) {
            if (model.getLoggedInTime() >= todayTimeInMillis && model.getUsername().equals(username)) {
                totalTimesLoggedIn++;
            }
        }

        return totalTimesLoggedIn;
    }

    /**
     * Get total duration logged in today for a single account
     */
    public static String getDurationLoggedInForSingleDay(Context context, String username) {
        //Get list of sessions
        List<SessionLogModel> list = RealmDatabase.getInstance(context).getSessionLogs();

        long durationLoggedIn = 0;

        //Get today time in millis
        long todayTimeInMillis = TimeUtils.getTodayTimeInMillis();

        //Iterate and find the values that have greater time
        for (SessionLogModel model : list) {
            if (model.getLoggedInTime() >= todayTimeInMillis && model.getUsername().equals(username)) {
                durationLoggedIn += model.getLoggedInDuration();
            }
        }

        return TimeUtils.convertLongToDuration(durationLoggedIn);
    }
}
