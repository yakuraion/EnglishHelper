package pro.yakuraion.englishhelper.vocabulary.ui.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.domain.entities.CompletedWord

@Composable
fun CompletedWords(
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
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
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
    NameText(name = word.name, modifier = modifier)
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

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CompletedWordsPreview() {
    AppTheme {
        CompletedWords(
            words = persistentListOf(
                CompletedWord(
                    name = "word 1"
                ),
                CompletedWord(
                    name = "word 2"
                ),
                CompletedWord(
                    name = "word 3"
                ),
            )
        )
    }
}
