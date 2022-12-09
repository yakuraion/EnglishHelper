package pro.yakuraion.englishhelper.vocabulary.ui.overview.cards

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.vocabulary.R

@Composable
fun OverviewTestingCard(
    wordsNumber: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OverviewCard(
        modifier = modifier,
        activeContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
        activeContentColor = MaterialTheme.colorScheme.onTertiaryContainer,
        onClick = onClick
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.vocabulary_overview_screen_testing_title),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = wordsNumber.toString(),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                fontWeight = FontWeight.Medium,
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}

@Composable
fun OverviewEmptyTestingCard(
    modifier: Modifier = Modifier
) {
    OverviewCard(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.vocabulary_overview_screen_empty_testing_title),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OverviewTestingCardPreview() {
    AppTheme {
        Scaffold {
            OverviewTestingCard(
                wordsNumber = 10,
                onClick = {}
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OverviewEmptyTestingCardPreview() {
    AppTheme {
        Scaffold {
            OverviewEmptyTestingCard()
        }
    }
}
