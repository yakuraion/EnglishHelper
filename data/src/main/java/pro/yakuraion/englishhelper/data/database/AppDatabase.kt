package pro.yakuraion.englishhelper.data.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import pro.yakuraion.englishhelper.data.database.daos.CompletedWordsDao
import pro.yakuraion.englishhelper.data.database.daos.LearningWordsDao
import pro.yakuraion.englishhelper.data.database.daos.TodayLearningWordsDao
import pro.yakuraion.englishhelper.data.database.daos.WordsDao
import pro.yakuraion.englishhelper.data.database.daos.WordsExtrasDao
import pro.yakuraion.englishhelper.data.database.entities.CompletedWordEntity
import pro.yakuraion.englishhelper.data.database.entities.LearningWordEntity
import pro.yakuraion.englishhelper.data.database.entities.TodayLearningWordEntity
import pro.yakuraion.englishhelper.data.database.entities.WordEntity
import pro.yakuraion.englishhelper.data.database.entities.WordExtraEntity

@Database(
    entities = [
        WordEntity::class,
        WordExtraEntity::class,
        LearningWordEntity::class,
        TodayLearningWordEntity::class,
        CompletedWordEntity::class
    ],
    autoMigrations = [
        AutoMigration(from = 2, to = 3, spec = Migration_2_3_Spec::class)
    ],
    version = 3
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun getWordsDao(): WordsDao

    abstract fun getWordsExtrasDao(): WordsExtrasDao

    abstract fun getLearningWordsDao(): LearningWordsDao

    abstract fun getTodayLearningWordsDao(): TodayLearningWordsDao

    abstract fun getCompletedWordsDao(): CompletedWordsDao
}
