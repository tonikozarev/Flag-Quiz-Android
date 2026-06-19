package com.example.flaggameandroid.feature.app

internal fun localizedAvatarUnlockInfo(language: AppLanguage): String =
  when (language) {
    AppLanguage.English ->
      "You unlock 5 more profile icons every time you level up. At level 1 you start with 5 unlocked icons, at level 2 you have 10, and so on."
    AppLanguage.Bulgarian ->
      "При всяко качване на ниво отключваш по 5 нови профилни икони. На ниво 1 започваш с 5 отключени икони, на ниво 2 имаш 10 и така нататък."
    AppLanguage.German ->
      "Mit jedem Levelaufstieg schaltest du 5 weitere Profilsymbole frei. Auf Level 1 startest du mit 5 freigeschalteten Symbolen, auf Level 2 hast du 10 und so weiter."
  }

internal fun cleanMedalIntro(language: AppLanguage): String =
  when (language) {
    AppLanguage.English -> "Perfect quiz counters"
    AppLanguage.Bulgarian -> "Броячи за перфектни тестове"
    AppLanguage.German -> "Zähler für fehlerfreie Quizze"
  }
