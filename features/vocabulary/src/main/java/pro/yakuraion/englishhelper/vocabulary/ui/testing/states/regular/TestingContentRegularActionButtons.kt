package pro.yakuraion.englishhelper.vocabulary.ui.testing.states.regular

import android.net.Uri
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.commonui.MediaPlayerUtils
import pro.yakuraion.englishhelper.commonui.openLink

@Composable
fun TestingContentRegularPlaySoundButton(
    soundUri: String,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    ActionButton(
        icon = Icons.Filled.VolumeUp,
        onClick = { MediaPlayerUtils.playSound(context, soundUri) },
        modifier = modifier
    )
}

@Composable
fun TestingContentRegularDictionaryButton(
    dictionaryUrl: String,
    onVisitedDictionary: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    ActionButton(
        icon = Icons.Default.Translate,
        onClick = {
            context.openLink(Uri.parse(dictionaryUrl))
            onVisitedDictionary()
        },
        modifier = modifier
    )
}

@Composable
private fun ActionButton(
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val iconSizeInPercent = 0.6f

    Button(
        onClick = onClick,
        modifier = modifier,
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(iconSizeInPercent)
        )
    }
}

object TestingContentRegularActionButtonsDefaults {

    val size = 60.dp
}
