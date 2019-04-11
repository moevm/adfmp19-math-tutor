package com.example.mathtutor

import android.app.NotificationManager
import android.content.Context
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        Log.i("FIREBASE", remoteMessage!!.notification!!.title)
        val notificationBuilder = NotificationCompat.Builder(this, "channel_id")
            .setContentTitle(remoteMessage!!.notification!!.title)
            .setContentText(remoteMessage.notification!!.body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigTextStyle())
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(0, notificationBuilder.build())
    }
}