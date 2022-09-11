package pro.yakuraion.englishhelper.vocabulary.di

import pro.yakuraion.englishhelper.common.mvvm.MVVMActivity
import pro.yakuraion.englishhelper.domain.di.InteractorsProviderHolder

internal val MVVMActivity<*>.diComponent: VocabularyComponent
    get() {
        val interactorsProvider = (application as InteractorsProviderHolder).getInteractorsProvider()
        return DaggerVocabularyComponent.builder().interactorsProvider(interactorsProvider).build()
    }
