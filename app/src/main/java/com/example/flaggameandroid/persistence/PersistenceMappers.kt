package com.example.flaggameandroid.persistence

import com.example.flaggameandroid.core.model.AchievementId
import com.example.flaggameandroid.core.model.AchievementsProgress
import com.example.flaggameandroid.core.model.HintDifficulty
import com.example.flaggameandroid.core.model.RatingsProgress
import com.example.flaggameandroid.feature.app.AppLanguage

internal fun ProgressEntity.toPersistedAppState(hintDifficultyName: String): PersistedAppState =
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

internal fun PersistedAppState.toProgressEntity(): ProgressEntity =
  ProgressEntity(
    hintCount = hintCount,
    level = level,
    hintsTowardNextLevel = hintsTowardNextLevel,
    correctAnswersTowardNextLevel = correctAnswersTowardNextLevel,
    eligibleQuizzesTowardNextLevel = eligibleQuizzesTowardNextLevel,
    lastOpenedAtEpochMillis = lastOpenedAtEpochMillis,
    lastPlayedAtEpochMillis = lastPlayedAtEpochMillis,
    inactiveIconActive = inactiveIconActive,
    ratingsSerialized = ratings.serialize(),
    achievementUnlocksSerialized = achievements.serialize(),
    accountName = accountName,
    avatarIndex = avatarIndex,
    languageName = language.name,
  )

internal fun PersistedQuizHistory.toEntity(): QuizHistoryEntity =
  QuizHistoryEntity(
    mode = mode.name,
    totalQuestions = totalQuestions,
    correctAnswers = correctAnswers,
    skippedAnswers = skippedAnswers,
    netScore = netScore,
    completedAtEpochMillis = completedAtEpochMillis,
  )
