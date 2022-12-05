package pro.yakuraion.englishhelper.commonui.compose.animation.content

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CounterAnimatedContent(
    targetState: Int,
    modifier: Modifier = Modifier,
    content: @Composable AnimatedVisibilityScope.(targetState: Int) -> Unit
) {
    AnimatedContent(
        targetState = targetState,
        modifier = modifier,
        transitionSpec = {
            if (targetState > initialState) {
                slideInVertically { height -> height / 2 } + fadeIn() with
                    slideOutVertically { height -> -height / 2 } + fadeOut()
            } else {
                slideInVertically { height -> -height / 2 } + fadeIn() with
                    slideOutVertically { height -> height / 2 } + fadeOut()
            }.using(
                SizeTransform(clip = false)
            )
        },
        content = content
    )
}
