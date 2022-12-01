package pro.yakuraion.englishhelper.vocabulary.ui.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarOutline
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.entities.learning.MemorizationLevel

@Composable
fun InProgressWords(
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
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            if (index != words.lastIndex) {
                Divider(modifier = Modifier.padding(vertical = 8.dp))
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
        NameText(
            name = word.name,
            modifier = Modifier.weight(1f)
        )
        WordLevelView(
            currentLevel = word.memorizationLevel.level
        )
    }
}

@Composable
private fun NameText(
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
    Icon(
        imageVector = if (enabled) Icons.Default.Star else Icons.Default.StarOutline,
        contentDescription = null,
        modifier = Modifier.alpha(if (enabled) 0.6f else 0.3f),
        tint = MaterialTheme.colorScheme.tertiary
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun InProgressWordsPreview() {
    AppTheme {
        InProgressWords(
            words = persistentListOf(
                LearningWord(
                    name = "word 1",
                    memorizationLevel = MemorizationLevel(0),
                    nextDayToLearn = 0
                ),
                LearningWord(
                    name = "word 2",
                    memorizationLevel = MemorizationLevel(1),
                    nextDayToLearn = 1
                ),
                LearningWord(
                    name = "word 3",
                    memorizationLevel = MemorizationLevel(2),
                    nextDayToLearn = 2
                ),
            )
        )
    }
}
