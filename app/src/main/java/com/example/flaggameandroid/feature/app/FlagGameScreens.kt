package com.example.flaggameandroid.feature.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flaggameandroid.core.model.AllInType
import com.example.flaggameandroid.core.model.FlagCountry
import com.example.flaggameandroid.core.model.FlagQuestion
import com.example.flaggameandroid.core.model.GameMode
import com.example.flaggameandroid.core.model.PlayerProgress
import com.example.flaggameandroid.core.model.QuestionResult
import com.example.flaggameandroid.core.model.QuizVariant
import com.example.flaggameandroid.theme.AccentGreen
import com.example.flaggameandroid.theme.AccentRed

@Composable
fun FlagGameRoute(
  screenViewModel: FlagGameViewModel = viewModel { FlagGameViewModel() },
) {
  val uiState by screenViewModel.uiState.collectAsStateWithLifecycle()

  when (uiState.screen) {
    AppScreen.Menu ->
      MenuScreen(
        onModeSelected = screenViewModel::onModeSelected,
      )
    AppScreen.Setup ->
      SetupScreen(
        setup = uiState.setup,
        availableContinents = uiState.availableContinents,
        setupError = uiState.setupError,
        onBack = screenViewModel::onBackToMenu,
        onVariantToggle = screenViewModel::onVariantToggled,
        onContinentToggle = screenViewModel::onContinentToggled,
        onQuestionCountChange = screenViewModel::onQuestionCountChanged,
        onSurpriseMe = screenViewModel::onSurpriseMeClicked,
        onAllInTypeSelected = screenViewModel::onAllInTypeSelected,
        onMultiplayerBaseSelected = screenViewModel::onMultiplayerBaseSelected,
        onPlayerNameChanged = screenViewModel::onPlayerNameChanged,
        onAddPlayer = screenViewModel::onAddPlayer,
        onRemovePlayer = screenViewModel::onRemovePlayer,
        onStartQuiz = screenViewModel::onStartQuiz,
      )
    AppScreen.Quiz ->
      QuizScreen(
        quiz = uiState.quiz,
        onBackToMenu = screenViewModel::onBackToMenu,
        onCountryAnswerSelected = screenViewModel::onCountryAnswerSelected,
        onTypedAnswerChanged = screenViewModel::onTypedAnswerChanged,
        onUseHint = screenViewModel::onUseHint,
        onNextQuestion = screenViewModel::onNextQuestion,
      )
    AppScreen.Results ->
      ResultsScreen(
        quiz = uiState.quiz,
        onPlayAgain = screenViewModel::onPlayAgain,
        onBackToMenu = screenViewModel::onBackToMenu,
      )
  }
}

@Composable
fun MenuScreen(
  onModeSelected: (GameMode) -> Unit,
  modifier: Modifier = Modifier,
) {
  ScreenShell(modifier = modifier) {
    HeroPanel(
      title = "Flag Game Android",
      subtitle = "Four modes, full-world questions, hints, streaks, and local multiplayer.",
    )

    GameMode.entries.forEach { mode ->
      ModeCard(mode = mode, onClick = { onModeSelected(mode) })
    }
  }
}

