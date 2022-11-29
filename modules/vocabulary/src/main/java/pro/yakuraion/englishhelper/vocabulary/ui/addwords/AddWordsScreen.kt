package pro.yakuraion.englishhelper.vocabulary.ui.addwords

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.commonui.compose.widgets.CustomTextField
import pro.yakuraion.englishhelper.commonui.compose.widgets.CustomTextFieldActionIcon
import pro.yakuraion.englishhelper.commonui.compose.widgets.CustomTextFieldError
import pro.yakuraion.englishhelper.vocabulary.R
import pro.yakuraion.englishhelper.vocabulary.di.viewmodel.daggerViewModel

@Composable
fun AddWordsScreen(
    viewModel: AddWordsViewModel = daggerViewModel(),
    onBackClick: () -> Unit
) {
    AddWordsScreen(
        uiState = viewModel.uiState,
        onWordChanged = { viewModel.onWordChanged(it) },
        onAddWordsClick = { viewModel.onAddWordClick() },
        onBackClick = onBackClick
    )
}

@Composable
private fun AddWordsScreen(
    uiState: AddWordsUiState,
    onWordChanged: (String) -> Unit,
    onAddWordsClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.vocabulary_add_words_screen_title))
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                val focusRequester = remember { FocusRequester() }
                CustomTextField(
                    value = uiState.word,
                    onValueChange = onWordChanged,
                    modifier = Modifier
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
        }
    }
}

@Composable
private fun formatIsWordAlreadyExistErrorMessage(word: String?): String {
    return word
        ?.let { stringResource(R.string.vocabulary_add_words_screen_already_exists_words_error, it) }
        .orEmpty()
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
            onBackClick = {}
        )
    }
}
