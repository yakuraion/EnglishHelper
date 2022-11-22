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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
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
    viewModel: AddWordsViewModel = daggerViewModel()
) {
    val uiState by viewModel.uiState
    AddWordsScreen(
        uiState = uiState,
        onAddWordsClick = { viewModel.onAddWordsClick(words = it) }
    )
}

@Composable
fun AddWordsScreen(
    uiState: AddWordsUiState,
    onAddWordsClick: (words: String) -> Unit
) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val isError = uiState is AddWordsUiState.EnteringWords && uiState.wordsAlreadyExistsError != null
            val isLoading = uiState is AddWordsUiState.EnteringWords && uiState.isLoading
            Column(modifier = Modifier.align(Alignment.Center)) {
                var words by remember { mutableStateOf("") }
                CustomTextField(
                    value = words,
                    onValueChange = { words = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    placeholder = stringResource(id = R.string.vocabulary_add_words_screen_type_word),
                    maxLines = 1,
                    actionEnabled = words.trim().length > 2,
                    actionIcon = Icons.Default.Add,
                    onActionClick = { onAddWordsClick(words) },
                    isError = isError,
                    errorMessage = uiState.getFormattedErrorMessage(),
                    isLoading = isLoading
                )
            }
        }
    }
}

@Composable
@OptIn(ExperimentalComposeUiApi::class)
private fun AddWordsUiState.getFormattedErrorMessage(): String {
    val words = (this as? AddWordsUiState.EnteringWords)?.wordsAlreadyExistsError?.words ?: emptyList()
    return if (words.isNotEmpty()) {
        val wordsText = words.joinToString()
        return pluralStringResource(
            R.plurals.vocabulary_add_words_screen_already_exists_words_error,
            words.count(),
            wordsText
        )
    } else {
        ""
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun AddWordsContentPreview() {
    AppTheme {
        AddWordsScreen(
            uiState = AddWordsUiState.EnteringWords(),
            onAddWordsClick = {}
        )
    }
}
