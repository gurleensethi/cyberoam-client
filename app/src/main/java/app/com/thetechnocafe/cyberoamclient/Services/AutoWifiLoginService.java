package app.com.thetechnocafe.cyberoamclient.Services;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;

import app.com.thetechnocafe.cyberoamclient.BroadcastReceivers.AutoLoginBroadcastReceiver;

public class AutoWifiLoginService extends Service {

    private static final String TAG = AutoWifiLoginService.class.getSimpleName();
    private boolean isBroadcastReceiverRegistered = false;
    private BroadcastReceiver mNetworkStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Intent autoLoginBroadcastIntent = new Intent(context, AutoLoginBroadcastReceiver.class);
            sendBroadcast(autoLoginBroadcastIntent);
        }
    };

    public AutoWifiLoginService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //Create intent filter
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        //Check if receiver is already registered
        if (!isBroadcastReceiverRegistered) {
            //Register the broadcast receiver
            registerReceiver(mNetworkStateReceiver, intentFilter);
        }

        //Change the state to registered
        isBroadcastReceiverRegistered = true;

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mNetworkStateReceiver);
        isBroadcastReceiverRegistered = false;
    }
}
