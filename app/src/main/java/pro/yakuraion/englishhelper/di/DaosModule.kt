package pro.yakuraion.englishhelper.di

import dagger.Module
import dagger.Provides
import pro.yakuraion.englishhelper.data.AppDatabase
import pro.yakuraion.englishhelper.vocabulary.data.daos.LearningWordsDao

@Module
class DaosModule {

    @Provides
    fun provideWordsDao(appDatabase: AppDatabase): LearningWordsDao = appDatabase.learningWordsDao()
}
