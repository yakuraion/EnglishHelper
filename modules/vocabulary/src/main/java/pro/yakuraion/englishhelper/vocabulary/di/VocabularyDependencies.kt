package pro.yakuraion.englishhelper.vocabulary.di

import android.content.SharedPreferences
import pro.yakuraion.englishhelper.vocabulary.data.daos.LearningWordsDao

interface VocabularyDependencies {

    fun sharedPreferences(): SharedPreferences

    fun wordsDao(): LearningWordsDao
}
