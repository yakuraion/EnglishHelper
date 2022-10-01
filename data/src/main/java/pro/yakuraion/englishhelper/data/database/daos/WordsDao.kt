package pro.yakuraion.englishhelper.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pro.yakuraion.englishhelper.data.database.entities.WordEntity

@Dao
internal interface WordsDao {

    @Query("SELECT * FROM word WHERE name = :name")
    suspend fun getByName(name: String): WordEntity?

    @Insert
    suspend fun insert(word: WordEntity)
}
