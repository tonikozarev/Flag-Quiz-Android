package com.example.flaggameandroid.feature.app

import com.example.flaggameandroid.core.model.MedalTier

internal fun cleanMedalInfo(language: AppLanguage): String =
  when (language) {
    AppLanguage.English ->
      "Bronze: finish a 10-24 question quiz with 0 mistakes.\n" +
        "Silver: finish a 25-49 question quiz with 0 mistakes.\n" +
        "Gold: finish a 50-99 question quiz with 0 mistakes.\n" +
        "Platinum: finish a 100-194 question quiz with 0 mistakes.\n" +
        "Diamond: finish all 195 countries with 0 mistakes.\n" +
        "Hints are allowed for medals."
    AppLanguage.Bulgarian ->
      "Бронз: завърши тест с 10-24 въпроса без грешка.\n" +
        "Сребро: завърши тест с 25-49 въпроса без грешка.\n" +
        "Злато: завърши тест с 50-99 въпроса без грешка.\n" +
        "Платина: завърши тест със 100-194 въпроса без грешка.\n" +
        "Диамант: завърши всички 195 държави без грешка.\n" +
        "Жокерите са позволени за медали."
    AppLanguage.German ->
      "Bronze: Beende ein Quiz mit 10-24 Fragen ohne Fehler.\n" +
        "Silber: Beende ein Quiz mit 25-49 Fragen ohne Fehler.\n" +
        "Gold: Beende ein Quiz mit 50-99 Fragen ohne Fehler.\n" +
        "Platin: Beende ein Quiz mit 100-194 Fragen ohne Fehler.\n" +
        "Diamant: Beende alle 195 Länder ohne Fehler.\n" +
        "Hinweise sind für Medaillen erlaubt."
  }

internal fun cleanMedalTitle(
  medalTier: MedalTier,
  language: AppLanguage,
): String =
  when (medalTier) {
    MedalTier.Bronze ->
      when (language) {
        AppLanguage.English -> "Bronze"
        AppLanguage.Bulgarian -> "Бронз"
        AppLanguage.German -> "Bronze"
      }
    MedalTier.Silver ->
      when (language) {
        AppLanguage.English -> "Silver"
        AppLanguage.Bulgarian -> "Сребро"
        AppLanguage.German -> "Silber"
      }
    MedalTier.Gold ->
      when (language) {
        AppLanguage.English -> "Gold"
        AppLanguage.Bulgarian -> "Злато"
        AppLanguage.German -> "Gold"
      }
    MedalTier.Titanium ->
      when (language) {
        AppLanguage.English -> "Platinum"
        AppLanguage.Bulgarian -> "Платина"
        AppLanguage.German -> "Platin"
      }
    MedalTier.Diamond ->
      when (language) {
        AppLanguage.English -> "Diamond"
        AppLanguage.Bulgarian -> "Диамант"
        AppLanguage.German -> "Diamant"
      }
  }
