package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;
import android.net.TrafficStats;

/**
 * Created by gurleensethi on 15/11/16.
 */

public class TrafficUtils {
    /**
     * Store Total number of bytes from Traffic Stats(Received + Transmitted),
     * store it in SP
     */
    private static void saveInitialBytes(Context context) {
        //Get total bytes
        long totalBytes = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();

        //Store the bytes
        SharedPreferenceUtils.setInitialDataBytes(context, totalBytes);
    }

    /**
     * Get current Data usage in MB
     */
    private static double getTotalUsage(Context context) {
        //Get initial bytes from SP
        long initialBytes = SharedPreferenceUtils.getInitialDataBytes(context);

        //Get current bytes
        long currentBytes = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();

        //Subtract to get session bytes
        long bytesUsed = currentBytes - initialBytes;

        //Convert to MB
        double bytesUsedInMB = (bytesUsed / 1024) / 1024;

        return bytesUsedInMB;
    }

}
