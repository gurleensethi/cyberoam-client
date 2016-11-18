package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by gurleensethi on 16/11/16.
 */

public class TimeUtils {
    /**
     * Return the logged in time in String format
     */
    public static String getTimeInString(Context context) {
        //Get stored time
        long timeInMillis = SharedPreferenceUtils.getLoggedInTime(context);

        return convertLongToString(timeInMillis);
    }

    /**
     * Convert time in millis to time in String
     */
    public static String convertLongToString(long timeInMillis) {
        //Convert to date
        Date date = new Date(timeInMillis);

        //Get Formatter
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a");

        return simpleDateFormat.format(date);
    }

    /**
     * Convert a long to time duration format (min:sec)
     */
    public static String convertLongToDuration(long timeInMillis) {
        long mins = TimeUnit.MILLISECONDS.toMinutes(timeInMillis);
        long secs = TimeUnit.MILLISECONDS.toSeconds(timeInMillis) - TimeUnit.MINUTES.toSeconds(mins);

        String formattedTime = mins + ":" + secs;
        return formattedTime;
    }
}
