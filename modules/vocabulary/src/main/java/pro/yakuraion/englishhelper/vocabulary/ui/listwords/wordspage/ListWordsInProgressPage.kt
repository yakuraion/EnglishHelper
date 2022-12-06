package pro.yakuraion.englishhelper.vocabulary.ui.listwords.wordspage

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.entities.learning.MemorizationLevel
import pro.yakuraion.englishhelper.vocabulary.R
import pro.yakuraion.englishhelper.vocabulary.ui.listwords.bottomsheet.ListWordsPageBottomSheetButton

@Composable
fun ListWordsInProgressPage(
    words: ImmutableList<LearningWord>,
    onResetInProgressWords: (List<LearningWord>) -> Unit,
    onDeleteInProgressWords: (List<LearningWord>) -> Unit,
    modifier: Modifier = Modifier
) {
    val state = rememberListWordsState(words = words)

    ListWordsPage(
        state = state,
        key = { _, item -> item.name },
        wordRowContent = { word -> WordRowContent(word = word) },
        onWordSelect = { word, isSelect -> if (isSelect) state.select(word) else state.deselect(word) },
        emptyWordsContent = { EmptyWords() },
        bottomSheetButtons = persistentListOf(
            {
                BottomSheetResetButton(
                    onClick = { onResetInProgressWords(state.selectedWords) }
                )
            },
            {
                BottomSheetDeleteButton(
                    onClick = { onDeleteInProgressWords(state.selectedWords) }
                )
            }
        ),
        onBottomSheetCloseClick = { state.deselectAll() },
        modifier = modifier
    )
}

@Composable
private fun WordRowContent(
    word: LearningWord,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        WordNameText(
            name = word.name,
            modifier = Modifier.weight(1f)
        )
        WordLevel(
            level = word.memorizationLevel.level
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
private fun WordLevel(
    level: Int,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.tertiary) {
        if (level == 0) {
            WordLevelNew(modifier = modifier)
        } else {
            WordLevelStars(level = level, modifier = modifier)
        }
    }
}

@Composable
private fun WordLevelNew(
    modifier: Modifier = Modifier
) {
    Text(
        text = stringResource(id = R.string.vocabulary_list_words_screen_word_level_new),
        modifier = modifier,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
private fun WordLevelStars(
    level: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        repeat(level) {
            WordLevelStar(enabled = true)
        }
        repeat(MemorizationLevel.MAX_LEVEL - level) {
            WordLevelStar(enabled = false)
        }
    }
}

@Composable
private fun WordLevelStar(enabled: Boolean) {
    val enabledAlpha = 0.8f
    val disableAlpha = 0.4f

    Icon(
        imageVector = if (enabled) Icons.Default.Star else Icons.Default.StarOutline,
        contentDescription = null,
        modifier = Modifier.alpha(if (enabled) enabledAlpha else disableAlpha)
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
            text = stringResource(id = R.string.vocabulary_list_words_screen_tab_in_progress_empty),
            modifier = Modifier.align(Alignment.Center),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun BottomSheetResetButton(
    onClick: () -> Unit
) {
    ListWordsPageBottomSheetButton(
        text = stringResource(id = R.string.vocabulary_list_words_screen_bottom_sheet_reset_progress_button),
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
private fun ListWordsInProgressPagePreview() {
    AppTheme {
        ListWordsInProgressPage(
            words = List(30) { index ->
                LearningWord(
                    name = "word $index",
                    memorizationLevel = MemorizationLevel(index % 4),
                    nextDayToLearn = 0
                )
            }.toPersistentList(),
            onResetInProgressWords = {},
            onDeleteInProgressWords = {}
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ListWordsInProgressPageEmptyPreview() {
    AppTheme {
        ListWordsInProgressPage(
            words = persistentListOf(),
            onResetInProgressWords = {},
            onDeleteInProgressWords = {}
        )
    }
}
