package pro.yakuraion.englishhelper.vocabulary.ui.testing

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import pro.yakuraion.englishhelper.common.di.viewmodel.InjectingSavedStateViewModelFactory
import pro.yakuraion.englishhelper.common.mvvm.MVVMActivity
import pro.yakuraion.englishhelper.vocabulary.di.diComponent
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
        val day by remember { viewModel.day }
        ScreenContentView(
            learningDay = day,
            onPlayButtonClick = { viewModel.onButtonClick() }
        )
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    private fun ScreenContentView(learningDay: Int, onPlayButtonClick: () -> Unit) {
        Scaffold {
            Column {
                Text(text = learningDay.toString())
                Button(onClick = { onPlayButtonClick.invoke() }) {
                    Text(text = "update")
                }
            }
        }
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, TestingActivity::class.java)
        }
    }
}
