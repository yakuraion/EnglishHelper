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
import kotlinx.coroutines.launch
import pro.yakuraion.englishhelper.commonui.di.viewmodel.AssistedSavedStateViewModelFactory
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWordFull
import pro.yakuraion.englishhelper.domain.usecases.GetNextWordToLearnTodayUseCase
import pro.yakuraion.englishhelper.domain.usecases.MoveLearningWordToNextLevelUseCase
import pro.yakuraion.englishhelper.domain.usecases.MoveLearningWordToPreviousLevelUseCase

class TestingViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getNextWordToLearnTodayUseCase: GetNextWordToLearnTodayUseCase,
    private val moveLearningWordToNextLevelUseCase: MoveLearningWordToNextLevelUseCase,
    private val moveLearningWordToPreviousLevelUseCase: MoveLearningWordToPreviousLevelUseCase
) : ViewModel() {

    var uiState: TestingUiState by mutableStateOf(TestingUiState.Loading)
        private set

    private var wordFull: LearningWordFull? = null

    private var isDictionaryVisited = false

    init {
        viewModelScope.launch {
            getNextWordToLearnTodayUseCase.getNextWordToLearnToday().collect { word ->
                wordFull = word
                isDictionaryVisited = false
                uiState = when {
                    word == null -> {
                        TestingUiState.NoMoreWords
                    }
                    word.word.soundFile != null -> {
                        TestingUiState.WordWithAudio(
                            word = word.word.name,
                            soundFile = word.word.soundFile!!,
                            linkUrl = word.word.wooordhuntUrl
                        )
                    }
                    else -> {
                        TestingUiState.WordSimple(
                            word = word.word.name,
                            linkUrl = word.word.wooordhuntUrl
                        )
                    }
                }
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
            }
        }
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<TestingViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): TestingViewModel
    }
}
