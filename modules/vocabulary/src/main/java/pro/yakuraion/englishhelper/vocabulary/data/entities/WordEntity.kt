package pro.yakuraion.englishhelper.vocabulary.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_entity")
data class WordEntity(
    @PrimaryKey val name: String,
    val pronunciation: String,

)
