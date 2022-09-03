package pro.yakuraion.englishhelper.vocabulary.di

import pro.yakuraion.englishhelper.vocabulary.data.daos.LearningWordsDao

interface VocabularyDependencies {

    fun wordsDao(): LearningWordsDao

    interface Provider {

        fun provideVocabularyDependencies(): VocabularyDependencies
    }
}
