package pro.yakuraion.englishhelper.vocabulary.ui.listwords.wordspage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import pro.yakuraion.englishhelper.vocabulary.ui.listwords.bottomsheet.ListWordsPageBottomSheet

@Composable
fun <WORD> ListWordsPage(
    state: ListWordsPageState<WORD>,
    key: (index: Int, item: WORD) -> Any,
    wordRowContent: @Composable (WORD) -> Unit,
    onWordSelect: (word: WORD, isSelect: Boolean) -> Unit,
    bottomSheetButtons: ImmutableList<@Composable () -> Unit>,
    onBottomSheetCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ListWordsPageBottomSheet(
        numberOfSelected = state.selectedWords.count(),
        bottomSheetState = state.bottomSheetState,
        bottomSheetButtons = bottomSheetButtons,
        onBottomSheetCloseClick = onBottomSheetCloseClick,
        modifier = modifier
    ) {
        Words(
            state = state,
            key = key,
            wordRowContent = wordRowContent,
            onWordSelect = onWordSelect
        )
    }
}

@Composable
private fun <WORD> Words(
    state: ListWordsPageState<WORD>,
    key: (index: Int, item: WORD) -> Any,
    wordRowContent: @Composable (WORD) -> Unit,
    onWordSelect: (word: WORD, isSelect: Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(
            items = state.words,
            key = key
        ) { _, word ->
            WordRow(
                word = word,
                isSelectionModeEnabled = state.isSelectionModeEnabled,
                isSelected = state.getIsSelected(word),
                onSelect = { isSelect -> onWordSelect(word, isSelect) },
                content = wordRowContent
            )
            Divider()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun <WORD> WordRow(
    word: WORD,
    isSelectionModeEnabled: Boolean,
    isSelected: Boolean,
    onSelect: (isSelect: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (WORD) -> Unit,
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.secondaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 60.dp)
            .background(backgroundColor)
            .combinedClickable(
                onClick = {
                    if (isSelectionModeEnabled) {
                        onSelect(!isSelected)
                    }
                },
                onLongClick = {
                    if (!isSelectionModeEnabled) {
                        onSelect(true)
                    }
                },
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        content(word)
    }
}
