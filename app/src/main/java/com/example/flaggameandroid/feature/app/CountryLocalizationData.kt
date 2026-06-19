package com.example.flaggameandroid.feature.app

internal val localizedContinentNames: Map<AppLanguage, Map<String, String>> =
  mapOf(
    AppLanguage.English to
      mapOf(
        "Africa" to "Africa",
        "Antarctica" to "Antarctica",
        "Asia" to "Asia",
        "Europe" to "Europe",
        "North America" to "North America",
        "Oceania" to "Oceania",
        "South America" to "South America",
      ),
    AppLanguage.Bulgarian to
      mapOf(
        "Africa" to "Африка",
        "Antarctica" to "Антарктида",
        "Asia" to "Азия",
        "Europe" to "Европа",
        "North America" to "Северна Америка",
        "Oceania" to "Океания",
        "South America" to "Южна Америка",
      ),
    AppLanguage.German to
      mapOf(
        "Africa" to "Afrika",
        "Antarctica" to "Antarktis",
        "Asia" to "Asien",
        "Europe" to "Europa",
        "North America" to "Nordamerika",
        "Oceania" to "Ozeanien",
        "South America" to "Südamerika",
      ),
  )

internal val localizedCountryAliases: Map<AppLanguage, Map<String, List<String>>> =
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
