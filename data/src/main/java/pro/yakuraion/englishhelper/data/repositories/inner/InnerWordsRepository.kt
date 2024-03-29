package pro.yakuraion.englishhelper.data.repositories.inner

import pro.yakuraion.englishhelper.data.converters.getWord
import pro.yakuraion.englishhelper.data.converters.getWordEntity
import pro.yakuraion.englishhelper.data.database.daos.WordsDao
import pro.yakuraion.englishhelper.domain.entities.Word
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class InnerWordsRepository @Inject constructor(
    private val wordsDao: WordsDao
) {

    suspend fun getAllWords(): List<Word> {
        return wordsDao.getAll().map { getWord(it) }
    }

    suspend fun getWordByName(name: String): Word? {
        val wordEntity = wordsDao.getByName(name)
        return wordEntity?.let { getWord(it) }
    }

    suspend fun addWord(name: String) {
        val wordEntity = getWordEntity(name)
        wordsDao.insert(wordEntity)
    }

    suspend fun deleteWord(name: String) {
        wordsDao.delete(name)
    }
}
