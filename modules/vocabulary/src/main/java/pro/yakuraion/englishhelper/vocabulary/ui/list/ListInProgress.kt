package pro.yakuraion.englishhelper.vocabulary.ui.list

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import pro.yakuraion.englishhelper.commonui.compose.widgets.layout.rememberAppBottomSheetState
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.entities.learning.MemorizationLevel
import pro.yakuraion.englishhelper.vocabulary.R

@Composable
fun ListInProgress(
    words: ImmutableList<LearningWord>,
    modifier: Modifier = Modifier
) {
    val bottomSheetState = rememberAppBottomSheetState()
    ListBottomSheet(
        numberOfSelected = 10,
        bottomSheetState = bottomSheetState,
        bottomSheetButtons = persistentListOf(
            { BottomSheetResetButton(onClick = {}) },
            { BottomSheetDeleteButton(onClick = {}) }
        ),
        modifier = modifier
    ) {
        Words(words = words)
    }
}

@Composable
private fun Words(
    words: ImmutableList<LearningWord>,
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
    word: LearningWord,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.fillMaxWidth()) {
        WordNameText(
            name = word.name,
            modifier = Modifier.weight(1f)
        )
        WordLevelView(
            currentLevel = word.memorizationLevel.level
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
        color = MaterialTheme.colorScheme.secondary,
        overflow = TextOverflow.Ellipsis,
        maxLines = 2,
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun WordLevelView(
    currentLevel: Int,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        repeat(currentLevel) {
            WordLevelStar(enabled = true)
        }
        repeat(MemorizationLevel.MAX_LEVEL - currentLevel) {
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
        modifier = Modifier.alpha(if (enabled) enabledAlpha else disableAlpha),
        tint = MaterialTheme.colorScheme.tertiary
    )
}

@Composable
private fun BottomSheetResetButton(
    onClick: () -> Unit
) {
    ListBottomSheetButton(
        text = stringResource(id = R.string.vocabulary_list_screen_bottom_sheet_reset_progress_button),
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
