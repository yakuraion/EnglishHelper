package pro.yakuraion.englishhelper.data.repositories

import pro.yakuraion.englishhelper.data.converters.getWord
import pro.yakuraion.englishhelper.data.database.daos.WordsDao
import pro.yakuraion.englishhelper.domain.entities.Word
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import javax.inject.Inject

internal class WordsRepositoryImpl @Inject constructor(
    private val wordsDao: WordsDao
) : WordsRepository {

    override suspend fun getWordByName(name: String): Word? {
        val wordEntity = wordsDao.getByName(name)
        return wordEntity?.let { getWord(it) }
    }
}
