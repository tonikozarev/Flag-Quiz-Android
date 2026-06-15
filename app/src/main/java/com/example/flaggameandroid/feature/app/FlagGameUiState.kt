package com.example.flaggameandroid.feature.app

import com.example.flaggameandroid.core.model.AllInType
import com.example.flaggameandroid.core.model.FlagCountry
import com.example.flaggameandroid.core.model.FlagQuestion
import com.example.flaggameandroid.core.model.GameMode
import com.example.flaggameandroid.core.model.PlayerProgress
import com.example.flaggameandroid.core.model.QuestionResult
import com.example.flaggameandroid.core.model.QuizVariant

sealed interface AppScreen {
  data object Menu : AppScreen

  data object Setup : AppScreen

  data object Quiz : AppScreen

  data object Results : AppScreen
}

enum class MultiplayerQuizBase(
  val title: String,
) {
  Continents("Continents setup"),
  AllIn("All-In setup"),
}

data class SetupState(
  val mode: GameMode = GameMode.Training,
  val variants: Set<QuizVariant> = QuizVariant.entries.toSet(),
  val selectedContinents: Set<String> = emptySet(),
  val questionCountInput: String = "10",
  val surpriseMe: Boolean = false,
  val allInType: AllInType = AllInType.Hardcore,
  val multiplayerBase: MultiplayerQuizBase = MultiplayerQuizBase.Continents,
  val playerNames: List<String> = listOf("Player 1", "Player 2"),
) {
  val questionCount: Int?
    get() = questionCountInput.toIntOrNull()

  val needsContinents: Boolean
    get() = mode == GameMode.Continents

  val needsPlayers: Boolean
    get() = mode == GameMode.LocalMultiplayer
}

data class QuizState(
  val mode: GameMode? = null,
  val allInType: AllInType? = null,
  val questions: List<FlagQuestion> = emptyList(),
  val currentQuestionIndex: Int = 0,
  val players: List<PlayerProgress> = listOf(PlayerProgress("Solo")),
  val currentPlayerIndex: Int = 0,
  val selectedCountry: FlagCountry? = null,
  val typedAnswer: String = "",
  val hiddenOptionCodes: Set<String> = emptySet(),
  val typedHintPrefix: String? = null,
  val hintUsedOnCurrentQuestion: Boolean = false,
  val results: List<QuestionResult> = emptyList(),
) {
  val currentQuestion: FlagQuestion?
    get() = questions.getOrNull(currentQuestionIndex)

  val currentPlayer: PlayerProgress
    get() = players.getOrElse(currentPlayerIndex) { PlayerProgress("Solo") }

  val totalQuestions: Int
    get() = questions.size

  val isLastQuestion: Boolean
    get() = currentQuestionIndex >= questions.lastIndex

  val isMultiplayer: Boolean
    get() = players.size > 1
}

data class FlagGameUiState(
  val screen: AppScreen = AppScreen.Menu,
  val setup: SetupState = SetupState(),
  val quiz: QuizState = QuizState(),
  val availableContinents: List<String> = emptyList(),
  val hintBanks: Map<String, Int> = emptyMap(),
  val setupError: String? = null,
)
