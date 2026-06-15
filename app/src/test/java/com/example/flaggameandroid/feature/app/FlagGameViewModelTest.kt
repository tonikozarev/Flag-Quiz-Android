package com.example.flaggameandroid.feature.app

import com.example.flaggameandroid.core.data.QuizQuestionGenerator
import com.example.flaggameandroid.core.data.StaticFlagCatalogRepository
import com.example.flaggameandroid.core.model.GameMode
import com.example.flaggameandroid.core.model.QuizVariant
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import kotlin.random.Random

class FlagGameViewModelTest {
  private fun viewModel(): FlagGameViewModel =
    FlagGameViewModel(
      catalogRepository = StaticFlagCatalogRepository(),
      questionGenerator = QuizQuestionGenerator(Random(3)),
      random = Random(4),
    )

  @Test
  fun trainingMode_startsConfigurableQuiz() {
    val viewModel = viewModel()

    viewModel.onModeSelected(GameMode.Training)
    viewModel.onQuestionCountChanged(12)
    viewModel.onStartQuiz()

    val state = viewModel.uiState.value
    assertEquals(AppScreen.Quiz, state.screen)
    assertEquals(GameMode.Training, state.quiz.mode)
    assertEquals(12, state.quiz.totalQuestions)
  }

  @Test
  fun setup_showsSevenContinentsIncludingSeparateAmericas() {
    val viewModel = viewModel()

    viewModel.onModeSelected(GameMode.Continents)

    assertEquals(
      listOf("Africa", "Antarctica", "Asia", "Europe", "North America", "Oceania", "South America"),
      viewModel.uiState.value.availableContinents,
    )
    assertTrue("Antarctica" !in viewModel.uiState.value.setup.selectedContinents)
  }

  @Test
  fun surpriseMeClearsCountAndCanBeDeselectedForCustomAmount() {
    val viewModel = viewModel()

    viewModel.onModeSelected(GameMode.Training)
    viewModel.onSurpriseMeClicked()
    assertEquals("", viewModel.uiState.value.setup.questionCountInput)
    assertTrue(viewModel.uiState.value.setup.surpriseMe)

    viewModel.onSurpriseMeClicked()
    viewModel.onQuestionCountChanged("14")
    viewModel.onStartQuiz()

    val state = viewModel.uiState.value
    assertEquals(AppScreen.Quiz, state.screen)
    assertEquals(14, state.quiz.totalQuestions)
  }

  @Test
  fun changedAnswerBeforeNext_usesFinalSelectionForScore() {
    val viewModel = viewModel()
    startSingleVariantQuiz(viewModel, QuizVariant.FlagToCountry, count = 3)

    val question = viewModel.uiState.value.quiz.currentQuestion!!
    val correct = question.correctCountry
    val wrong = question.options.first { it.code != correct.code }

    viewModel.onCountryAnswerSelected(correct)
    assertEquals(0, viewModel.uiState.value.quiz.currentPlayer.score)

    viewModel.onCountryAnswerSelected(wrong)
    viewModel.onNextQuestion()

    assertEquals(0, viewModel.uiState.value.quiz.players.first().score)
    assertEquals(false, viewModel.uiState.value.quiz.results.first().isCorrect)
  }

  @Test
  fun fiveCorrectInARow_keepsNewHintPointLockedUntilQuizEnds() {
    val viewModel = viewModel()
    startSingleVariantQuiz(viewModel, QuizVariant.FlagToCountry, count = 6)

    repeat(5) {
      answerCurrentCorrectly(viewModel)
      viewModel.onNextQuestion()
    }

    val player = viewModel.uiState.value.quiz.players.first()
    assertEquals(5, player.score)
    assertEquals(0, player.hintPoints)
    assertEquals(1, player.earnedHintPoints)
    assertEquals(5, player.correctStreak)
  }

  @Test
  fun earnedHintPointsBecomeUsableAfterQuizEnds() {
    val viewModel = viewModel()
    startSingleVariantQuiz(viewModel, QuizVariant.FlagToCountry, count = 5)

    repeat(5) {
      answerCurrentCorrectly(viewModel)
      viewModel.onNextQuestion()
    }

    val player = viewModel.uiState.value.quiz.players.first()
    assertEquals(AppScreen.Results, viewModel.uiState.value.screen)
    assertEquals(1, player.hintPoints)
    assertEquals(0, player.earnedHintPoints)
    assertEquals(1, viewModel.uiState.value.hintBanks.getValue("Solo"))
  }

