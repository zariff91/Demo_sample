package com.tiseno.poplook.functions;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tiseno.poplook.MainActivity;
import com.useinsider.insider.Insider;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;

/**
 * Created by rahn on 10/1/15.
 */
public class MyFcmListenerService extends FirebaseMessagingService {
//    @Override
//    NotificationCompat.Builder notificationBuilder;
    private static final String TAG = "MyGcmListenerService";

//    /**
//     * Called when message is received.
//     *
//     * @param from SenderID of the sender.
//     * @param data Data bundle containing message data as key/value pairs.
//     *             For Set of keys use data.keySet().
//     */
    // [START receive_message]

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.e("newToken here = ", s);
        getSharedPreferences("_", MODE_PRIVATE).edit().putString("fb", s).apply();
    }


    @Override
    public void onMessageReceived(RemoteMessage message){


        super.onMessageReceived(message);
        if (message != null && message.getData().containsKey("source") && message.getData().get("source").equals("Insider")) {
            Insider.Instance.handleFCMNotification(getApplicationContext(),message);

//            String from = message.getFrom();
//            Map data = message.getData();
//            Log.d(TAG, "From: " + from);
//            Log.d(TAG, "Message: " + message);
//            Log.d(TAG, "Category ID: " + data.get("categoryId"));
//
//            String imageURL = data.get("image_url").toString();
//            String categoryID = data.get("categoryId").toString();
//            String categoryName = data.get("categoryName").toString();

            return;
        }



//        if (from.startsWith("/topics/")) {
//            // message received from some topic.
//        } else {
//            // normal downstream message.
//        }

        // [START_EXCLUDE]
        /**
         * Production applications would usually process the message here.
         * Eg: - Syncing with server.
         *     - Store message in local database.
         *     - Update UI.
         */

        /**
         * In some cases it may be useful to show a notification indicating to the user
         * that a message was received.
         */
//        sendNotification(message,categoryID,categoryName,imageURL);
        // [END_EXCLUDE]
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received GCM message.
     *
     * @param message GCM message received.
     */
    private void sendNotification(String message, String categoryID, String categoryName,String imageURL) {
        Bitmap remote_picture = null;

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("comeFromNotification", "1");
        intent.putExtra("categoryID", categoryID);
        intent.putExtra("categoryName", categoryName);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("comeFromNotification", "1");
        editor.putString("categoryID", categoryID);
        editor.putString("categoryName", categoryName);
        editor.apply();

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
        notiStyle.setSummaryText(message);

        try {
            remote_picture = BitmapFactory.decodeStream((InputStream) new URL(imageURL).getContent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        notiStyle.bigPicture(remote_picture);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            try {
//                if (imageURL.length() == 0) {
//                    notificationBuilder = new NotificationCompat.Builder(this)
//                            .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher))
//                            .setSmallIcon(R.drawable.ic_launcher_trans)
//                            .setContentTitle("POPLOOK Notification")
//                            .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                            .setContentText(message)
//                            .setAutoCancel(true)
//                            .setSound(defaultSoundUri)
//                            .setContentIntent(pendingIntent);
//                } else {
//                    notificationBuilder = new NotificationCompat.Builder(this)
//                            .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher))
//                            .setSmallIcon(R.drawable.ic_launcher_trans)
//                            .setContentTitle("POPLOOK Notification")
//                            .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                            .setContentText(message)
//                            .setAutoCancel(true)
//                            .setSound(defaultSoundUri)
//                            .setStyle(notiStyle)
//                            .setContentIntent(pendingIntent);
//                }
//            }catch (Exception e){
//                notificationBuilder = new NotificationCompat.Builder(this)
//                        .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_launcher))
//                        .setSmallIcon(R.drawable.ic_launcher_trans)
//                        .setContentTitle("POPLOOK Notification")
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                        .setContentText(message)
//                        .setAutoCancel(true)
//                        .setSound(defaultSoundUri)
//                        .setContentIntent(pendingIntent);
//            }
//
//        }else{
//            try {
//                if (imageURL.length() == 0) {
//                    notificationBuilder = new NotificationCompat.Builder(this)
//                            .setSmallIcon(R.drawable.ic_launcher)
//                            .setContentTitle("POPLOOK Notification")
//                            .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                            .setContentText(message)
//                            .setAutoCancel(true)
//                            .setSound(defaultSoundUri)
//                            .setContentIntent(pendingIntent);
//
//                } else {
//                    notificationBuilder = new NotificationCompat.Builder(this)
//                            .setSmallIcon(R.drawable.ic_launcher)
//                            .setContentTitle("POPLOOK Notification")
//                            .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                            .setContentText(message)
//                            .setAutoCancel(true)
//                            .setSound(defaultSoundUri)
//                            .setStyle(notiStyle)
//                            .setContentIntent(pendingIntent);
//
//                }
//            }catch (Exception e){
//                notificationBuilder = new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.ic_launcher)
//                        .setContentTitle("POPLOOK Notification")
//                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
//                        .setContentText(message)
//                        .setAutoCancel(true)
//                        .setSound(defaultSoundUri)
//                        .setContentIntent(pendingIntent);
//            }
//
//        }
//
//
//        NotificationManager notificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
