package pro.yakuraion.englishhelper.vocabulary.ui.addwords

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import pro.yakuraion.englishhelper.common.di.viewmodel.InjectingSavedStateViewModelFactory
import pro.yakuraion.englishhelper.common.mvvm.MVVMActivity
import pro.yakuraion.englishhelper.vocabulary.di.diComponent
import javax.inject.Inject

class AddWordsActivity : MVVMActivity<AddWordsViewModel>(AddWordsViewModel::class) {

    @Inject
    override lateinit var abstractViewModelFactory: InjectingSavedStateViewModelFactory

    override fun inject() {
        diComponent.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ScreenView()
        }
    }

    @Composable
    private fun ScreenView() {
        val uiState by remember { viewModel.uiState }
        ScreenContentView(
            uiState = uiState,
            onAddWordsClick = { viewModel.onAddWordsClick(it) }
        )
        // todo replace with Navigation
        if (uiState is AddWordsViewModel.UIState.WordsAdded) {
            finish()
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    private fun ScreenContentView(uiState: AddWordsViewModel.UIState, onAddWordsClick: (words: String) -> Unit) {
        Scaffold {
            Column {
                AddWordsView(onAddWordsClick = onAddWordsClick)
                if (uiState is AddWordsViewModel.UIState.Error) {
                    ErrorView(message = uiState.message)
                }
            }
        }
    }

    @Preview
    @Composable
    private fun ScreenContentPreview() {
        ScreenContentView(
            uiState = AddWordsViewModel.UIState.EnteringWords,
            onAddWordsClick = {}
        )
    }

    @Composable
    private fun AddWordsView(onAddWordsClick: (words: String) -> Unit) {
        var text by rememberSaveable { mutableStateOf("") }
        Column {
            TextField(value = text, onValueChange = { text = it })
            Button(onClick = { onAddWordsClick.invoke(text) }) {
                Text(text = "add words")
            }
        }
    }

    @Composable
    private fun ErrorView(message: String) {
        Text(text = message, color = Color.Red)
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, AddWordsActivity::class.java)
        }
    }
}
