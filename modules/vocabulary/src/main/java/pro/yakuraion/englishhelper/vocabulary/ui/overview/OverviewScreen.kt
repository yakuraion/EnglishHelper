package pro.yakuraion.englishhelper.vocabulary.ui.overview

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import pro.yakuraion.englishhelper.common.ui.compose.FloatingButtonWithIconAndText
import pro.yakuraion.englishhelper.common.ui.theme.AppTheme
import pro.yakuraion.englishhelper.vocabulary.R

@Composable
fun OverviewScreen(
    onAddWordsClick: () -> Unit,
    onStartTestingClick: () -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingButtonWithIconAndText(
                icon = Icons.Filled.PlayArrow,
                text = stringResource(id = R.string.vocabulary_overview_screen_start_testing),
                onClick = { onStartTestingClick() }
            )
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun OverviewScreenPreview() {
    AppTheme {
        OverviewScreen(onAddWordsClick = {}, onStartTestingClick = {})
    }
}
