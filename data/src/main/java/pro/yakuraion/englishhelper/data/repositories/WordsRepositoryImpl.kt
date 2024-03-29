package pro.yakuraion.englishhelper.data.repositories

import androidx.room.withTransaction
import kotlinx.coroutines.flow.Flow
import pro.yakuraion.englishhelper.data.database.AppDatabase
import pro.yakuraion.englishhelper.data.network.wooordhunt.WooordhuntHtmlDownloader
import pro.yakuraion.englishhelper.data.network.wooordhunt.WooordhuntHtmlParser
import pro.yakuraion.englishhelper.data.repositories.inner.InnerCompletedWordsRepository
import pro.yakuraion.englishhelper.data.repositories.inner.InnerLearningWordsRepository
import pro.yakuraion.englishhelper.data.repositories.inner.InnerTodayLearningWordsRepository
import pro.yakuraion.englishhelper.data.repositories.inner.InnerWordsExtrasRepository
import pro.yakuraion.englishhelper.data.repositories.inner.InnerWordsRepository
import pro.yakuraion.englishhelper.domain.entities.CompletedWord
import pro.yakuraion.englishhelper.domain.entities.WooordhuntWord
import pro.yakuraion.englishhelper.domain.entities.Word
import pro.yakuraion.englishhelper.domain.entities.WordExtra
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.entities.learning.MemorizationLevel
import pro.yakuraion.englishhelper.domain.repositories.WordsRepository
import javax.inject.Inject

internal class WordsRepositoryImpl @Inject constructor(
    private val appDatabase: AppDatabase,
    private val innerWordsRepository: InnerWordsRepository,
    private val innerWordsExtrasRepository: InnerWordsExtrasRepository,
    private val innerLearningWordsRepository: InnerLearningWordsRepository,
    private val innerCompletedWordsRepository: InnerCompletedWordsRepository,
    private val innerTodayLearningWordsRepository: InnerTodayLearningWordsRepository,
    private val wooordhuntHtmlDownloader: WooordhuntHtmlDownloader,
    private val wooordhuntHtmlParser: WooordhuntHtmlParser
) : WordsRepository {

    override suspend fun getWordByName(name: String): Word? {
        return innerWordsRepository.getWordByName(name)
    }

    override suspend fun getWordExtraByName(name: String): WordExtra? {
        return innerWordsExtrasRepository.getByName(name)
    }

    override suspend fun downloadWoooordhuntWord(word: String): WooordhuntWord? {
        return wooordhuntHtmlDownloader.downloadHtml(word)?.let { html ->
            wooordhuntHtmlParser.parse(word, html)
        }
    }

    override fun getTodayLearningWords(): Flow<List<LearningWord>> {
        return innerTodayLearningWordsRepository.getWords()
    }

    override fun getLearningWords(): Flow<List<LearningWord>> {
        return innerLearningWordsRepository.getWords()
    }

    override suspend fun getLearningWordsAvailableToLearnBy(learningDay: Int): List<LearningWord> {
        return innerLearningWordsRepository.getWordsAvailableToLearnBy(learningDay)
    }

    override fun getCompletedWords(): Flow<List<CompletedWord>> {
        return innerCompletedWordsRepository.getWords()
    }

    override suspend fun addNewLiteWord(name: String, firstDayToLearn: Int) {
        appDatabase.withTransaction {
            innerWordsRepository.addWord(name)
            innerLearningWordsRepository.addWord(name, nextDayToLearn = firstDayToLearn)
            innerTodayLearningWordsRepository.addWord(name)
        }
    }

    override suspend fun addNewWord(name: String, html: String, extra: WordExtra, firstDayToLearn: Int) {
        appDatabase.withTransaction {
            innerWordsRepository.addWord(name)
            innerWordsExtrasRepository.addWord(extra, html)
            innerLearningWordsRepository.addWord(name, nextDayToLearn = firstDayToLearn)
            innerTodayLearningWordsRepository.addWord(name)
        }
    }

    override suspend fun deleteWord(name: String) {
        innerWordsRepository.deleteWord(name)
    }

    override suspend fun completeWord(word: LearningWord) {
        appDatabase.withTransaction {
            innerLearningWordsRepository.removeWord(word.name)
            innerCompletedWordsRepository.addWord(word.name)
        }
    }

    override suspend fun resetLearningDayProgress(name: String, firstDayToLearn: Int) {
        appDatabase.withTransaction {
            val word = LearningWord(name, MemorizationLevel.new(), firstDayToLearn)
            innerLearningWordsRepository.updateWord(word)
            innerTodayLearningWordsRepository.insertOrUpdate(name)
        }
    }

    override suspend fun learnCompletedWordAgain(name: String, firstDayToLearn: Int) {
        appDatabase.withTransaction {
            innerCompletedWordsRepository.deleteWord(name)
            innerLearningWordsRepository.addWord(name, firstDayToLearn)
        }
    }

    override suspend fun setTodayLearningWords(words: List<LearningWord>) {
        innerTodayLearningWordsRepository.setWords(words)
    }

    override suspend fun updateTodayLearningDay(word: LearningWord, addToTodayLearning: Boolean) {
        appDatabase.withTransaction {
            innerLearningWordsRepository.updateWord(word)
            if (addToTodayLearning) {
                innerTodayLearningWordsRepository.updateWord(word.name)
            } else {
                innerTodayLearningWordsRepository.removeWord(word.name)
            }
        }
    }
}
