package com.example.flaggameandroid.feature.app

import com.example.flaggameandroid.core.model.ActivityDayRecord
import com.example.flaggameandroid.core.model.AppTimeZone
import com.example.flaggameandroid.core.model.CountryPracticeStats
import com.example.flaggameandroid.core.model.CountryTag
import com.example.flaggameandroid.core.model.DailyChallengeCache
import com.example.flaggameandroid.core.model.DailyChallengeTheme
import com.example.flaggameandroid.core.model.FlagCountry
import com.example.flaggameandroid.core.model.GameMode
import com.example.flaggameandroid.core.model.CreateQuizPreset
import com.example.flaggameandroid.core.model.CreateQuizSource
import com.example.flaggameandroid.core.model.QuestionResult
import com.example.flaggameandroid.core.model.MistakeReviewRecoveryWrongCount
import java.time.Instant
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.temporal.TemporalAdjusters

internal data class QuizPoolResolution(
  val pool: List<FlagCountry>,
  val dailyChallengeCache: DailyChallengeCache? = null,
)

internal fun resolveQuizPool(
  setup: SetupState,
  countries: List<FlagCountry>,
  practiceStats: Map<String, CountryPracticeStats>,
  dailyChallengeCache: DailyChallengeCache?,
  nowEpochMillis: Long,
  timeZone: AppTimeZone = AppTimeZone.Utc,
): QuizPoolResolution {
  val generatedDailyChallengeCache =
    if (setup.mode == GameMode.DailyChallenge) {
      buildDailyChallengeCache(
        countries = countries,
        dailyChallengeCache = dailyChallengeCache,
        nowEpochMillis = nowEpochMillis,
        timeZone = timeZone,
      )
    } else {
      dailyChallengeCache
    }
  val pool =
    when (setup.mode) {
      GameMode.DailyChallenge ->
        if (generatedDailyChallengeCache?.completed == true) {
          emptyList()
        } else {
          dailyChallengePool(countries, generatedDailyChallengeCache, nowEpochMillis, timeZone)
        }
      GameMode.MistakeReview -> countries.filter { country -> practiceStats[country.code]?.isMistakeReviewEligible == true }
      GameMode.CreateQuiz -> createQuizPool(setup, countries)
      GameMode.Continents,
      GameMode.SpeedRun,
      GameMode.LocalMultiplayer,
      GameMode.AllIn,
      GameMode.Training -> countryPoolFor(setup, countries)
    }

  val updatedCache =
    if (setup.mode == GameMode.DailyChallenge) {
      generatedDailyChallengeCache
    } else {
      dailyChallengeCache
    }

  return QuizPoolResolution(
    pool = pool,
    dailyChallengeCache = updatedCache,
  )
}

internal fun dailyChallengePool(
  countries: List<FlagCountry>,
  dailyChallengeCache: DailyChallengeCache?,
  nowEpochMillis: Long,
  timeZone: AppTimeZone = AppTimeZone.Utc,
): List<FlagCountry> {
  val dayKey = localDayKey(nowEpochMillis, timeZone)
  val theme =
    dailyChallengeCache?.takeIf { it.dayKey == dayKey }?.theme
      ?: determineDailyChallengeTheme(dayKey, countries)
  val themedPool = countriesForTheme(theme, countries)
  return if (themedPool.size >= 4) themedPool else countries
}

internal fun buildDailyChallengeCache(
  countries: List<FlagCountry>,
  dailyChallengeCache: DailyChallengeCache?,
  nowEpochMillis: Long,
  timeZone: AppTimeZone = AppTimeZone.Utc,
): DailyChallengeCache {
  val dayKey = localDayKey(nowEpochMillis, timeZone)
  val theme =
    dailyChallengeCache?.takeIf { it.dayKey == dayKey }?.theme
      ?: determineDailyChallengeTheme(dayKey, countries)
  val questionCount = minOf(10, countriesForTheme(theme, countries).size.coerceAtLeast(4))
  val currentInstanceKey = listOf(dayKey, theme.name, questionCount, dayKey).joinToString(separator = ":")
  return if (dailyChallengeCache?.instanceKey == currentInstanceKey) {
    dailyChallengeCache.copy(
      dayKey = dayKey,
      theme = theme,
      questionCount = questionCount,
      seed = dayKey,
    )
  } else {
    DailyChallengeCache(
      dayKey = dayKey,
      theme = theme,
      questionCount = questionCount,
      seed = dayKey,
      completed = false,
      completedAtEpochMillis = 0L,
    )
  }
}

