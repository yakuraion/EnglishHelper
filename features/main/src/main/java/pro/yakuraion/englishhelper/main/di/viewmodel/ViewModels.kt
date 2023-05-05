package pro.yakuraion.englishhelper.main.di.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import pro.yakuraion.englishhelper.commonui.di.viewmodel.daggerViewModel
import pro.yakuraion.englishhelper.main.di.DaggerMainComponent

@Composable
internal inline fun <reified VM : ViewModel> featureDaggerViewModel(): VM =
    daggerViewModel { useCasesProvider ->
        DaggerMainComponent.builder().useCasesProvider(useCasesProvider).build()
    }
