package pro.yakuraion.englishhelper.domain.usecases

interface IsWordAlreadyExistUseCase {

    suspend fun isWordAlreadyExist(name: String): Boolean
}
