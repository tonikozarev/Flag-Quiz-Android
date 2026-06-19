package com.example.flaggameandroid.feature.app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flaggameandroid.core.model.AchievementId
import com.example.flaggameandroid.core.model.AchievementSector
import com.example.flaggameandroid.core.model.AchievementsProgress
import com.example.flaggameandroid.core.model.HintDifficulty
import com.example.flaggameandroid.core.model.RatingsProgress
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
internal fun RatingsSection(
  ratings: RatingsProgress,
  language: AppLanguage,
) {
  var showMedalInfo by remember { mutableStateOf(false) }
  SectionCard(title = cleanText(language, UiText.Medals)) {
    androidx.compose.foundation.layout.Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
    ) {
      Text(
        text = cleanMedalIntro(language),
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier.weight(1f),
      )
      InfoButton(onClick = { showMedalInfo = !showMedalInfo })
    }
    if (showMedalInfo) {
      InfoPanel(text = cleanMedalInfo(language))
    }
    androidx.compose.foundation.layout.FlowRow(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalArrangement = Arrangement.spacedBy(10.dp),
    ) {
      com.example.flaggameandroid.core.model.MedalTier.entries.forEach { medalTier ->
        MedalTierRow(
          medalTier = medalTier,
          count = ratings.countFor(medalTier),
          title = cleanMedalTitle(medalTier, language),
        )
      }
    }
  }
}

@Composable
internal fun AchievementsSection(
  achievements: AchievementsProgress,
  language: AppLanguage,
) {
  var expandedAchievement by remember { mutableStateOf<AchievementId?>(null) }
  SectionCard(title = cleanText(language, UiText.Achievements)) {
    Text(
      text = localizedAchievementHint(language),
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
    AchievementSector.entries.forEach { sector ->
      Text(
        text = localizedAchievementSectorTitle(sector, language),
        style = MaterialTheme.typography.titleMedium,
        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
        modifier = Modifier.padding(top = 6.dp),
      )
      AchievementId.entries.filter { it.sector == sector }.forEach { achievementId ->
        val unlockedAt = achievements.unlockedAt(achievementId)
        val unlocked = unlockedAt != null
        AchievementCardItem(
          achievementId = achievementId,
          title = localizedAchievementTitle(achievementId, language),
          status = localizedAchievementStatus(language, unlockedAt),
          unlocked = unlocked,
          expanded = expandedAchievement == achievementId,
          description = localizedAchievementDescription(achievementId, language),
          onClick = { expandedAchievement = if (expandedAchievement == achievementId) null else achievementId },
        )
      }
    }
  }
}

internal fun formatAchievementDate(epochMillis: Long?): String {
  if (epochMillis == null) return "-"
  return SimpleDateFormat("dd.MM.yyyy", Locale.ROOT).format(Date(epochMillis))
}

internal fun hintDifficultyDescription(difficulty: HintDifficulty): String =
  when (difficulty) {
    HintDifficulty.Rookie -> "Collect 1 hint for every correct answer."
    HintDifficulty.Medium -> "Collect 1 hint for every 5 correct answers in a row."
    HintDifficulty.Hard -> "Collect 1 hint for every 10 correct answers in a row."
    HintDifficulty.Impossible -> "Collect 1 hint for every 50 correct answers in a row."
  }
