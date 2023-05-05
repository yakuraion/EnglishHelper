package pro.yakuraion.englishhelper.startup.di.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import pro.yakuraion.englishhelper.commonui.di.viewmodel.daggerViewModel
import pro.yakuraion.englishhelper.startup.di.DaggerStartUpComponent

@Composable
internal inline fun <reified VM : ViewModel> featureDaggerViewModel(): VM =
    daggerViewModel { useCasesProvider ->
        DaggerStartUpComponent.builder().useCasesProvider(useCasesProvider).build()
    }
