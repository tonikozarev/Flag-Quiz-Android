package com.example.flaggameandroid.persistence

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.flaggameandroid.engagement.AppEngagementCoordinator

private val Context.flagGameSettingsDataStore by preferencesDataStore(name = "flag_game_settings")

private val Migration1To2 =
  object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
      database.execSQL("ALTER TABLE progress ADD COLUMN lastOpenedAtEpochMillis INTEGER NOT NULL DEFAULT 0")
      database.execSQL("ALTER TABLE progress ADD COLUMN lastPlayedAtEpochMillis INTEGER NOT NULL DEFAULT 0")
      database.execSQL("ALTER TABLE progress ADD COLUMN inactiveIconActive INTEGER NOT NULL DEFAULT 0")
    }
  }

private val Migration2To3 =
  object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
      database.execSQL("ALTER TABLE progress ADD COLUMN ratingsSerialized TEXT NOT NULL DEFAULT ''")
      database.execSQL("ALTER TABLE progress ADD COLUMN achievementUnlocksSerialized TEXT NOT NULL DEFAULT ''")
    }
  }

private val Migration3To4 =
  object : Migration(3, 4) {
    override fun migrate(database: SupportSQLiteDatabase) {
      database.execSQL("ALTER TABLE progress ADD COLUMN accountName TEXT NOT NULL DEFAULT ''")
      database.execSQL("ALTER TABLE progress ADD COLUMN avatarIndex INTEGER NOT NULL DEFAULT 0")
      database.execSQL("ALTER TABLE progress ADD COLUMN languageName TEXT NOT NULL DEFAULT 'English'")
    }
  }

class AppContainer(
  context: Context,
) {
  private val database: FlagGameDatabase =
    Room.databaseBuilder(
      context,
      FlagGameDatabase::class.java,
      "flag_game.db",
    ).addMigrations(Migration1To2, Migration2To3, Migration3To4).build()

  val settingsStore: SettingsStore = DataStoreSettingsStore(context.flagGameSettingsDataStore)

  val progressStore: ProgressStore =
    RoomProgressStore(
      progressDao = database.progressDao(),
      quizHistoryDao = database.quizHistoryDao(),
    )

  val engagementCoordinator: AppEngagementCoordinator =
    AppEngagementCoordinator(
      context = context.applicationContext,
      settingsStore = settingsStore,
      progressStore = progressStore,
    )
}

object AppGraph {
  @Volatile
  private var container: AppContainer? = null

  fun from(context: Context): AppContainer {
    return container ?: synchronized(this) {
      container ?: AppContainer(context.applicationContext).also { container = it }
    }
  }
}
