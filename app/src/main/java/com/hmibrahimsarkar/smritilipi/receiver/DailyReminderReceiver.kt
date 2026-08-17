package com.hmibrahimsarkar.smritilipi.receiver

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.hmibrahimsarkar.smritilipi.MainActivity
import com.hmibrahimsarkar.smritilipi.R
import com.hmibrahimsarkar.smritilipi.data.local.AppDatabase
import com.hmibrahimsarkar.smritilipi.data.local.preferences.ThemePreferencesRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.Calendar

class DailyReminderReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val pendingResult = goAsync()
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val repository = ThemePreferencesRepository(context)
                val isMasterEnabled = repository.isReminderMasterEnabled.first()
                val isDailyEnabled = repository.isDailyReminderEnabled.first()
                val hour = repository.reminderHour.first()
                val minute = repository.reminderMinute.first()

                if (isMasterEnabled && isDailyEnabled) {
                    // Smart Reminder Check: Check if user wrote or updated any note today
                    val calendar = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, 0)
                        set(Calendar.MINUTE, 0)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)
                    }
                    val startOfDayTimestamp = calendar.timeInMillis

                    val db = AppDatabase.getDatabase(context)
                    val notesWrittenToday = db.noteDao().countNotesUpdatedSince(startOfDayTimestamp)

                    if (notesWrittenToday == 0) {
                        // User has not written anything today, show encouraging notification!
                        showNotification(context)
                    }

                    // Reschedule for tomorrow
                    DailyReminderManager.scheduleDailyReminder(context, hour, minute)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                pendingResult.finish()
            }
        }
    }

    private fun showNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as? NotificationManager
            ?: return

        val channelId = "smritilipi_daily_reminder_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "দৈনিক লেখার রিমাইন্ডার",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "প্রতিদিন নতুন নোট ও ডায়েরি লেখার নোটিফিকেশন"
            }
            notificationManager.createNotificationChannel(channel)
        }

        val mainIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("OPEN_NEW_NOTE", true)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            2001,
            mainIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val encouragingMessages = listOf(
            "আজকের অনুভূতিগুলো নোটে লিখে রাখুন।",
            "একটি নতুন নোট বা আপনার ভাবনা লিখে রাখুন।",
            "আপনার নোটবুক আপনার অপেক্ষায় আছে...",
            "মনের কোণে জমানো কথাগুলো নোটে রূপ দিন।",
            "স্মৃতিলিপিতে লিখে রাখুন আজকের দিনের স্মৃতি..."
        )
        val selectedMessage = encouragingMessages.random()

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("আজ কিছু লিখেছেন?")
            .setContentText(selectedMessage)
            .setStyle(NotificationCompat.BigTextStyle().bigText(selectedMessage))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(2001, notification)
    }
}
