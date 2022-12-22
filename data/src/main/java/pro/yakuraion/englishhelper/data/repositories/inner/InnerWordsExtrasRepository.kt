package pro.yakuraion.englishhelper.data.repositories.inner

import pro.yakuraion.englishhelper.data.converters.getWordExtra
import pro.yakuraion.englishhelper.data.converters.getWordExtraEntity
import pro.yakuraion.englishhelper.data.database.daos.WordsExtrasDao
import pro.yakuraion.englishhelper.domain.entities.WordExtra
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class InnerWordsExtrasRepository @Inject constructor(
    private val wordsExtrasDao: WordsExtrasDao
) {

    suspend fun getByName(name: String): WordExtra? {
        return wordsExtrasDao.getByName(name)?.let { getWordExtra(it) }
    }

    suspend fun addWord(word: WordExtra, html: String) {
        val entity = getWordExtraEntity(word, html)
        wordsExtrasDao.insert(entity)
    }
}
