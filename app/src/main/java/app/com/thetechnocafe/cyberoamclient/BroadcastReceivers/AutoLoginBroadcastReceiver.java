package app.com.thetechnocafe.cyberoamclient.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import app.com.thetechnocafe.cyberoamclient.Utils.SharedPreferenceUtils;

/**
 * Created by gurleensethi on 23/11/16.
 */

public class AutoLoginBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //Get connectivity service
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();

        if (activeNetwork != null) {

            //Check if connection type is WiFi
            boolean isWifi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;

            if (isWifi) {
                //Check if AutoLogin is enabled
                if (SharedPreferenceUtils.getAutoLoginOnWifi(context)) {
                    Toast.makeText(context, "Wifi!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
