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
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWordFull
import pro.yakuraion.englishhelper.domain.usecases.GetNextWordToLearnTodayUseCase
import pro.yakuraion.englishhelper.domain.usecases.MoveLearningWordToNextLevelUseCase
import pro.yakuraion.englishhelper.domain.usecases.MoveLearningWordToPreviousLevelUseCase
import pro.yakuraion.englishhelper.domain.utils.DictionaryUtils
import pro.yakuraion.englishhelper.vocabulary.ui.testing.states.TestingUiState
import kotlin.properties.Delegates
import kotlin.random.Random

class TestingViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getNextWordToLearnTodayUseCase: GetNextWordToLearnTodayUseCase,
    private val moveLearningWordToNextLevelUseCase: MoveLearningWordToNextLevelUseCase,
    private val moveLearningWordToPreviousLevelUseCase: MoveLearningWordToPreviousLevelUseCase
) : ViewModel() {

    var uiState: TestingUiState by mutableStateOf(TestingUiState.Loading)
        private set

    private var wordFullJob: Job? by Delegates.observable(null) { _, oldValue, _ ->
        oldValue?.cancel()
    }

    private var wordFull: LearningWordFull? = null

    private var isDictionaryVisited = false

    init {
        loadNextWord()
    }

    private fun loadNextWord() {
        wordFullJob = viewModelScope.launch {
            wordFull = getNextWordToLearnTodayUseCase.getNextWordToLearnToday()
            isDictionaryVisited = false
            uiState = getUiState(wordFull)
        }
    }

    private fun getUiState(word: LearningWordFull?): TestingUiState {
        return when {
            word == null -> {
                TestingUiState.NoMoreWords
            }
            word.word.soundUri != null -> {
                TestingUiState.Regular(
                    queueId = Random.nextLong(),
                    word = word.word.name,
                    soundUri = word.word.soundUri!!,
                    examples = word.word.examples,
                    dictionaryUrl = DictionaryUtils.getDictionaryUrl(word.word.name)
                )
            }
            else -> {
                TestingUiState.Lite(
                    queueId = Random.nextLong(),
                    word = word.word.name,
                    dictionaryUrl = DictionaryUtils.getDictionaryUrl(word.word.name)
                )
            }
        }
    }

    fun onVisitedDictionary() {
        isDictionaryVisited = true
    }

    fun onWordTested() {
        wordFull?.learningWord?.let { learningWord ->
            viewModelScope.launch {
                if (isDictionaryVisited) {
                    moveLearningWordToPreviousLevelUseCase.moveLearningWordToPreviousLevel(learningWord)
                } else {
                    moveLearningWordToNextLevelUseCase.moveLearningWordToNextLevel(learningWord)
                }
                when (val uiState = uiState) {
                    is TestingUiState.Lite -> {
                        loadNextWord()
                    }
                    is TestingUiState.Regular -> {
                        uiState.isAnswered = true
                    }
                    else -> {}
                }
            }
        }
    }

    // only for Regular state
    fun onContinueClick() {
        loadNextWord()
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<TestingViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): TestingViewModel
    }
}
