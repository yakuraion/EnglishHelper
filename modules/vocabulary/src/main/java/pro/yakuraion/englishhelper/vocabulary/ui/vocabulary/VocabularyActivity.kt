package pro.yakuraion.englishhelper.vocabulary.ui.vocabulary

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import pro.yakuraion.englishhelper.common.di.viewmodel.InjectingSavedStateViewModelFactory
import pro.yakuraion.englishhelper.common.mvvm.MVVMActivity
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
            Text(text = viewModel.message)
        }
    }

    companion object {

        fun createIntent(context: Context): Intent {
            return Intent(context, VocabularyActivity::class.java)
        }
    }
}
