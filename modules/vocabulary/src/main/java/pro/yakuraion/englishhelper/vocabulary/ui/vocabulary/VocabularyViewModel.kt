package pro.yakuraion.englishhelper.vocabulary.ui.vocabulary

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import pro.yakuraion.englishhelper.common.di.viewmodel.AssistedSavedStateViewModelFactory
import pro.yakuraion.englishhelper.vocabulary.data.daos.WordsDao

class VocabularyViewModel @AssistedInject constructor(
    @Assisted private val savedStateHandle: SavedStateHandle,
    private val wordsDao: WordsDao
) : ViewModel() {

    val message = "hello from viewmodel"

    @AssistedFactory
    interface Factory : AssistedSavedStateViewModelFactory<VocabularyViewModel> {

        override fun create(savedStateHandle: SavedStateHandle): VocabularyViewModel
    }
}
