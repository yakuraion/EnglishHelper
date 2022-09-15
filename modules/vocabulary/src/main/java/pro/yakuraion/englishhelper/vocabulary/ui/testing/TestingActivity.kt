package pro.yakuraion.englishhelper.vocabulary.ui.testing

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.common.di.viewmodel.InjectingSavedStateViewModelFactory
import pro.yakuraion.englishhelper.common.mvvm.MVVMActivity
import pro.yakuraion.englishhelper.domain.entities.LearningWord
import pro.yakuraion.englishhelper.vocabulary.di.diComponent
import javax.inject.Inject

class TestingActivity : MVVMActivity<TestingViewModel>(TestingViewModel::class) {

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
        val words by remember { viewModel.words }
        ScreenContentView(
            words = words
        )
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    private fun ScreenContentView(words: List<LearningWord>) {
        Scaffold {
            WordsListView(words)
        }
    }

    @Composable
    private fun WordsListView(words: List<LearningWord>) {
        Column {
            LazyColumn {
                items(words) { word ->
                    WordView(word)
                }
            }
        }
    }

    @Composable
    private fun WordView(word: LearningWord) {
        Row {
            Text(text = word.word.name, modifier = Modifier.padding(end = 8.dp))
            Text(text = word.memorizationLevel.level.toString(), modifier = Modifier.padding(end = 8.dp))
            Text(text = word.nextDayToLearn.toString())
        }
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, TestingActivity::class.java)
        }
    }
}