@Composable
fun SetupScreen(
  setup: SetupState,
  availableContinents: List<String>,
  setupError: String?,
  onBack: () -> Unit,
  onVariantToggle: (QuizVariant) -> Unit,
  onContinentToggle: (String) -> Unit,
  onQuestionCountChange: (String) -> Unit,
  onSurpriseMe: () -> Unit,
  onAllInTypeSelected: (AllInType) -> Unit,
  onMultiplayerBaseSelected: (MultiplayerQuizBase) -> Unit,
  onPlayerNameChanged: (Int, String) -> Unit,
  onAddPlayer: () -> Unit,
  onRemovePlayer: () -> Unit,
  onStartQuiz: () -> Unit,
  modifier: Modifier = Modifier,
) {
  ScreenShell(modifier = modifier) {
    HeaderRow(title = setup.mode.title, onBack = onBack)

    if (setup.mode == GameMode.LocalMultiplayer) {
      SectionCard(title = "Players") {
        setup.playerNames.forEachIndexed { index, name ->
          OutlinedTextField(
            value = name,
            onValueChange = { onPlayerNameChanged(index, it) },
            label = { Text("Player ${index + 1}") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
          )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
          OutlinedButton(onClick = onRemovePlayer, modifier = Modifier.weight(1f)) {
            Text("Remove")
          }
          Button(onClick = onAddPlayer, modifier = Modifier.weight(1f)) {
            Text("Add player")
          }
        }
      }

      SectionCard(title = "Quiz base") {
        MultiplayerQuizBase.entries.forEach { base ->
          SelectableRow(
            title = base.title,
            selected = setup.multiplayerBase == base,
            onClick = { onMultiplayerBaseSelected(base) },
          )
        }
      }
    }

    if (setup.mode == GameMode.AllIn || setup.multiplayerBase == MultiplayerQuizBase.AllIn && setup.mode == GameMode.LocalMultiplayer) {
      SectionCard(title = "All-In type") {
        AllInType.entries.forEach { type ->
          SelectableRow(
            title = type.title,
            description = type.description,
            selected = setup.allInType == type,
            onClick = { onAllInTypeSelected(type) },
          )
        }
      }
    }

    if (setup.mode == GameMode.Continents || setup.multiplayerBase == MultiplayerQuizBase.Continents && setup.mode == GameMode.LocalMultiplayer) {
      SectionCard(title = "Continents") {
        FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
          availableContinents.forEach { continent ->
            val isSelectable = continent != "Antarctica"
            FilterChip(
              selected = continent in setup.selectedContinents,
              onClick = { if (isSelectable) onContinentToggle(continent) },
              enabled = isSelectable,
              label = {
                Text(
                  text = continent,
                  textDecoration = if (isSelectable) TextDecoration.None else TextDecoration.LineThrough,
                )
              },
            )
          }
        }
      }
    }

    if (setup.mode != GameMode.AllIn && !(setup.mode == GameMode.LocalMultiplayer && setup.multiplayerBase == MultiplayerQuizBase.AllIn)) {
      SectionCard(title = "Question count") {
        OutlinedTextField(
          value = setup.questionCountInput,
          onValueChange = onQuestionCountChange,
          label = { Text("Amount of questions") },
          placeholder = { Text(if (setup.surpriseMe) "Surprise me selected" else "Example: 20") },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          singleLine = true,
          enabled = !setup.surpriseMe,
          modifier = Modifier.fillMaxWidth(),
        )
        OutlinedButton(onClick = onSurpriseMe, modifier = Modifier.fillMaxWidth()) {
          Text(if (setup.surpriseMe) "Use custom amount" else "Surprise me!")
        }
      }
    }

    if (!(setup.mode == GameMode.AllIn && setup.allInType == AllInType.Hardcore) &&
      !(setup.mode == GameMode.LocalMultiplayer && setup.multiplayerBase == MultiplayerQuizBase.AllIn && setup.allInType == AllInType.Hardcore)
    ) {
      SectionCard(title = "Question variants") {
        QuizVariant.entries.forEach { variant ->
          CheckRow(
            title = variant.title,
            description = variant.description,
            checked = variant in setup.variants,
            onClick = { onVariantToggle(variant) },
          )
        }
      }
    } else {
      SectionCard(title = "Question variants") {
        Text("Hardcore uses all three variants automatically.", style = MaterialTheme.typography.bodyMedium)
      }
    }

    if (setupError != null) {
      Text(text = setupError, color = AccentRed, style = MaterialTheme.typography.bodyMedium)
    }

    Button(onClick = onStartQuiz, modifier = Modifier.fillMaxWidth(), contentPadding = PaddingValues(18.dp)) {
      Text("Start quiz")
    }
  }
}

