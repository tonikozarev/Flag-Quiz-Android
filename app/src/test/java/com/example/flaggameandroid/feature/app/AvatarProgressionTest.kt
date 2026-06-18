package com.example.flaggameandroid.feature.app

import com.example.flaggameandroid.core.model.ProgressionRules
import org.junit.Assert.assertEquals
import org.junit.Test

class AvatarProgressionTest {
  @Test
  fun avatarOptions_matchTenLevelsWithFiveIconsEach() {
    assertEquals(10, ProgressionRules.MaxLevel)
    assertEquals(50, ProgressionRules.TotalAvatarCount)
    assertEquals(ProgressionRules.TotalAvatarCount, AvatarOptions.size)
  }
}
