package pro.yakuraion.englishhelper.vocabulary.ui.overview.cards

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.vocabulary.R

@Composable
fun OverviewWordsTotalCard(
    numberOfInProgressWords: Int,
    numberOfCompletedWords: Int,
    modifier: Modifier = Modifier
) {
    OverviewCard(modifier = modifier) {
        Column {
            Text(
                text = stringResource(id = R.string.vocabulary_overview_screen_words_total_title),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                NumberText(
                    titleText = stringResource(R.string.vocabulary_overview_screen_words_total_in_progress),
                    numberText = numberOfInProgressWords,
                    modifier = Modifier.weight(1f)
                )
                NumberText(
                    titleText = stringResource(R.string.vocabulary_overview_screen_words_total_completed),
                    numberText = numberOfCompletedWords,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun NumberText(
    titleText: String,
    numberText: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = titleText,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = numberText.toString(),
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OverviewWordsTotalCardPreview() {
    AppTheme {
        Scaffold {
            OverviewWordsTotalCard(
                numberOfInProgressWords = 10,
                numberOfCompletedWords = 20
            )
        }
    }
}
