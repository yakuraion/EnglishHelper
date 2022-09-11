package pro.yakuraion.englishhelper.vocabulary.ui.testing

import pro.yakuraion.englishhelper.common.di.viewmodel.InjectingSavedStateViewModelFactory
import pro.yakuraion.englishhelper.common.mvvm.MVVMActivity
import pro.yakuraion.englishhelper.vocabulary.di.diComponent

class TestingActivity : MVVMActivity<TestingViewModel>(TestingViewModel::class) {

    override lateinit var abstractViewModelFactory: InjectingSavedStateViewModelFactory

    override fun inject() {
        diComponent.inject(this)
    }
}
