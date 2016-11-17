package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gurleensethi on 16/11/16.
 */

public class TimeUtils {
    public static String getTimeInString(Context context) {
        //Get stored time
        long timeInMillis = SharedPreferenceUtils.getLoggedInTime(context);

        //Convert to date
        Date date = new Date(timeInMillis);

        //Get Formatter
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:MM a");

        return simpleDateFormat.format(date);
    }
}
