package pro.yakuraion.englishhelper.commonui.compose.widgets

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme

@OptIn(ExperimentalPagerApi::class)
@Composable
fun AppPagerWithTabs(
    count: Int,
    title: @Composable (Int) -> String,
    modifier: Modifier = Modifier,
    content: @Composable (Int) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        val coroutineScope = rememberCoroutineScope()
        val pagerState = rememberPagerState()

        AppTabRow(selectedTabIndex = pagerState.currentPage) {
            (0 until count).forEach { page ->
                AppTab(
                    selected = pagerState.currentPage == page,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page)
                        }
                    },
                    text = title(page)
                )
            }
        }

        HorizontalPager(
            count = count,
            modifier = Modifier.weight(1f),
            state = pagerState
        ) { page ->
            content(page)
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppPagerWithTabsPreview() {
    AppTheme {
        AppPagerWithTabs(
            count = 3,
            title = { "Title $it" }
        ) { page ->
            Box(modifier = Modifier.fillMaxSize()) {
                Text(
                    text = "Page $page",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}
