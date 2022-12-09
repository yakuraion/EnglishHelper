package pro.yakuraion.englishhelper.vocabulary.ui.listwords.wordspage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlinx.collections.immutable.ImmutableList
import pro.yakuraion.englishhelper.commonui.compose.widgets.layout.AppBottomSheetState
import pro.yakuraion.englishhelper.commonui.compose.widgets.layout.rememberAppBottomSheetState
import pro.yakuraion.englishhelper.vocabulary.ui.listwords.wordspage.content.ListWordsContentState
import pro.yakuraion.englishhelper.vocabulary.ui.listwords.wordspage.content.rememberListWordsContentState

class ListWordsPageState<W>(
    val contentState: ListWordsContentState<W>,
    val bottomSheetState: AppBottomSheetState
) {

    val selectedWords: List<W>
        get() = contentState.selectedWords

    fun selectWord(word: W) {
        contentState.select(word)
        updateBottomSheetState()
    }

    fun deselectWord(word: W) {
        contentState.deselect(word)
        updateBottomSheetState()
    }

    fun deselectAll() {
        contentState.deselectAll()
        bottomSheetState.collapse()
    }

    private fun updateBottomSheetState() {
        if (contentState.selectedWords.isNotEmpty()) {
            bottomSheetState.expand()
        } else {
            bottomSheetState.collapse()
        }
    }
}

@Composable
fun <W> rememberListWordsPageState(
    words: ImmutableList<W>,
    bottomSheetState: AppBottomSheetState = rememberAppBottomSheetState()
): ListWordsPageState<W> {
    val contentState = rememberListWordsContentState(words = words)
    return remember(contentState, bottomSheetState) {
        ListWordsPageState(contentState, bottomSheetState)
    }
}
