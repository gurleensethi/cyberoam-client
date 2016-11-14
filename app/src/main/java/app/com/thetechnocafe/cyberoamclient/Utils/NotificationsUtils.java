package app.com.thetechnocafe.cyberoamclient.Utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;

import app.com.thetechnocafe.cyberoamclient.Login.LoginActivity;
import app.com.thetechnocafe.cyberoamclient.R;

/**
 * Created by gurleensethi on 12/11/16.
 */

public class NotificationsUtils {
    public static final int SIMPLE_TEXT_NOTIFICATION_ID = 0;
    private static final int NOTIFICATION_PENDING_INTENT_REQUEST_CODE = 1;

    public static void sendSimpleTextNotification(Context context, String titleMessage, String textMessage) {
        //Create Notification Builder
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        //Create notification style
        NotificationCompat.Style style = new NotificationCompat.BigPictureStyle();

        //Set icon, message and title
        builder.setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(titleMessage)
                .setContentText(textMessage)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setAutoCancel(true);

        //Create pending intent from intent and set it to builder
        Intent intent = new Intent(context, LoginActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_PENDING_INTENT_REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        //Deliver the notification
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(SIMPLE_TEXT_NOTIFICATION_ID, builder.build());
    }
}
