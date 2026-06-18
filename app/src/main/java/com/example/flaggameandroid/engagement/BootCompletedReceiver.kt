package com.example.flaggameandroid.engagement

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.flaggameandroid.persistence.AppGraph

class BootCompletedReceiver : BroadcastReceiver() {
  override fun onReceive(
    context: Context,
    intent: Intent,
  ) {
    if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
      AppGraph.from(context.applicationContext).engagementCoordinator.scheduleDailyCheck()
    }
  }
}
