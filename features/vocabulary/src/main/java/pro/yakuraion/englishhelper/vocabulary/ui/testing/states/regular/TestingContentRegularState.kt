package pro.yakuraion.englishhelper.vocabulary.ui.testing.states.regular

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import pro.yakuraion.englishhelper.vocabulary.ui.testing.states.TestingUiState

class TestingContentRegularState(val uiState: TestingUiState.Regular) {

    var showExamples by mutableStateOf(false)
        private set

    var answer: String by mutableStateOf("")
        private set

    val isActionEnabled: Boolean
        get() = answer.length > 2

    var isWrongAnswer by mutableStateOf(false)
        private set

    fun onShowExamplesClick() {
        showExamples = true
    }

    fun onHideExamplesClick() {
        showExamples = false
    }

    fun onAnswerChanged(answer: String) {
        this.answer = answer
        isWrongAnswer = false
    }

    fun onDoneClick(onRightAnswer: () -> Unit) {
        if (uiState.wordExtra.name.equals(answer.trim(), false)) {
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
    return remember(uiState.queueId) {
        TestingContentRegularState(uiState)
    }
}
