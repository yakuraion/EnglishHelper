package pro.yakuraion.englishhelper.commonui.compose.widgets.buttons

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import androidx.compose.material3.OutlinedButton as MaterialOutlinedButton

@Composable
fun AppOutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    leadingIcon: @Composable (() -> Unit)? = null
) {
    MaterialOutlinedButton(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = color
        ),
        border = BorderStroke(2.dp, color)
    ) {
        leadingIcon?.let { leadingIcon ->
            leadingIcon()
            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
        }
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppOutlinedButtonPreview() {
    AppTheme {
        AppOutlinedButton(
            text = "Text",
            onClick = {}
        )
    }
}