@Composable
fun QuizScreen(
  quiz: QuizState,
  onBackToMenu: () -> Unit,
  onCountryAnswerSelected: (FlagCountry) -> Unit,
  onTypedAnswerChanged: (String) -> Unit,
  onUseHint: () -> Unit,
  onNextQuestion: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val question = quiz.currentQuestion ?: return
  val canSubmit =
    when (question.variant) {
      QuizVariant.TypeCountryName -> quiz.typedAnswer.isNotBlank()
      QuizVariant.FlagToCountry,
      QuizVariant.CountryToFlag -> quiz.selectedCountry != null
    }

  ScreenShell(modifier = modifier) {
    HeaderRow(title = quiz.mode?.title ?: "Quiz", onBack = onBackToMenu)

    SectionCard(title = "Question ${quiz.currentQuestionIndex + 1} of ${quiz.totalQuestions}") {
      if (quiz.isMultiplayer) {
        Text("Next up: ${quiz.currentPlayer.name}", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
      }
      Text("Usable hints: ${quiz.currentPlayer.hintPoints}", style = MaterialTheme.typography.bodyMedium)
      Text("Score is revealed at the end.", style = MaterialTheme.typography.bodyMedium)
    }

    QuestionPrompt(question)

    if (question.variant == QuizVariant.TypeCountryName) {
      OutlinedTextField(
        value = quiz.typedAnswer,
        onValueChange = onTypedAnswerChanged,
        label = { Text("Country name") },
        singleLine = true,
        supportingText = {
          quiz.typedHintPrefix?.let { Text("Hint: starts with $it") }
        },
        modifier = Modifier.fillMaxWidth(),
      )
    } else {
      Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        question.options
          .filterNot { it.code in quiz.hiddenOptionCodes }
          .forEach { option ->
            AnswerButton(
              question = question,
              option = option,
              selectedCountry = quiz.selectedCountry,
              onCountryAnswerSelected = onCountryAnswerSelected,
            )
          }
      }
    }

    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
      OutlinedButton(
        onClick = onUseHint,
        enabled = quiz.currentPlayer.hintPoints >= 2 && !quiz.hintUsedOnCurrentQuestion,
        modifier = Modifier.weight(1f),
      ) {
        Text("Use hint")
      }
      Button(onClick = onNextQuestion, enabled = canSubmit, modifier = Modifier.weight(1f)) {
        Text(if (quiz.isLastQuestion) "Finish" else "Next")
      }
    }
  }
}

@Composable
fun ResultsScreen(
  quiz: QuizState,
  onPlayAgain: () -> Unit,
  onBackToMenu: () -> Unit,
  modifier: Modifier = Modifier,
) {
  ScreenShell(modifier = modifier) {
    HeaderRow(title = "Quiz complete", onBack = onBackToMenu)

    SectionCard(title = "Final results") {
      quiz.players.sortedByDescending { it.score }.forEach { player ->
        PlayerResultRow(player = player, totalQuestions = quiz.results.count { it.playerName == player.name })
      }
    }

    SectionCard(title = "Answer review") {
      quiz.results.forEachIndexed { index, result ->
        ResultRow(index = index + 1, result = result)
      }
    }

    Row(horizontalArrangement = Arrangement.spacedBy(12.dp), modifier = Modifier.fillMaxWidth()) {
      Button(onClick = onPlayAgain, modifier = Modifier.weight(1f)) {
        Text("Play again")
      }
      OutlinedButton(onClick = onBackToMenu, modifier = Modifier.weight(1f)) {
        Text("Menu")
      }
    }
  }
}

@Composable
private fun ScreenShell(
  modifier: Modifier = Modifier,
  content: @Composable ColumnScope.() -> Unit,
) {
  val backgroundGradient =
    Brush.verticalGradient(
      colors = listOf(MaterialTheme.colorScheme.background, MaterialTheme.colorScheme.surface),
    )

  Box(
    modifier =
      modifier
        .fillMaxSize()
        .background(backgroundGradient)
        .padding(20.dp),
  ) {
    Column(
      modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.spacedBy(16.dp),
      content = content,
    )
  }
}

@Composable
private fun HeroPanel(
  title: String,
  subtitle: String,
) {
  ElevatedCard(
    colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
    modifier = Modifier.fillMaxWidth(),
  ) {
    Column(modifier = Modifier.padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
      Text(text = title, style = MaterialTheme.typography.headlineLarge)
      Text(text = subtitle, style = MaterialTheme.typography.bodyLarge)
    }
  }
}

@Composable
private fun HeaderRow(
  title: String,
  onBack: () -> Unit,
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceBetween,
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(text = title, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.weight(1f))
    OutlinedButton(onClick = onBack) {
      Text("Back")
    }
  }
}

@Composable
private fun ModeCard(
  mode: GameMode,
  onClick: () -> Unit,
) {
  Card(modifier = Modifier.fillMaxWidth()) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
      Text(text = mode.title, style = MaterialTheme.typography.titleLarge)
      Text(text = mode.description, style = MaterialTheme.typography.bodyMedium)
      Button(onClick = onClick, modifier = Modifier.fillMaxWidth()) {
        Text("Start")
      }
    }
  }
}

