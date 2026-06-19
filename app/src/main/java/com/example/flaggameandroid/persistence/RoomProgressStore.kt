package com.example.flaggameandroid.persistence

import com.example.flaggameandroid.core.model.HintDifficulty

class RoomProgressStore(
  private val progressDao: ProgressDao,
  private val quizHistoryDao: QuizHistoryDao,
) : ProgressStore {
  override suspend fun loadProgress(): PersistedAppState {
    val entity = progressDao.load() ?: return PersistedAppState()
    return entity.toPersistedAppState(hintDifficultyName = HintDifficulty.Medium.name)
  }

  override suspend fun saveProgress(progress: PersistedAppState) {
    progressDao.upsert(progress.toProgressEntity())
  }

  override suspend fun recordQuiz(history: PersistedQuizHistory) {
    quizHistoryDao.insert(history.toEntity())
  }
}
