package pro.yakuraion.englishhelper.vocabulary.ui.list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import pro.yakuraion.englishhelper.commonui.compose.widgets.layout.rememberAppBottomSheetState
import pro.yakuraion.englishhelper.domain.entities.CompletedWord
import pro.yakuraion.englishhelper.vocabulary.R

@Composable
fun ListCompleted(
    words: ImmutableList<CompletedWord>,
    modifier: Modifier = Modifier
) {
    val bottomSheetState = rememberAppBottomSheetState()
    ListBottomSheet(
        numberOfSelected = 10,
        bottomSheetState = bottomSheetState,
        bottomSheetButtons = persistentListOf(
            { BottomSheetLearnAgainButton(onClick = {}) },
            { BottomSheetDeleteButton(onClick = {}) }
        ),
        modifier = modifier
    ) {
        Words(words = words)
    }
}

@Composable
private fun Words(
    words: ImmutableList<CompletedWord>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        itemsIndexed(
            items = words,
            key = { _, item -> item.name }
        ) { index, word ->
            WordRow(
                word = word,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            if (index != words.lastIndex) {
                Divider()
            }
        }
    }
}

@Composable
private fun WordRow(
    word: CompletedWord,
    modifier: Modifier = Modifier
) {
    WordNameText(name = word.name, modifier = modifier)
}

@Composable
private fun WordNameText(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        modifier = modifier,
        color = MaterialTheme.colorScheme.secondary,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun BottomSheetLearnAgainButton(
    onClick: () -> Unit
) {
    ListBottomSheetButton(
        text = stringResource(id = R.string.vocabulary_list_screen_bottom_sheet_learn_again_button),
        icon = Icons.Default.Refresh,
        onClick = onClick
    )
}

@Composable
private fun BottomSheetDeleteButton(
    onClick: () -> Unit
) {
    ListBottomSheetButton(
        text = stringResource(id = R.string.vocabulary_list_screen_bottom_sheet_delete_button),
        icon = Icons.Default.Delete,
        onClick = onClick
    )
}
