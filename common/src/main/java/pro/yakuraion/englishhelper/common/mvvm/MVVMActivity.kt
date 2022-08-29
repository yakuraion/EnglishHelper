package pro.yakuraion.englishhelper.common.mvvm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import pro.yakuraion.englishhelper.common.di.viewmodel.InjectingSavedStateViewModelFactory
import kotlin.reflect.KClass

abstract class MVVMActivity<VM : ViewModel>(
    private val viewModelClass: KClass<out ViewModel>
) : ComponentActivity() {

    abstract val abstractViewModelFactory: InjectingSavedStateViewModelFactory

    protected lateinit var viewModel: VM

    abstract fun inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject()
        viewModel = abstractViewModelFactory.create(viewModelClass, this, getDefaultArgs())
    }

    protected open fun getDefaultArgs(): Bundle? = intent.extras
}
