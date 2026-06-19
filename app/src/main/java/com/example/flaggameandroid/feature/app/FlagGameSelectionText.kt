package com.example.flaggameandroid.feature.app

import com.example.flaggameandroid.core.model.GameMode
import com.example.flaggameandroid.core.model.HintDifficulty
import com.example.flaggameandroid.core.model.MedalTier
import com.example.flaggameandroid.core.model.QuizVariant

internal fun modeBaseTitle(
  base: MultiplayerQuizBase,
  language: AppLanguage,
): String =
  when (base) {
    MultiplayerQuizBase.Continents ->
      when (language) {
        AppLanguage.English -> "Continents"
        AppLanguage.Bulgarian -> "Континенти"
        AppLanguage.German -> "Kontinente"
      }
    MultiplayerQuizBase.AllIn ->
      when (language) {
        AppLanguage.English -> "No Bluff, All Tough"
        AppLanguage.Bulgarian -> "Без блъф, само трудно"
        AppLanguage.German -> "Kein Bluff, alles hart"
      }
  }

internal fun modeBaseDescription(
  base: MultiplayerQuizBase,
  language: AppLanguage,
): String =
  when (base) {
    MultiplayerQuizBase.Continents ->
      when (language) {
        AppLanguage.English -> "Quiz by selected continents."
        AppLanguage.Bulgarian -> "Тест по избрани континенти."
        AppLanguage.German -> "Quiz nach ausgewählten Kontinenten."
      }
    MultiplayerQuizBase.AllIn ->
      when (language) {
        AppLanguage.English -> "Quiz from all countries."
        AppLanguage.Bulgarian -> "Тест от всички държави."
        AppLanguage.German -> "Quiz mit allen Ländern."
      }
  }

internal fun localizedModeTitle(
  mode: GameMode,
  language: AppLanguage,
): String =
  when (mode) {
    GameMode.Training ->
      when (language) {
        AppLanguage.English -> "Training"
        AppLanguage.Bulgarian -> "Тренировка"
        AppLanguage.German -> "Training"
      }
    GameMode.Continents ->
      when (language) {
        AppLanguage.English -> "Continents"
        AppLanguage.Bulgarian -> "Континенти"
        AppLanguage.German -> "Kontinente"
      }
    GameMode.AllIn ->
      when (language) {
        AppLanguage.English -> "No Bluff, All Tough"
        AppLanguage.Bulgarian -> "Без блъф, само трудно"
        AppLanguage.German -> "Kein Bluff, alles hart"
      }
    GameMode.LocalMultiplayer ->
      when (language) {
        AppLanguage.English -> "Local multiplayer"
        AppLanguage.Bulgarian -> "Локална игра"
        AppLanguage.German -> "Lokaler Mehrspieler"
      }
  }

internal fun localizedModeDescription(
  mode: GameMode,
  language: AppLanguage,
): String =
  when (mode) {
    GameMode.Training ->
      when (language) {
        AppLanguage.English -> "Mix flags, country names, and typed answers at your pace. Training does not give level-up progress."
        AppLanguage.Bulgarian -> "Смесвай флагове, имена на държави и писмени отговори със свое темпо. Тренировката не дава прогрес към ниво."
        AppLanguage.German -> "Mische Flaggen, Ländernamen und Texteingaben in deinem Tempo. Training bringt keinen Level-Fortschritt."
      }
    GameMode.Continents ->
      when (language) {
        AppLanguage.English -> "Build a quiz from the continents you want to practice."
        AppLanguage.Bulgarian -> "Създай тест от континентите, които искаш да упражняваш."
        AppLanguage.German -> "Erstelle ein Quiz aus den Kontinenten, die du üben möchtest."
      }
    GameMode.AllIn ->
      when (language) {
        AppLanguage.English -> "All countries with only the variants you choose."
        AppLanguage.Bulgarian -> "Всички държави само с вариантите, които избереш."
        AppLanguage.German -> "Alle Länder mit nur den Varianten, die du auswählst."
      }
    GameMode.LocalMultiplayer ->
      when (language) {
        AppLanguage.English -> "Up to 5 players pass one device and play turn by turn."
        AppLanguage.Bulgarian -> "До 5 играчи използват едно устройство и играят поред."
        AppLanguage.German -> "Bis zu 5 Spieler teilen sich ein Gerät und spielen reihum."
      }
  }

