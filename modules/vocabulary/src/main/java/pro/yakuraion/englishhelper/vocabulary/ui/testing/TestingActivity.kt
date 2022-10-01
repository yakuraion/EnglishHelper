package pro.yakuraion.englishhelper.vocabulary.ui.testing

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.common.di.viewmodel.InjectingSavedStateViewModelFactory
import pro.yakuraion.englishhelper.common.mvvm.MVVMActivity
import pro.yakuraion.englishhelper.domain.entities.Word
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWordFull
import pro.yakuraion.englishhelper.domain.entities.learning.MemorizationLevel
import pro.yakuraion.englishhelper.vocabulary.di.diComponent
import java.io.File
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
        val isLoading by remember { viewModel.isLoading }
        val word by remember { viewModel.word }
        ScreenContentView(
            word = word,
            isLoading = isLoading,
            onKnowClick = { viewModel.onKnowClick(it) },
            onDoNotKnowClick = { viewModel.onDoNotKnowClick(it) }
        )
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    private fun ScreenContentView(
        word: LearningWordFull?,
        isLoading: Boolean,
        onKnowClick: (LearningWordFull) -> Unit,
        onDoNotKnowClick: (LearningWordFull) -> Unit
    ) {
        Scaffold {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    ProgressView()
                } else if (word != null) {
                    TestingView(word, onKnowClick, onDoNotKnowClick)
                } else {
                    NoWordsView()
                }
            }
        }
    }

    @Composable
    private fun TestingView(
        word: LearningWordFull,
        onKnowClick: (LearningWordFull) -> Unit,
        onDoNotKnowClick: (LearningWordFull) -> Unit
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Do you know this word?")
            Spacer(modifier = Modifier.height(20.dp))
            WordView(word = word)
            Spacer(modifier = Modifier.height(20.dp))
            AnswersButtons(
                onKnowClick = { onKnowClick(word) },
                onDoNotKnowClick = { onDoNotKnowClick(word) }
            )
        }
    }

    @Composable
    private fun WordView(word: LearningWordFull) {
        word.word.soundFile?.let { soundFile ->
            Image(
                imageVector = ImageVector.vectorResource(
                    id = pro.yakuraion.englishhelper.common.R.drawable.ic_baseline_volume_up_24
                ),
                contentDescription = "",
                modifier = Modifier.clickable { playSound(soundFile) },
            )
        } ?: Text(text = word.word.name)
    }

    private fun playSound(file: File) {
        MediaPlayer.create(this, Uri.fromFile(file)).start()
    }

    @Composable
    private fun AnswersButtons(onKnowClick: () -> Unit, onDoNotKnowClick: () -> Unit) {
        Row {
            Button(onClick = onKnowClick) {
                Text(text = "I know")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = onDoNotKnowClick) {
                Text(text = "I don't know")
            }
        }
    }

    @Composable
    private fun NoWordsView() {
        Text(text = "There aren't any words")
    }

    @Composable
    private fun ProgressView() {
        CircularProgressIndicator()
    }

    @Preview
    @Composable
    private fun ScreenContentPreviewTestingWord() {
        ScreenContentView(
            word = LearningWordFull(
                Word("Mother", null),
                LearningWord("Mother", MemorizationLevel.new(), 0)
            ),
            isLoading = false,
            {},
            {}
        )
    }

    @Preview
    @Composable
    private fun ScreenContentPreviewTestingSound() {
        ScreenContentView(
            word = LearningWordFull(
                Word("Mother", File("")),
                LearningWord("Mother", MemorizationLevel.new(), 0)
            ),
            isLoading = false,
            {},
            {}
        )
    }

    @Preview
    @Composable
    private fun ScreenContentPreviewLoading() {
        ScreenContentView(word = null, isLoading = true, {}, {})
    }

    @Preview
    @Composable
    private fun ScreenContentPreviewNowWords() {
        ScreenContentView(word = null, isLoading = false, {}, {})
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, TestingActivity::class.java)
        }
    }
}
