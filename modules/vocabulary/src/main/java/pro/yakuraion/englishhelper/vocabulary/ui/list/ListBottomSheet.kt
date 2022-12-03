package pro.yakuraion.englishhelper.vocabulary.ui.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList
import pro.yakuraion.englishhelper.commonui.compose.widgets.layout.AppBottomSheet
import pro.yakuraion.englishhelper.commonui.compose.widgets.layout.AppBottomSheetState
import pro.yakuraion.englishhelper.vocabulary.R

@Composable
fun ListBottomSheet(
    numberOfSelected: Int,
    bottomSheetState: AppBottomSheetState,
    bottomSheetButtons: ImmutableList<@Composable () -> Unit>,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    AppBottomSheet(
        bottomSheet = {
            ListBottomSheetContent(
                numberOfSelected = numberOfSelected,
                buttons = bottomSheetButtons,
                onCloseClick = { bottomSheetState.collapse() }
            )
        },
        bottomSheetHeight = 180.dp,
        state = bottomSheetState,
        modifier = modifier
    ) {
        content()
    }
}

@Composable
fun ListBottomSheetContent(
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
            Text(
                text = stringResource(
                    R.string.vocabulary_list_screen_bottom_sheet_number_of_selected_words,
                    numberOfSelected
                ),
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

@Composable
fun ListBottomSheetButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = MaterialTheme.shapes.small
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = icon, contentDescription = null)
            Text(text = text)
        }
    }
}