internal fun localizedModeShortLabel(
  mode: GameMode,
  language: AppLanguage,
): String =
  when (mode) {
    GameMode.Training ->
      when (language) {
        AppLanguage.English -> "Practice freely."
        AppLanguage.Bulgarian -> "Упражнявай свободно."
        AppLanguage.German -> "Frei üben."
      }
    GameMode.Continents ->
      when (language) {
        AppLanguage.English -> "Pick continents."
        AppLanguage.Bulgarian -> "Избери континенти."
        AppLanguage.German -> "Kontinente wählen."
      }
    GameMode.AllIn ->
      when (language) {
        AppLanguage.English -> "All countries."
        AppLanguage.Bulgarian -> "Всички държави."
        AppLanguage.German -> "Alle Länder."
      }
    GameMode.LocalMultiplayer ->
      when (language) {
        AppLanguage.English -> "Play together."
        AppLanguage.Bulgarian -> "Играй заедно."
        AppLanguage.German -> "Zusammen spielen."
      }
  }

internal fun localizedVariantTitle(
  variant: QuizVariant,
  language: AppLanguage,
): String =
  when (variant) {
    QuizVariant.FlagToCountry ->
      when (language) {
        AppLanguage.English -> "Flag -> country"
        AppLanguage.Bulgarian -> "Флаг -> държава"
        AppLanguage.German -> "Flagge -> Land"
      }
    QuizVariant.CountryToFlag ->
      when (language) {
        AppLanguage.English -> "Country -> flag"
        AppLanguage.Bulgarian -> "Държава -> флаг"
        AppLanguage.German -> "Land -> Flagge"
      }
    QuizVariant.TypeCountryName ->
      when (language) {
        AppLanguage.English -> "Type the country"
        AppLanguage.Bulgarian -> "Напиши държавата"
        AppLanguage.German -> "Land eintippen"
      }
  }

internal fun localizedVariantDescription(
  variant: QuizVariant,
  language: AppLanguage,
): String =
  when (variant) {
    QuizVariant.FlagToCountry ->
      when (language) {
        AppLanguage.English -> "See a flag and pick the country."
        AppLanguage.Bulgarian -> "Виж флаг и избери държавата."
        AppLanguage.German -> "Sieh eine Flagge und wähle das Land."
      }
    QuizVariant.CountryToFlag ->
      when (language) {
        AppLanguage.English -> "See a country and pick the flag."
        AppLanguage.Bulgarian -> "Виж държава и избери флага."
        AppLanguage.German -> "Sieh ein Land und wähle die Flagge."
      }
    QuizVariant.TypeCountryName ->
      when (language) {
        AppLanguage.English -> "See a flag and write the country name."
        AppLanguage.Bulgarian -> "Виж флаг и напиши името на държавата."
        AppLanguage.German -> "Sieh eine Flagge und tippe den Ländernamen."
      }
  }

internal fun localizedHintDifficultyTitle(
  difficulty: HintDifficulty,
  language: AppLanguage,
): String =
  when (difficulty) {
    HintDifficulty.Rookie ->
      when (language) {
        AppLanguage.English -> "Easy (Every 3-streak)"
        AppLanguage.Bulgarian -> "Лесно (Всеки 3 поредни ✔)"
        AppLanguage.German -> "Einsteiger (Alle 3 in Folge)"
      }
    HintDifficulty.Medium ->
      when (language) {
        AppLanguage.English -> "Medium (Every 5-streak)"
        AppLanguage.Bulgarian -> "Средно (5 поредни ✔)"
        AppLanguage.German -> "Mittel (Alle 5 in Folge)"
      }
    HintDifficulty.Hard ->
      when (language) {
        AppLanguage.English -> "Hard (Every 10-streak)"
        AppLanguage.Bulgarian -> "Трудно (10 поредни ✔)"
        AppLanguage.German -> "Schwer (Alle 10 in Folge)"
      }
    HintDifficulty.Impossible ->
      when (language) {
        AppLanguage.English -> "Impossible (Every 50-streak)"
        AppLanguage.Bulgarian -> "Невъзможно (50 поредни ✔)"
        AppLanguage.German -> "Unmöglich (Alle 50 in Folge)"
      }
  }

