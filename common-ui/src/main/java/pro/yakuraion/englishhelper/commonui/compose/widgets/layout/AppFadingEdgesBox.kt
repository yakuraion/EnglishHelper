package pro.yakuraion.englishhelper.commonui.compose.widgets.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// todo: optimize
@Composable
fun AppFadingEdgesBox(
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    leftFade: Dp = 0.dp,
    topFade: Dp = 0.dp,
    rightFade: Dp = 0.dp,
    bottomFade: Dp = 0.dp,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .drawWithContent {
                drawContent()

                if (leftFade != 0.dp) {
                    drawRect(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                backgroundColor,
                                Color.Transparent
                            ),
                            endX = leftFade.toPx()
                        ),
                        size = size.copy(width = leftFade.toPx())
                    )
                }

                if (topFade != 0.dp) {
                    drawRect(
                        Brush.verticalGradient(
                            colors = listOf(
                                backgroundColor,
                                Color.Transparent
                            ),
                            endY = topFade.toPx()
                        ),
                        size = size.copy(height = topFade.toPx())
                    )
                }

                if (rightFade != 0.dp) {
                    drawRect(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                backgroundColor,
                            ),
                            startX = size.width - rightFade.toPx()
                        ),
                        topLeft = Offset(size.width - rightFade.toPx(), 0f),
                        size = size.copy(width = rightFade.toPx())
                    )
                }

                if (bottomFade != 0.dp) {
                    drawRect(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                backgroundColor
                            ),
                            startY = size.height - bottomFade.toPx()
                        ),
                        topLeft = Offset(0f, size.height - bottomFade.toPx()),
                        size = size.copy(height = bottomFade.toPx())
                    )
                }
            },
        content = content
    )
}

@Suppress("MagicNumber")
@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.background(Color.White)) {
        AppFadingEdgesBox(
            backgroundColor = Color.White,
            bottomFade = 30.dp
        ) {
            Text(text = "Some text\n".repeat(5).trim())
        }
    }
}
