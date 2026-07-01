package com.example.rby_wwe.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.rby_wwe.BaseActivity

class ReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // Ambil data dari intent
        val title = intent.getStringExtra("title") ?: "Pengingat Desa"
        val message = intent.getStringExtra("message") ?: "Waktunya melakukan pengecekan"
        val targetClassName = intent.getStringExtra("target_activity")

        // Tentukan halaman tujuan saat notifikasi diklik
        val targetIntent = if (!targetClassName.isNullOrEmpty()) {
            try {
                val clazz = Class.forName(targetClassName)
                Intent(context, clazz).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
            } catch (e: Exception) {
                Intent(context, BaseActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
                }
            }
        } else {
            Intent(context, BaseActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        }

        // Tampilkan notifikasi menggunakan helper yang sudah ada
        NotificationHelper.showNotification(
            context = context,
            title = title,
            message = message,
            intent = targetIntent
        )
    }
}