internal fun localizedHintDifficultyShortRule(
  difficulty: HintDifficulty,
  language: AppLanguage,
): String =
  when (difficulty) {
    HintDifficulty.Rookie ->
      when (language) {
        AppLanguage.English -> "Every 3-streak"
        AppLanguage.Bulgarian -> "Всеки 3 поредни верни"
        AppLanguage.German -> "Alle 3 in Folge"
      }
    HintDifficulty.Medium ->
      when (language) {
        AppLanguage.English -> "Every 5-streak"
        AppLanguage.Bulgarian -> "Всеки 5 верни поред"
        AppLanguage.German -> "Alle 5 in Folge"
      }
    HintDifficulty.Hard ->
      when (language) {
        AppLanguage.English -> "Every 10-streak"
        AppLanguage.Bulgarian -> "Всеки 10 верни поред"
        AppLanguage.German -> "Alle 10 in Folge"
      }
    HintDifficulty.Impossible ->
      when (language) {
        AppLanguage.English -> "Every 50-streak"
        AppLanguage.Bulgarian -> "Всеки 50 верни поред"
        AppLanguage.German -> "Alle 50 in Folge"
      }
  }

internal fun localizedHintDifficultyDescription(
  difficulty: HintDifficulty,
  language: AppLanguage,
): String =
  when (difficulty) {
    HintDifficulty.Rookie ->
      when (language) {
        AppLanguage.English -> "Collect 1 hint for every 3 correct answers in a row."
        AppLanguage.Bulgarian -> "Събирай 1 жокер за всеки 3 верни отговора подред."
        AppLanguage.German -> "Sammle 1 Hinweis für je 3 richtige Antworten in Folge."
      }
    HintDifficulty.Medium ->
      when (language) {
        AppLanguage.English -> "Collect 1 hint for every 5 correct answers in a row."
        AppLanguage.Bulgarian -> "Събирай 1 жокер на всеки 5 верни отговора поред."
        AppLanguage.German -> "Sammle 1 Hinweis für jeweils 5 richtige Antworten in Folge."
      }
    HintDifficulty.Hard ->
      when (language) {
        AppLanguage.English -> "Collect 1 hint for every 10 correct answers in a row."
        AppLanguage.Bulgarian -> "Събирай 1 жокер на всеки 10 верни отговора поред."
        AppLanguage.German -> "Sammle 1 Hinweis für jeweils 10 richtige Antworten in Folge."
      }
    HintDifficulty.Impossible ->
      when (language) {
        AppLanguage.English -> "Collect 1 hint for every 50 correct answers in a row."
        AppLanguage.Bulgarian -> "Събирай 1 жокер на всеки 50 верни отговора поред."
        AppLanguage.German -> "Sammle 1 Hinweis für jeweils 50 richtige Antworten in Folge."
      }
  }

