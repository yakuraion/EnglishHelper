package pro.yakuraion.englishhelper.vocabulary.ui.list

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.commonui.compose.widgets.AppPagerWithTabs
import pro.yakuraion.englishhelper.commonui.compose.widgets.buttons.AppArrowBackButton
import pro.yakuraion.englishhelper.vocabulary.R
import pro.yakuraion.englishhelper.vocabulary.di.viewmodel.daggerViewModel

@Composable
fun ListScreen(
    viewModel: ListViewModel = daggerViewModel(),
    onBackClick: () -> Unit
) {
    ListScreen(onBackClick = onBackClick)
}

@Composable
private fun ListScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = { TopBar(onBackClick = onBackClick) }
    ) { paddingValues ->
        AppPagerWithTabs(
            count = Page.values().count(),
            title = { Page.values()[it].title() },
            modifier = Modifier.padding(paddingValues)
        ) { pageNumber ->
            Box(modifier = Modifier.fillMaxSize()) {
                when (Page.values()[pageNumber]) {
                    Page.IN_PROGRESS -> {
                        Text(
                            text = "In progress",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                    Page.COMPLETED -> {
                        Text(
                            text = "Completed",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TopBar(onBackClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(id = R.string.vocabulary_testing_screen_title))
        },
        navigationIcon = {
            AppArrowBackButton(onBackClick = onBackClick)
        }
    )
}

private enum class Page {
    IN_PROGRESS {

        @Composable
        override fun title(): String = stringResource(id = R.string.vocabulary_list_screen_tab_in_progress_title)
    },

    COMPLETED {

        @Composable
        override fun title(): String = stringResource(id = R.string.vocabulary_list_screen_tab_completed_title)
    };

    @Composable
    abstract fun title(): String
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ListScreenPreview() {
    AppTheme {
        ListScreen(onBackClick = {})
    }
}
