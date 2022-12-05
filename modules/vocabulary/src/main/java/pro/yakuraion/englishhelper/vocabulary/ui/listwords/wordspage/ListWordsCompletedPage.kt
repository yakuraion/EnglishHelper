package pro.yakuraion.englishhelper.vocabulary.ui.listwords.wordspage

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.domain.entities.CompletedWord
import pro.yakuraion.englishhelper.vocabulary.R
import pro.yakuraion.englishhelper.vocabulary.ui.listwords.bottomsheet.ListWordsPageBottomSheetButton

@Composable
fun ListWordsCompletedPage(
    words: ImmutableList<CompletedWord>,
    onResetCompletedWords: (List<CompletedWord>) -> Unit,
    onDeleteCompletedWords: (List<CompletedWord>) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberListWordsState(words = words)

    ListWordsPage(
        state = state,
        key = { _, item -> item.name },
        wordRowContent = { word -> WordRowContent(word = word) },
        onWordSelect = { word, isSelect -> if (isSelect) state.select(word) else state.deselect(word) },
        bottomSheetButtons = persistentListOf(
            {
                BottomSheetLearnAgainButton(
                    onClick = { onResetCompletedWords(state.selectedWords) }
                )
            },
            {
                BottomSheetDeleteButton(
                    onClick = { onDeleteCompletedWords(state.selectedWords) }
                )
            }
        ),
        onBottomSheetCloseClick = { state.deselectAll() },
        modifier = modifier
    )
}

@Composable
private fun WordRowContent(
    word: CompletedWord,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        WordNameText(
            name = word.name,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun WordNameText(
    name: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = name,
        modifier = modifier,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun BottomSheetLearnAgainButton(
    onClick: () -> Unit
) {
    ListWordsPageBottomSheetButton(
        text = stringResource(id = R.string.vocabulary_list_screen_bottom_sheet_learn_again_button),
        icon = Icons.Default.Refresh,
        onClick = onClick
    )
}

@Composable
private fun BottomSheetDeleteButton(
    onClick: () -> Unit
) {
    ListWordsPageBottomSheetButton(
        text = stringResource(id = R.string.vocabulary_list_screen_bottom_sheet_delete_button),
        icon = Icons.Default.Delete,
        onClick = onClick
    )
}

@Suppress("MagicNumber")
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ListWordsCompletedPagePreview() {
    AppTheme {
        ListWordsCompletedPage(
            words = List(30) { index ->
                CompletedWord(
                    name = "word $index"
                )
            }.toPersistentList(),
            onResetCompletedWords = {},
            onDeleteCompletedWords = {}
        )
    }
}
