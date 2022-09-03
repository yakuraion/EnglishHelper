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
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.common.di.viewmodel.InjectingSavedStateViewModelFactory
import pro.yakuraion.englishhelper.common.mvvm.MVVMActivity
import pro.yakuraion.englishhelper.vocabulary.data.entities.LearningWordEntity
import pro.yakuraion.englishhelper.vocabulary.data.entities.MemorizationLevel
import pro.yakuraion.englishhelper.vocabulary.data.entities.Word
import pro.yakuraion.englishhelper.vocabulary.di.VocabularyComponent
import pro.yakuraion.englishhelper.vocabulary.di.VocabularyDependencies
import javax.inject.Inject

class VocabularyActivity : MVVMActivity<VocabularyViewModel>(VocabularyViewModel::class) {

    @Inject
    override lateinit var abstractViewModelFactory: InjectingSavedStateViewModelFactory

    override fun inject() {
        val dependencies = (application as VocabularyDependencies.Provider).provideVocabularyDependencies()
        VocabularyComponent.create(dependencies).inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VocabularyScreen()
        }
    }

    @Composable
    fun VocabularyScreen() {
        val words by viewModel.words.collectAsState(initial = emptyList())
        VocabularyContent(
            words = words,
            onAddClick = { viewModel.onAddNameClick(it) }
        )
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun VocabularyContent(words: List<LearningWordEntity>, onAddClick: (name: String) -> Unit) {
        Scaffold {
            Column {
                AddNameView(onAddClick = onAddClick)
                WordsListView(words)
            }
        }
    }

    @Preview
    @Composable
    fun VocabularyContentPreview() {
        VocabularyContent(
            words = listOf(
                LearningWordEntity(Word("Butter"), MemorizationLevel.new()),
                LearningWordEntity(Word("Population"), MemorizationLevel.new()),
                LearningWordEntity(Word("its_a_very_long_word"), MemorizationLevel.new())
            ),
            onAddClick = {}
        )
    }

    @Composable
    fun AddNameView(onAddClick: (name: String) -> Unit) {
        val name = rememberSaveable { mutableStateOf("") }
        Column {
            TextField(
                value = name.value,
                onValueChange = { name.value = it },
            )
            TextButton(onClick = { onAddClick.invoke(name.value) }) {
                Text(text = "add word")
            }
        }
    }

    @Composable
    fun WordsListView(words: List<LearningWordEntity>) {
        Column {
            LazyColumn {
                items(words) { word ->
                    WordView(word)
                }
            }
        }
    }

    @Composable
    fun WordView(word: LearningWordEntity) {
        Row {
            Text(text = word.word.name, modifier = Modifier.padding(end = 8.dp))
            Text(text = word.memorizationLevel.level.toString())
        }
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, VocabularyActivity::class.java)
        }
    }
}