internal fun localizedMedalTitle(
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

internal fun localizedMedalLabel(language: AppLanguage): String =
  when (language) {
    AppLanguage.English -> "Perfect quiz count"
    AppLanguage.Bulgarian -> "Брояч за перфектни тестове"
    AppLanguage.German -> "Zähler für fehlerfreie Quizze"
  }

internal fun localizedMedalIntro(language: AppLanguage): String =
  when (language) {
    AppLanguage.English -> "Perfect quiz counters"
    AppLanguage.Bulgarian -> "Броячи за перфектни тестове"
    AppLanguage.German -> "Zähler für fehlerfreie Quizze"
  }

internal fun cleanModeSelectionTitle(language: AppLanguage): String =
  when (language) {
    AppLanguage.English -> "Choose mode"
    AppLanguage.Bulgarian -> "Избери режим"
    AppLanguage.German -> "Modus wählen"
  }

internal fun cleanHeroPill(
  index: Int,
  language: AppLanguage,
): String =
  when (index) {
    0 ->
      when (language) {
        AppLanguage.English -> "Global"
        AppLanguage.Bulgarian -> "Глобално"
        AppLanguage.German -> "Global"
      }
    1 ->
      when (language) {
        AppLanguage.English -> "Continents"
        AppLanguage.Bulgarian -> "Континенти"
        AppLanguage.German -> "Kontinente"
      }
    else ->
      when (language) {
        AppLanguage.English -> "Flags"
        AppLanguage.Bulgarian -> "Флагове"
        AppLanguage.German -> "Flaggen"
      }
  }

internal fun cleanModeTitle(
  mode: GameMode,
  language: AppLanguage,
): String =
  when (mode) {
    GameMode.Training ->
      when (language) {
        AppLanguage.English -> "Training"
        AppLanguage.Bulgarian -> "Тренировка"
        AppLanguage.German -> "Training"
      }
    GameMode.Continents ->
      when (language) {
        AppLanguage.English -> "Continents"
        AppLanguage.Bulgarian -> "Континенти"
        AppLanguage.German -> "Kontinente"
      }
    GameMode.AllIn ->
      when (language) {
        AppLanguage.English -> "No Bluff, All Tough"
        AppLanguage.Bulgarian -> "Без блъф, само трудно"
        AppLanguage.German -> "Kein Bluff, alles hart"
      }
    GameMode.LocalMultiplayer ->
      when (language) {
        AppLanguage.English -> "Local multiplayer"
        AppLanguage.Bulgarian -> "Локална игра"
        AppLanguage.German -> "Lokaler Mehrspieler"
      }
  }

internal fun cleanModeShortLabel(
  mode: GameMode,
  language: AppLanguage,
): String =
  when (mode) {
    GameMode.Training ->
      when (language) {
        AppLanguage.English -> "Practice freely."
        AppLanguage.Bulgarian -> "Упражнявай свободно."
        AppLanguage.German -> "Frei üben."
      }
    GameMode.Continents ->
      when (language) {
        AppLanguage.English -> "Pick continents."
        AppLanguage.Bulgarian -> "Избери континенти."
        AppLanguage.German -> "Kontinente wählen."
      }
    GameMode.AllIn ->
      when (language) {
        AppLanguage.English -> "All countries."
        AppLanguage.Bulgarian -> "Всички държави."
        AppLanguage.German -> "Alle Länder."
      }
    GameMode.LocalMultiplayer ->
      when (language) {
        AppLanguage.English -> "Play together."
        AppLanguage.Bulgarian -> "Играй заедно."
        AppLanguage.German -> "Zusammen spielen."
      }
  }

internal fun cleanModeDescription(
  mode: GameMode,
  language: AppLanguage,
): String =
  when (mode) {
    GameMode.Training ->
      when (language) {
        AppLanguage.English -> "Mix flags, country names, and typed answers at your pace. Training does not give level-up progress."
        AppLanguage.Bulgarian -> "Смесвай флагове, имена на държави и писмени отговори със свое темпо. Тренировката не дава прогрес към ниво."
        AppLanguage.German -> "Mische Flaggen, Ländernamen und Texteingaben in deinem Tempo. Training bringt keinen Level-Fortschritt."
      }
    GameMode.Continents ->
      when (language) {
        AppLanguage.English -> "Build a quiz from the continents you want to practice."
        AppLanguage.Bulgarian -> "Създай тест от континентите, които искаш да упражняваш."
        AppLanguage.German -> "Erstelle ein Quiz aus den Kontinenten, die du üben möchtest."
      }
    GameMode.AllIn ->
      when (language) {
        AppLanguage.English -> "All countries with only the variants you choose."
        AppLanguage.Bulgarian -> "Всички държави само с вариантите, които избереш."
        AppLanguage.German -> "Alle Länder mit nur den Varianten, die du auswählst."
      }
    GameMode.LocalMultiplayer ->
      when (language) {
        AppLanguage.English -> "Up to 5 players pass one device and play turn by turn."
        AppLanguage.Bulgarian -> "До 5 играчи използват едно устройство и играят поред."
        AppLanguage.German -> "Bis zu 5 Spieler teilen sich ein Gerät und spielen reihum."
      }
  }

