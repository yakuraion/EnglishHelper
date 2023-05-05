package pro.yakuraion.englishhelper.commonui.di.viewmodel

import androidx.lifecycle.ViewModel

interface FeatureComponent {

    fun getViewModelFactories(): Map<Class<out ViewModel>, AssistedFactoryProvider>
}
