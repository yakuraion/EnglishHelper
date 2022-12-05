package pro.yakuraion.englishhelper.vocabulary.ui.listwords.wordspage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.ImmutableList
import pro.yakuraion.englishhelper.commonui.compose.widgets.layout.AppBottomSheetState
import pro.yakuraion.englishhelper.commonui.compose.widgets.layout.rememberAppBottomSheetState

class ListWordsPageState<WORD>(
    val words: ImmutableList<WORD>,
    val bottomSheetState: AppBottomSheetState
) {

    private val _selectedWords = mutableStateListOf<WORD>()
    val selectedWords: List<WORD>
        get() = _selectedWords

    val isSelectionModeEnabled: Boolean
        get() = _selectedWords.isNotEmpty()

    fun getIsSelected(word: WORD) = _selectedWords.contains(word)

    fun select(word: WORD) {
        if (!_selectedWords.contains(word)) {
            _selectedWords.add(word)
        }
        updateBottomSheetState()
    }

    fun deselect(word: WORD) {
        if (_selectedWords.contains(word)) {
            _selectedWords.remove(word)
        }
        updateBottomSheetState()
    }

    fun deselectAll() {
        _selectedWords.clear()
        updateBottomSheetState()
    }

    private fun updateBottomSheetState() {
        if (isSelectionModeEnabled) {
            bottomSheetState.expand()
        } else {
            bottomSheetState.collapse()
        }
    }
}

@Composable
fun <WORD> rememberListWordsState(
    words: ImmutableList<WORD>,
    bottomSheetState: AppBottomSheetState = rememberAppBottomSheetState()
) = remember(words, bottomSheetState) {
    ListWordsPageState(words, bottomSheetState)
}
