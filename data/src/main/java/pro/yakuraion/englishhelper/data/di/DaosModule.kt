package pro.yakuraion.englishhelper.data.di

import dagger.Module
import dagger.Provides
import pro.yakuraion.englishhelper.data.database.AppDatabase
import pro.yakuraion.englishhelper.data.database.daos.LearningWordsDao
import pro.yakuraion.englishhelper.data.database.daos.TodayLearningWordsDao

@Module
internal class DaosModule {

    @Provides
    fun provideLearningWordsDao(appDatabase: AppDatabase): LearningWordsDao = appDatabase.getLearningWordsDao()

    @Provides
    fun provideTodayLearningWordsDao(appDatabase: AppDatabase): TodayLearningWordsDao =
        appDatabase.getTodayLearningWordsDao()
}
