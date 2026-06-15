package com.example.flaggameandroid.core.data

import com.example.flaggameandroid.core.model.GameMode
import com.example.flaggameandroid.core.model.QuizConfig
import com.example.flaggameandroid.core.model.QuizVariant
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test
import kotlin.random.Random

class QuizQuestionGeneratorTest {
  private val repository = StaticFlagCatalogRepository()

  @Test
  fun catalog_hasFullCountrySetWithValidData() {
    val countries = repository.getCountries()

    assertTrue(countries.size >= 195)
    assertEquals(countries.size, countries.distinctBy { it.code }.size)
    assertTrue(countries.all { it.name.isNotBlank() })
    assertTrue(countries.all { it.emoji.isNotBlank() })
    assertTrue(countries.all { it.continent.isNotBlank() })
  }

  @Test
  fun catalog_splitsAmericasIntoNorthAndSouthAmerica() {
    val continents = repository.getCountries().map { it.continent }.toSet()

    assertTrue("North America" in continents)
    assertTrue("South America" in continents)
    assertTrue("Americas" !in continents)
  }

  @Test
  fun buildQuestions_respectsSelectedVariantsAndDoesNotRepeatCountries() {
    val generator = QuizQuestionGenerator(Random(7))
    val variants = setOf(QuizVariant.FlagToCountry, QuizVariant.TypeCountryName)

    val questions =
      generator.buildQuestions(
        countries = repository.getCountries(),
        config =
          QuizConfig(
            mode = GameMode.Training,
            variants = variants,
            questionCount = 25,
          ),
      )

    assertEquals(25, questions.size)
    assertTrue(questions.all { it.variant in variants })
    assertEquals(25, questions.map { it.correctCountry.code }.distinct().size)
    questions.forEach { question ->
      assertEquals(4, question.options.distinctBy { it.code }.size)
      assertTrue(question.options.any { it.code == question.correctCountry.code })
    }
  }
}
