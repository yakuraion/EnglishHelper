package pro.yakuraion.englishhelper.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import pro.yakuraion.englishhelper.data.database.daos.LearningWordsDao
import pro.yakuraion.englishhelper.data.database.entities.LearningWordEntity

@Database(
    entities = [LearningWordEntity::class],
    version = 1,
    exportSchema = false
)
internal abstract class AppDatabase : RoomDatabase() {

    abstract fun learningWordsDao(): LearningWordsDao
}
