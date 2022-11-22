package pro.yakuraion.englishhelper.vocabulary.ui.addwords

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.commonui.compose.theme.AppTheme
import pro.yakuraion.englishhelper.commonui.compose.widgets.CustomTextField
import pro.yakuraion.englishhelper.vocabulary.R
import pro.yakuraion.englishhelper.vocabulary.di.daggerViewModel

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun AddWordsScreen(
    viewModel: AddWordsViewModel = daggerViewModel(),
) {
    AddWordsScreen(
        word = viewModel.word,
        isWordAlreadyExistError = viewModel.isWordAlreadyExistError,
        isLoading = viewModel.isLoading,
        onWordChanged = { viewModel.onWordChanged(it) },
        onAddWordsClick = { viewModel.onAddWordClick() }
    )
}

@Composable
fun AddWordsScreen(
    word: String,
    isWordAlreadyExistError: AddWordsViewModel.IsWordAlreadyExistError?,
    isLoading: Boolean,
    onWordChanged: (String) -> Unit,
    onAddWordsClick: () -> Unit
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Column(modifier = Modifier.align(Alignment.Center)) {
                CustomTextField(
                    value = word,
                    onValueChange = onWordChanged,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    placeholder = stringResource(id = R.string.vocabulary_add_words_screen_type_word),
                    maxLines = 1,
                    actionEnabled = word.length > 2,
                    actionIcon = Icons.Default.Add,
                    onActionClick = onAddWordsClick,
                    isError = isWordAlreadyExistError != null,
                    errorMessage = isWordAlreadyExistError?.let { formatIsWordAlreadyExistErrorMessage(it) }.orEmpty(),
                    isLoading = isLoading
                )
            }
        }
    }
}

@Composable
private fun formatIsWordAlreadyExistErrorMessage(error: AddWordsViewModel.IsWordAlreadyExistError): String {
    return stringResource(R.string.vocabulary_add_words_screen_already_exists_words_error, error.word)
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AddWordsContentPreview() {
    AppTheme {
        AddWordsScreen(
            word = "word",
            isWordAlreadyExistError = null,
            isLoading = false,
            onWordChanged = {},
            onAddWordsClick = {}
        )
    }
}
