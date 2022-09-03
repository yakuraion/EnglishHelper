package pro.yakuraion.englishhelper

import android.app.Application
import androidx.room.Room
import pro.yakuraion.englishhelper.data.AppDatabase
import pro.yakuraion.englishhelper.di.AppComponent
import pro.yakuraion.englishhelper.di.DaggerAppComponent
import pro.yakuraion.englishhelper.vocabulary.di.VocabularyDependencies

class App : Application(), VocabularyDependencies.Provider {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        initDI()
    }

    override fun provideVocabularyDependencies(): VocabularyDependencies {
        return appComponent
    }

    private fun createAppDatabase(): AppDatabase {
        // todo remove allowMainThreadQueries
        // todo remove fallbackToDestructiveMigration
        return Room
            .databaseBuilder(applicationContext, AppDatabase::class.java, DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    private fun initDI() {
        appComponent = DaggerAppComponent.builder()
            .appDatabase(createAppDatabase())
            .build()
    }

    companion object {

        private const val DATABASE_NAME = "database"
    }
}
