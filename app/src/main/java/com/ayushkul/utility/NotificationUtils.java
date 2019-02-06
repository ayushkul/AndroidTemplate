package com.ayushkul.utility;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Patterns;

import com.ayushkul.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ayush Kulshrestha on 06/02/19.
 */

public class NotificationUtils {
    private Context context;

    public NotificationUtils(Context context) {
        this.context = context;
    }

    public void showNotificationMessage(final String title, final String message, final long timeStamp, Intent intent, String imageUrl) {
        // Check for empty push message
        if (TextUtils.isEmpty(message))
            return;


        // notification icon
        final int icon = R.mipmap.ic_launcher;

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, "default");

//        if (intent.getStringExtra(AppConstants.NOTIFICATION_TYPE_KEY) != null && intent.getStringExtra(AppConstants.NOTIFICATION_TYPE_KEY).equals(AppConstants.RECALL_NOTIFICATION))
//            mBuilder.setStyle(new NotificationCompat.BigTextStyle()
//                    .bigText(message));
//        else
            mBuilder.setStyle(new NotificationCompat.InboxStyle().addLine(message));

        final Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (!TextUtils.isEmpty(imageUrl)) {

            if (imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {

                Bitmap bitmap = getBitmapFromURL(imageUrl);

                if (bitmap != null)
                    showBigNotification(bitmap, mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
                else
                    showSmallNotification(mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
            }
        } else {
            showSmallNotification(mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
            playNotificationSound();
        }
    }

    private void showBigNotification(Bitmap bitmap, NotificationCompat.Builder mBuilder, int icon, String title, String message, long timeStamp, PendingIntent resultPendingIntent, Uri alarmSound) {

    }

    private void showSmallNotification(NotificationCompat.Builder mBuilder, int icon, String title, String message, long timeStamp, PendingIntent resultPendingIntent, Uri alarmSound) {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.addLine(message);

//        Bitmap largeIcon = BitmapFactory.decodeResource(mBuilder.mContext.getResources(), R.mipmap.ic_launcher);

        // mBuilder.setLargeIcon(largeIcon);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
            mBuilder.setColor(ContextCompat.getColor(context, R.color.colorAccent));
        } else
            mBuilder.setSmallIcon(icon);

        Notification notification = mBuilder.setTicker(title)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setColor(context.getResources().getColor(android.R.color.black))
                .setSound(alarmSound)
                .setWhen(timeStamp)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build();


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && notificationManager != null) {
            NotificationChannel channel = new NotificationChannel("default", "default", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
        if (notificationManager != null) {
            int num = (int) System.currentTimeMillis();
            notificationManager.notify(num, notification);

        }
    }

    /**
     * Downloading push notification image before displaying it in
     * the notification tray
     */
    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Playing notification sound
    private void playNotificationSound() {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + context.getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(context, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
