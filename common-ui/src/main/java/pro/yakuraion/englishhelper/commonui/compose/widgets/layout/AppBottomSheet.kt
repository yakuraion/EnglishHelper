package pro.yakuraion.englishhelper.commonui.compose.widgets.layout

import android.content.res.Configuration
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme

@Composable
fun AppBottomSheet(
    bottomSheet: @Composable () -> Unit,
    bottomSheetHeight: Dp,
    state: AppBottomSheetState,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val transition = updateTransition(
        targetState = state.value,
        label = "Bottom sheet animation"
    )

    val contentBottomPadding by transition.animateDp(label = "Content bottom padding Transition") { value ->
        when (value) {
            AppBottomSheetState.Value.EXPANDED -> bottomSheetHeight
            AppBottomSheetState.Value.COLLAPSED -> 0.dp
        }
    }

    val bottomSheetOffset by transition.animateDp(label = "Bottom sheet offset Transition") { value ->
        when (value) {
            AppBottomSheetState.Value.EXPANDED -> 0.dp
            AppBottomSheetState.Value.COLLAPSED -> bottomSheetHeight
        }
    }

    Box(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = contentBottomPadding)
        ) {
            content()
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(bottomSheetHeight)
                .align(Alignment.BottomCenter)
                .offset(y = bottomSheetOffset)
        ) {
            Divider()
            bottomSheet()
        }
    }
}

class AppBottomSheetState(initialState: Value) {

    var value: Value by mutableStateOf(initialState)
        private set

    val isExpanded: Boolean
        get() = value == Value.EXPANDED

    val isCollapsed: Boolean
        get() = value == Value.COLLAPSED

    enum class Value {
        COLLAPSED,
        EXPANDED
    }

    fun collapse() {
        value = Value.COLLAPSED
    }

    fun expand() {
        value = Value.EXPANDED
    }
}

@Composable
fun rememberAppBottomSheetState(
    initialState: AppBottomSheetState.Value = AppBottomSheetState.Value.COLLAPSED
): AppBottomSheetState = remember {
    AppBottomSheetState(initialState)
}

@Suppress("MagicNumber")
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppBottomSheetPreview() {
    AppTheme {
        val state = rememberAppBottomSheetState()
        AppBottomSheet(
            bottomSheet = {
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                )
            },
            bottomSheetHeight = 200.dp,
            state = state
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(50) { index ->
                    Text(
                        text = "row $index",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (state.isCollapsed) {
                                    state.expand()
                                } else {
                                    state.collapse()
                                }
                            }
                    )
                }
            }
        }
    }
}
