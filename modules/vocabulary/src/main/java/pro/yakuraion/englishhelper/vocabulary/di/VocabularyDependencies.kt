package pro.yakuraion.englishhelper.vocabulary.di

import android.content.SharedPreferences
import pro.yakuraion.englishhelper.common.coroutines.Dispatchers
import pro.yakuraion.englishhelper.vocabulary.data.daos.LearningWordsDao

interface VocabularyDependencies {

    fun dispatchers(): Dispatchers

    fun sharedPreferences(): SharedPreferences

    fun wordsDao(): LearningWordsDao
}
