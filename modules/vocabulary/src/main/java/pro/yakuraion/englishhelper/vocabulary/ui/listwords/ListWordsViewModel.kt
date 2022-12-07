package pro.yakuraion.englishhelper.vocabulary.ui.listwords

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import pro.yakuraion.englishhelper.commonui.di.viewmodel.AssistedSavedStateViewModelFactory
import pro.yakuraion.englishhelper.domain.entities.CompletedWord
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.usecases.DeleteWordUseCase
import pro.yakuraion.englishhelper.domain.usecases.GetCompletedWordsUseCase
import pro.yakuraion.englishhelper.domain.usecases.GetLearningWordsUseCase
import pro.yakuraion.englishhelper.domain.usecases.LearnCompletedWordAgainUseCase
import pro.yakuraion.englishhelper.domain.usecases.ResetLearningWordProgressUseCase

class ListWordsViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getLearningWordsUseCase: GetLearningWordsUseCase,
    private val getCompletedWordsUseCase: GetCompletedWordsUseCase,
    private val deleteWordUseCase: DeleteWordUseCase,
    private val resetLearningWordProgressUseCase: ResetLearningWordProgressUseCase,
    private val learnCompletedWordAgainUseCase: LearnCompletedWordAgainUseCase,
) : ViewModel() {

    var inProgressWords: ImmutableList<LearningWord> by mutableStateOf(persistentListOf())
        private set

    var completedWords: ImmutableList<CompletedWord> by mutableStateOf(persistentListOf())
        private set

    init {
        viewModelScope.launch {
            getLearningWordsUseCase.getLearningWords().collect { words ->
                inProgressWords = words.toImmutableList()
            }

            getCompletedWordsUseCase.getCompletedWords().collect { words ->
                completedWords = words.toImmutableList()
            }
        }
    }

    fun onResetLearningWords(words: List<LearningWord>) {
        viewModelScope.launch {
            words.forEach { word ->
                resetLearningWordProgressUseCase.resetLearningWordProgress(word)
            }
        }
    }

    fun onResetCompletedWords(words: List<CompletedWord>) {
        viewModelScope.launch {
            words.forEach { word ->
                learnCompletedWordAgainUseCase.learnCompletedWordAgain(word)
            }
        }
    }

    fun onDeleteLearningWords(words: List<LearningWord>) {
        deleteWords(words.map { it.name })
    }

    fun onDeleteCompletedWords(words: List<CompletedWord>) {
        deleteWords(words.map { it.name })
    }

    private fun deleteWords(names: List<String>) {
        viewModelScope.launch {
            names.forEach { name ->
                deleteWordUseCase.deleteWord(name)
            }
        }
    }

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<ListWordsViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): ListWordsViewModel
    }
}
