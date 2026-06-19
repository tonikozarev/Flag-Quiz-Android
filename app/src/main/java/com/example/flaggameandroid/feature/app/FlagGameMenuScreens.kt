package com.example.flaggameandroid.feature.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.flaggameandroid.core.model.AllInType
import com.example.flaggameandroid.core.model.AchievementsProgress
import com.example.flaggameandroid.core.model.GameMode
import com.example.flaggameandroid.core.model.HintDifficulty
import com.example.flaggameandroid.core.model.PlayerProgress
import com.example.flaggameandroid.core.model.RatingsProgress
import kotlinx.coroutines.delay

@Composable
fun MenuScreen(
  levelProgress: LevelProgressState,
  profile: ProfileState,
  language: AppLanguage,
  onStartClick: () -> Unit,
  onMedalsClick: () -> Unit,
  onAchievementsClick: () -> Unit,
  onSettingsClick: () -> Unit,
  onQuitClick: () -> Unit,
  onLevelUpSeen: () -> Unit,
  onAccountNameChanged: (String) -> Unit,
  onAvatarSelected: (Int) -> Unit,
  modifier: Modifier = Modifier,
) {
  var profileDialogVisible by remember { mutableStateOf(false) }

  if (profileDialogVisible) {
    ProfileEditorDialog(
      profile = profile,
      levelProgress = levelProgress,
      language = language,
      onDismiss = { profileDialogVisible = false },
      onSave = { name, avatarIndex ->
        onAccountNameChanged(name)
        onAvatarSelected(avatarIndex)
        profileDialogVisible = false
      },
    )
  }

  ScreenShell(modifier = modifier) {
    LevelProgressPanel(
      levelProgress = levelProgress,
      profile = profile,
      onLevelUpSeen = onLevelUpSeen,
      language = language,
      onClick = { profileDialogVisible = true },
    )

    HeroPanel(
      title = cleanText(language, UiText.WorldFlagGame),
      subtitle = cleanText(language, UiText.HeroSubtitle),
      language = language,
      onStartClick = onStartClick,
      onMedalsClick = onMedalsClick,
      onAchievementsClick = onAchievementsClick,
      onSettingsClick = onSettingsClick,
      onQuitClick = onQuitClick,
    )
  }
}

@Composable
fun GameModesScreen(
  language: AppLanguage,
  onBack: () -> Unit,
  onModeSelected: (GameMode) -> Unit,
  modifier: Modifier = Modifier,
) {
  var expandedInfoMode by remember { mutableStateOf<GameMode?>(null) }

  ScreenShell(modifier = modifier) {
    HeaderRow(title = cleanModeSelectionTitle(language))

    GameMode.entries.forEach { mode ->
      ModeCard(
        mode = mode,
        language = language,
        infoExpanded = expandedInfoMode == mode,
        onInfoClick = {
          expandedInfoMode = if (expandedInfoMode == mode) null else mode
        },
        onClick = { onModeSelected(mode) },
      )
    }
  }
}

@Composable
fun MedalsScreen(
  ratings: RatingsProgress,
  language: AppLanguage,
  onBack: () -> Unit,
  modifier: Modifier = Modifier,
) {
  ScreenShell(modifier = modifier) {
    HeaderRow(title = cleanText(language, UiText.Medals))
    RatingsSection(ratings = ratings, language = language)
  }
}

@Composable
fun AchievementsScreen(
  achievements: AchievementsProgress,
  language: AppLanguage,
  onBack: () -> Unit,
  modifier: Modifier = Modifier,
) {
  ScreenShell(modifier = modifier) {
    HeaderRow(title = cleanText(language, UiText.Achievements))
    AchievementsSection(achievements = achievements, language = language)
  }
}

@Composable
fun SettingsScreen(
  settings: SettingsState,
  hintCount: Int,
  inactiveIconActive: Boolean,
  onBack: () -> Unit,
  onHintDifficultySelected: (HintDifficulty) -> Unit,
  onLanguageSelected: (AppLanguage) -> Unit,
  onReminderEnabledChanged: (Boolean) -> Unit,
  onResetHintsClick: () -> Unit,
  onAddTestingHintsClick: () -> Unit,
  onTestingLevelUpClick: () -> Unit,
  onTestingResetLevelClick: () -> Unit,
  onUnlockRandomAchievementClick: () -> Unit,
  onLockAllAchievementsClick: () -> Unit,
  onResetAchievementsAndMedalsClick: () -> Unit,
  onToggleTestingIconClick: () -> Unit,
  onTriggerTestingReminderClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  var expandedDifficulty by remember { mutableStateOf<HintDifficulty?>(null) }
  var testingButtonEnabled by remember { mutableStateOf(true) }
  var languageMenuExpanded by remember { mutableStateOf(false) }

  LaunchedEffect(testingButtonEnabled) {
    if (!testingButtonEnabled) {
      delay(3_000)
      testingButtonEnabled = true
    }
  }

  ScreenShell(modifier = modifier) {
    HeaderRow(title = t(settings.language, UiText.Settings))

    Card(modifier = Modifier.fillMaxWidth()) {
      Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Text(
            text = t(settings.language, UiText.Language),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
          )
          LanguageSelector(
            selectedLanguage = settings.language,
            expanded = languageMenuExpanded,
            onExpandedChange = { languageMenuExpanded = it },
            onLanguageSelected = onLanguageSelected,
          )
        }
      }
    }

    Card(modifier = Modifier.fillMaxWidth()) {
      Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Text(
            text = t(settings.language, UiText.Hints),
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(1f),
          )
          Text("$hintCount", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
        HintDifficulty.entries.forEach { difficulty ->
          CompactInfoRow(
            title = localizedHintDifficultyTitle(difficulty, settings.language),
            shortText = localizedHintDifficultyShortRule(difficulty, settings.language),
            infoText = localizedHintDifficultyDescription(difficulty, settings.language),
            selected = settings.hintDifficulty == difficulty,
            infoExpanded = expandedDifficulty == difficulty,
            onClick = { onHintDifficultySelected(difficulty) },
            onInfoClick = {
              expandedDifficulty = if (expandedDifficulty == difficulty) null else difficulty
            },
          )
        }
      }
    }

    ReminderSettingsCard(
      language = settings.language,
      reminderEnabled = settings.reminderEnabled,
      onReminderEnabledChanged = onReminderEnabledChanged,
    )

    TestingToolsCard(
      language = settings.language,
      inactiveIconActive = inactiveIconActive,
      testingButtonEnabled = testingButtonEnabled,
      onAddTestingHintsClick = {
        onAddTestingHintsClick()
        testingButtonEnabled = false
      },
      onResetHintsClick = onResetHintsClick,
      onTestingLevelUpClick = onTestingLevelUpClick,
      onTestingResetLevelClick = onTestingResetLevelClick,
      onUnlockRandomAchievementClick = onUnlockRandomAchievementClick,
      onLockAllAchievementsClick = onLockAllAchievementsClick,
      onResetAchievementsAndMedalsClick = onResetAchievementsAndMedalsClick,
      onToggleTestingIconClick = onToggleTestingIconClick,
      onTriggerTestingReminderClick = onTriggerTestingReminderClick,
    )
  }
}