package pro.yakuraion.englishhelper.commonui.compose.widgets.buttons

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun AppButtonWithText(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Medium,
            style = MaterialTheme.typography.titleSmall
        )
    }
}