internal fun determineDailyChallengeTheme(
  dayKey: Long,
  countries: List<FlagCountry>,
): DailyChallengeTheme {
  val validThemes =
    DailyChallengeTheme.entries.filter { theme -> countriesForTheme(theme, countries).size >= 4 }
  if (validThemes.isEmpty()) return DailyChallengeTheme.World
  return validThemes[(dayKey.mod(validThemes.size.toLong())).toInt()]
}

internal fun countriesForTheme(
  theme: DailyChallengeTheme,
  countries: List<FlagCountry>,
): List<FlagCountry> =
  when (theme) {
    DailyChallengeTheme.World -> countries
    DailyChallengeTheme.Africa -> countries.filter { it.continent == "Africa" }
    DailyChallengeTheme.Asia -> countries.filter { it.continent == "Asia" }
    DailyChallengeTheme.Europe -> countries.filter { it.continent == "Europe" }
    DailyChallengeTheme.NorthAmerica -> countries.filter { it.continent == "North America" }
    DailyChallengeTheme.Oceania -> countries.filter { it.continent == "Oceania" }
    DailyChallengeTheme.SouthAmerica -> countries.filter { it.continent == "South America" }
    DailyChallengeTheme.FlagsWithStripes -> countries.filter { it.tags.contains(CountryTag.StripedFlag) }
    DailyChallengeTheme.Capitals -> countries.filter { !it.capital.isNullOrBlank() }
  }

internal fun localDayKey(
  epochMillis: Long,
  timeZone: AppTimeZone = AppTimeZone.Utc,
): Long = Instant.ofEpochMilli(epochMillis).atZone(ZoneOffset.UTC).toLocalDate().toEpochDay()

internal fun updateCountryPracticeStats(
  previous: Map<String, CountryPracticeStats>,
  results: List<QuestionResult>,
  completedAtEpochMillis: Long,
  mode: GameMode,
): Map<String, CountryPracticeStats> =
  if (mode == GameMode.Training) {
    previous
  } else {
    val updated =
      results.fold(previous) { statsByCode, result ->
        val code = result.question.correctCountry.code
        val current = statsByCode[code] ?: CountryPracticeStats()
        val next =
          if (result.isCorrect) {
            current.copy(correctCount = current.correctCount + 1)
          } else {
            current.copy(
              wrongCount = current.wrongCount + 1,
              lastMissedAtEpochMillis = completedAtEpochMillis,
            )
          }
        statsByCode + (code to next)
      }

    if (mode != GameMode.MistakeReview) {
      updated
    } else {
      val reviewedCodes = results.map { it.question.correctCountry.code }.toSet()
      updated.mapValues { (code, stats) ->
        if (code in reviewedCodes) {
          stats.copy(wrongCount = MistakeReviewRecoveryWrongCount)
        } else {
          stats
        }
      }
    }
  }

internal fun mistakeReviewEligibleCountryCount(
  practiceStats: Map<String, CountryPracticeStats>,
): Int = practiceStats.values.count { it.isMistakeReviewEligible }

internal fun mistakeReviewEligibleCountries(
  countries: List<FlagCountry>,
  practiceStats: Map<String, CountryPracticeStats>,
): List<FlagCountry> =
  countries.filter { country -> practiceStats[country.code]?.isMistakeReviewEligible == true }

