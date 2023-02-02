package pro.yakuraion.englishhelper.vocabulary.ui.testing

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import pro.yakuraion.englishhelper.commonui.di.viewmodel.AssistedSavedStateViewModelFactory
import pro.yakuraion.englishhelper.domain.entities.WordExtra
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.usecases.GetNextWordToLearnTodayUseCase
import pro.yakuraion.englishhelper.domain.usecases.GetWordExtraUseCase
import pro.yakuraion.englishhelper.domain.usecases.MoveLearningWordToNextLevelUseCase
import pro.yakuraion.englishhelper.domain.usecases.MoveLearningWordToPreviousLevelUseCase
import pro.yakuraion.englishhelper.domain.utils.DictionaryUtils
import pro.yakuraion.englishhelper.vocabulary.ui.testing.states.TestingUiState
import kotlin.properties.Delegates
import kotlin.random.Random

class TestingViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getNextWordToLearnTodayUseCase: GetNextWordToLearnTodayUseCase,
    private val getWordExtraUseCase: GetWordExtraUseCase,
    private val moveLearningWordToNextLevelUseCase: MoveLearningWordToNextLevelUseCase,
    private val moveLearningWordToPreviousLevelUseCase: MoveLearningWordToPreviousLevelUseCase
) : ViewModel() {

    var uiState: TestingUiState by mutableStateOf(TestingUiState.Loading)
        private set

    private var loadNextWordJob: Job? by Delegates.observable(null) { _, oldValue, _ ->
        oldValue?.cancel()
    }

    private var learningWord: LearningWord? = null
    private var wordExtra: WordExtra? = null

    private var isDictionaryVisited = false

    init {
        loadNextWord()
    }

    private fun loadNextWord() {
        loadNextWordJob = viewModelScope.launch {
            learningWord = getNextWordToLearnTodayUseCase.getNextWordToLearnToday()
            wordExtra = learningWord?.let { getWordExtraUseCase.getWordExtra(it.name) }
            isDictionaryVisited = false
            uiState = getUiState(learningWord, wordExtra)
        }
    }

    private fun getUiState(learningWord: LearningWord?, wordExtra: WordExtra?): TestingUiState {
        return when {
            learningWord == null -> {
                TestingUiState.NoMoreWords
            }
            wordExtra == null -> {
                TestingUiState.Lite(
                    queueId = Random.nextLong(),
                    word = learningWord.name,
                    dictionaryUrl = DictionaryUtils.getDictionaryUrl(learningWord.name)
                )
            }
            else -> {
                TestingUiState.Regular(
                    queueId = Random.nextLong(),
                    wordExtra = wordExtra,
                    dictionaryUrl = DictionaryUtils.getDictionaryUrl(wordExtra.name)
                )
            }
        }
    }

    fun onRegularVisitedDictionary() {
        isDictionaryVisited = true
    }

    fun onRegularWordCheck() {
        learningWord?.let { learningWord ->
            viewModelScope.launch {
                if (isDictionaryVisited) {
                    moveLearningWordToPreviousLevelUseCase.moveLearningWordToPreviousLevel(learningWord)
                } else {
                    moveLearningWordToNextLevelUseCase.moveLearningWordToNextLevel(learningWord)
                }
                (uiState as TestingUiState.Regular).isAnswered = true
            }
        }
    }

    fun onRegularContinueClick() {
        loadNextWord()
    }

    fun onLiteWordCheckSuccess() {
        learningWord?.let { learningWord ->
            viewModelScope.launch {
                moveLearningWordToNextLevelUseCase.moveLearningWordToNextLevel(learningWord)
                loadNextWord()
            }
        }
    }

    fun onLiteWordCheckFailed() {
        learningWord?.let { learningWord ->
            viewModelScope.launch {
                moveLearningWordToPreviousLevelUseCase.moveLearningWordToPreviousLevel(learningWord)
                loadNextWord()
            }
        }
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<TestingViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): TestingViewModel
    }
}
