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
): String =
  when (continent) {
    "Africa" ->
      when (language) {
        AppLanguage.English -> "Africa"
        AppLanguage.Bulgarian -> "Африка"
        AppLanguage.German -> "Afrika"
      }
    "Antarctica" ->
      when (language) {
        AppLanguage.English -> "Antarctica"
        AppLanguage.Bulgarian -> "Антарктида"
        AppLanguage.German -> "Antarktis"
      }
    "Asia" ->
      when (language) {
        AppLanguage.English -> "Asia"
        AppLanguage.Bulgarian -> "Азия"
        AppLanguage.German -> "Asien"
      }
    "Europe" ->
      when (language) {
        AppLanguage.English -> "Europe"
        AppLanguage.Bulgarian -> "Европа"
        AppLanguage.German -> "Europa"
      }
    "North America" ->
      when (language) {
        AppLanguage.English -> "North America"
        AppLanguage.Bulgarian -> "Северна Америка"
        AppLanguage.German -> "Nordamerika"
      }
    "Oceania" ->
      when (language) {
        AppLanguage.English -> "Oceania"
        AppLanguage.Bulgarian -> "Океания"
        AppLanguage.German -> "Ozeanien"
      }
    "South America" ->
      when (language) {
        AppLanguage.English -> "South America"
        AppLanguage.Bulgarian -> "Южна Америка"
        AppLanguage.German -> "Südamerika"
      }
    else -> continent
  }

private val localizedCountryAliases: Map<AppLanguage, Map<String, List<String>>> =
  mapOf(
    AppLanguage.Bulgarian to
      mapOf(
        "US" to listOf("САЩ", "Съединени американски щати", "Съединени щати"),
        "GB" to listOf("Великобритания", "Обединено кралство"),
        "AE" to listOf("ОАЕ", "Обединени арабски емирства"),
        "BA" to listOf("Босна и Херцеговина"),
        "CI" to listOf("Кот д'Ивоар", "Кот д Ивоар"),
        "CZ" to listOf("Чехия", "Чешка република"),
        "CD" to listOf("ДР Конго", "Демократична република Конго"),
        "CG" to listOf("Конго", "Република Конго"),
        "DO" to listOf("Доминиканска република"),
        "FM" to listOf("Микронезия"),
        "IR" to listOf("Иран"),
        "KP" to listOf("Северна Корея"),
        "KR" to listOf("Южна Корея"),
        "LA" to listOf("Лаос"),
        "MD" to listOf("Молдова"),
        "PS" to listOf("Палестина"),
        "RU" to listOf("Русия"),
        "SY" to listOf("Сирия"),
        "TZ" to listOf("Танзания"),
        "VA" to listOf("Ватикан"),
        "VE" to listOf("Венецуела"),
        "VN" to listOf("Виетнам"),
      ),
    AppLanguage.German to
      mapOf(
        "US" to listOf("USA", "Vereinigte Staaten von Amerika", "Vereinigte Staaten"),
        "GB" to listOf("Großbritannien", "Vereinigtes Königreich"),
        "AE" to listOf("VAE", "Vereinigte Arabische Emirate"),
        "BA" to listOf("Bosnien und Herzegowina"),
        "CI" to listOf("Elfenbeinküste", "Cote d Ivoire", "Côte d'Ivoire"),
        "CZ" to listOf("Tschechien", "Tschechische Republik"),
        "CD" to listOf("DR Kongo", "Demokratische Republik Kongo"),
        "CG" to listOf("Kongo", "Republik Kongo"),
        "DO" to listOf("Dominikanische Republik"),
        "FM" to listOf("Mikronesien"),
        "KP" to listOf("Nordkorea"),
        "KR" to listOf("Südkorea"),
        "MD" to listOf("Moldau", "Republik Moldau"),
        "PS" to listOf("Palästina"),
        "RU" to listOf("Russland"),
        "SY" to listOf("Syrien"),
        "TZ" to listOf("Tansania"),
        "VA" to listOf("Vatikan", "Vatikanstadt"),
        "VE" to listOf("Venezuela"),
        "VN" to listOf("Vietnam"),
      ),
    AppLanguage.English to emptyMap(),
  )
