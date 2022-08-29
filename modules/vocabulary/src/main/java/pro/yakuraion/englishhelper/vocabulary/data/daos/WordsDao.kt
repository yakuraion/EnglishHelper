package pro.yakuraion.englishhelper.vocabulary.data.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import pro.yakuraion.englishhelper.vocabulary.data.entities.WordEntity

@Dao
interface WordsDao {

    @Query("SELECT * FROM word_entity WHERE name = :name")
    fun getByName(name: String): WordEntity

    @Insert
    fun insert(word: WordEntity)

    @Query("DELETE FROM word_entity WHERE name = :name")
    fun delete(name: String)
}
