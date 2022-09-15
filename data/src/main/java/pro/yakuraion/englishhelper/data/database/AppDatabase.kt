package pro.yakuraion.englishhelper.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pro.yakuraion.englishhelper.data.database.daos.LearningWordsDao
import pro.yakuraion.englishhelper.data.database.daos.TodayLearningWordsDao
import pro.yakuraion.englishhelper.data.database.entities.LearningWordEntity
import pro.yakuraion.englishhelper.data.database.entities.TodayLearningWordEntity

@Database(
    entities = [LearningWordEntity::class, TodayLearningWordEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun getLearningWordsDao(): LearningWordsDao

    abstract fun getTodayLearningWordsDao(): TodayLearningWordsDao
}
