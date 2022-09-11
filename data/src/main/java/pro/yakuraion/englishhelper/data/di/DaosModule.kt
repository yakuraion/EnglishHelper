package pro.yakuraion.englishhelper.data.di

import dagger.Module
import dagger.Provides
import pro.yakuraion.englishhelper.data.database.AppDatabase
import pro.yakuraion.englishhelper.data.database.daos.LearningWordsDao

@Module
internal class DaosModule {

    @Provides
    fun provideWordsDao(appDatabase: AppDatabase): LearningWordsDao = appDatabase.learningWordsDao()
}
