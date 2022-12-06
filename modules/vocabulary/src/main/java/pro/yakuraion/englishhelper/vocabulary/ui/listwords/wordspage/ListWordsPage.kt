package pro.yakuraion.englishhelper.vocabulary.ui.listwords.wordspage

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import kotlinx.collections.immutable.ImmutableList
import pro.yakuraion.englishhelper.vocabulary.ui.listwords.wordspage.bottomsheet.ListWordsPageBottomSheet
import pro.yakuraion.englishhelper.vocabulary.ui.listwords.wordspage.content.ListWordsContent

@Composable
fun <W> ListWordsPage(
    state: ListWordsPageState<W>,
    key: (index: Int, item: W) -> Any,
    wordRowContent: @Composable (W) -> Unit,
    emptyWordsContent: @Composable () -> Unit,
    bottomSheetButtons: ImmutableList<@Composable () -> Unit>,
    modifier: Modifier = Modifier,
) {

    ListWordsPageBottomSheet(
        numberOfSelected = state.selectedWords.count(),
        bottomSheetState = state.bottomSheetState,
        bottomSheetButtons = bottomSheetButtons,
        onBottomSheetCloseClick = { state.deselectAll() },
        modifier = modifier
    ) {
        ListWordsContent(
            state = state.contentState,
            onWordSelect = { word, isSelect ->
                if (isSelect) {
                    state.selectWord(word)
                } else {
                    state.deselectWord(word)
                }
            },
            key = key,
            wordContent = wordRowContent,
            emptyWordsContent = emptyWordsContent
        )
    }
}
