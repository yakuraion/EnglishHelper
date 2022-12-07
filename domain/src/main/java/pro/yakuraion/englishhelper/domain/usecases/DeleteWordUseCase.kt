package pro.yakuraion.englishhelper.domain.usecases

interface DeleteWordUseCase {

    suspend fun deleteWord(name: String)
}
