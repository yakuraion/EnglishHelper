package pro.yakuraion.englishhelper.domain.usecases

import pro.yakuraion.englishhelper.domain.entities.WordExtra

interface GetWordExtraUseCase {

    suspend fun getWordExtra(name: String): WordExtra?
}
