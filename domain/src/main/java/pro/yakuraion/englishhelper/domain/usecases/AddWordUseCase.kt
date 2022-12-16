package pro.yakuraion.englishhelper.domain.usecases

interface AddWordUseCase {

    suspend fun addWord(name: String, withExtraInfo: Boolean): Result

    enum class Result {
        SUCCESS,
        WORD_NOT_FOUND
    }
}