@Composable
private fun SectionCard(
  title: String,
  content: @Composable ColumnScope.() -> Unit,
) {
  Card(modifier = Modifier.fillMaxWidth()) {
    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
      Text(text = title, style = MaterialTheme.typography.titleLarge)
      content()
    }
  }
}

@Composable
private fun SelectableRow(
  title: String,
  selected: Boolean,
  onClick: () -> Unit,
  description: String? = null,
) {
  val colors =
    if (selected) {
      CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    } else {
      CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    }
  Card(onClick = onClick, colors = colors, modifier = Modifier.fillMaxWidth()) {
    Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
      Text(title, color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface)
      if (description != null) {
        Text(description, color = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface)
      }
    }
  }
}

@Composable
private fun CheckRow(
  title: String,
  description: String,
  checked: Boolean,
  onClick: () -> Unit,
) {
  Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
    Checkbox(checked = checked, onCheckedChange = { onClick() })
    Column(modifier = Modifier.weight(1f)) {
      Text(title, style = MaterialTheme.typography.titleMedium)
      Text(description, style = MaterialTheme.typography.bodySmall)
    }
  }
}

@Composable
private fun QuestionPrompt(question: FlagQuestion) {
  ElevatedCard(
    colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
    modifier = Modifier.fillMaxWidth(),
  ) {
    Column(
      modifier = Modifier.padding(20.dp),
      verticalArrangement = Arrangement.spacedBy(14.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      when (question.variant) {
        QuizVariant.FlagToCountry,
        QuizVariant.TypeCountryName -> {
          Text(text = question.correctCountry.emoji, fontSize = 76.sp)
          Text(
            text = if (question.variant == QuizVariant.FlagToCountry) "Which country owns this flag?" else "Type this country name.",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
          )
        }
        QuizVariant.CountryToFlag -> {
          Text(text = question.correctCountry.name, style = MaterialTheme.typography.headlineMedium, textAlign = TextAlign.Center)
          Text(text = "Choose the matching flag.", style = MaterialTheme.typography.titleMedium)
        }
      }
    }
  }
}

@Composable
private fun AnswerButton(
  question: FlagQuestion,
  option: FlagCountry,
  selectedCountry: FlagCountry?,
  onCountryAnswerSelected: (FlagCountry) -> Unit,
) {
  val selected = selectedCountry?.code == option.code
  val color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant
  Button(
    onClick = { onCountryAnswerSelected(option) },
    colors =
      ButtonDefaults.buttonColors(
        containerColor = color,
        contentColor = buttonContentColor(color),
      ),
    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
    modifier = Modifier.fillMaxWidth(),
  ) {
    Text(
      text = if (question.variant == QuizVariant.CountryToFlag) option.emoji else option.name,
      fontSize = if (question.variant == QuizVariant.CountryToFlag) 32.sp else 16.sp,
    )
  }
}

@Composable
private fun PlayerResultRow(
  player: PlayerProgress,
  totalQuestions: Int,
) {
  Surface(
    color = MaterialTheme.colorScheme.surfaceVariant,
    shape = RoundedCornerShape(8.dp),
    modifier = Modifier.fillMaxWidth(),
  ) {
    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
      Text(text = player.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
      Text(text = "Score: ${player.score} / $totalQuestions")
      Text(text = "Hint points available: ${player.hintPoints}")
    }
  }
}

@Composable
private fun ResultRow(
  index: Int,
  result: QuestionResult,
) {
  val background = if (result.isCorrect) AccentGreen.copy(alpha = 0.15f) else AccentRed.copy(alpha = 0.15f)
  Surface(
    color = background,
    shape = RoundedCornerShape(8.dp),
    modifier = Modifier.fillMaxWidth(),
  ) {
    Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
      Text(text = "Question $index - ${result.playerName}", style = MaterialTheme.typography.titleMedium)
      Text(text = "Correct: ${result.question.correctCountry.emoji} ${result.question.correctCountry.name}")
      Text(text = "Your answer: ${result.selectedCountry?.name ?: result.typedAnswer.ifBlank { "No answer" }}")
      Text(text = if (result.hintUsed) "Hint used" else "No hint used")
    }
  }
}

@Composable
private fun buttonContentColor(background: Color): Color {
  return when (background) {
    MaterialTheme.colorScheme.primary -> MaterialTheme.colorScheme.onPrimary
    MaterialTheme.colorScheme.surfaceVariant -> MaterialTheme.colorScheme.onSurface
    else -> if (background.luminance() > 0.5f) Color.Black else Color.White
  }
}
