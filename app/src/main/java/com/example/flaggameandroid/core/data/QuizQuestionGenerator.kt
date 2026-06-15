package com.example.flaggameandroid.core.data

import com.example.flaggameandroid.core.model.FlagCountry
import com.example.flaggameandroid.core.model.FlagQuestion
import com.example.flaggameandroid.core.model.QuizConfig
import com.example.flaggameandroid.core.model.QuizVariant
import kotlin.random.Random

class QuizQuestionGenerator(
  private val random: Random = Random.Default,
) {
  fun buildQuestions(
    countries: List<FlagCountry>,
    config: QuizConfig,
  ): List<FlagQuestion> {
    val pool = countries.distinctBy { it.code }.shuffled(random)
    require(pool.size >= 4) { "Need at least 4 countries to build a quiz." }

    val variants = config.variants.ifEmpty { QuizVariant.entries.toSet() }.toList()
    val targetCount = config.questionCount.coerceIn(1, pool.size)

    return pool.take(targetCount).mapIndexed { index, correctCountry ->
      val variant = variants[index % variants.size]
      val wrongOptions =
        pool
          .filterNot { it.code == correctCountry.code }
          .shuffled(random)
          .take(3)

      FlagQuestion(
        correctCountry = correctCountry,
        options = (wrongOptions + correctCountry).shuffled(random),
        variant = variant,
      )
    }.shuffled(random)
  }
}

object QuizAnswerChecker {
  fun isCountrySelectionCorrect(
    selectedCountry: FlagCountry?,
    correctCountry: FlagCountry,
  ): Boolean = selectedCountry?.code == correctCountry.code

  fun isTypedAnswerCorrect(
    typedAnswer: String,
    correctCountry: FlagCountry,
  ): Boolean {
    val normalizedAnswer = typedAnswer.normalizeAnswer()
    val acceptedAnswers = (listOf(correctCountry.name) + correctCountry.aliases).map { it.normalizeAnswer() }
    return normalizedAnswer in acceptedAnswers
  }

  fun String.normalizeAnswer(): String =
    trim()
      .lowercase()
      .replace(Regex("[^a-z0-9 ]"), "")
      .replace(Regex("\\s+"), " ")
}
