package pro.yakuraion.englishhelper.vocabulary.ui.overview.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.common.applyIf

@Composable
fun OverviewCard(
    modifier: Modifier = Modifier,
    activeContainerColor: Color = MaterialTheme.colorScheme.secondaryContainer,
    activeContentColor: Color = MaterialTheme.colorScheme.onSecondaryContainer,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val (containerColor, contentColor) = if (onClick != null) {
        activeContainerColor to activeContentColor
    } else {
        MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(color = containerColor)
            .applyIf(onClick != null) {
                clickable { onClick?.invoke() }
            }
            .padding(16.dp)
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            content()
        }
    }
}
