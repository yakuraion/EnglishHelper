package pro.yakuraion.englishhelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import pro.yakuraion.englishhelper.vocabulary.ui.vocabulary.VocabularyActivity

class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(VocabularyActivity.createIntent(this))
    }
}
