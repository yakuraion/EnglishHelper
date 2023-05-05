package pro.yakuraion.englishhelper.vocabulary.di.viewmodel

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import pro.yakuraion.englishhelper.commonui.di.viewmodel.daggerViewModel
import pro.yakuraion.englishhelper.vocabulary.di.DaggerVocabularyComponent

@Composable
internal inline fun <reified VM : ViewModel> featureDaggerViewModel(): VM =
    daggerViewModel { useCasesProvider ->
        DaggerVocabularyComponent.builder().useCasesProvider(useCasesProvider).build()
    }
