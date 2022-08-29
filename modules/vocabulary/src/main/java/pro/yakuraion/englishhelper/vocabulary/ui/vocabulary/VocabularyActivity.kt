package pro.yakuraion.englishhelper.vocabulary.ui.vocabulary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text

class VocabularyActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Text(text = "hello")
        }
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, VocabularyActivity::class.java)
        }
    }
}
