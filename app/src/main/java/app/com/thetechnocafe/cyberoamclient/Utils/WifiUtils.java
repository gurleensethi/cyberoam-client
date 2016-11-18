package app.com.thetechnocafe.cyberoamclient.Utils;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by gurleensethi on 18/11/16.
 */

public class WifiUtils {
    public static int getWifiStrength(Context context) {
        //Get wifi manager
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        //Get wifi info
        WifiInfo info = wifiManager.getConnectionInfo();

        //Return level
        return WifiManager.calculateSignalLevel(info.getRssi(), ValueUtils.WIFI_LEVELS);
    }
}
