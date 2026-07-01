package com.example.rby_wwe.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.rby_wwe.R

object NotificationHelper {

    // Gunakan ID channel baru agar sistem mereset settingan ke prioritas tinggi (Heads-up)
    private const val CHANNEL_ID = "notif_desa_heads_up_v1"

    fun showNotification(
        context: Context,
        title: String,
        message: String,
        intent: Intent
    ) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 1. Buat Channel dengan IMPORTANCE_HIGH (Wajib agar melayang di Android 8.0+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Informasi Desa",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifikasi yang muncul di atas layar"
                enableLights(true)
                enableVibration(true)
            }
            manager.createNotificationChannel(channel)
        }

        // 2. PendingIntent agar notifikasi bisa diklik
        // Gunakan requestCode unik (System.currentTimeMillis) agar Intent selalu segar
        val requestCode = System.currentTimeMillis().toInt()
        val pendingIntent = PendingIntent.getActivity(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // 3. Build notifikasi dengan PRIORITY_MAX agar muncul melayang (Heads-up)
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notification) 
            .setContentTitle(title)
            .setContentText(message)
            .setAutoCancel(true) // Hilang setelah diklik
            .setContentIntent(pendingIntent) // Aksi klik
            .setPriority(NotificationCompat.PRIORITY_MAX) 
            .setDefaults(NotificationCompat.DEFAULT_ALL)   // Suara & Getar
            .setSound(soundUri)
            .build()

        manager.notify(requestCode, notification)
    }
}
