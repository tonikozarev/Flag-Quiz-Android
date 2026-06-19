package com.example.flaggameandroid.feature.app

internal fun t(
  language: AppLanguage,
  text: UiText,
): String =
  when (text) {
    UiText.ChooseMode ->
      when (language) {
        AppLanguage.English -> "Choose mode"
        AppLanguage.Bulgarian -> "Избери режим"
        AppLanguage.German -> "Modus wählen"
      }
    UiText.WorldFlagGame -> cleanText(language, UiText.WorldFlagGame)
    UiText.HeroSubtitle -> cleanText(language, UiText.HeroSubtitle)
    UiText.Menu ->
      when (language) {
        AppLanguage.English -> "Menu"
        AppLanguage.Bulgarian -> "Меню"
        AppLanguage.German -> "Menu"
      }
    UiText.Start -> cleanText(language, UiText.Start)
    UiText.Medals -> cleanText(language, UiText.Medals)
    UiText.Achievements -> cleanText(language, UiText.Achievements)
    UiText.Settings -> cleanText(language, UiText.Settings)
    UiText.Quit -> cleanText(language, UiText.Quit)
    UiText.Profile ->
      when (language) {
        AppLanguage.English -> "Profile"
        AppLanguage.Bulgarian -> "Профил"
        AppLanguage.German -> "Profil"
      }
    UiText.AccountName ->
      when (language) {
        AppLanguage.English -> "Account name"
        AppLanguage.Bulgarian -> "Име на акаунта"
        AppLanguage.German -> "Profilname"
      }
    UiText.ProfileIcon ->
      when (language) {
        AppLanguage.English -> "Profile icon"
        AppLanguage.Bulgarian -> "Икона на профила"
        AppLanguage.German -> "Profilsymbol"
      }
    UiText.ChangeIcon ->
      when (language) {
        AppLanguage.English -> "Change icon"
        AppLanguage.Bulgarian -> "Смени иконата"
        AppLanguage.German -> "Symbol ändern"
      }
    UiText.ChooseProfileIcon ->
      when (language) {
        AppLanguage.English -> "Choose profile icon"
        AppLanguage.Bulgarian -> "Избери икона за профила"
        AppLanguage.German -> "Profilsymbol wählen"
      }
    UiText.Save ->
      when (language) {
        AppLanguage.English -> "Save"
        AppLanguage.Bulgarian -> "Запази"
        AppLanguage.German -> "Speichern"
      }
    UiText.Cancel ->
      when (language) {
        AppLanguage.English -> "Cancel"
        AppLanguage.Bulgarian -> "Отказ"
        AppLanguage.German -> "Abbrechen"
      }
    UiText.Close ->
      when (language) {
        AppLanguage.English -> "Close"
        AppLanguage.Bulgarian -> "Затвори"
        AppLanguage.German -> "Schließen"
      }
    UiText.Language,
    UiText.LanguageLabel ->
      when (language) {
        AppLanguage.English -> "Language"
        AppLanguage.Bulgarian -> "Език"
        AppLanguage.German -> "Sprache"
      }
    UiText.AppLanguage ->
      when (language) {
        AppLanguage.English -> "App language"
        AppLanguage.Bulgarian -> "Език на приложението"
        AppLanguage.German -> "App-Sprache"
      }
    UiText.Hints ->
      when (language) {
        AppLanguage.English -> "Hints"
        AppLanguage.Bulgarian -> "Жокери"
        AppLanguage.German -> "Hinweise"
      }
    UiText.CollectedHints ->
      when (language) {
        AppLanguage.English -> "Collected hints"
        AppLanguage.Bulgarian -> "Събрани жокери"
        AppLanguage.German -> "Gesammelte Hinweise"
      }
    UiText.AddTenHints ->
      when (language) {
        AppLanguage.English -> "Add 10 hints"
        AppLanguage.Bulgarian -> "Добави 10 жокера"
        AppLanguage.German -> "10 Hinweise hinzufügen"
      }
    UiText.ResetHints ->
      when (language) {
        AppLanguage.English -> "Reset hints"
        AppLanguage.Bulgarian -> "Нулирай жокерите"
        AppLanguage.German -> "Hinweise zurücksetzen"
      }
    UiText.SwitchToNormalIcon ->
      when (language) {
        AppLanguage.English -> "Switch to normal icon"
        AppLanguage.Bulgarian -> "Върни нормалната икона"
        AppLanguage.German -> "Zum normalen Symbol wechseln"
      }
    UiText.SwitchToInactiveIcon ->
      when (language) {
        AppLanguage.English -> "Switch to inactive icon"
        AppLanguage.Bulgarian -> "Върни неактивната икона"
        AppLanguage.German -> "Zum inaktiven Symbol wechseln"
      }
    UiText.SendTestReminder ->
      when (language) {
        AppLanguage.English -> "Send test reminder"
        AppLanguage.Bulgarian -> "Изпрати тестово напомняне"
        AppLanguage.German -> "Test-Erinnerung senden"
      }
    UiText.ResetAchievementsAndMedals ->
      when (language) {
        AppLanguage.English -> "Reset medals"
        AppLanguage.Bulgarian -> "Нулирай медалите"
        AppLanguage.German -> "Medaillen zurücksetzen"
      }
    UiText.IconStatusInactive ->
      when (language) {
        AppLanguage.English -> "Icon status: inactive test icon enabled"
        AppLanguage.Bulgarian -> "Състояние на иконата: включена е тестовата икона"
        AppLanguage.German -> "Symbolstatus: inaktives Testsymbol aktiviert"
      }
    UiText.IconStatusNormal ->
      when (language) {
        AppLanguage.English -> "Icon status: normal icon enabled"
        AppLanguage.Bulgarian -> "Състояние на иконата: включена е нормалната икона"
        AppLanguage.German -> "Symbolstatus: normales Symbol aktiviert"
      }
    UiText.Player ->
      when (language) {
        AppLanguage.English -> "Player"
        AppLanguage.Bulgarian -> "Играч"
        AppLanguage.German -> "Spieler"
      }
    UiText.Remove ->
      when (language) {
        AppLanguage.English -> "Remove"
        AppLanguage.Bulgarian -> "Премахни"
        AppLanguage.German -> "Entfernen"
      }
    UiText.AddPlayer ->
      when (language) {
        AppLanguage.English -> "Add player"
        AppLanguage.Bulgarian -> "Добави играч"
        AppLanguage.German -> "Spieler hinzufügen"
      }
    UiText.QuizBase ->
      when (language) {
        AppLanguage.English -> "Quiz base"
        AppLanguage.Bulgarian -> "Основа на теста"
        AppLanguage.German -> "Quiz-Basis"
      }
    UiText.Continents ->
      when (language) {
        AppLanguage.English -> "Continents"
        AppLanguage.Bulgarian -> "Континенти"
        AppLanguage.German -> "Kontinente"
      }
    UiText.QuestionCount,
    UiText.AmountOfQuestions ->
      when (language) {
        AppLanguage.English -> "Amount of questions"
        AppLanguage.Bulgarian -> "Брой въпроси"
        AppLanguage.German -> "Anzahl der Fragen"
      }
    UiText.ExampleQuestionCount ->
      when (language) {
        AppLanguage.English -> "Example: 10"
        AppLanguage.Bulgarian -> "Пример: 10"
        AppLanguage.German -> "Beispiel: 10"
      }
    UiText.AllowedRange ->
      when (language) {
        AppLanguage.English -> "Allowed range"
        AppLanguage.Bulgarian -> "Позволен диапазон"
        AppLanguage.German -> "Erlaubter Bereich"
      }
    UiText.PerfectRunNoMedal ->
      when (language) {
        AppLanguage.English -> "Quizzes under 10 questions do not earn medals."
        AppLanguage.Bulgarian -> "Тестовете под 10 въпроса не печелят медали."
        AppLanguage.German -> "Quizze unter 10 Fragen bringen keine Medaillen."
      }
    UiText.UseCustomAmount ->
      when (language) {
        AppLanguage.English -> "Use custom amount"
        AppLanguage.Bulgarian -> "Ползвай собствен брой"
        AppLanguage.German -> "Eigene Anzahl verwenden"
      }
    UiText.SurpriseMe ->
      when (language) {
        AppLanguage.English -> "Surprise me!"
        AppLanguage.Bulgarian -> "Изненадай ме!"
        AppLanguage.German -> "Überrasch mich!"
      }
    UiText.StartQuiz ->
      when (language) {
        AppLanguage.English -> "Start quiz"
        AppLanguage.Bulgarian -> "Стартирай теста"
        AppLanguage.German -> "Quiz starten"
      }
    UiText.LeaveQuizTitle ->
      when (language) {
        AppLanguage.English -> "Leave quiz?"
        AppLanguage.Bulgarian -> "Да напуснеш ли теста?"
        AppLanguage.German -> "Quiz verlassen?"
      }
    UiText.LeaveQuizBody ->
      when (language) {
        AppLanguage.English -> "This quiz will not count toward results, newly earned hints, medals, achievements, or level progression."
        AppLanguage.Bulgarian -> "Този тест няма да се брои за резултатите, новите жокери, медали, постиженията или напредъка към ниво."
        AppLanguage.German -> "Dieses Quiz zählt nicht für Ergebnisse, neue Hinweise, Medaillen, Erfolge oder Level-Fortschritt."
      }
    UiText.ExitAppTitle ->
      when (language) {
        AppLanguage.English -> "Exit app?"
        AppLanguage.Bulgarian -> "Да излезеш ли от приложението?"
        AppLanguage.German -> "App schließen?"
      }
    UiText.ExitAppBody ->
      when (language) {
        AppLanguage.English -> "Do you really want to close World Flag Game?"
        AppLanguage.Bulgarian -> "Наистина ли искаш да затвориш World Flag Game?"
        AppLanguage.German -> "Möchtest du World Flag Game wirklich schließen?"
      }
    UiText.Exit ->
      when (language) {
        AppLanguage.English -> "Exit"
        AppLanguage.Bulgarian -> "Изход"
        AppLanguage.German -> "Schließen"
      }
    UiText.Leave ->
      when (language) {
        AppLanguage.English -> "Leave"
        AppLanguage.Bulgarian -> "Напусни"
        AppLanguage.German -> "Verlassen"
      }
    UiText.Stay ->
      when (language) {
        AppLanguage.English -> "Stay"
        AppLanguage.Bulgarian -> "Остани"
        AppLanguage.German -> "Bleiben"
      }
    UiText.QuizInfo ->
      when (language) {
        AppLanguage.English -> "Score is revealed at the end. Newly earned hints and level progress count only after finishing the full quiz."
        AppLanguage.Bulgarian -> "Резултатът се показва накрая. Ново спечелените жокери и напредъкът към ниво се броят само след завършване на целия тест."
        AppLanguage.German -> "Der Punktestand wird am Ende gezeigt. Neue Hinweise und Fortschritt zählen erst nach dem vollständigen Quiz."
      }
    UiText.GuessTheFlag,
    UiText.QuestionPromptFlag ->
      when (language) {
        AppLanguage.English -> "Guess the flag"
        AppLanguage.Bulgarian -> "Познай флага"
        AppLanguage.German -> "Errate die Flagge"
      }
    UiText.CountryName,
    UiText.QuestionPromptCountry ->
      when (language) {
        AppLanguage.English -> "Country name"
        AppLanguage.Bulgarian -> "Име на държавата"
        AppLanguage.German -> "Ländername"
      }
    UiText.HintStartsWith ->
      when (language) {
        AppLanguage.English -> "Starts with"
        AppLanguage.Bulgarian -> "Започва с"
        AppLanguage.German -> "Beginnt mit"
      }
    UiText.Hint ->
      when (language) {
        AppLanguage.English -> "Hint"
        AppLanguage.Bulgarian -> "Жокер"
        AppLanguage.German -> "Hinweis"
      }
    UiText.Skip,
    UiText.Unskip ->
      when (language) {
        AppLanguage.English -> "\u21B7"
        AppLanguage.Bulgarian -> "\u21B7"
        AppLanguage.German -> "\u21B7"
      }
    UiText.Finish ->
      when (language) {
        AppLanguage.English -> "Finish"
        AppLanguage.Bulgarian -> "Приключи"
        AppLanguage.German -> "Fertig"
      }
    UiText.Next ->
      when (language) {
        AppLanguage.English -> "Next"
        AppLanguage.Bulgarian -> "Напред"
        AppLanguage.German -> "Weiter"
      }
    UiText.PlayAgain ->
      when (language) {
        AppLanguage.English -> "Play again"
        AppLanguage.Bulgarian -> "Играй пак"
        AppLanguage.German -> "Nochmal spielen"
      }
    UiText.QuizComplete ->
      when (language) {
        AppLanguage.English -> "Quiz complete"
        AppLanguage.Bulgarian -> "Тестът е завършен"
        AppLanguage.German -> "Quiz beendet"
      }
    UiText.FinalResults ->
      when (language) {
        AppLanguage.English -> "Final results"
        AppLanguage.Bulgarian -> "Крайни резултати"
        AppLanguage.German -> "Endergebnisse"
      }
    UiText.AnswerReview ->
      when (language) {
        AppLanguage.English -> "Answer review"
        AppLanguage.Bulgarian -> "Преглед на отговорите"
        AppLanguage.German -> "Antwortübersicht"
      }
    UiText.CorrectAnswers -> cleanText(language, UiText.CorrectAnswers)
    UiText.Skipped ->
      when (language) {
        AppLanguage.English -> "Skipped"
        AppLanguage.Bulgarian -> "Прескочени"
        AppLanguage.German -> "Übersprungen"
      }
    UiText.Unanswered ->
      when (language) {
        AppLanguage.English -> "Unanswered"
        AppLanguage.Bulgarian -> "Неотговорени"
        AppLanguage.German -> "Unbeantwortet"
      }
    UiText.Unsure ->
      when (language) {
        AppLanguage.English -> "Unsure"
        AppLanguage.Bulgarian -> "Несигурни"
        AppLanguage.German -> "Unsicher"
      }
    UiText.NetScore ->
      when (language) {
        AppLanguage.English -> "Score"
        AppLanguage.Bulgarian -> "Резултат"
        AppLanguage.German -> "Punktestand"
      }
    UiText.HintPointsAvailable ->
      when (language) {
        AppLanguage.English -> "Won hint points"
        AppLanguage.Bulgarian -> "Спечелени точки за жокери"
        AppLanguage.German -> "Gewonnene Hinweise"
      }
    UiText.QuestionReview ->
      when (language) {
        AppLanguage.English -> "Question"
        AppLanguage.Bulgarian -> "Въпрос"
        AppLanguage.German -> "Frage"
      }
    UiText.Correct ->
      when (language) {
        AppLanguage.English -> "Correct"
        AppLanguage.Bulgarian -> "Верен"
        AppLanguage.German -> "Richtig"
      }
    UiText.YourAnswer ->
      when (language) {
        AppLanguage.English -> "Your answer"
        AppLanguage.Bulgarian -> "Твой отговор"
        AppLanguage.German -> "Deine Antwort"
      }
    UiText.NoAnswer ->
      when (language) {
        AppLanguage.English -> "No answer"
        AppLanguage.Bulgarian -> "Няма отговор"
        AppLanguage.German -> "Keine Antwort"
      }
    UiText.HintUsed ->
      when (language) {
        AppLanguage.English -> "Hint streak is paused and stays X."
        AppLanguage.Bulgarian -> "Веригата за жокери е на пауза и остава X."
        AppLanguage.German -> "Die Hinweis-Serie pausiert und bleibt X."
      }
    UiText.NoHintUsed ->
      when (language) {
        AppLanguage.English -> "No hint used - Streak is now X."
        AppLanguage.Bulgarian -> "Няма използван жокер - поредицата е X."
        AppLanguage.German -> "Kein Hinweis verwendet - die Hinweis-Serie ist jetzt X."
      }
    UiText.CompletedTests -> cleanText(language, UiText.CompletedTests)
    UiText.Level -> cleanText(language, UiText.Level)
    UiText.NextUp ->
      when (language) {
        AppLanguage.English -> "Next up"
        AppLanguage.Bulgarian -> "Следващ"
        AppLanguage.German -> "Als Nächstes"
      }
    UiText.NextLevelRequirements ->
      when (language) {
        AppLanguage.English -> "Next level requirements"
        AppLanguage.Bulgarian -> "Изисквания за следващо ниво"
        AppLanguage.German -> "Anforderungen für das nächste Level"
      }
    UiText.Open -> cleanText(language, UiText.Open)
    UiText.MedalsCountLabel ->
      when (language) {
        AppLanguage.English -> "Medals"
        AppLanguage.Bulgarian -> "Медали"
        AppLanguage.German -> "Medaillen"
      }
    UiText.PerfectQuizCount ->
      when (language) {
        AppLanguage.English -> "Perfect quiz count"
        AppLanguage.Bulgarian -> "Брой перфектни тестове"
        AppLanguage.German -> "Anzahl fehlerfreier Quizze"
      }
    UiText.LevelUpTitle ->
      when (language) {
        AppLanguage.English -> "Level up!"
        AppLanguage.Bulgarian -> "Ниво нагоре!"
        AppLanguage.German -> "Levelaufstieg!"
      }
    UiText.LevelUpBody ->
      when (language) {
        AppLanguage.English -> "You reached level %1\$d and earned 5 free hints."
        AppLanguage.Bulgarian -> "Достигна ниво %1\$d и получи 5 безплатни жокера."
        AppLanguage.German -> "Du hast Level %1\$d erreicht und 5 kostenlose Hinweise erhalten."
      }
  }