internal fun updateActivityCalendar(
  previous: Map<Long, ActivityDayRecord>,
  completedAtEpochMillis: Long,
  mode: GameMode,
  timeZone: AppTimeZone = AppTimeZone.Utc,
): Map<Long, ActivityDayRecord> {
  val dayKey = localDayKey(completedAtEpochMillis, timeZone)
  val current = previous[dayKey] ?: ActivityDayRecord(dayKey = dayKey)
  val previousDayKey = dayKey - 1
  val previousDay = previous[previousDayKey]
  val streakStartDayKey =
    if (previousDay != null) {
      previousDay.streakStartDayKey ?: previousDayKey
    } else {
      dayKey
    }
  return previous + (
    dayKey to
      current.copy(
        quizzesCompleted = current.quizzesCompleted + 1,
        dailyChallengeCompleted = current.dailyChallengeCompleted || mode == GameMode.DailyChallenge,
        lastUpdatedAtEpochMillis = completedAtEpochMillis,
        streakStartDayKey = streakStartDayKey,
        lastActiveDayKey = dayKey,
      )
  )
}

internal fun recentActivityDays(
  activityCalendar: Map<Long, ActivityDayRecord>,
  days: Int = 30,
  nowEpochMillis: Long = System.currentTimeMillis(),
  timeZone: AppTimeZone = AppTimeZone.Utc,
): List<ActivityDayRecord> {
  val today = localDayKey(nowEpochMillis, timeZone)
  return (0 until days)
    .map { offset ->
      val dayKey = today - offset
      activityCalendar[dayKey] ?: ActivityDayRecord(dayKey = dayKey)
    }
    .reversed()
}

internal fun weekActivityDays(
  activityCalendar: Map<Long, ActivityDayRecord>,
  nowEpochMillis: Long = System.currentTimeMillis(),
  timeZone: AppTimeZone = AppTimeZone.Utc,
): List<ActivityDayRecord> {
  val today = LocalDate.ofEpochDay(localDayKey(nowEpochMillis, timeZone))
  val monday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
  return (0..6).map { offset ->
    val date = monday.plusDays(offset.toLong())
    val dayKey = date.toEpochDay()
    activityCalendar[dayKey] ?: ActivityDayRecord(dayKey = dayKey)
  }
}

internal fun streakLength(
  activityCalendar: Map<Long, ActivityDayRecord>,
  nowEpochMillis: Long = System.currentTimeMillis(),
  timeZone: AppTimeZone = AppTimeZone.Utc,
): Int {
  val today = localDayKey(nowEpochMillis, timeZone)
  val todayRecord = activityCalendar[today] ?: return 0
  val startDayKey = todayRecord.streakStartDayKey ?: today
  return (today - startDayKey + 1).coerceAtLeast(1).toInt()
}

private fun createQuizPool(
  setup: SetupState,
  countries: List<FlagCountry>,
): List<FlagCountry> =
  when (setup.createQuizSource) {
    CreateQuizSource.PresetFilter -> countries.filter { country -> matchesCreateQuizPreset(country, setup.createQuizPreset) }
    CreateQuizSource.ManualCountries -> countries.filter { country -> country.code in setup.selectedCountryCodes }
  }

private fun matchesCreateQuizPreset(
  country: FlagCountry,
  preset: CreateQuizPreset,
): Boolean {
  val code = country.code
  return when (preset) {
    CreateQuizPreset.TwoColors -> code in createQuizTwoColorCountries
    CreateQuizPreset.ThreeColors -> code in createQuizThreeColorCountries
    CreateQuizPreset.FourPlusColors -> code !in createQuizTwoColorCountries && code !in createQuizThreeColorCountries
    CreateQuizPreset.HorizontalStripes -> code in createQuizHorizontalStripeCountries
    CreateQuizPreset.VerticalStripes -> code in createQuizVerticalStripeCountries
    CreateQuizPreset.Stars -> code in createQuizStarCountries
    CreateQuizPreset.Crosses -> code in createQuizCrossCountries
    CreateQuizPreset.NoSymbols -> code in createQuizNoSymbolCountries
    CreateQuizPreset.Animals -> code in createQuizAnimalCountries
  }
}

private val createQuizTwoColorCountries =
  setOf(
    "AL", "AT", "BD", "BH", "CA", "CH", "CN", "DK", "FI", "FM", "GE", "GR", "HN", "ID", "IL",
    "JP", "KG", "KZ", "LV", "MA", "MC", "MK", "NG", "PE", "PK", "PL", "PW", "QA", "SA", "SC",
    "SE", "SG", "SO", "TN", "TO", "TR", "UA", "VN",
  )

