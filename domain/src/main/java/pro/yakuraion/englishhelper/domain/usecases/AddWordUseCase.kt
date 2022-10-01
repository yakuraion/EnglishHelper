package pro.yakuraion.englishhelper.domain.usecases

interface AddWordUseCase {

    suspend fun addWord(name: String)
}
