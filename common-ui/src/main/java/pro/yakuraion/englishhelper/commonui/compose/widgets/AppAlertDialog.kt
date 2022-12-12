package pro.yakuraion.englishhelper.commonui.compose.widgets

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.commonui.R
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme

@Composable
fun AppAlertDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    body: @Composable (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        modifier = modifier,
        dismissButton = dismissButton,
        title = title,
        text = body
    )
}

object AppAlertDialogDefaults {

    private val ButtonPadding = 4.dp

    @Composable
    fun ConfirmButton(
        onConfirm: () -> Unit,
        text: String = stringResource(id = R.string.alert_dialog_default_confirm_button_text)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .clickable { onConfirm() }
                .padding(ButtonPadding)
        )
    }

    @Composable
    fun DismissButton(
        onDismissClick: () -> Unit,
        text: String = stringResource(id = R.string.alert_dialog_default_dismiss_button_text)
    ) {
        Text(
            text = text,
            modifier = Modifier
                .clickable { onDismissClick() }
                .padding(ButtonPadding)
        )
    }

    @Composable
    fun Title(
        text: String
    ) {
        Text(text = text)
    }

    @Composable
    fun TextBody(
        text: String
    ) {
        Text(text = text)
    }
}

@Suppress("MagicNumber")
@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppAlertDialogPreview() {
    AppTheme {
        AppAlertDialog(
            onDismissRequest = {},
            confirmButton = { AppAlertDialogDefaults.ConfirmButton(onConfirm = {}) },
            dismissButton = { AppAlertDialogDefaults.DismissButton(onDismissClick = {}) },
            title = { AppAlertDialogDefaults.Title(text = "Title") },
            body = { AppAlertDialogDefaults.TextBody(text = "Some text! ".repeat(10)) }
        )
    }
}
