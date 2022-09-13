package pro.yakuraion.englishhelper.domain.interactors

interface WordsInteractor {

    suspend fun isWordAlreadyExist(name: String): Boolean

    suspend fun addWord(name: String)
}
