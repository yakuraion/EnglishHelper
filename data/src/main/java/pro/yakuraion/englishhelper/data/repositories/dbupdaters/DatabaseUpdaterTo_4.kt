package pro.yakuraion.englishhelper.data.repositories.dbupdaters

import pro.yakuraion.englishhelper.data.database.daos.WordsExtrasDao
import pro.yakuraion.englishhelper.data.network.wooordhunt.WooordhuntHtmlParser
import javax.inject.Inject

@Suppress("ClassName")
internal class DatabaseUpdaterTo_4 @Inject constructor(
    private val wordsExtrasDao: WordsExtrasDao,
    private val wooordhuntHtmlParser: WooordhuntHtmlParser
) : DatabaseUpdater {

    override suspend fun update() {
        wordsExtrasDao.getAll().forEach { wordExtraEntity ->
            wooordhuntHtmlParser.parse(wordExtraEntity.name, wordExtraEntity.html)?.let { wooordhuntWord ->
                val newEntity = wordExtraEntity.copy(remoteSoundUri = wooordhuntWord.soundUri)
                wordsExtrasDao.insertOrReplace(newEntity)
            }
        }
    }
}
