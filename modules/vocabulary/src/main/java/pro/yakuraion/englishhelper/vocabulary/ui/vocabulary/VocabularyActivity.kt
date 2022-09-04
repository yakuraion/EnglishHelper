package pro.yakuraion.englishhelper.vocabulary.ui.vocabulary

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
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.common.di.viewmodel.InjectingSavedStateViewModelFactory
import pro.yakuraion.englishhelper.common.mvvm.MVVMActivity
import pro.yakuraion.englishhelper.vocabulary.data.entities.LearningWordEntity
import pro.yakuraion.englishhelper.vocabulary.data.entities.MemorizationLevel
import pro.yakuraion.englishhelper.vocabulary.data.entities.Word
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
        val learningDay by viewModel.learningDay.collectAsState()
        val words by viewModel.words.collectAsState(initial = emptyList())
        ScreenContentView(
            learningDay = learningDay,
            onLearningDaySet = { viewModel.onLearningDaySet(it) },
            onAddWordsClick = { openAddWordsActivity() },
            words = words
        )
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    private fun ScreenContentView(
        learningDay: Int,
        onLearningDaySet: (day: Int) -> Unit,
        onAddWordsClick: () -> Unit,
        words: List<LearningWordEntity>
    ) {
        Scaffold {
            Column {
                LearningDayView(learningDay, onLearningDaySet)
                AddWordsView(onAddWordsClick)
                WordsListView(words)
            }
        }
    }

    @Preview
    @Composable
    private fun ScreenContentPreview() {
        ScreenContentView(
            learningDay = 12,
            onLearningDaySet = {},
            onAddWordsClick = {},
            words = listOf(
                LearningWordEntity(Word("Butter"), MemorizationLevel.new()),
                LearningWordEntity(Word("Population"), MemorizationLevel.new()),
                LearningWordEntity(Word("its_a_very_long_word"), MemorizationLevel.new())
            )
        )
    }

    @Composable
    private fun LearningDayView(learningDay: Int, onLearningDaySet: (day: Int) -> Unit) {
        var day by rememberSaveable { mutableStateOf(learningDay.toString()) }
        Row {
            TextField(value = day, onValueChange = { day = it })
            TextButton(onClick = { onLearningDaySet.invoke(day.toInt()) }) {
                Text(text = "change day")
            }
        }
    }

    @Composable
    private fun AddWordsView(onAddWordsClick: () -> Unit) {
        Button(onClick = { onAddWordsClick.invoke() }) {
            Text(text = "add words")
        }
    }

    @Composable
    private fun WordsListView(words: List<LearningWordEntity>) {
        Column {
            LazyColumn {
                items(words) { word ->
                    WordView(word)
                }
            }
        }
    }

    @Composable
    private fun WordView(word: LearningWordEntity) {
        Row {
            Text(text = word.word.name, modifier = Modifier.padding(end = 8.dp))
            Text(text = word.memorizationLevel.level.toString())
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
