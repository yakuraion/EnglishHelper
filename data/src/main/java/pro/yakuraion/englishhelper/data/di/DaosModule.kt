package pro.yakuraion.englishhelper.data.di

import dagger.Module
import dagger.Provides
import pro.yakuraion.englishhelper.data.database.AppDatabase
import pro.yakuraion.englishhelper.data.database.daos.CompletedWordsDao
import pro.yakuraion.englishhelper.data.database.daos.LearningWordsDao
import pro.yakuraion.englishhelper.data.database.daos.TodayLearningWordsDao
import pro.yakuraion.englishhelper.data.database.daos.WordsDao

@Module
internal class DaosModule {

    @Provides
    fun provideWordsDao(appDatabase: AppDatabase): WordsDao = appDatabase.getWordsDao()

    @Provides
    fun provideLearningWordsDao(appDatabase: AppDatabase): LearningWordsDao = appDatabase.getLearningWordsDao()

    @Provides
    fun provideTodayLearningWordsDao(appDatabase: AppDatabase): TodayLearningWordsDao =
        appDatabase.getTodayLearningWordsDao()

    @Provides
    fun provideCompletedWordsDao(appDatabase: AppDatabase): CompletedWordsDao = appDatabase.getCompletedWordsDao()
}
