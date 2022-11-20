package pro.yakuraion.englishhelper.vocabulary.ui.testing

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pro.yakuraion.englishhelper.common.ui.compose.PrimaryOutlineButton
import pro.yakuraion.englishhelper.common.ui.theme.AppTheme
import pro.yakuraion.englishhelper.vocabulary.R

@Composable
fun TestingScreen() {
    TestingLayout {
        TestingWordWithAudio()
    }
}

@Composable
fun TestingLayout(content: @Composable () -> Unit) {
    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            PrimaryOutlineButton(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 16.dp, end = 16.dp)
            ) {
                Icon(imageVector = Icons.Filled.HelpOutline, contentDescription = null)
                Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                Text(text = stringResource(id = R.string.vocabulary_testing_help_button))
            }
            content()
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TestingScreenWithAudioPreview() {
    AppTheme {
        TestingLayout {
            TestingWordWithAudio()
        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun TestingScreenSimplePreview() {
    AppTheme {
        TestingLayout {
            TestingWordSimple()
        }
    }
}
