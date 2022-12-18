package pro.yakuraion.englishhelper.vocabulary.ui.testing.states.regular

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import pro.yakuraion.englishhelper.vocabulary.ui.testing.states.TestingUiState

class TestingContentRegularState(uiState: TestingUiState.Regular) {

    val queueId = uiState.queueId

    val word = uiState.word

    val soundUri = uiState.soundUri

    val examples = uiState.examples

    val showExamples = uiState.showExamples

    val revealExamples = uiState.revealExamples

    val dictionaryUri = uiState.dictionaryUrl

    var answer: String by mutableStateOf("")
        private set

    val isActionEnabled: Boolean
        get() = answer.length > 2

    var isWrongAnswer by mutableStateOf(false)
        private set

    fun onAnswerChanged(answer: String) {
        this.answer = answer
        isWrongAnswer = false
    }

    fun onDoneClick(onRightAnswer: () -> Unit) {
        if (word.equals(answer.trim(), false)) {
            onRightAnswer()
        } else {
            isWrongAnswer = true
        }
    }
}

@Composable
fun rememberTestingContentRegularState(
    uiState: TestingUiState.Regular
): TestingContentRegularState {
    return remember(uiState) {
        TestingContentRegularState(uiState)
    }
}
