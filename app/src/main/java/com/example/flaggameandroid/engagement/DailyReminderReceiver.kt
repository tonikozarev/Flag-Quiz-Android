package com.example.flaggameandroid.engagement

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.flaggameandroid.persistence.AppGraph

class DailyReminderReceiver : BroadcastReceiver() {
  override fun onReceive(
    context: Context,
    intent: Intent,
  ) {
    AppGraph.from(context.applicationContext).engagementCoordinator.onDailyCheckTriggered()
  }
}
