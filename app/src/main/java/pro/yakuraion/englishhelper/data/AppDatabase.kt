package pro.yakuraion.englishhelper.data

import androidx.room.Database
import androidx.room.RoomDatabase
import pro.yakuraion.englishhelper.vocabulary.data.daos.LearningWordsDao
import pro.yakuraion.englishhelper.vocabulary.data.entities.LearningWordEntity

@Database(
    entities = [LearningWordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun learningWordsDao(): LearningWordsDao
}
