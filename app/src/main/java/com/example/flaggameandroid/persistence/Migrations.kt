package com.example.flaggameandroid.persistence

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val Migration6To7 =
  object : Migration(6, 7) {
    override fun migrate(db: SupportSQLiteDatabase) {
      db.execSQL(
        "ALTER TABLE progress ADD COLUMN savedQuizTemplatesSerialized TEXT NOT NULL DEFAULT ''",
      )
    }
  }
