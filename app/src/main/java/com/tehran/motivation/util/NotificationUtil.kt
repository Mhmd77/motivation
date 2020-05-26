package com.tehran.motivation.util

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.tehran.motivation.R


private val NOTIFICATION_ID = 0
private val REQUEST_CODE = 0
private val FLAGS = 0


fun NotificationManager.sendNotification(message: String, applicationContext: Context) {
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.motivation_notification_channel_id)
    ).setSmallIcon(R.drawable.ic_note)
        .setContentTitle(applicationContext.getString(R.string.notification_title))
        .setContentText(message)

    notify(NOTIFICATION_ID, builder.build())

}