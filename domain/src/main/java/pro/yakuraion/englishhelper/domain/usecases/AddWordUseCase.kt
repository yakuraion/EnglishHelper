package pro.yakuraion.englishhelper.domain.usecases

interface AddWordUseCase {

    suspend fun addWord(name: String, withAudio: Boolean): Result

    enum class Result {
        SUCCESS,
        WORD_AUDIO_NOT_FOUND
    }
}
