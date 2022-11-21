package pro.yakuraion.englishhelper.vocabulary.ui.testing

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pro.yakuraion.englishhelper.commonui.di.viewmodel.InjectingSavedStateViewModelFactory
import pro.yakuraion.englishhelper.commonui.mvvm.MVVMActivity
import pro.yakuraion.englishhelper.commonui.openLink
import pro.yakuraion.englishhelper.domain.entities.Word
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWord
import pro.yakuraion.englishhelper.domain.entities.learning.LearningWordFull
import pro.yakuraion.englishhelper.domain.entities.learning.MemorizationLevel
import pro.yakuraion.englishhelper.vocabulary.di.diComponent
import java.io.File
import java.net.URLEncoder
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
            onKnowClick = { viewModel.onKnowClick() },
            onDoNotKnowClick = { viewModel.onDoNotKnowClick() }
        )
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun ScreenContentView(
        word: LearningWordFull?,
        isLoading: Boolean,
        onKnowClick: () -> Unit,
        onDoNotKnowClick: () -> Unit
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
        onKnowClick: () -> Unit,
        onDoNotKnowClick: () -> Unit
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Do you know this word?")
            Spacer(modifier = Modifier.height(20.dp))
            val soundFile = word.word.soundFile
            if (soundFile != null) {
                WordWithSoundTestingView(
                    word = word.word.name,
                    soundFile = soundFile,
                    onKnowClick = { onKnowClick.invoke() },
                    onDoNotKnowClick = { onDoNotKnowClick.invoke() }
                )
            } else {
                WordOnlyTextTestingView(
                    word = word.word.name,
                    onKnowClick = { onKnowClick.invoke() },
                    onDoNotKnowClick = { onDoNotKnowClick.invoke() }
                )
            }
        }
    }

    @Composable
    private fun WordWithSoundTestingView(
        word: String,
        soundFile: File,
        onKnowClick: () -> Unit,
        onDoNotKnowClick: () -> Unit
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            var writtenWord by remember { mutableStateOf("") }
            Image(
                imageVector = Icons.Filled.VolumeUp,
                contentDescription = "",
                modifier = Modifier
                    .clickable { playSound(soundFile) }
                    .size(60.dp),
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(value = writtenWord, onValueChange = { writtenWord = it })
            Spacer(modifier = Modifier.height(20.dp))
            LinkView(word = word)
            Spacer(modifier = Modifier.height(20.dp))
            WordWithSoundTestingAnswersButtons(
                onCheckClick = {
                    if (writtenWord.lowercase().trim() == word.lowercase()) {
                        Toast.makeText(
                            this@TestingActivity,
                            "Right answer",
                            Toast.LENGTH_SHORT
                        ).show()
                        writtenWord = ""
                        onKnowClick.invoke()
                    } else {
                        Toast.makeText(
                            this@TestingActivity,
                            "Wrong answer",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                },
                onDoNotKnowClick = {
                    Toast.makeText(
                        this@TestingActivity,
                        "It was \"$word\"",
                        Toast.LENGTH_SHORT
                    ).show()
                    writtenWord = ""
                    onDoNotKnowClick.invoke()
                }
            )
        }
    }

    @Composable
    private fun WordWithSoundTestingAnswersButtons(
        onCheckClick: () -> Unit,
        onDoNotKnowClick: () -> Unit
    ) {
        Row {
            Button(onClick = { onCheckClick.invoke() }) {
                Text(text = "Check")
            }
            Spacer(modifier = Modifier.width(20.dp))
            Button(onClick = { onDoNotKnowClick.invoke() }) {
                Text(text = "I don't know")
            }
        }
    }

    private fun playSound(file: File) {
        MediaPlayer.create(this, Uri.fromFile(file)).start()
    }

    @Composable
    private fun WordOnlyTextTestingView(
        word: String,
        onKnowClick: () -> Unit,
        onDoNotKnowClick: () -> Unit
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = word, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(20.dp))
            LinkView(word = word)
            Spacer(modifier = Modifier.height(20.dp))
            WordOnlyTextTestingAnswersButtons(
                onKnowClick = { onKnowClick() },
                onDoNotKnowClick = { onDoNotKnowClick() }
            )
        }
    }

    @Composable
    private fun LinkView(word: String) {
        Text(
            text = "Open in browser",
            modifier = Modifier.clickable { openLink(getWordLink(word)) },
            color = Color.Blue,
            style = TextStyle(textDecoration = TextDecoration.Underline)
        )
    }

    @Composable
    private fun WordOnlyTextTestingAnswersButtons(onKnowClick: () -> Unit, onDoNotKnowClick: () -> Unit) {
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

        fun getWordLink(word: String): Uri {
            val encodedWord = URLEncoder.encode(word, "utf-8")
            return Uri.parse("https://wooordhunt.ru/word/$encodedWord")
        }
    }
}
