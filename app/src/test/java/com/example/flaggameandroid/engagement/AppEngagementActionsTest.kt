package com.example.flaggameandroid.engagement

import com.example.flaggameandroid.persistence.InMemoryProgressStore
import com.example.flaggameandroid.persistence.PersistedAppState
import junit.framework.TestCase.assertEquals
import org.junit.Test
import kotlinx.coroutines.runBlocking

class AppEngagementActionsTest {
  @Test
  fun recordAppOpened_updatesLastOpenedTimestamp() {
    val progressStore =
      InMemoryProgressStore(
        initialState = PersistedAppState(lastOpenedAtEpochMillis = 123L),
      )

    recordAppOpened(
      progressStore = progressStore,
      nowProvider = { 456L },
    )

    assertEquals(456L, runBlocking { progressStore.loadProgress().lastOpenedAtEpochMillis })
  }
}
