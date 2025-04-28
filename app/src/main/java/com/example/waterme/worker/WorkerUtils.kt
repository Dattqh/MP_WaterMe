package com.example.waterme.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.waterme.CHANNEL_ID
import com.example.waterme.MainActivity
import com.example.waterme.NOTIFICATION_ID
import com.example.waterme.NOTIFICATION_TITLE
import com.example.waterme.R
import com.example.waterme.REQUEST_CODE
import com.example.waterme.VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
import com.example.waterme.VERBOSE_NOTIFICATION_CHANNEL_NAME
import kotlin.random.Random

fun makePlantReminderNotification(
    message: String,
    context: Context
) {
    println("makePlantReminderNotification: Creating notification with message: $message")

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(
            CHANNEL_ID,
            VERBOSE_NOTIFICATION_CHANNEL_NAME,
            importance
        ).apply {
            description = VERBOSE_NOTIFICATION_CHANNEL_DESCRIPTION
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager?
        notificationManager?.createNotificationChannel(channel) ?: run {
            println("Error: NotificationManager is null")
            return
        }
    }

    val pendingIntent: PendingIntent = createPendingIntent(context)

    // Thông điệp ngẫu nhiên bằng tiếng Việt
    val notificationMessages = listOf(
        context.getString(R.string.time_to_water, message),
        "Cây $message đang khát nước! Tưới ngay nhé!",
        "Đừng quên tưới $message nha!",
        "Hãy chăm sóc $message bằng một ít nước nào!"
    )
    val randomMessage = notificationMessages[Random.nextInt(notificationMessages.size)]

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle(NOTIFICATION_TITLE)
        .setContentText(randomMessage)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    try {
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, builder.build())
        println("makePlantReminderNotification: Notification sent successfully")
    } catch (e: SecurityException) {
        println("Error: Notification permission not granted - ${e.message}")
    }
}

fun createPendingIntent(appContext: Context): PendingIntent {
    val intent = Intent(appContext, MainActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    // Flag to detect unsafe launches of intents for Android 12 and higher
    // to improve platform security
    var flags = PendingIntent.FLAG_UPDATE_CURRENT
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        flags = flags or PendingIntent.FLAG_IMMUTABLE
    }

    return PendingIntent.getActivity(
        appContext,
        REQUEST_CODE,
        intent,
        flags
    )
}
