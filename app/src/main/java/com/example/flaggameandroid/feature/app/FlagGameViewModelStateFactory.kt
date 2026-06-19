package com.example.flaggameandroid.feature.app

import com.example.flaggameandroid.core.model.FlagCountry
import com.example.flaggameandroid.core.model.GameMode
import com.example.flaggameandroid.core.model.ProgressionRules
import com.example.flaggameandroid.persistence.PersistedAppState

internal fun buildInitialUiState(
  initialPersistedState: PersistedAppState,
  allContinents: List<String>,
  selectableContinents: List<String>,
  countries: List<FlagCountry>,
): FlagGameUiState =
  FlagGameUiState(
    settings =
      SettingsState(
        hintDifficulty = initialPersistedState.hintDifficulty,
        reminderEnabled = initialPersistedState.reminderEnabled,
        language = initialPersistedState.language,
      ),
    availableContinents = allContinents,
    setup = SetupState(selectedContinents = selectableContinents.toSet()),
    questionCountLimit = countries.size,
    profile =
      ProfileState(
        accountName = initialPersistedState.accountName,
        avatarIndex = initialPersistedState.avatarIndex.coerceIn(0, ProgressionRules.TotalAvatarCount - 1),
      ),
    hintCount = initialPersistedState.hintCount,
    ratings = initialPersistedState.ratings,
    achievements = initialPersistedState.achievements,
    levelProgress =
      LevelProgressState(
        level = initialPersistedState.level,
        hintsTowardNextLevel = initialPersistedState.hintsTowardNextLevel,
        correctAnswersTowardNextLevel = initialPersistedState.correctAnswersTowardNextLevel,
        eligibleQuizzesTowardNextLevel = initialPersistedState.eligibleQuizzesTowardNextLevel,
      ),
    lastOpenedAtEpochMillis = initialPersistedState.lastOpenedAtEpochMillis,
    lastPlayedAtEpochMillis = initialPersistedState.lastPlayedAtEpochMillis,
    inactiveIconActive = initialPersistedState.inactiveIconActive,
  )

internal fun FlagGameUiState.resetToMenu(
  allContinents: List<String>,
  selectableContinents: List<String>,
  questionCountLimit: Int,
): FlagGameUiState =
  copy(
    screen = AppScreen.Menu,
    availableContinents = allContinents,
    setup = SetupState(selectedContinents = selectableContinents.toSet()),
    questionCountLimit = questionCountLimit,
    setupError = null,
  )

internal fun FlagGameUiState.toPersistedAppState(): PersistedAppState =
  PersistedAppState(
    hintDifficulty = settings.hintDifficulty,
    reminderEnabled = settings.reminderEnabled,
    language = settings.language,
    accountName = profile.accountName,
    avatarIndex = profile.avatarIndex,
    hintCount = hintCount,
    ratings = ratings,
    achievements = achievements,
    level = levelProgress.level,
    hintsTowardNextLevel = levelProgress.hintsTowardNextLevel,
    correctAnswersTowardNextLevel = levelProgress.correctAnswersTowardNextLevel,
    eligibleQuizzesTowardNextLevel = levelProgress.eligibleQuizzesTowardNextLevel,
    lastOpenedAtEpochMillis = lastOpenedAtEpochMillis,
    lastPlayedAtEpochMillis = lastPlayedAtEpochMillis,
    inactiveIconActive = inactiveIconActive,
  )

internal fun buildSetupForMode(
  mode: GameMode,
  selectableContinents: List<String>,
  countries: List<FlagCountry>,
  displayName: String,
): SetupState =
  SetupState(
    mode = mode,
    selectedContinents = selectableContinents.toSet(),
    questionCountInput = if (mode == GameMode.AllIn) countries.size.toString() else "10",
    playerNames = listOf(displayName, "Player 2"),
  )