private val createQuizThreeColorCountries =
  setOf(
    "AM", "AO", "AR", "AU", "BA", "BB", "BE", "BF", "BI", "BG", "BJ", "BO", "BS", "BW", "BY",
    "CD", "CG", "CI", "CL", "CM", "CO", "CR", "CU", "CV", "CY", "CZ", "DZ", "EE", "FR", "GA",
    "GB", "GN", "HU", "IE", "IR", "IS", "JM", "KH", "KP", "LA", "LB", "LI", "LT", "LU", "MH",
    "MG", "ML", "MN", "MR", "MV", "MW", "NE", "NL", "NO", "NP", "NR", "NZ", "PA", "RO", "RU",
    "RW", "VC", "WS", "SI", "SK", "SL", "SN", "TH", "TT", "US", "UY", "YE",
  )
  
private val createQuizCrossCountries =
  setOf("AU", "BI", "CH", "DK", "DM", "DO", "FI", "FJ", "GB", "GE", "GR", "IS", "JM", "MT", "NZ", "TO", "TV")

private val createQuizHorizontalStripeCountries =
  setOf(
    "AR", "AZ", "BS", "BO", "BW", "BG", "BF", "BI", "CV", "KH", "CF", "CO", "KM", "CR",
    "HR", "EC", "EG", "SV", "GQ", "EE", "SZ", "ET", "GA", "GM", "GH", "GR", "GW", "HT",
    "HN", "IN", "IR", "IQ", "JO", "KE", "KW", "LA", "LB", "LS", "LR", "LY", "LI", "LT",
    "MG", "MW", "MU", "NR", "NI", "NE", "KP", "OM", "PS", "PY", "RW", "SL", "SG", "SK",
    "SI", "SS", "SD", "SR", "SY", "TJ", "TG", "AE", "UZ", "VU", "YE", "ZW",
  )

private val createQuizStarCountries =
  setOf(
    "AO", "AR", "AU", "AZ", "BA", "BF", "BI", "BR", "BS", "BZ", "CA", "CD", "CF", "CL",
    "CM", "CN", "CO", "CR", "CU", "CV", "DJ", "DM", "DO", "ET", "FJ", "FM", "GA", "GD",
    "GH", "GN", "GW", "HN", "IE", "IL", "JM", "JO", "KE", "KN", "KP", "LB", "LR", "LY",
    "MH", "MD", "MG", "MK", "ML", "MN", "MR", "MW", "MY", "MZ", "NA", "NR", "NZ", "PA",
    "PE", "PG", "PH", "PK", "PS", "RW", "SA", "SB", "SC", "SG", "SI", "SN", "SO", "SR",
    "SS", "ST", "SY", "SZ", "TH", "TJ", "TM", "TN", "TO", "TT", "TV", "US", "VE", "VN",
    "WS", "YE", "ZA", "ZM", "ZW",
  )

private val createQuizNoSymbolCountries =
  setOf(
    "AM", "BS", "BH", "BJ", "BO", "BW", "TD", "CO", "CG", "CR", "CI", "CZ", "EE",
    "GA", "GM", "GN", "GY", "ID", "KW", "LV", "LT", "MG", "ML", "MU", "MC", "NE", "NG",
    "PS", "PE", "QA", "SC", "SL", "ZA", "SD", "TZ", "TH", "TT", "AE", "YE",
  )

private val createQuizVerticalStripeCountries =
  setOf("AD", "BE", "BJ", "CF", "DZ", "GA", "GN", "GW", "MG", "ML", "MD", "MN", "OM", "PK", "PE", "PT", "QA", "SN", "VC", "AE", "VA")

private val createQuizAnimalCountries =
  setOf("AD", "AL", "AO", "AR", "BT", "DM", "EC", "EG", "FJ", "GT", "KE", "KI", "KZ", "MD", "ME", "PG", "RS", "UG", "VU", "ZM", "ZW")
