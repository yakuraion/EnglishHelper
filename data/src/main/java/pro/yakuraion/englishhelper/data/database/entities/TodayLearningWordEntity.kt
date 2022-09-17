package pro.yakuraion.englishhelper.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "today_learning_word"
)
class TodayLearningWordEntity(
    @PrimaryKey val name: String
)