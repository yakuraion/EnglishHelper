package pro.yakuraion.englishhelper.vocabulary.di

import pro.yakuraion.englishhelper.commonui.mvvm.MVVMActivity
import pro.yakuraion.englishhelper.domain.di.UseCasesProviderHolder

internal val MVVMActivity<*>.diComponent: VocabularyComponent
    get() {
        val useCasesProvider = (application as UseCasesProviderHolder).getUseCasesProvider()
        return DaggerVocabularyComponent.builder().useCasesProvider(useCasesProvider).build()
    }
