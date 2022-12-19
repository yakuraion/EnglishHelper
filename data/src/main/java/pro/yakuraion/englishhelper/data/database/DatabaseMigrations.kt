package pro.yakuraion.englishhelper.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE word RENAME COLUMN soundFile TO soundUri")
        database.execSQL("ALTER TABLE word ADD COLUMN examplesJson text NOT NULL DEFAULT '[]'")
    }
}