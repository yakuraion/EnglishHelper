package pro.yakuraion.englishhelper.commonui.compose.widgets

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.androidcommon.kotlin.applyIf
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme

@Composable
fun AppTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    maxLines: Int = Int.MAX_VALUE,
    placeholderText: String = "",
    error: CustomTextFieldError? = null,
    actionIcon: CustomTextFieldActionIcon? = null,
    shapes: AppTextFieldShapes = AppTextFieldDefaults.shapes(),
) {
    Column(modifier = modifier) {
        ErrorBorderBox(
            isError = error?.isError == true,
            shape = shapes.shape,
            modifier = Modifier.height(IntrinsicSize.Min)
        ) {
            TextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier.fillMaxWidth(),
                textStyle = MaterialTheme.typography.bodyLarge,
                placeholder = {
                    Text(
                        text = placeholderText,
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                trailingIcon = {},
                maxLines = maxLines,
                shape = shapes.shape,
                colors = transparentTextFieldColors()
            )
            if (actionIcon != null) {
                IconLoadingActionContainer(
                    icon = actionIcon.icon,
                    onClick = actionIcon.onClick,
                    isEnabled = actionIcon.isEnabled,
                    isLoading = actionIcon.isLoading,
                    shape = shapes.actionShape,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
        if (error != null) {
            Spacer(modifier = Modifier.height(2.dp))
            ErrorLabel(text = error.text)
        }
    }
}

@Composable
private fun transparentTextFieldColors() = TextFieldDefaults.textFieldColors(
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
    errorIndicatorColor = Color.Transparent
)

@Composable
private fun ErrorBorderBox(
    isError: Boolean,
    shape: Shape,
    modifier: Modifier = Modifier,
    errorBorderStroke: BorderStroke = BorderStroke(
        width = 2.dp,
        color = MaterialTheme.colorScheme.error
    ),
    content: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = modifier
            .applyIf(isError) {
                border(
                    border = errorBorderStroke,
                    shape = shape
                )
            },
        content = content
    )
}

@Composable
private fun ErrorLabel(text: String) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.labelMedium
    )
}

@Composable
private fun IconLoadingActionContainer(
    icon: ImageVector,
    onClick: () -> Unit,
    isEnabled: Boolean,
    isLoading: Boolean,
    shape: Shape,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
) {
    val containerWidth = 48.dp
    val progressSize = 24.dp
    val iconSize = 24.dp

    ActionContainer(
        onClick = onClick,
        onClickEnabled = !isLoading && isEnabled,
        shape = shape,
        enabled = isEnabled,
        containerColor = containerColor,
        contentColor = contentColor,
        modifier = modifier
            .width(containerWidth)
            .clickable(
                enabled = !isLoading && isEnabled,
                onClick = onClick
            )
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(progressSize)
                    .align(Alignment.Center),
                color = LocalContentColor.current
            )
        } else {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier
                    .size(iconSize)
                    .align(Alignment.Center)
            )
        }
    }
}

@Composable
private fun ActionContainer(
    onClick: () -> Unit,
    onClickEnabled: Boolean,
    shape: Shape,
    enabled: Boolean,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val disabledAlpha = 0.4f

    Box(
        modifier = modifier
            .alpha(if (enabled) 1f else disabledAlpha)
            .fillMaxSize()
            .clip(shape)
            .background(containerColor)
            .clickable(enabled = onClickEnabled, onClick = onClick)
    ) {
        CompositionLocalProvider(LocalContentColor provides contentColor) {
            content()
        }
    }
}

data class CustomTextFieldActionIcon(
    val icon: ImageVector,
    val onClick: () -> Unit,
    val isEnabled: Boolean = true,
    val isLoading: Boolean = false
)

data class CustomTextFieldError(
    val isError: Boolean,
    val text: String
)

data class AppTextFieldShapes(
    val shape: Shape,
    val actionShape: Shape
)

object AppTextFieldDefaults {

    private val CornerRadius = 8.dp

    fun shapes(
        shape: Shape = RoundedCornerShape(CornerRadius),
        actionShape: Shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = CornerRadius,
            bottomEnd = CornerRadius,
            bottomStart = 0.dp
        )
    ) = AppTextFieldShapes(
        shape = shape,
        actionShape = actionShape
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppTextFieldPreview() {
    AppTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            val context = LocalContext.current
            var value by remember { mutableStateOf("") }
            AppTextField(
                value = value,
                onValueChange = { value = it },
                modifier = Modifier.padding(16.dp),
                placeholderText = "Placeholder",
                actionIcon = CustomTextFieldActionIcon(
                    icon = Icons.Default.Done,
                    onClick = {
                        Toast.makeText(context, "On action click", Toast.LENGTH_SHORT).show()
                    }
                ),
            )
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppTextFieldWithTextPreview() {
    AppTheme {
        AppTextField(
            value = "Some text",
            onValueChange = {},
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppTextFieldWithErrorPreview() {
    AppTheme {
        AppTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.padding(16.dp),
            error = CustomTextFieldError(
                isError = true,
                text = "Error text"
            )
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppTextFieldWithEmptyErrorPreview() {
    AppTheme {
        AppTextField(
            value = "",
            onValueChange = { },
            modifier = Modifier.padding(16.dp),
            error = CustomTextFieldError(
                isError = false,
                text = ""
            )
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppTextFieldLoadingIconPreview() {
    AppTheme {
        AppTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.padding(16.dp),
            actionIcon = CustomTextFieldActionIcon(
                icon = Icons.Default.Done,
                onClick = {},
                isLoading = true
            )
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppTextFieldDisabledIconPreview() {
    AppTheme {
        AppTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.padding(16.dp),
            actionIcon = CustomTextFieldActionIcon(
                icon = Icons.Default.Done,
                onClick = {},
                isEnabled = false
            )
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppTextFieldIconWithErrorPreview() {
    AppTheme {
        AppTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.padding(16.dp),
            actionIcon = CustomTextFieldActionIcon(
                icon = Icons.Default.Done,
                onClick = {},
                isEnabled = true
            ),
            error = CustomTextFieldError(
                isError = true,
                text = "Error text"
            )
        )
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AppTextFieldDisabledIconWithErrorPreview() {
    AppTheme {
        AppTextField(
            value = "",
            onValueChange = {},
            modifier = Modifier.padding(16.dp),
            actionIcon = CustomTextFieldActionIcon(
                icon = Icons.Default.Done,
                onClick = {},
                isEnabled = false
            ),
            error = CustomTextFieldError(
                isError = true,
                text = "Error text"
            )
        )
    }
}
