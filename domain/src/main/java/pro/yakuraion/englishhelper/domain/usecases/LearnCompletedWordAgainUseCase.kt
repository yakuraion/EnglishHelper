package pro.yakuraion.englishhelper.domain.usecases

import pro.yakuraion.englishhelper.domain.entities.CompletedWord

interface LearnCompletedWordAgainUseCase {

    suspend fun learnCompletedWordAgain(word: CompletedWord)
}
