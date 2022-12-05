package pro.yakuraion.englishhelper.vocabulary.ui.listwords.bottomsheet

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ThumbDown
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import pro.yakuraion.englishhelper.commonui.compose.animation.content.CounterAnimatedContent
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.commonui.compose.widgets.layout.AppBottomSheet
import pro.yakuraion.englishhelper.commonui.compose.widgets.layout.AppBottomSheetState
import pro.yakuraion.englishhelper.commonui.compose.widgets.layout.rememberAppBottomSheetState
import pro.yakuraion.englishhelper.vocabulary.R

@Composable
fun ListWordsPageBottomSheet(
    numberOfSelected: Int,
    bottomSheetState: AppBottomSheetState,
    bottomSheetButtons: ImmutableList<@Composable () -> Unit>,
    onBottomSheetCloseClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AppBottomSheet(
        bottomSheet = {
            BottomSheetContent(
                numberOfSelected = numberOfSelected,
                buttons = bottomSheetButtons,
                onCloseClick = onBottomSheetCloseClick
            )
        },
        bottomSheetHeight = 180.dp,
        state = bottomSheetState,
        modifier = modifier
    ) {
        content()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun BottomSheetContent(
    numberOfSelected: Int,
    buttons: ImmutableList<@Composable () -> Unit>,
    onCloseClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp, end = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CounterAnimatedContent(targetState = numberOfSelected) { targetCount ->
                Text(text = targetCount.toString())
            }
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = stringResource(R.string.vocabulary_list_words_screen_bottom_sheet_number_of_selected_words),
                modifier = Modifier.weight(1f)
            )

            IconButton(onClick = onCloseClick) {
                Icon(imageVector = Icons.Default.Close, contentDescription = null)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomStart)
        ) {
            buttons.forEachIndexed { index, button ->
                Box(modifier = Modifier.weight(1f)) {
                    button()
                }
                if (index != buttons.lastIndex) {
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ListWordsPageBottomSheetPreview() {
    AppTheme {
        Scaffold {
            val state = rememberAppBottomSheetState(AppBottomSheetState.Value.EXPANDED)

            ListWordsPageBottomSheet(
                numberOfSelected = 10,
                bottomSheetState = state,
                bottomSheetButtons = persistentListOf(
                    {
                        ListWordsPageBottomSheetButton(
                            text = "Button 1",
                            icon = Icons.Default.ThumbUp,
                            onClick = { }
                        )
                    },
                    {
                        ListWordsPageBottomSheetButton(
                            text = "Button 2",
                            icon = Icons.Default.ThumbDown,
                            onClick = { }
                        )
                    }
                ),
                onBottomSheetCloseClick = {}
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Content")
                }
            }
        }
    }
}
