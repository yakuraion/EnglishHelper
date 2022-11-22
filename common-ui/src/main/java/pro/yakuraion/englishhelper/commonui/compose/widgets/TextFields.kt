package pro.yakuraion.englishhelper.commonui.compose.widgets

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.common.applyIf
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    maxLines: Int = Int.MAX_VALUE,
    actionEnabled: Boolean = true,
    actionIcon: ImageVector? = null,
    onActionClick: () -> Unit = {},
    isError: Boolean = false,
    errorMessage: String? = null,
    isLoading: Boolean = false
) {
    Column(modifier = modifier) {
        Box(modifier = Modifier.height(IntrinsicSize.Min)) {
            InnerTextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = placeholder,
                maxLines = maxLines,
                isError = isError,
                actionIcon = actionIcon
            )
            if (isLoading) {
                ActionLoading(modifier = Modifier.align(Alignment.CenterEnd))
            } else if (actionIcon != null) {
                ActionIcon(
                    icon = actionIcon,
                    onIconClick = onActionClick,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    enabled = actionEnabled
                )
            }
        }
        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(2.dp))
            ErrorMessage(text = errorMessage)
        }
    }
}

@Composable
private fun InnerTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    maxLines: Int = Int.MAX_VALUE,
    isError: Boolean = false,
    actionIcon: ImageVector? = null
) {
    val shape = MaterialTheme.shapes.small
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .fillMaxWidth()
            .applyIf(isError) {
                border(
                    width = 2.dp,
                    color = MaterialTheme.colorScheme.error,
                    shape = shape
                )
            },
        placeholder = placeholder?.let { { Text(text = placeholder) } },
        trailingIcon = actionIcon?.let {
            {
                Icon(
                    imageVector = actionIcon,
                    contentDescription = null,
                    modifier = Modifier.alpha(0f)
                )
            }
        },
        maxLines = maxLines,
        shape = shape,
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        )
    )
}

@Composable
private fun ActionLoading(
    modifier: Modifier = Modifier
) {
    ActionContainer(modifier = modifier) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp).align(Alignment.Center),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun ActionIcon(
    icon: ImageVector,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    ActionContainer(
        modifier = modifier,
        enabled = enabled,
        onClick = onIconClick
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun ActionContainer(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    val alpha = if (enabled) 1f else 0.4f
    Box(modifier = modifier.alpha(alpha)) {
        Spacer(
            modifier = Modifier
                .width(48.dp)
                .fillMaxHeight()
                .clip(
                    RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 8.dp,
                        bottomEnd = 8.dp,
                        bottomStart = 0.dp
                    )
                )
                .background(MaterialTheme.colorScheme.primary)
                .clickable { onClick() }
        )
        content()
    }
}

@Composable
private fun ErrorMessage(
    text: String
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.error
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CustomTextFieldPreview() {
    AppTheme {
        val content = LocalContext.current
        var value by remember { mutableStateOf("") }
        CustomTextField(
            value = value,
            onValueChange = { value = it },
            modifier = Modifier.padding(16.dp),
            placeholder = "Placeholder",
            actionIcon = Icons.Default.Done,
            onActionClick = {
                Toast.makeText(content, "On action click", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CustomTextFieldLoadingPreview() {
    AppTheme {
        CustomTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.padding(16.dp),
            isLoading = true
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CustomTextFieldWithDisabledIconPreview() {
    AppTheme {
        CustomTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.padding(16.dp),
            actionEnabled = false,
            actionIcon = Icons.Default.Done
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CustomTextFieldWithErrorPreview() {
    AppTheme {
        CustomTextField(
            value = "",
            onValueChange = { },
            modifier = Modifier.padding(16.dp),
            isError = true,
            errorMessage = "Something wrong happened",
        )
    }
}
