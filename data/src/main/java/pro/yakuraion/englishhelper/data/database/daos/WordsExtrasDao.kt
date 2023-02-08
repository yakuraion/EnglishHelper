package pro.yakuraion.englishhelper.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pro.yakuraion.englishhelper.data.database.entities.WordExtraEntity

@Dao
internal interface WordsExtrasDao {

    @Query("SELECT * FROM word_extra")
    suspend fun getAll(): List<WordExtraEntity>

    @Query("SELECT * FROM word_extra WHERE name = :name")
    suspend fun getByName(name: String): WordExtraEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrReplace(word: WordExtraEntity)
}
