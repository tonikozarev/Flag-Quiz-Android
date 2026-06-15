package com.example.flaggameandroid.core.model

data class QuizConfig(
  val mode: GameMode,
  val variants: Set<QuizVariant>,
  val selectedContinents: Set<String> = emptySet(),
  val questionCount: Int,
  val surpriseMe: Boolean = false,
  val allInType: AllInType? = null,
  val players: List<String> = listOf("Solo"),
)

data class PlayerProgress(
  val name: String,
  val score: Int = 0,
  val hintPoints: Int = 0,
  val earnedHintPoints: Int = 0,
  val correctStreak: Int = 0,
) {
  fun afterAnswer(isCorrect: Boolean): PlayerProgress {
    if (!isCorrect) return copy(correctStreak = 0)

    val newStreak = correctStreak + 1
    return copy(
      score = score + 1,
      correctStreak = newStreak,
      earnedHintPoints = earnedHintPoints + if (newStreak % 5 == 0) 1 else 0,
    )
  }

  fun spendHint(): PlayerProgress = copy(hintPoints = hintPoints - 2)

  fun releaseEarnedHints(): PlayerProgress =
    copy(
      hintPoints = hintPoints + earnedHintPoints,
      earnedHintPoints = 0,
    )
}
