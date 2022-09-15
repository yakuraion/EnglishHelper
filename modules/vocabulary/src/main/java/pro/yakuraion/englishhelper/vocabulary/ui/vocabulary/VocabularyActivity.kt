package pro.yakuraion.englishhelper.vocabulary.ui.vocabulary

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pro.yakuraion.englishhelper.common.di.viewmodel.InjectingSavedStateViewModelFactory
import pro.yakuraion.englishhelper.common.mvvm.MVVMActivity
import pro.yakuraion.englishhelper.vocabulary.di.diComponent
import pro.yakuraion.englishhelper.vocabulary.ui.addwords.AddWordsActivity
import javax.inject.Inject

class VocabularyActivity : MVVMActivity<VocabularyViewModel>(VocabularyViewModel::class) {

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
        ScreenContentView(
            onAddWordsClick = { openAddWordsActivity() }
        )
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    private fun ScreenContentView(
        onAddWordsClick: () -> Unit,
    ) {
        Scaffold {
            Column {
                AddWordsView(onAddWordsClick)
            }
        }
    }

    @Preview
    @Composable
    private fun ScreenContentPreview() {
        ScreenContentView(
            onAddWordsClick = {}
        )
    }

    @Composable
    private fun AddWordsView(onAddWordsClick: () -> Unit) {
        Button(onClick = { onAddWordsClick.invoke() }) {
            Text(text = "add words")
        }
    }

    private fun openAddWordsActivity() {
        startActivity(AddWordsActivity.createIntent(this))
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, VocabularyActivity::class.java)
        }
    }
}
