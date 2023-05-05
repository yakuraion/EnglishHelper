package pro.yakuraion.englishhelper.vocabulary.ui.addwords

import android.content.res.Configuration
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.commonui.compose.widgets.AppAlertDialog
import pro.yakuraion.englishhelper.commonui.compose.widgets.AppAlertDialogDefaults
import pro.yakuraion.englishhelper.commonui.compose.widgets.AppTextField
import pro.yakuraion.englishhelper.commonui.compose.widgets.AppTopAppBar
import pro.yakuraion.englishhelper.commonui.compose.widgets.CustomTextFieldActionIcon
import pro.yakuraion.englishhelper.commonui.compose.widgets.CustomTextFieldError
import pro.yakuraion.englishhelper.commonui.compose.widgets.buttons.AppArrowBackButton
import pro.yakuraion.englishhelper.commonui.compose.widgets.buttons.AppOutlinedButton
import pro.yakuraion.englishhelper.commonui.openLink
import pro.yakuraion.englishhelper.domain.utils.DictionaryUtils
import pro.yakuraion.englishhelper.vocabulary.R
import pro.yakuraion.englishhelper.vocabulary.di.viewmodel.featureDaggerViewModel

@Composable
fun AddWordsScreen(
    viewModel: AddWordsViewModel = featureDaggerViewModel(),
    onBackClick: () -> Unit,
) {
    AddWordsScreen(
        uiState = viewModel.uiState,
        onWordChanged = { viewModel.onWordChanged(it) },
        onAddWordsClick = { viewModel.onAddWordClick() },
        onBackClick = onBackClick,
        onWordNotFoundDialogDismiss = { viewModel.onWordNotFoundDialogDismiss() },
        onWordNotFoundDialogAddClick = { viewModel.onWordNotFoundDialogAddClick() }
    )
}

@Composable
private fun AddWordsScreen(
    uiState: AddWordsUiState,
    onWordChanged: (String) -> Unit,
    onAddWordsClick: () -> Unit,
    onBackClick: () -> Unit,
    onWordNotFoundDialogDismiss: () -> Unit,
    onWordNotFoundDialogAddClick: () -> Unit,
) {
    Scaffold(
        topBar = { TopBar(onBackClick = onBackClick) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            OpenDictionaryButton(
                uiState.word,
                modifier = Modifier.align(Alignment.TopEnd)
            )
            EnterWordTextField(
                uiState = uiState,
                onWordChanged = onWordChanged,
                onAddWordsClick = onAddWordsClick,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (uiState.isWordNotFoundDialogShowing) {
            WordNotFoundDialog(
                word = uiState.word,
                onDismiss = onWordNotFoundDialogDismiss,
                onAddClick = onWordNotFoundDialogAddClick
            )
        }
    }
}

@Composable
private fun OpenDictionaryButton(
    word: String,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current

    AppOutlinedButton(
        text = stringResource(id = R.string.vocabulary_add_words_screen_dictionary_button),
        onClick = {
            val link = DictionaryUtils.getDictionaryUrl(word)
            context.openLink(Uri.parse(link))
        },
        modifier = modifier
            .padding(top = 16.dp, end = 16.dp),
        leadingIcon = {
            Icon(imageVector = Icons.Filled.HelpOutline, contentDescription = null)
        }
    )
}

@Composable
private fun TopBar(onBackClick: () -> Unit) {
    AppTopAppBar(
        title = {
            Text(text = stringResource(id = R.string.vocabulary_add_words_screen_title))
        },
        navigationIcon = {
            AppArrowBackButton(onBackClick = onBackClick)
        }
    )
}

@Composable
private fun EnterWordTextField(
    uiState: AddWordsUiState,
    onWordChanged: (String) -> Unit,
    onAddWordsClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }

    AppTextField(
        value = uiState.word,
        onValueChange = onWordChanged,
        modifier = modifier
            .padding(horizontal = 16.dp)
            .focusRequester(focusRequester),
        maxLines = 1,
        placeholderText = stringResource(id = R.string.vocabulary_add_words_screen_type_word),
        actionIcon = CustomTextFieldActionIcon(
            icon = Icons.Default.Add,
            onClick = onAddWordsClick,
            isEnabled = uiState.isAddButtonEnabled,
            isLoading = uiState.isAddButtonLoading
        ),
        error = CustomTextFieldError(
            isError = uiState.isWordAlreadyExistError,
            text = formatIsWordAlreadyExistErrorMessage(word = uiState.alreadyExistWord)
        )
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
private fun formatIsWordAlreadyExistErrorMessage(word: String?): String {
    return word
        ?.let { stringResource(R.string.vocabulary_add_words_screen_already_exists_words_error, it) }
        .orEmpty()
}

@Composable
private fun WordNotFoundDialog(
    word: String,
    onDismiss: () -> Unit,
    onAddClick: () -> Unit,
) {
    AppAlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            AppAlertDialogDefaults.ConfirmButton(
                onConfirm = onAddClick,
                text = stringResource(id = R.string.vocabulary_add_words_screen_not_found_dialog_confirm)
            )
        },
        dismissButton = {
            AppAlertDialogDefaults.DismissButton(onDismissClick = onDismiss)
        },
        title = {
            AppAlertDialogDefaults.Title(
                text = stringResource(id = R.string.vocabulary_add_words_screen_not_found_dialog_title)
            )
        },
        body = {
            AppAlertDialogDefaults.TextBody(
                text = stringResource(R.string.vocabulary_add_words_screen_not_found_dialog_text, word)
            )
        }
    )
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AddWordsContentPreview() {
    AppTheme {
        AddWordsScreen(
            uiState = AddWordsUiState(),
            onWordChanged = {},
            onAddWordsClick = {},
            onBackClick = {},
            onWordNotFoundDialogDismiss = {},
            onWordNotFoundDialogAddClick = {}
        )
    }
}
