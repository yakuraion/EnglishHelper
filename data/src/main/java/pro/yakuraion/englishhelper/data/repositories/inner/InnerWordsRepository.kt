package pro.yakuraion.englishhelper.data.repositories.inner

import pro.yakuraion.englishhelper.data.converters.getWord
import pro.yakuraion.englishhelper.data.converters.getWordEntity
import pro.yakuraion.englishhelper.data.database.daos.WordsDao
import pro.yakuraion.englishhelper.domain.entities.Word
import pro.yakuraion.englishhelper.domain.entities.learning.WordExample
import javax.inject.Inject

internal class InnerWordsRepository @Inject constructor(
    private val wordsDao: WordsDao
) {

    suspend fun getWordByName(name: String): Word? {
        val wordEntity = wordsDao.getByName(name)
        return wordEntity?.let { getWord(it) }
    }

    suspend fun addWord(name: String, soundUri: String?, examples: List<WordExample>) {
        val wordEntity = getWordEntity(name, soundUri, examples)
        wordsDao.insert(wordEntity)
    }

    suspend fun deleteWord(name: String) {
        wordsDao.delete(name)
    }
}
