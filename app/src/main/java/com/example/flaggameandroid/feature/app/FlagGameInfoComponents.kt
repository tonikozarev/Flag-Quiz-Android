package com.example.flaggameandroid.feature.app

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.flaggameandroid.theme.FlagGameAndroidTheme
import androidx.compose.ui.tooling.preview.Preview

internal const val InfoSymbolText = "𝒊"

@Composable
internal fun InfoButton(
  label: String = InfoSymbolText,
  modifier: Modifier = Modifier,
  contentColor: Color = MaterialTheme.colorScheme.onSurface,
  borderColor: Color = MaterialTheme.colorScheme.outline.copy(alpha = 0.78f),
  containerColor: Color = Color.Transparent,
  onClick: () -> Unit,
) {
  val iconOnly = label == InfoSymbolText
  if (!iconOnly) {
    OutlinedButton(
      onClick = onClick,
      modifier = modifier,
      contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 6.dp, vertical = 10.dp),
    ) {
      Text(
        text = label,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        maxLines = 1,
        softWrap = false,
      )
    }
    return
  }

  Surface(
    color = containerColor,
    contentColor = contentColor,
    shape = RoundedCornerShape(999.dp),
    border = BorderStroke(1.dp, borderColor),
    modifier =
      modifier
        .then(
          Modifier
            .width(42.dp)
            .height(40.dp),
        )
        .clickable(onClick = onClick),
  ) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(horizontal = 0.dp)) {
      Text(
        text = label,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.Bold,
        color = contentColor,
        maxLines = 1,
      )
    }
  }
}

@Composable
internal fun InfoPanel(text: String) {
  Surface(
    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f),
    shape = RoundedCornerShape(10.dp),
    modifier = Modifier.fillMaxWidth(),
  ) {
    Text(
      text = text,
      modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
      style = MaterialTheme.typography.bodySmall,
      color = MaterialTheme.colorScheme.onSurface,
    )
  }
}

@Composable
internal fun QuestionTrackingPanel(
  title: String,
  questionNumbers: List<Int>,
) {
  InfoPanel(
    text =
      if (questionNumbers.isEmpty()) {
        "$title: -"
      } else {
        "$title: ${formatQuestionRanges(questionNumbers)}"
      },
  )
}

internal fun formatQuestionRanges(numbers: List<Int>): String {
  if (numbers.isEmpty()) return "-"
  val sorted = numbers.distinct().sorted()
  val ranges = mutableListOf<String>()
  var start = sorted.first()
  var previous = start
  for (index in 1 until sorted.size) {
    val current = sorted[index]
    if (current == previous + 1) {
      previous = current
      continue
    }
    ranges += if (start == previous) "$start" else "$start-$previous"
    start = current
    previous = current
  }
  ranges += if (start == previous) "$start" else "$start-$previous"
  return ranges.joinToString(", ")
}

@Composable
internal fun SettingSwitchRow(
  title: String,
  description: String,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit,
) {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.spacedBy(12.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(3.dp)) {
      Text(text = title, style = MaterialTheme.typography.titleMedium)
      Text(text = description, style = MaterialTheme.typography.bodySmall)
    }
    Switch(checked = checked, onCheckedChange = onCheckedChange)
  }
}

@Preview(showBackground = true, name = "Info Button")
@Composable
private fun PreviewInfoButton() {
  FlagGameAndroidTheme {
    InfoButton(onClick = {})
  }
}

