package pro.yakuraion.englishhelper.vocabulary.di

import pro.yakuraion.englishhelper.common.mvvm.MVVMActivity

internal val MVVMActivity<*>.diComponent: VocabularyComponent
    get() {
        val dependencies = (application as VocabularyDependenciesProvider).provideVocabularyDependencies()
        return VocabularyComponent.create(dependencies)
    }
