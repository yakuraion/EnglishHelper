package pro.yakuraion.englishhelper.vocabulary.ui.vocabulary

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pro.yakuraion.englishhelper.commonui.di.viewmodel.InjectingSavedStateViewModelFactory
import pro.yakuraion.englishhelper.commonui.mvvm.MVVMActivity
import pro.yakuraion.englishhelper.vocabulary.di.diComponent
import pro.yakuraion.englishhelper.vocabulary.ui.testing.TestingActivity
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
            onAddWordsClick = { openAddWordsActivity() },
            onStartTestingClick = { openTestingActivity() }
        )
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun ScreenContentView(
        onAddWordsClick: () -> Unit,
        onStartTestingClick: () -> Unit
    ) {
        Scaffold {
            Column {
                Button(onClick = { onAddWordsClick.invoke() }) {
                    Text(text = "add words")
                }
                Button(onClick = { onStartTestingClick.invoke() }) {
                    Text(text = "start testing")
                }
            }
        }
    }

    @Preview
    @Composable
    private fun ScreenContentPreview() {
        ScreenContentView({}, {})
    }

    private fun openAddWordsActivity() {
    }

    private fun openTestingActivity() {
        startActivity(TestingActivity.createIntent(this))
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, VocabularyActivity::class.java)
        }
    }
}
