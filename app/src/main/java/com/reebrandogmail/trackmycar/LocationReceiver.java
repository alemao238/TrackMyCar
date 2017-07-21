package com.reebrandogmail.trackmycar;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.SmsMessage;
import android.util.Log;

import com.reebrandogmail.trackmycar.fragments.MapsActivity;

/**
 * Created by renanribeirobrando on 09/07/17.
 */


public class LocationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        final Bundle bundle = intent.getExtras();

        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {

                    SmsMessage currentMessage;

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        String format = intent.getStringExtra("format");
                        currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i], format);
                    } else {
                        currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    }

                    String numeroTelefone = currentMessage.getDisplayOriginatingAddress();

                    String mensagem = currentMessage.getDisplayMessageBody();
                    Log.i("SmsReceiver", "senderNum: "+ numeroTelefone + "; message: " + mensagem);

                    Intent i2= new Intent("android.intent.action.SMSRECEBIDO")
                            .putExtra("tracker", numeroTelefone)
                            .putExtra("message", mensagem);
                    context.sendBroadcast(i2);

                    showNotification(context, numeroTelefone, mensagem);
                }
            }
        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);
        }
    }

    private void showNotification(Context context, String telNumber, String message) {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_menu_location);
        mBuilder.setContentTitle("Tracker number: " + telNumber);
        mBuilder.setContentText(message);

        Intent resultIntent = new Intent(context, MapsActivity.class);

        resultIntent
                .putExtra("tracker", telNumber)
                .putExtra("message", message);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MapsActivity.class);


        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // notificationID allows you to update the notification later on.
        mNotificationManager.notify(1, mBuilder.build());
    }
}
