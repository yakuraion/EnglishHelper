package pro.yakuraion.englishhelper.data

import androidx.room.Database
import androidx.room.RoomDatabase
import pro.yakuraion.englishhelper.vocabulary.data.daos.WordsDao
import pro.yakuraion.englishhelper.vocabulary.data.entities.WordEntity

@Database(
    entities = [WordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun wordsDao(): WordsDao
}
