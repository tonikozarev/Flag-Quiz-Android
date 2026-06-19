package com.example.flaggameandroid.feature.app

import com.example.flaggameandroid.core.model.FlagCountry
import java.util.Locale

internal fun AppLanguage.toLocale(): Locale =
  when (this) {
    AppLanguage.English -> Locale.ENGLISH
    AppLanguage.Bulgarian -> Locale.forLanguageTag("bg")
    AppLanguage.German -> Locale.GERMAN
  }

internal fun FlagCountry.localizedName(language: AppLanguage): String {
  val displayName = Locale("", code).getDisplayCountry(language.toLocale()).trim()
  return displayName.ifBlank { name }
}

internal fun FlagCountry.acceptedTypedAnswers(language: AppLanguage): List<String> {
  val localizedAliases = localizedCountryAliases[language].orEmpty()[code].orEmpty()
  return when (language) {
    AppLanguage.English -> listOf(name) + aliases + localizedAliases
    AppLanguage.Bulgarian,
    AppLanguage.German -> listOf(localizedName(language)) + localizedAliases
  }.distinct()
}

internal fun localizedContinentName(
  continent: String,
  language: AppLanguage,
): String = localizedContinentNames[language].orEmpty()[continent] ?: continent
