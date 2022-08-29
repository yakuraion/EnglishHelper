package pro.yakuraion.englishhelper.vocabulary.di

import pro.yakuraion.englishhelper.vocabulary.data.daos.WordsDao

interface VocabularyDependencies {

    fun wordsDao(): WordsDao

    interface Provider {

        fun provideVocabularyDependencies(): VocabularyDependencies
    }
}
