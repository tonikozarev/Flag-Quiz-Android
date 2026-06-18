package com.example.flaggameandroid.persistence

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.flaggameandroid.core.model.AchievementId
import com.example.flaggameandroid.core.model.AchievementsProgress
import com.example.flaggameandroid.core.model.HintDifficulty
import com.example.flaggameandroid.core.model.MedalTier
import com.example.flaggameandroid.core.model.RatingsProgress
import com.example.flaggameandroid.feature.app.AppLanguage

@Entity(tableName = "progress")
data class ProgressEntity(
  @PrimaryKey val id: Int = SingletonId,
  val hintCount: Int,
  val level: Int,
  val hintsTowardNextLevel: Int,
  val correctAnswersTowardNextLevel: Int,
  val eligibleQuizzesTowardNextLevel: Int,
  val lastOpenedAtEpochMillis: Long = 0L,
  val lastPlayedAtEpochMillis: Long = 0L,
  val inactiveIconActive: Boolean = false,
  val ratingsSerialized: String = "",
  val achievementUnlocksSerialized: String = "",
  val accountName: String = "",
  val avatarIndex: Int = 0,
  val languageName: String = AppLanguage.English.name,
) {
  fun toPersistedAppState(hintDifficultyName: String): PersistedAppState =
    PersistedAppState(
      hintDifficulty = enumValueOf<HintDifficulty>(hintDifficultyName),
      hintCount = hintCount,
      level = level,
      hintsTowardNextLevel = hintsTowardNextLevel,
      correctAnswersTowardNextLevel = correctAnswersTowardNextLevel,
      eligibleQuizzesTowardNextLevel = eligibleQuizzesTowardNextLevel,
      lastOpenedAtEpochMillis = lastOpenedAtEpochMillis,
      lastPlayedAtEpochMillis = lastPlayedAtEpochMillis,
      inactiveIconActive = inactiveIconActive,
      ratings = ratingsSerialized.toRatingsProgress(),
      achievements = achievementUnlocksSerialized.toAchievementsProgress(),
      accountName = accountName,
      avatarIndex = avatarIndex,
      language = AppLanguage.entries.firstOrNull { it.name == languageName } ?: AppLanguage.English,
    )

  companion object {
    const val SingletonId: Int = 1
  }
}

@Entity(tableName = "quiz_history")
data class QuizHistoryEntity(
  @PrimaryKey(autoGenerate = true) val id: Long = 0,
  val mode: String,
  val totalQuestions: Int,
  val correctAnswers: Int,
  val skippedAnswers: Int,
  val netScore: Int,
  val completedAtEpochMillis: Long,
)

fun RatingsProgress.serialize(): String =
  MedalFields.joinToString(separator = "|") { (key, tier) -> "$key=${countFor(tier)}" }

fun String.toRatingsProgress(): RatingsProgress {
  if (isBlank()) return RatingsProgress()
  val values =
    split("|")
      .mapNotNull { token ->
        val parts = token.split("=")
        if (parts.size == 2) {
          parts[0] to parts[1].toIntOrNull()
        } else {
          null
        }
      }.toMap()
  return RatingsProgress(
    bronzeCount = values["bronze"] ?: 0,
    silverCount = values["silver"] ?: 0,
    goldCount = values["gold"] ?: 0,
    titaniumCount = values["titanium"] ?: 0,
    diamondCount = values["diamond"] ?: 0,
  )
}

fun AchievementsProgress.serialize(): String =
  unlockedAtEpochMillisById.entries
    .sortedBy { it.key.name }
    .joinToString(separator = "|") { "${it.key.name}=${it.value}" }

fun String.toAchievementsProgress(): AchievementsProgress {
  if (isBlank()) return AchievementsProgress()
  val unlocked =
    split("|")
      .mapNotNull { token ->
        val parts = token.split("=")
        if (parts.size != 2) return@mapNotNull null
        val achievementId = AchievementId.entries.firstOrNull { it.name == parts[0] } ?: return@mapNotNull null
        val unlockedAt = parts[1].toLongOrNull() ?: return@mapNotNull null
        achievementId to unlockedAt
      }.toMap()
  return AchievementsProgress(unlockedAtEpochMillisById = unlocked)
}

private val MedalFields =
  listOf(
    "bronze" to MedalTier.Bronze,
    "silver" to MedalTier.Silver,
    "gold" to MedalTier.Gold,
    "titanium" to MedalTier.Titanium,
    "diamond" to MedalTier.Diamond,
  )
