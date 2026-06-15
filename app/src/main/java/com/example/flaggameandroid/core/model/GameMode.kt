package com.example.flaggameandroid.core.model

enum class GameMode(
  val title: String,
  val description: String,
) {
  Training(
    title = "Training",
    description = "Mix flags, country names, and typed answers at your pace.",
  ),
  Continents(
    title = "Continents",
    description = "Build a quiz from the continents you want to practice.",
  ),
  AllIn(
    title = "All-In mode",
    description = "Go through the world with tougher full-catalog rules.",
  ),
  LocalMultiplayer(
    title = "Local Multiplayer",
    description = "Up to 5 players pass one device and play turn by turn.",
  ),
}

enum class QuizVariant(
  val title: String,
  val description: String,
) {
  FlagToCountry(
    title = "Flag -> country",
    description = "See a flag and pick the country.",
  ),
  CountryToFlag(
    title = "Country -> flag",
    description = "See a country and pick the flag.",
  ),
  TypeCountryName(
    title = "Type the country",
    description = "See a flag and write the country name.",
  ),
}

enum class AllInType(
  val title: String,
  val description: String,
) {
  Hardcore(
    title = "Hardcore",
    description = "All countries, all question variants, fully randomized.",
  ),
  NoBluffAllTough(
    title = "No Bluff, All Tough",
    description = "All countries with only the variants you choose.",
  ),
}
