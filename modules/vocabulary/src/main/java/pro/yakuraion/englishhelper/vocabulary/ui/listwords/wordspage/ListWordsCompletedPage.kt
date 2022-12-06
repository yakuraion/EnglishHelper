package pro.yakuraion.englishhelper.vocabulary.ui.listwords.wordspage

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.domain.entities.CompletedWord
import pro.yakuraion.englishhelper.vocabulary.R
import pro.yakuraion.englishhelper.vocabulary.ui.listwords.wordspage.bottomsheet.ListWordsPageBottomSheetButton

@Composable
fun ListWordsCompletedPage(
    state: ListWordsPageState<CompletedWord>,
    onResetCompletedWords: (List<CompletedWord>) -> Unit,
    onDeleteCompletedWords: (List<CompletedWord>) -> Unit,
    modifier: Modifier = Modifier
) {
    ListWordsPage(
        state = state,
        key = { _, item -> item.name },
        wordRowContent = { word -> WordRowContent(word = word) },
        emptyWordsContent = { EmptyWords() },
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
        modifier = modifier,
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
private fun EmptyWords(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = stringResource(id = R.string.vocabulary_list_words_screen_tab_completed_empty),
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun BottomSheetLearnAgainButton(
    onClick: () -> Unit
) {
    ListWordsPageBottomSheetButton(
        text = stringResource(id = R.string.vocabulary_list_words_screen_bottom_sheet_learn_again_button),
        icon = Icons.Default.Refresh,
        onClick = onClick
    )
}

@Composable
private fun BottomSheetDeleteButton(
    onClick: () -> Unit
) {
    ListWordsPageBottomSheetButton(
        text = stringResource(id = R.string.vocabulary_list_words_screen_bottom_sheet_delete_button),
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
        val words = List(30) { index ->
            CompletedWord(
                name = "word $index"
            )
        }.toPersistentList()

        ListWordsCompletedPage(
            state = rememberListWordsPageState(words = words),
            onResetCompletedWords = {},
            onDeleteCompletedWords = {}
        )
    }
}

@Suppress("MagicNumber")
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ListWordsCompletedPageEmptyPreview() {
    AppTheme {
        ListWordsCompletedPage(
            state = rememberListWordsPageState(words = persistentListOf()),
            onResetCompletedWords = {},
            onDeleteCompletedWords = {}
        )
    }
}
