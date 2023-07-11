package com.pearl.v_ride_lib

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pearl.v_ride.HomeScreen
import com.pearl.v_ride.MainActivity
import com.pearl.v_ride.R


var channelId = ""
var channelName = ""

class MyFirebaseMessagingService: FirebaseMessagingService()  {
    private val tag = "FirebaseMessagingService"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        println("$tag token --> $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        try {

            if (remoteMessage.notification != null) {
                showNotification(remoteMessage.notification?.title, remoteMessage.notification?.body)
            } else {
                showNotification(remoteMessage.data["title"], remoteMessage.data["message"])
            }

        } catch (e: Exception) {
            println("$tag error -->${e.localizedMessage}")
        }
    }

    fun  getRemoteView(title: String, message: String): RemoteViews{
        val remoteView = RemoteViews("com.pearl.v_ride_lib",R.layout.notification)

        remoteView.setTextViewText(R.id.title,title)
        remoteView.setTextViewText(R.id.message,message)
        remoteView.setImageViewResource(R.id.app_logo,R.drawable.bell)

        return remoteView
    }

    private fun showNotification(
        title: String?,
        body: String?
    ) {
       /* val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT
        )*/

        val intent = Intent()
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

         channelId = getString(R.string.channel_id)
        channelName = getString(R.string.channel_name)
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupNotificationChannels(channelId, channelName, notificationManager)
        }

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        var notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.bell)
            .setContentTitle(title)
            .setContentText(body)
            .setVibrate(longArrayOf(1000,1000,1000,1000))
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)

        notificationBuilder = notificationBuilder.setContent(getRemoteView(title!!, body!!))
        notificationManager.notify(0, notificationBuilder.build())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupNotificationChannels(
        channelId: String,
        channelName: String,
        notificationManager: NotificationManager
    ) {

        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        channel.enableLights(true)
        channel.lightColor = Color.GREEN
        channel.enableVibration(true)
        notificationManager.createNotificationChannel(channel)
    }
}