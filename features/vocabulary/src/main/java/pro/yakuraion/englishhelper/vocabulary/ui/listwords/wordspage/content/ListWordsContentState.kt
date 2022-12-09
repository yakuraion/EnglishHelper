package pro.yakuraion.englishhelper.vocabulary.ui.listwords.wordspage.content

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.ImmutableList
import timber.log.Timber

class ListWordsContentState<W>(
    val words: ImmutableList<W>
) {

    private val _selectedWords = mutableStateListOf<W>()
    val selectedWords: List<W>
        get() = _selectedWords

    val isSelectionModeEnabled: Boolean
        get() = _selectedWords.isNotEmpty()

    fun select(word: W) {
        if (!_selectedWords.contains(word)) {
            _selectedWords.add(word)
        }
    }

    fun deselect(word: W) {
        if (_selectedWords.contains(word)) {
            _selectedWords.remove(word)
        }
    }

    fun deselectAll() {
        _selectedWords.clear()
    }
}

@Composable
fun <W> rememberListWordsContentState(words: ImmutableList<W>) = remember(words) {
    Timber.tag("kek").d("new ListWordsContentState")
    ListWordsContentState(words)
}