  @Test
  fun wrongAnswerResetsCorrectStreakBeforeHintIsEarned() {
    val viewModel = viewModel()
    startSingleVariantQuiz(viewModel, QuizVariant.FlagToCountry, count = 8)

    repeat(4) {
      answerCurrentCorrectly(viewModel)
      viewModel.onNextQuestion()
    }
    answerCurrentWrongly(viewModel)
    viewModel.onNextQuestion()
    repeat(3) {
      answerCurrentCorrectly(viewModel)
      viewModel.onNextQuestion()
    }

    val player = viewModel.uiState.value.quiz.players.first()
    assertEquals(7, player.score)
    assertEquals(0, player.hintPoints)
    assertEquals(0, player.earnedHintPoints)
    assertEquals(3, player.correctStreak)
  }

  @Test
  fun hintCostsTwoOldUsablePoints() {
    val viewModel = viewModel()
    startSingleVariantQuiz(viewModel, QuizVariant.FlagToCountry, count = 10)

    repeat(10) {
      answerCurrentCorrectly(viewModel)
      viewModel.onNextQuestion()
    }

    assertEquals(AppScreen.Results, viewModel.uiState.value.screen)
    startSingleVariantQuiz(viewModel, QuizVariant.FlagToCountry, count = 2)

    assertEquals(2, viewModel.uiState.value.quiz.currentPlayer.hintPoints)
    viewModel.onUseHint()

    val quiz = viewModel.uiState.value.quiz
    assertEquals(0, quiz.currentPlayer.hintPoints)
    assertTrue(quiz.hintUsedOnCurrentQuestion)
    assertEquals(2, quiz.hiddenOptionCodes.size)
  }

  @Test
  fun multiplayer_rotatesTurnsAndKeepsSeparateScores() {
    val viewModel = viewModel()

    viewModel.onModeSelected(GameMode.LocalMultiplayer)
    viewModel.onQuestionCountChanged(4)
    viewModel.onStartQuiz()

    assertEquals("Player 1", viewModel.uiState.value.quiz.currentPlayer.name)
    answerCurrentCorrectly(viewModel)
    viewModel.onNextQuestion()

    assertEquals("Player 2", viewModel.uiState.value.quiz.currentPlayer.name)
    answerCurrentWrongly(viewModel)
    viewModel.onNextQuestion()

    val players = viewModel.uiState.value.quiz.players
    assertEquals(1, players.first { it.name == "Player 1" }.score)
    assertEquals(0, players.first { it.name == "Player 2" }.score)
    assertEquals("Player 1", viewModel.uiState.value.quiz.currentPlayer.name)
  }

  @Test
  fun typedAnswer_acceptsAliasCaseAndWhitespace() {
    val viewModel = viewModel()

    viewModel.onModeSelected(GameMode.Training)
    QuizVariant.entries.filterNot { it == QuizVariant.TypeCountryName }.forEach(viewModel::onVariantToggled)
    viewModel.onQuestionCountChanged(1)
    viewModel.onStartQuiz()

    val country = viewModel.uiState.value.quiz.currentQuestion!!.correctCountry
    val answer = country.aliases.firstOrNull() ?: country.name
    viewModel.onTypedAnswerChanged("  ${answer.uppercase()}  ")
    viewModel.onNextQuestion()

    assertTrue(viewModel.uiState.value.quiz.results.first().isCorrect)
  }

  private fun startSingleVariantQuiz(
    viewModel: FlagGameViewModel,
    variant: QuizVariant,
    count: Int,
  ) {
    viewModel.onModeSelected(GameMode.Training)
    QuizVariant.entries.filterNot { it == variant }.forEach(viewModel::onVariantToggled)
    viewModel.onQuestionCountChanged(count)
    viewModel.onStartQuiz()
  }

  private fun answerCurrentCorrectly(viewModel: FlagGameViewModel) {
    val question = viewModel.uiState.value.quiz.currentQuestion!!
    when (question.variant) {
      QuizVariant.TypeCountryName -> viewModel.onTypedAnswerChanged(question.correctCountry.name)
      QuizVariant.FlagToCountry,
      QuizVariant.CountryToFlag -> viewModel.onCountryAnswerSelected(question.correctCountry)
    }
  }

  private fun answerCurrentWrongly(viewModel: FlagGameViewModel) {
    val question = viewModel.uiState.value.quiz.currentQuestion!!
    when (question.variant) {
      QuizVariant.TypeCountryName -> viewModel.onTypedAnswerChanged("wrong answer")
      QuizVariant.FlagToCountry,
      QuizVariant.CountryToFlag -> viewModel.onCountryAnswerSelected(question.options.first { it.code != question.correctCountry.code })
    }
  }
}
