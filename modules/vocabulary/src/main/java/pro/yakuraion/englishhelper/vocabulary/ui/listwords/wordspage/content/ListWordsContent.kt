package pro.yakuraion.englishhelper.vocabulary.ui.listwords.wordspage.content

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

@Composable
fun <W> ListWordsContent(
    state: ListWordsContentState<W>,
    onWordSelect: (word: W, isSelect: Boolean) -> Unit,
    key: (index: Int, item: W) -> Any,
    wordContent: @Composable (W) -> Unit,
    emptyWordsContent: @Composable () -> Unit
) {
    if (state.words.isNotEmpty()) {
        Words(
            state = state,
            key = key,
            wordRowContent = wordContent,
            onWordSelect = onWordSelect
        )
    } else {
        emptyWordsContent()
    }
}

@Composable
private fun <W> Words(
    state: ListWordsContentState<W>,
    key: (index: Int, item: W) -> Any,
    wordRowContent: @Composable (W) -> Unit,
    onWordSelect: (word: W, isSelect: Boolean) -> Unit,
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
                isSelected = word in state.selectedWords,
                onSelect = { isSelect -> onWordSelect(word, isSelect) },
                content = wordRowContent
            )
            Divider()
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun <W> WordRow(
    word: W,
    isSelectionModeEnabled: Boolean,
    isSelected: Boolean,
    onSelect: (isSelect: Boolean) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (W) -> Unit,
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
