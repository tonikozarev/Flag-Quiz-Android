package com.example.flaggameandroid.feature.app

import com.example.flaggameandroid.core.model.AchievementId
import com.example.flaggameandroid.core.model.AchievementsProgress
import com.example.flaggameandroid.core.model.RatingsProgress
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class FlagGameProgressStateReducersTest {
  @Test
  fun withTestingLevelUp_incrementsLevelAndAddsFiveHints() {
    val state =
      FlagGameUiState(
        hintCount = 7,
        levelProgress = LevelProgressState(level = 2, hintsTowardNextLevel = 3),
      )

    val updated = state.withTestingLevelUp()

    assertEquals(3, updated.levelProgress.level)
    assertTrue(updated.levelProgress.levelUpVisible)
    assertEquals(12, updated.hintCount)
    assertEquals(12, updated.quiz.currentPlayer.hintPoints)
  }

  @Test
  fun withMedalsReset_clearsOnlyMedals() {
    val state =
      FlagGameUiState(
        ratings = RatingsProgress(bronzeCount = 4, silverCount = 2),
        achievements =
          AchievementsProgress(
            unlockedAtEpochMillisById =
              mapOf(
                AchievementId.FirstPerfect to 100L,
              ),
          ),
      )

    val updated = state.withMedalsReset()

    assertEquals(0, updated.ratings.bronzeCount)
    assertEquals(0, updated.ratings.silverCount)
    assertTrue(updated.achievements.isUnlocked(AchievementId.FirstPerfect))
  }
}
