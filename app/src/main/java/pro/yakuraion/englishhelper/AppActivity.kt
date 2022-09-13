package pro.yakuraion.englishhelper

import android.os.Bundle
import androidx.activity.ComponentActivity
import pro.yakuraion.englishhelper.vocabulary.ui.testing.TestingActivity

class AppActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(TestingActivity.createIntent(this))
    }
}
