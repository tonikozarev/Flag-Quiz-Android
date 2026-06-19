package com.example.flaggameandroid.engagement

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

internal class DailyReminderScheduler(
  private val context: Context,
  private val nowProvider: () -> Long = System::currentTimeMillis,
) {
  fun scheduleDailyCheck() {
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager ?: return
    val pendingIntent = dailyCheckPendingIntent()
    val triggerAtMillis = DailyEngagementRules.nextReminderEpochMillis(nowProvider())

    alarmManager.setInexactRepeating(
      AlarmManager.RTC_WAKEUP,
      triggerAtMillis,
      AlarmManager.INTERVAL_DAY,
      pendingIntent,
    )
  }

  private fun dailyCheckPendingIntent(): PendingIntent {
    val intent = Intent(context, DailyReminderReceiver::class.java)
    return PendingIntent.getBroadcast(
      context,
      SchedulerRequestCodes.DailyReminder,
      intent,
      PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )
  }
}

private object SchedulerRequestCodes {
  const val DailyReminder: Int = 2002
}
