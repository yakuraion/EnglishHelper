package pro.yakuraion.englishhelper.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TestEntity(
    @PrimaryKey val id: String
)
