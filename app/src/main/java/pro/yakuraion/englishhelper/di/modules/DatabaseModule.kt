package pro.yakuraion.englishhelper.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import pro.yakuraion.englishhelper.data.AppDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        // todo remove allowMainThreadQueries
        // todo remove fallbackToDestructiveMigration
        return Room
            .databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {

        private const val DATABASE_NAME = "database"
    }
}
