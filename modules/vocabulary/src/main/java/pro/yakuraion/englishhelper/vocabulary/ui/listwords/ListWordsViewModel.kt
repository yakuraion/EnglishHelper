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
import pro.yakuraion.englishhelper.domain.usecases.GetCompletedWordsUseCase
import pro.yakuraion.englishhelper.domain.usecases.GetLearningWordsUseCase

class ListWordsViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val getLearningWordsUseCase: GetLearningWordsUseCase,
    private val getCompletedWordsUseCase: GetCompletedWordsUseCase
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

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<ListWordsViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): ListWordsViewModel
    }
}
