package pro.yakuraion.englishhelper.domain.repositories

import pro.yakuraion.englishhelper.domain.entities.Word

interface WordsRepository {

    suspend fun getWordByName(name: String): Word?
}
